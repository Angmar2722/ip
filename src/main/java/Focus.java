import java.util.Scanner;

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
        TaskList tasks = new TaskList();

        while (true) {

            String input = scanner.nextLine();

            // Split into command + rest. Keep spaces in the rest.
            String[] parts = input.split(" ", 2);
            String cmd = parts[0];
            String taskParams = (parts.length > 1) ? parts[1] : ""; //For user input with 2 or more words

            printLine();

            switch (cmd) {

                case "bye":

                    System.out.println("     Bye. Hope to see you again soon!");
                    printLine();
                    break;

                case "list":

                    tasks.printTaskList();
                    break;

                case "mark":

                    int taskIndex = Integer.parseInt(taskParams) - 1;
                    tasks.markTaskAsDone(taskIndex);
                    break;

                case "unmark":

                    taskIndex = Integer.parseInt(taskParams) - 1;
                    tasks.markTaskAsNotDone(taskIndex);
                    break;

                case "todo":

                    tasks.addTask(new ToDo(taskParams));
                    break;

                case "deadline": {

                    String[] deadlineStringSplit = taskParams.split(" /by ", 2);
                    String deadlineDesc = deadlineStringSplit[0];
                    String deadlineBy = deadlineStringSplit[1];
                    tasks.addTask(new Deadline(deadlineDesc, deadlineBy));
                    break;

                }

                case "event": {

                    String[] eventStringSplit = taskParams.split(" /from ", 2);
                    String desc = eventStringSplit[0];
                    String[] segTo = eventStringSplit[1].split(" /to ", 2);
                    String eventStart = segTo[0];
                    String eventEnd = segTo[1];
                    tasks.addTask(new Event(desc, eventStart, eventEnd));
                    break;

                }

                default:
                    System.out.println("     Unknown command.");
                    printLine();

            }

        }

    }

}
