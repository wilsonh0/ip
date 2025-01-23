import java.util.ArrayList;
import java.util.Scanner;

public class Oogway {
    private static final String NAME = "Master Oogway";
    private static final ArrayList<Task> tasks = new ArrayList<>();

    private static final String LINE = "____________________________________________________________";

    /**
     * Wraps message with horizontal lines.
     */
    private static void wrapMessage(String message) {
        System.out.printf("%s\n%s\n%s%n", Oogway.LINE, message, Oogway.LINE);
    }

    /**
     * Prints the introduction message.
     */
    private static void introductionMessage() {
        String message = "Greetings, young one. I am " + NAME + ".\n Enlighten me... What do you seek?";

        wrapMessage(message);
    }

    /**
     * Prints the exit message.
     */
    private static void exitMessage() {
        String message = "Farewell, young one. I hope to guide you again someday.\n"
                + "And never forget... Yesterday is history, Tomorrow is a mystery, but Today is a gift. "
                + "That is why it is called the present.";

        wrapMessage(message);
    }

    /**
     * Adds a task to the task list.
     * @param userInput the input string containing the task description
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

            tasks.add(task);
            String message = "Alright, I have noted down the task:\n  "
                    + task + "\nNow you have " + tasks.size() + " tasks in the list.";
            wrapMessage(message);

        } catch (OogwayException e) {
            wrapMessage(e.getMessage());
        }
    }

    private static Task getTask(String taskType, String[] arr) throws OogwayException {
        Task task;
        String taskDescription = arr[1];

        switch (taskType) {
            case "todo" -> task = new ToDo(taskDescription);
            case "deadline" -> {
                String[] splitBySlash = taskDescription.split(" /by ", 2);

                if (splitBySlash.length < 2) {
                    throw new OogwayException("Ah, young one, a deadline must include /by followed by the due date.");
                }
                task = new Deadline(splitBySlash[0], splitBySlash[1]);

            }
            case "event" -> {
                String[] splitBySlash = taskDescription.split(" /from | /to ", 3);

                if (splitBySlash.length < 3) {
                    throw new OogwayException("Ah, young one, an event must include /from and /to timings.");
                }
                task = new Event(splitBySlash[0], splitBySlash[1], splitBySlash[2]);
            }
            default -> throw new OogwayException("Ah, young one, I do not understand that command.");
        }
        return task;
    }

    /**
     * Lists all tasks in the task list.
     */
    private static void listTasks() {
        if (tasks.isEmpty()) {
            wrapMessage("Ah, it seems you have no tasks yet, young one.");
        } else {
            StringBuilder message = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                message.append(((i + 1))).append(".").append(tasks.get(i)).append("\n");
            }
            wrapMessage(String.valueOf(message.toString()));
        }
    }

    /**
     * Marks a task as done based on the user's input.
     * @param userInput the input string containing the task index
     */
    private static void handleMark(String userInput) {
        try {
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

            if (index < 0 || index >= tasks.size()) {
                throw new OogwayException("Ah, young one, that task does not exist.");
            }

            tasks.get(index).setDone();
            String message = "Ah, young one, it brings me great joy to see progress. I have marked this task as complete for you:\n"
                    + "  " + tasks.get(index);

            wrapMessage(message);

        } catch (OogwayException e) {
            wrapMessage(e.getMessage());
        }
    }

    /**
     * Marks a task as undone based on the user's input.
     * @param userInput the input string containing the task index
     */
    private static void handleUnmark(String userInput) {
        try {
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

            if (index < 0 || index >= tasks.size()) {
                throw new OogwayException("Ah, young one, that task does not exist.");
            }

            tasks.get(index).setUndone();
            String message = "Patience, young one. I have returned this task to its unfinished state:\n"
                    + "  " + tasks.get(index);

            wrapMessage(message);

        } catch (OogwayException e) {
            wrapMessage(e.getMessage());
        }
    }

    public static void main(String[] args) {
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
                    handleMark(userInput);
                    break;
                case "unmark":
                    handleUnmark(userInput);
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