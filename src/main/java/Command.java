/**
 * Represents the supported commands in Focus.
 */
public enum Command {

    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN;

    /**
     * Converts a raw string into a Focus Command enum.
     *
     * @param input user input command
     * @return matching Command, or UNKNOWN if not recognized
     */
    public static Command fromString(String input) {
        switch (input) {
            case "bye":      return BYE;
            case "list":     return LIST;
            case "mark":     return MARK;
            case "unmark":   return UNMARK;
            case "todo":     return TODO;
            case "deadline": return DEADLINE;
            case "event":    return EVENT;
            case "delete":   return DELETE;
            default:         return UNKNOWN;
        }
    }

    /** Whether this command requires a non-empty argument string. */
    public boolean requiresNonEmptyArgument() {
        switch (this) {
            case MARK:
            case UNMARK:
            case TODO:
            case DEADLINE:
            case EVENT:
            case DELETE:
                return true;
            default:
                return false;
        }
    }

}