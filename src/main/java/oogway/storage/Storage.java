package oogway.storage;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


import oogway.tasks.Deadline;
import oogway.tasks.Event;
import oogway.tasks.Task;
import oogway.tasks.ToDo;

/**
 * Handles the loading and saving of tasks to a file for persistent storage.
 */
public class Storage {

    private final Path filePath;

    /**
     * Constructs a {@code Storage} instance with the default file path.
     *
     * @throws IOException If an error occurs while creating the file or directories.
     */
    public Storage() throws IOException {
        this(null);
    }

    /**
     * Constructs a {@code Storage} instance with a custom file path.
     * If no custom path is provided, a default save location is used.
     *
     * @param customFilePath The custom file path for saving tasks.
     * @throws IOException If an error occurs while creating the file or directories.
     */
    public Storage(String customFilePath) throws IOException {
        // Default save location
        if (customFilePath == null || customFilePath.isEmpty()) {
            Path saveDirectory = Paths.get("").toAbsolutePath().resolve("data");

            // Create save directory if it does not exist
            if (!Files.exists(saveDirectory)) {
                Files.createDirectory(saveDirectory);
            }

            this.filePath = saveDirectory.resolve("save.txt");
        } else {
            this.filePath = Paths.get(customFilePath);

            // Create parent directories if they do not exist
            if (!Files.exists(this.filePath.getParent())) {
                Files.createDirectories(this.filePath.getParent());
            }
            Files.createFile(this.filePath);
        }

        // Ensure the save file exists
        if (!Files.exists(this.filePath)) {
            Files.createFile(this.filePath);
        }
    }

    /**
     * Returns the file path where tasks are stored.
     *
     * @return The {@code Path} representing the storage file.
     */
    public Path getFilePath() {
        return filePath;
    }

    /**
     * Saves the current tasks to the file.
     *
     * @param taskList The list of tasks to be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void saveToFile(TaskList taskList) throws IOException {
        List<String> taskStrings = new ArrayList<>();
        for (Task task : taskList.getTasks()) {
            taskStrings.add(task.toSaveFormat());
        }

        Files.write(filePath, taskStrings);
    }

    /**
     * Loads tasks from the file into a {@code TaskList}.
     *
     * @return A {@code TaskList} containing the tasks from the file.
     * @throws IOException If an error occurs while reading the file or the file format is invalid.
     */
    public TaskList loadFromFile() throws IOException {
        TaskList taskList = new TaskList();

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
                case "T" -> taskList.addTask(new ToDo(description, isDone));
                case "D" -> {
                    if (arr.length < 4) {
                        throw new IOException("Invalid deadline task found in save file.");
                    }
                    taskList.addTask(new Deadline(description, isDone, arr[3]));
                }
                case "E" -> {
                    if (arr.length < 5) {
                        throw new IOException("Invalid event task found in save file.");
                    }
                    taskList.addTask(new Event(description, isDone, arr[3], arr[4]));
                }
                default -> throw new IOException("Invalid task type found in save file.");
            }
        }

        return taskList;
    }
}
