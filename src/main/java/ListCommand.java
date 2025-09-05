public class ListCommand extends FocusCommand {

    @Override
    public void execute(TaskList tasks) {
        tasks.printTaskList();
    }

}