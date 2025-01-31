package oogway.storage;

import oogway.tasks.Deadline;
import oogway.tasks.Event;
import oogway.tasks.Task;
import oogway.tasks.ToDo;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private final Path filePath;

    public FileHandler() throws IOException {
        this(null);
    }

    public FileHandler(String customFilePath) throws IOException {
        // Default save location
        if (customFilePath == null || customFilePath.isEmpty()) {
            Path saveDirectory = Paths.get("").toAbsolutePath().resolve("data");

            // Check if save directory exists, if not create it
            if (!Files.exists(saveDirectory)) {
                Files.createDirectory(saveDirectory);
            }

            this.filePath = saveDirectory.resolve("save.txt");
        } else {
            this.filePath = Paths.get(customFilePath);

            // Check if parent directory exists, if not create it
            if (!Files.exists(this.filePath.getParent())) {
                Files.createDirectories(this.filePath.getParent());
            }
            Files.createFile(this.filePath);
        }

        if (!Files.exists(this.filePath)) {
            Files.createFile(this.filePath);
        }
    }

    public Path getFilePath() {
        return filePath;
    }

    public void saveToFile(Storage storage) throws IOException {
        List<String> taskStrings = new ArrayList<>();
        for (Task task : storage.getTasks()) {
            taskStrings.add(task.toSaveFormat());
        }

        Files.write(filePath, taskStrings);
    }

    public Storage loadFromFile() throws IOException {
        Storage storage = new Storage();

        if (!Files.exists(filePath)) {
            throw new IOException("Save file not found.");
        }

        List<String> lines = Files.readAllLines(filePath);

        for (String line : lines) {
            String[] arr = line.split(" \\| ");

            String taskType = arr[0];
            boolean isDone = arr[1].equals("1");
            String description = arr[2];

            switch (taskType) {
                case "T" -> storage.addTask(new ToDo(description, isDone));
                case "D" -> {
                    if (arr.length < 4) {
                        throw new IOException("Invalid deadline task found in save file.");
                    }
                    storage.addTask(new Deadline(description, isDone, arr[3]));
                }
                case "E" -> {
                    if (arr.length < 5) {
                        throw new IOException("Invalid event task found in save file.");
                    }
                    storage.addTask(new Event(description, isDone, arr[3], arr[4]));
                }
                default -> throw new IOException("Invalid task type found in save file.");
            }
        }

        return storage;
    }
}
