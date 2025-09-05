package Focus;

public abstract class FocusCommand {

    /** return true if this command exits the app */
    public boolean isExit() {
        return false;
    }

    /** return true if the command mutates the task list (so we autosave) */
    public boolean isMutating() {
        return false;
    }

    /** Execute the command. Let Focus.TaskList do the printing. */
    public abstract void execute(TaskList tasks) throws FocusException;

}
