package Focus;

public class TodoCommand extends FocusCommand {

    private final String description;
    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public void execute(TaskList tasks) {
        tasks.addTask(new ToDo(description), false);
    }

}
