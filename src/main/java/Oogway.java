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
        if (userInput == null || userInput.trim().isEmpty()) {
            wrapMessage("Ah, young one, tasks cannot be empty.");
            return;
        }

        Task task = null;
        String taskDescription = userInput.split(" ", 2)[1];

        switch (taskType) {
            case "todo" -> task = new ToDo(taskDescription);
            case "deadline" -> {
                String[] splitBySlash = taskDescription.split(" /by ", 2);
                task = new Deadline(splitBySlash[0], splitBySlash[1]);

            }
            case "event" -> {
                String[] splitBySlash = taskDescription.split(" /from | /to ", 3);
                task = new Event(splitBySlash[0], splitBySlash[1], splitBySlash[2]);
            }
        }

        if (task != null) {
            tasks.add(task);

            String message = "Alright, I have noted down the task:\n  "
                    + task + "\nNow you have " + tasks.size() + " tasks in the list.";

            wrapMessage(message);
        }
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
        String[] arr = userInput.split(" ");
        String message;

        try {
            int index = Integer.parseInt(arr[1]) - 1; // Convert to zero-based index
            tasks.get(index).setDone();
            message = "Ah, young one, it brings me great joy to see progress. I have marked this task as complete for you:\n";
            message += "  " + tasks.get(index);
        } catch (NumberFormatException e) {
            message = "Tis not a valid number...";
        } catch (IndexOutOfBoundsException e) {
            message = "Ah, young one, that task does not exist.";
        }

        wrapMessage(message);
    }

    /**
     * Marks a task as undone based on the user's input.
     * @param userInput the input string containing the task index
     */
    private static void handleUnmark(String userInput) {
        String[] arr = userInput.split(" ");
        String message;

        try {
            int index = Integer.parseInt(arr[1]) - 1; // Convert to zero-based index
            tasks.get(index).setUndone();
            message = "Patience, young one. I have returned this task to its unfinished state:\n";
            message += "  " + tasks.get(index);
        } catch (NumberFormatException e) {
            message = "Tis not a valid number...";
        } catch (IndexOutOfBoundsException e) {
            message = "Ah, young one, that task does not exist.";
        }

        wrapMessage(message);
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