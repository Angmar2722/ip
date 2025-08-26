/**
 * Represents a task
 */

public class Task {

    protected String description;
    protected boolean isDone;

    /**
     * Initializes task with task description and whether the task is done
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markNotDone() {
        this.isDone = false;
    }

    /**
     * Prints whether task is done along with task name
     * @return A string of form: [whether task done] task name
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

}
