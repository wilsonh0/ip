import java.util.Scanner;

public class Oogway {
    private static final String name = "Master Oogway";

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

            // Echo
            horizontalLine();
            System.out.println(msg);
            horizontalLine();
        }

        // Exit
        exitMessage();
    }
}