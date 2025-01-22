import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Oogway {
    private static final String name = "Master Oogway";
    private static final ArrayList<Task> tasks = new ArrayList<>();

    private static void horizontalLine() {
        System.out.println("____________________________________________________________");
    }

    public static void introductionMessage() {
        horizontalLine();
        System.out.println("Greetings, young one. I am " + name + ".\nEnlighten me... What do you seek?");
        horizontalLine();
    }

    public static void exitMessage() {
        horizontalLine();
        System.out.println("Farewell, young one. I hope to guide you again someday.");
        System.out.println("And never forget... Yesterday is history, Tomorrow is a mystery, but Today is a gift. That is why it is called the present.");
        horizontalLine();
    }

    public static void addTask(String msg) {
        tasks.add(new Task(msg));

        horizontalLine();
        System.out.println("Added: " + msg);
        horizontalLine();
    }

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

    private static void handleMark(String msg) {
        String[] arr = msg.split(" ");
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

    private static void handleUnmark(String msg) {
        String[] arr = msg.split(" ");
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
            String msg = sc.nextLine();

            if (msg.equals("bye")) {
                break;
            } else if (msg.equals("list")) {
                listTasks();
            } else if (msg.startsWith("mark ")) {
                handleMark(msg);
            } else if (msg.startsWith("unmark ")) {
                handleUnmark(msg);
            } else {
                addTask(msg);
            }
        }

        // Exit
        exitMessage();
    }
}