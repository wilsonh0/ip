import java.io.IOException;
import java.util.Scanner;

import oogway.exception.OogwayException;
import oogway.storage.FileHandler;
import oogway.storage.Storage;
import oogway.tasks.Deadline;
import oogway.tasks.Event;
import oogway.tasks.Task;
import oogway.tasks.ToDo;
import oogway.ui.Ui;


public class Oogway {
    private FileHandler fileHandler;
    private Storage storage;
    private Ui ui;

    public Oogway(String filePath) {
        ui = new Ui();

        try {
            fileHandler = new FileHandler(filePath);
            storage = fileHandler.loadFromFile();
        } catch (IOException e) {
            ui.loadErrorMessage(e.getMessage());
            storage = new Storage();
        }


    }

    public void run() {
        ui.introductionMessage();

        // Read user input
        Scanner sc = new Scanner(System.in);

        label:
        while (sc.hasNextLine()) {
            String userInput = sc.nextLine();
            String firstWord = userInput.split(" ")[0];

            switch (firstWord) {
                case "bye":
                    break label;
                case "list":
                    listTasks();
                    break;
                case "mark":
                    markTask(userInput, true);
                    break;
                case "unmark":
                    markTask(userInput, false);
                    break;
                case "delete":
                    deleteTask(userInput);
                    break;
                default:
                    addTask(firstWord, userInput);
                    break;
            }
        }

        // Exit
        ui.exitMessage();

    }

    public static void main(String[] args) {
        new Oogway(null).run();
    }

    /**
     * Adds a task to the task list based on the provided task type and user input.
     *
     * @param taskType The type of task to add ("todo", "deadline", or "event").
     * @param userInput The full user input containing the task description.
     */
    private void addTask(String taskType, String userInput) {
        // Checks if string is empty
        try {
            if (userInput == null || userInput.trim().isEmpty()) {
                throw new OogwayException("Ah, young one, tasks cannot be empty.");
            }

            String[] arr = userInput.split(" ", 2);

            if (arr.length < 2) {
                throw new OogwayException("Ah, young one, you seem to be missing a task description.");
            }

            Task task = getTask(taskType, arr);

            storage.addTask(task);

            try {
                fileHandler.saveToFile(storage);
            } catch (IOException e) {
                ui.saveErrorMessage(e.getMessage());
            }

            String message = "Alright, I have noted down the task:\n  "
                    + task + "\nNow you have " + storage.getTaskCount() + " tasks in the list.";
            ui.wrapMessage(message);

        } catch (OogwayException e) {
            ui.wrapMessage(e.getMessage());
        }
    }

    /**
     * Creates a Task object based on the specified task type and input array.
     *
     * @param taskType The type of task to create ("todo", "deadline", or "event").
     * @param arr An array of strings where the second element contains the task description.
     * @return The created Task object.
     * @throws OogwayException If the task type is invalid or the input does not meet the required format for the task type.
     *                         For example:
     *                         - A "deadline" task requires "/by" followed by a due date.
     *                         - An "event" task requires "/from" and "/to" timings.
     */
    private Task getTask(String taskType, String[] arr) throws OogwayException {
        Task task;
        String taskDescription = arr[1];

        switch (taskType) {
            case "todo" -> task = new ToDo(taskDescription, false);
            case "deadline" -> {
                String[] splitBySlash = taskDescription.split(" /by ", 2);

                if (splitBySlash.length < 2) {
                    throw new OogwayException("Ah, young one, a deadline must include /by followed by the due date.");
                }

                try {
                    task = new Deadline(splitBySlash[0], false, splitBySlash[1]);
                } catch (Exception e) {
                    throw new OogwayException(("Invalid date. Follow this format: [yyyy-MM-dd HHmm]"));
                }

            }
            case "event" -> {
                String[] splitBySlash = taskDescription.split(" /from | /to ", 3);

                if (splitBySlash.length < 3) {
                    throw new OogwayException("Ah, young one, an event must include /from and /to timings.");
                }
                try {
                    task = new Event(splitBySlash[0], false, splitBySlash[1], splitBySlash[2]);
                } catch (Exception e) {
                    throw new OogwayException(("Invalid date. Follow this format: /from [yyyy-MM-dd HHmm] /to [HHmm]"));
                }

            }
            default -> throw new OogwayException("Ah, young one, I do not understand that command.");
        }
        return task;
    }


    /**
     * Lists all tasks in the task list. If the list is empty, displays an appropriate message.
     */
    private void listTasks() {
        if (storage.isEmpty()) {
            ui.wrapMessage("Ah, it seems you have no tasks yet, young one.");
        } else {
            StringBuilder message = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < storage.getTaskCount(); i++) {
                message.append(((i + 1))).append(".").append(storage.getTask(i)).append("\n");
            }
            ui.wrapMessage(message.toString());
        }
    }

    private int extractTaskIndex(String userInput) throws OogwayException {
        String[] arr = userInput.split(" ");
        if (arr.length < 2) {
            throw new OogwayException("Ah, young one, you must specify a task number.");
        }

        int index;
        try {
            index = Integer.parseInt(arr[1]) - 1; // Convert to zero-based index
        } catch (NumberFormatException e) {
            throw new OogwayException("Ah, young one, that is not a valid number.");
        }

        if (index < 0 || index >= storage.getTaskCount()) {
            throw new OogwayException("Ah, young one, that task does not exist.");
        }

        return index;
    }

    /**
     * Marks a task as done or undone based on the user input.
     *
     * @param userInput The command input containing the task index (e.g., "mark 2").
     */
    private void markTask(String userInput, boolean done) {
        try {
            int index = extractTaskIndex(userInput);

            storage.getTask(index).setDone(done);

            try {
                fileHandler.saveToFile(storage);
            } catch (IOException e) {
                ui.saveErrorMessage(e.getMessage());
            }

            String message;
            if (done) {
                message = "Ah, young one, it brings me great joy to see progress. I have marked this task as complete for you:\n"
                        + "  " + storage.getTask(index);
            } else {
                message = "Patience, young one. I have returned this task to its unfinished state:\n"
                        + "  " + storage.getTask(index);
            }

            ui.wrapMessage(message);

        } catch (OogwayException e) {
            ui.wrapMessage(e.getMessage());
        }
    }


    /**
     * Deletes a task from the task list based on the user input.
     *
     * @param userInput The input string containing the command and the task index (e.g., "delete 2").
     */
    private void deleteTask(String userInput) {
        try {
            int index = extractTaskIndex(userInput);

            Task task = storage.deleteTask(index);

            try {
                fileHandler.saveToFile(storage);
            } catch (IOException e) {
                ui.saveErrorMessage(e.getMessage());
            }

            String message = "Alright, I have removed the task:\n  "
                    + task + "\nNow you have " + storage.getTaskCount() + " tasks in the list.";

            ui.wrapMessage(message);

        } catch (OogwayException e) {
            ui.wrapMessage(e.getMessage());
        }
    }
}