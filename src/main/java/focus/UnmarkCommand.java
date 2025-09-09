package focus;

/**
 * Marks a task as not done.
 */
public class UnmarkCommand extends FocusCommand {

    private final int userIndex;

    /**
     * Constructs an {@code UnmarkCommand}.
     *
     * @param userIndex One-based index of the task (inputted by the user) to unmark.
     */
    public UnmarkCommand(int userIndex) {
        this.userIndex = userIndex;
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    /**
     * Executes the command by marking the task as not done.
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
        tasks.markTaskAsNotDone(i);
    }

}
