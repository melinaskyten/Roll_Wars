import java.io.*;
import java.util.Scanner;

public class Feedback {

    private static Feedback instance;
    private final String FILENAME = "feedback.txt";

//     Singleton
//     Hidden constructor
//     Only way to create an instance of Feedback is through
//     the static method getInstance()

    private Feedback() {}

    public static Feedback getInstance() {
        if (instance == null) {
            instance = new Feedback();
        }
        return instance;
    }

    public void getFeedback() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input feedback or report a bug (anonymous):");
        String feedback = scanner.nextLine();
        System.out.println("Your feedback is much appreciated, thank you!");
        saveFeedback(feedback);
    }

    private void saveFeedback(String feedback) {
        try (FileWriter writer = new FileWriter(FILENAME, true)) {
            writer.write(feedback + "\n");
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

}
