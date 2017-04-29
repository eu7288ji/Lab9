import java.util.Scanner;

public class Input {

    private static Scanner scanner = new Scanner(System.in);

    public static int getPositiveIntInput (String question) {

        if (question != null) {

            System.out.println(question);
        }

        while (true) {

            try {

                String stringInput = scanner.nextLine();
                int intInput = Integer.parseInt(stringInput);
                if (intInput >= 0) {

                    return intInput;
                } else {

                    System.out.println("Enter a positive number.");
                }
            } catch (NumberFormatException nfe) {

                System.out.println("Enter a positive number.");
            }
        }
    }

    public static String getStringInput (String question) {

        if (question != null) {

            System.out.println(question);
        }

        String entry = scanner.nextLine();
        return entry;
    }
}
