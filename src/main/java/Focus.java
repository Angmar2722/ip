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
        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {

            String input = scanner.nextLine();
            String[] userCommand = input.split(" ");
            printLine();

            if (input.equals("bye")) {

                System.out.println("     Bye. Hope to see you again soon!");
                printLine();
                break;

            } else if (input.equals("list")) {

                System.out.println("     Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.printf("     %d.%s\n", i + 1, tasks.get(i));
                }
                printLine();

            } else if (input.startsWith("mark") || input.startsWith("unmark")) {

                int taskIndex = Integer.parseInt(userCommand[1]) - 1;

                if (userCommand[0].equals("mark")) {
                    System.out.println("     Nice! I've marked this task as done:");
                    tasks.get(taskIndex).markAsDone();
                    System.out.printf("       %s\n", tasks.get(taskIndex));
                } else if (userCommand[0].equals("unmark")) {
                    System.out.println("     OK, I've marked this task as not done yet:");
                    tasks.get(taskIndex).markNotDone();
                    System.out.printf("       %s\n", tasks.get(taskIndex));
                }

                printLine();

            } else {

                System.out.printf("     added: %s\n", input);
                tasks.add(new Task(input));
                printLine();

            }

        }

    }

}
