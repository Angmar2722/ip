package focus;

/**
 * Adds a new Deadline task to the task list.
 * The date is expected to be input in yyyy-MM-dd format.
 */

public class DeadlineCommand extends FocusCommand {

    private final String description;
    private final String deadline; // yyyy-MM-dd

    /**
     * Constructs a DeadlineCommand.
     *
     * @param description Description of the deadline.
     * @param deadline Due date in yyyy-MM-dd format.
     */
    public DeadlineCommand(String description, String deadline) {
        this.description = description;
        this.deadline = deadline;
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    /**
     * Executes the command by adding the deadline to the list and showing feedback.
     *
     * @param tasks Task list to update.
     * @throws FocusException If the date cannot be parsed.
     */
    @Override
    public void execute(TaskList tasks) {
        tasks.addTask(new Deadline(description, deadline), false);
    }

}

