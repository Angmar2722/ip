public class EventCommand extends FocusCommand {

    private final String description;
    private final String start;
    private final String end;

    public EventCommand(String description, String start, String end) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean isMutating() { return true; }

    @Override
    public void execute(TaskList tasks) {
        tasks.addTask(new Event(description, start, end), false);
    }

}
