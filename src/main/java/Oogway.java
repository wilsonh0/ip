import java.util.ArrayList;
import java.util.Scanner;

public class Oogway {
    private static final String NAME = "Master Oogway";
    private static final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Prints a horizontal line.
     */
    private static void horizontalLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints the introduction message.
     */
    private static void introductionMessage() {
        horizontalLine();
        System.out.println("Greetings, young one. I am " + NAME + ".");
        System.out.println("Enlighten me... What do you seek?");
        horizontalLine();
    }

    /**
     * Prints the exit message.
     */
    private static void exitMessage() {
        horizontalLine();
        System.out.println("Farewell, young one. I hope to guide you again someday.");
        System.out.println("And never forget... Yesterday is history, Tomorrow is a mystery, but Today is a gift. That is why it is called the present.");
        horizontalLine();
    }

    /**
     * Adds a task to the task list.
     * @param description the input string containing the task description
     */
    private static void addTask(String description) {
        // Checks if string is empty
        if (description == null || description.trim().isEmpty()) {
            horizontalLine();
            System.out.println("Ah, young one, tasks cannot be empty.");
            horizontalLine();
            return;
        }
        tasks.add(new Task(description));

        horizontalLine();
        System.out.println("Added: " + description);
        horizontalLine();
    }

    /**
     * Lists all tasks in the task list.
     */
    private static void listTasks() {
        horizontalLine();
        if (tasks.isEmpty()) {
            System.out.println("Ah, it seems you have no tasks yet, young one.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
        horizontalLine();
    }

    /**
     * Marks a task as done based on the user's input.
     * @param userInput the input string containing the task index
     */
    private static void handleMark(String userInput) {
        String[] arr = userInput.split(" ");
        horizontalLine();
        try {
            int index = Integer.parseInt(arr[1]) - 1; // Convert to zero-based index
            tasks.get(index).setDone();
            System.out.println("Ah, young one, it brings me great joy to see progress. I have marked this task as complete for you:");
            System.out.println("  " + tasks.get(index));
        } catch (NumberFormatException e) {
            System.out.println("Tis not a valid number...");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Ah, young one, that task does not exist.");
        }
        horizontalLine();
    }

    /**
     * Marks a task as undone based on the user's input.
     * @param userInput the input string containing the task index
     */
    private static void handleUnmark(String userInput) {
        String[] arr = userInput.split(" ");
        horizontalLine();
        try {
            int index = Integer.parseInt(arr[1]) - 1; // Convert to zero-based index
            tasks.get(index).setUndone();
            System.out.println("Patience, young one. I have returned this task to its unfinished state:");
            System.out.println("  " + tasks.get(index));
        } catch (NumberFormatException e) {
            System.out.println("Tis not a valid number...");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Ah, young one, that task does not exist.");
        }
        horizontalLine();
    }

    public static void main(String[] args) {
        // Introduction
        introductionMessage();

        // Read user input
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String userInput = sc.nextLine();

            if (userInput.equals("bye")) {
                break;
            } else if (userInput.equals("list")) {
                listTasks();
            } else if (userInput.startsWith("mark ")) {
                handleMark(userInput);
            } else if (userInput.startsWith("unmark ")) {
                handleUnmark(userInput);
            } else {
                addTask(userInput);
            }
        }

        // Exit
        exitMessage();
    }
}