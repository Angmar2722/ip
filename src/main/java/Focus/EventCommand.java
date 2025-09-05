package Focus;

/**
 * Adds a new Event task to the task list.
 * The time period is captured as an even start and an end string.
 */
public class EventCommand extends FocusCommand {

    private final String description;
    private final String start;
    private final String end;

    /**
     * Constructs an EventCommand.
     *
     * @param description Description of the event.
     * @param start Start time text (e.g. Mon 2pm).
     * @param end End time text (e.g. 4pm).
     */
    public EventCommand(String description, String start, String end) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean isMutating() { return true; }

    /**
     * Executes the command by adding the event to the list.
     *
     * @param tasks Task list to update.
     */
    @Override
    public void execute(TaskList tasks) {
        tasks.addTask(new Event(description, start, end), false);
    }

}
