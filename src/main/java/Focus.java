import java.util.Scanner;

public class Focus {

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    public static void main(String[] args) {

        printLine();
        System.out.println(" Hello! I'm Focus\n" + " What can I do for you?");
        printLine();

        Scanner scanner = new Scanner(System.in);

        while (true) {

            String echoInput = scanner.nextLine();

            printLine();

            if (echoInput.equals("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                printLine();
                break;
            } else {
                System.out.println(echoInput);  //Echo user command
                printLine();
            }

        }

    }

}
