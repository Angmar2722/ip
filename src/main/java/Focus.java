import java.util.Scanner;
import java.util.ArrayList;

public class Focus {

    /**
     * prints a horizontal line of dashes
     */
    public static void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    public static void main(String[] args) {

        printLine();
        System.out.println("    Hello! I'm Focus\n" + "    What can I do for you?");
        printLine();

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();

        while (true) {

            String input = scanner.nextLine();

            printLine();

            if (input.equals("bye")) {
                System.out.println("     Bye. Hope to see you again soon!");
                printLine();
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.printf("     %d. %s\n", i + 1, tasks.get(i));
                }
                printLine();
            } else {
                System.out.printf("     added: %s\n", input);
                tasks.add(input);
                printLine();
            }

        }

    }

}
