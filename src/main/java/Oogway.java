public class Oogway {
    private static final String name = "Master Oogway";

    private static void horizontalLine() {
        System.out.println("____________________________________________________________");
    }

    public static void introductionMessage() {
        System.out.println("Greetings, young one. I am " + name + ".\nEnlighten me... What do you seek?");
    }

    public static void exitMessage() {
        System.out.println("Farewell, young one. I hope to guide you again someday.");
        System.out.println("And never forget... Yesterday is history, Tomorrow is a mystery, but Today is a gift. That is why it is called the present.");
    }

    public static void main(String[] args) {
        // Introduction
        horizontalLine();
        introductionMessage();
        horizontalLine();

        // Exit
        exitMessage();
        horizontalLine();
    }
}