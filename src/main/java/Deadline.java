import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDate deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = LocalDate.parse(deadline);
    }

    @Override
    public String toString() {
        DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return String.format("       [D]%s (by: %s)", super.toString(), deadline.format(outputDateFormat));
    }

    @Override
    public String toStorageString() {
        return String.format("D %s| %s", super.toStorageString(), this.deadline.toString());
    }

}