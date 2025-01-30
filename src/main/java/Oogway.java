import java.util.Scanner;

public class Oogway {
    private static final String NAME = "Master Oogway";
    private static Storage storage = new Storage();
    private static final FileHandler fileHandler = new FileHandler();
    private static final String LINE = "____________________________________________________________";

    /**
     * Prints a message wrapped with horizontal lines.
     *
     * @param message The message to be wrapped and printed.
     */
    private static void wrapMessage(String message) {
        System.out.printf("%s\n%s\n%s%n", Oogway.LINE, message, Oogway.LINE);
    }

    /**
     * Prints the introduction message when the program starts.
     */
    private static void introductionMessage() {
        String message = "Greetings, young one. I am " + NAME + ".\n Enlighten me... What do you seek?";

        wrapMessage(message);
    }

    /**
     * Prints the farewell message when the program exits.
     */
    private static void exitMessage() {
        String message = "Farewell, young one. I hope to guide you again someday.\n"
                + "And never forget... Yesterday is history, Tomorrow is a mystery, but Today is a gift. "
                + "That is why it is called the present.";

        wrapMessage(message);
    }

    /**
     * Adds a task to the task list based on the provided task type and user input.
     *
     * @param taskType The type of task to add ("todo", "deadline", or "event").
     * @param userInput The full user input containing the task description.
     */
    private static void addTask(String taskType, String userInput) {
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
            fileHandler.saveToFile(storage);

            String message = "Alright, I have noted down the task:\n  "
                    + task + "\nNow you have " + storage.getTaskCount() + " tasks in the list.";
            wrapMessage(message);

        } catch (OogwayException e) {
            wrapMessage(e.getMessage());
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
    private static Task getTask(String taskType, String[] arr) throws OogwayException {
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
    private static void listTasks() {
        if (storage.isEmpty()) {
            wrapMessage("Ah, it seems you have no tasks yet, young one.");
        } else {
            StringBuilder message = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < storage.getTaskCount(); i++) {
                message.append(((i + 1))).append(".").append(storage.getTask(i)).append("\n");
            }
            wrapMessage(message.toString());
        }
    }

    private static int extractTaskIndex(String userInput) throws OogwayException {
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
    private static void markTask(String userInput, boolean done) {
        try {
            int index = extractTaskIndex(userInput);

            storage.getTask(index).setDone(done);
            fileHandler.saveToFile(storage);

            String message;
            if (done) {
                message = "Ah, young one, it brings me great joy to see progress. I have marked this task as complete for you:\n"
                        + "  " + storage.getTask(index);
            } else {
                message = "Patience, young one. I have returned this task to its unfinished state:\n"
                        + "  " + storage.getTask(index);
            }

            wrapMessage(message);

        } catch (OogwayException e) {
            wrapMessage(e.getMessage());
        }
    }


    /**
     * Deletes a task from the task list based on the user input.
     *
     * @param userInput The input string containing the command and the task index (e.g., "delete 2").
     */
    private static void deleteTask(String userInput) {
        try {
            int index = extractTaskIndex(userInput);

            Task task = storage.deleteTask(index);
            fileHandler.saveToFile(storage);

            String message = "Alright, I have removed the task:\n  "
                    + task + "\nNow you have " + storage.getTaskCount() + " tasks in the list.";

            wrapMessage(message);

        } catch (OogwayException e) {
            wrapMessage(e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Load from save file
        storage = fileHandler.loadFromFile();

        // Introduction
        introductionMessage();

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
        exitMessage();
    }
}