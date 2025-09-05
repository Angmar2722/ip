package Focus;

/**
 * Represents an event that spans a time period with a start and an end.
 * The start and end are stored in the format used by user input.
 */
public class Event extends Task{

    protected String eventStart;
    protected String eventEnd;

    /**
     * Constructs an Event with the given description and time range.
     *
     * @param description Description of this event.
     * @param eventStart Start text (e.g., code Mon 2pm).
     * @param eventEnd End text (e.g., 4pm).
     */
    public Event (String description, String eventStart, String eventEnd) {
        super(description);
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    @Override
    public String toString() {
        return String.format("       [E]%s (from: %s to: %s)", super.toString(), this.eventStart, this.eventEnd);
    }

    /**
     * Returns a storage-friendly representation of this event.
     *
     * @return Encoded string to be written to disk.
     */
    @Override
    public String toStorageString() {
        return String.format("E %s | %s - %s", super.toStorageString(), this.eventStart, this.eventEnd);
    }

}
