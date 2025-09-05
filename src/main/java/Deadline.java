public class Deadline extends Task {

    protected String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return String.format("       [D]%s  (by: %s)", super.toString(), this.deadline);
    }

    @Override
    public String toStorageString() {
        return String.format("D %s| %s", super.toStorageString(), this.deadline);
    }

}
