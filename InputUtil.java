import java.util.Scanner;

/** Small helper for safe integer input from the console. */
public class InputUtil {
    /** When true, typed input is echoed (used to generate readable sample output from piped input). */
    public static boolean echo = false;

    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            if (echo) System.out.println(line);
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }
}
