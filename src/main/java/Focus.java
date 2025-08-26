import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Focus {

    /**
     * prints a horizontal line of dashes
     */
    public static void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    /** Throw an error for an empty description of a known command. */
    private static void emptyCommandError(String cmd) {
        throw new FocusException(String.format("     OOPS!!! The description of a %s cannot be empty.", cmd));
    }

    /** Throw an error for an unknown command. */
    private static void unknownCommandError() {
        throw new FocusException("     OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

    public static void main(String[] args) {

        printLine();
        System.out.println("    Hello! I'm Focus\n" + "    What can I do for you?");
        printLine();

        Scanner scanner = new Scanner(System.in);
        TaskList tasks = new TaskList();

        ArrayList<String> cmdList = new ArrayList<>(Arrays.asList("mark", "unmark", "todo", "deadline", "event", "delete"));

        while (true) {

            String input = scanner.nextLine();

            // Split into command + rest. Keep spaces in the rest.
            String[] parts = input.split(" ", 2);
            String cmdString = parts[0];
            Command cmd = Command.fromString(cmdString);
            String taskParams = (parts.length > 1) ? parts[1] : ""; //For user input with 2 or more words

            printLine();

            try {

                if (cmd == Command.UNKNOWN) {
                    unknownCommandError();
                }
                if (cmd.requiresNonEmptyArgument() && taskParams.isBlank()) {
                    emptyCommandError(cmdString);
                }

                switch (cmd) {

                    case BYE:

                        System.out.println("     Bye. Hope to see you again soon!");
                        printLine();
                        break;

                    case LIST:

                        tasks.printTaskList();
                        break;

                    case MARK:

                        tasks.markTaskAsDone(Integer.parseInt(taskParams) - 1);
                        break;

                    case UNMARK:

                        tasks.markTaskAsNotDone(Integer.parseInt(taskParams) - 1);
                        break;

                    case TODO:

                        tasks.addTask(new ToDo(taskParams));
                        break;

                    case DEADLINE: {

                        String[] deadlineStringSplit = taskParams.split(" /by ", 2);
                        String deadlineDesc = deadlineStringSplit[0];
                        String deadlineBy = deadlineStringSplit[1];
                        tasks.addTask(new Deadline(deadlineDesc, deadlineBy));
                        break;

                    }

                    case EVENT: {

                        String[] eventStringSplit = taskParams.split(" /from ", 2);
                        String desc = eventStringSplit[0];
                        String[] segTo = eventStringSplit[1].split(" /to ", 2);
                        String eventStart = segTo[0];
                        String eventEnd = segTo[1];
                        tasks.addTask(new Event(desc, eventStart, eventEnd));
                        break;

                    }

                    case DELETE: {

                        tasks.deleteTask(Integer.parseInt(taskParams) - 1);
                        break;

                    }

                    default:
                        unknownCommandError();
                        printLine();

                }

            } catch (FocusException e) {
                System.out.println("     " + e.getMessage());
                printLine();
            }

        }

    }

}
