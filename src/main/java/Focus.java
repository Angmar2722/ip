import java.util.Scanner;
import java.io.IOException;

public class Focus {

    private static TaskStorage taskStorage = new TaskStorage("data/Focus.txt");
    private static TaskList taskList = new TaskList();

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

        try {
            taskList = taskStorage.loadTasks();
        } catch (IOException e) {
            System.out.println("No stored task list found!");
        }

        printLine();
        System.out.println("    Hello! I'm Focus\n" + "    What can I do for you?");
        printLine();

        Scanner scanner = new Scanner(System.in);

        boolean changedTaskList = false;

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
                    scanner.close();
                    return;

                case LIST:

                    taskList.printTaskList();
                    break;

                case MARK:

                    taskList.markTaskAsDone(Integer.parseInt(taskParams) - 1);
                    break;

                case UNMARK:

                    taskList.markTaskAsNotDone(Integer.parseInt(taskParams) - 1);
                    break;

                case TODO:

                    taskList.addTask(new ToDo(taskParams), false);
                    changedTaskList = true;
                    break;

                case DEADLINE: {

                    String[] deadlineStringSplit = taskParams.split(" /by ", 2);
                    String deadlineDesc = deadlineStringSplit[0];
                    String deadlineBy = deadlineStringSplit[1];
                    taskList.addTask(new Deadline(deadlineDesc, deadlineBy), false);
                    changedTaskList = true;
                    break;

                }

                case EVENT: {

                    String[] eventStringSplit = taskParams.split(" /from ", 2);
                    String desc = eventStringSplit[0];
                    String[] segTo = eventStringSplit[1].split(" /to ", 2);
                    String eventStart = segTo[0];
                    String eventEnd = segTo[1];
                    taskList.addTask(new Event(desc, eventStart, eventEnd), false);
                    changedTaskList = true;
                    break;

                }

                case DELETE: {

                    taskList.deleteTask(Integer.parseInt(taskParams) - 1);
                    changedTaskList = true;
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

            if (changedTaskList) {
                try {
                    taskStorage.saveTasks(taskList);
                } catch (IOException e) {
                    System.out.println("Error saving task list!");
                }
            }

        }

    }

}
