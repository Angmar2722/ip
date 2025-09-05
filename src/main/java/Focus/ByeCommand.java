package Focus;

public class ByeCommand extends FocusCommand {

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(TaskList tasks) {
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
    }

}
