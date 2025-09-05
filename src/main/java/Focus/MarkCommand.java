package Focus;

/**
 * Marks a task as done.
 */
public class MarkCommand extends FocusCommand {

    private final int userIndex; // 1-based index from user

    /**
     * Constructs a MarkCommand.
     *
     * @param userIndex One-based index of the task (inputted by the user) to mark.
     */
    public MarkCommand(int userIndex) {
        this.userIndex = userIndex;
    }

    @Override
    public boolean isMutating() { return true; }

    /**
     * Executes the command by marking the task as done.
     *
     * @param tasks Task list to update.
     * @throws FocusException If the index is out of range.
     */
    @Override
    public void execute(TaskList tasks) throws FocusException {
        int i = userIndex - 1;
        if (i < 0 || i >= tasks.getTasks().size()) {
            throw new FocusException("Index out of range.");
        }
        tasks.markTaskAsDone(i);
    }

}
