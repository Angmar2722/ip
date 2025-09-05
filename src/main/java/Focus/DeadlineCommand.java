package Focus;

public class DeadlineCommand extends FocusCommand {

    private final String description;
    private final String deadline; // yyyy-MM-dd

    public DeadlineCommand(String description, String deadline) {
        this.description = description;
        this.deadline = deadline;
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public void execute(TaskList tasks) {
        tasks.addTask(new Deadline(description, deadline), false);
    }

}
