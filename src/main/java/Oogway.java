import java.util.Scanner;
import java.util.stream.IntStream;

public class Oogway {
    private static final String name = "Master Oogway";
    private static String[] store = new String[100];

    private static int counter = 0;

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

    public static void addToStore(String msg) {
        store[counter++] = msg;
        horizontalLine();
        System.out.println("Added: " + msg);
        horizontalLine();
    }

    private static void listFromStore() {
        if (counter == 0) {
            System.out.println("Ah, it seems you have no tasks yet, young one.");
        } else {
            IntStream.range(0, counter) // Generate indices from 0 to counter - 1
                    .mapToObj(index -> (index + 1) + ". " + store[index]) // Format each item with its index
                    .forEach(System.out::println); // Print each formatted item
        }
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
            }

            if (msg.equals("list")) {
                listFromStore();
                continue;
            }

            addToStore(msg);
        }

        // Exit
        exitMessage();
    }
}