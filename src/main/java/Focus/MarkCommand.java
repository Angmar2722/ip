package Focus;

public class MarkCommand extends FocusCommand {

    private final int userIndex; // 1-based index from user

    public MarkCommand(int userIndex) {
        this.userIndex = userIndex;
    }

    @Override
    public boolean isMutating() { return true; }

    @Override
    public void execute(TaskList tasks) throws FocusException {
        int i = userIndex - 1;
        if (i < 0 || i >= tasks.getTasks().size()) {
            throw new FocusException("Index out of range.");
        }
        tasks.markTaskAsDone(i);
    }

}
