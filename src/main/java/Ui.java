import java.util.Scanner;

public class Ui {

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    public void showError(String message) {
        System.out.println("     " + message);
    }

    public void showWelcome() {
        printLine();
        System.out.println("    Hello! I'm Focus\n" + "    What can I do for you?");
        printLine();
    }

}