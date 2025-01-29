import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private final Path filePath;

    public FileHandler() {
        Path saveDirectory = Paths.get("").toAbsolutePath().resolve("data");

        // Check if save directory exists, if not create it
        if (!Files.exists(saveDirectory)) {
            try {
                Files.createDirectory(saveDirectory);
                System.out.println("Save directory created.");
            } catch (Exception e) {
                System.out.println("Error creating save directory.");
            }
        }

        // Check if save file exists, if not create it
        if (!Files.exists(saveDirectory.resolve("save.txt"))) {
            try {
                Files.createFile(saveDirectory.resolve("save.txt"));
                System.out.println("Save file created.");
            } catch (Exception e) {
                System.out.println("Error creating save file.");
            }
        }

        this.filePath = saveDirectory.resolve("save.txt");
    }

    public Path getFilePath() {
        return filePath;
    }

    public void saveToFile(Storage storage) {
        try {
            List<String> taskStrings = new ArrayList<>();
            for (Task task : storage.getTasks()) {
                taskStrings.add(task.toSaveFormat());
            }

            Files.write(filePath, taskStrings);
            System.out.println("Tasks saved to file.");

        } catch (Exception e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    public Storage loadFromFile() {
        Storage storage = new Storage();

        if (!Files.exists(filePath)) {
            System.out.println("Save file not found.");
            return storage;
        }

        try {
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
                            System.out.println("Invalid deadline task found in save file.");
                            break;
                        }
                        storage.addTask(new Deadline(description, isDone, arr[3]));
                    }
                    case "E" -> {
                        if (arr.length < 5) {
                            System.out.println("Invalid event task found in save file.");
                            break;
                        }
                        storage.addTask(new Event(description, isDone, arr[3], arr[4]));
                    }
                    default -> System.out.println("Invalid task type found in save file.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }

        return storage;
    }
}
