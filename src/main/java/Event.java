public class Event extends Task{

    protected String eventStart;
    protected String eventEnd;

    public Event (String description, String eventStart, String eventEnd) {
        super(description);
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    @Override
    public String toString() {
        return String.format("       [E]%s (from: %s to: %s)", super.toString(), this.eventStart, this.eventEnd);
    }

}
