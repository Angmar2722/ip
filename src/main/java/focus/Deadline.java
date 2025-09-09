package focus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that has to be completed by a specific date.
 */
public class Deadline extends Task {

    protected LocalDate deadline;

    /**
     * Constructs a Deadline event with the given description and due date.
     * The date is expected in format code yyyy-MM-dd.
     *
     * @param description Description of this deadline.
     * @param deadline Due date in format yyyy-MM-dd.
     */
    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = LocalDate.parse(deadline);
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return String.format("       [D]%s (by: %s)", super.toString(), deadline.format(outputDateFormat));
    }

    /**
     * Returns a storage-friendly representation of this deadline.
     *
     * @return Encoded string to be written to disk.
     */
    @Override
    public String toStorageString() {
        return String.format("D %s | %s", super.toStorageString(), this.deadline.toString());
    }

}
