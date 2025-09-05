package Focus;

/**
 * Parses raw user input into executable FocusCommand objects.
 */
public class InputParser {

    /** Throw an error for an empty description of a known command. */
    private static void emptyCommandError(String cmd) {
        throw new FocusException(("     " +
                        "OOPS!!! The description of a %s cannot be empty.\n    " +
                        "____________________________________________________________" +
                cmd));
    }

    /**
     * Parses the given user input into a command.
     *
     * @param input Raw line entered by the user.
     * @return A FocusCommand corresponding to the input.
     * @throws FocusException If the input cannot be parsed into a valid command.
     */
    public static FocusCommand parse(String input) throws FocusException {

        if (input == null || input.trim().isEmpty()) {
            throw new FocusException("     Empty command.");
        }

        String[] headTail = input.trim().split("\\s+", 2);
        String cmd = headTail[0].toLowerCase();
        String args = (headTail.length > 1) ? headTail[1].trim() : "";

        switch (cmd) {
        case "list": return new ListCommand();
        case "todo":
            if (args.isEmpty()) {
                emptyCommandError(cmd);
            }
            return new TodoCommand(args);
        case "deadline":
            if (args.isEmpty()) {
                emptyCommandError(cmd);
            }
            return parseDeadline(args);
        case "event":
            if (args.isEmpty()) {
                emptyCommandError(cmd);
            }
            return parseEvent(args);
        case "mark":
            return new MarkCommand(parseIndex(args));
        case "unmark":
            return new UnmarkCommand(parseIndex(args));
        case "delete":
            return new DeleteCommand(parseIndex(args));
        case "find":
            if (args.isEmpty()) {
                throw new FocusException("     Usage: find <keyword>\n"
                + "____________________________________________________________");
            }
            return new FindCommand(args);
    case "bye":
            return new ByeCommand();
        default:
            throw new FocusException(String.format("     " +
                            "OOPS!!! I'm sorry, but I don't know what that means :-(\n    " +
                            "____________________________________________________________todo read book"));
        }
    }


    /**
     * Parses a Deadline command in the form:
     * deadline <desc> /by yyyy-MM-dd.
     *
     * @param args Argument portion after the deadline keyword.
     * @return A command that adds the deadline.
     * @throws FocusException If the arguments are missing or malformed.
     */
    private static FocusCommand parseDeadline(String args) throws FocusException {
        String[] seg = args.split("/by", 2);
        if (seg.length < 2) {
            throw new FocusException("     Use: deadline <desc> /by yyyy-MM-dd\n" +
                    "____________________________________________________________");
        }
        return new DeadlineCommand(seg[0].trim(), seg[1].trim());
    }


    /**
     * Parses an Event command in the form:
     * event <desc> /from <start> /to <end>.
     *
     * @param args Argument portion after the {event} keyword.
     * @return A command that adds the event.
     * @throws FocusException If the arguments are missing or malformed.
     */
    private static FocusCommand parseEvent(String args) throws FocusException {
        String[] fromSplit = args.split("/from", 2);
        if (fromSplit.length < 2) {
            throw new FocusException("     Use: event <desc> /from <start> /to <end>\n"
            + "____________________________________________________________");
        }
        String desc = fromSplit[0].trim();

        String[] toSplit = fromSplit[1].split("/to", 2);
        if (toSplit.length < 2) {
            throw new FocusException("     Use: event <desc> /from <start> /to <end>\n"
            + "____________________________________________________________");
        }
        String start = toSplit[0].trim();
        String end = toSplit[1].trim();

        return new EventCommand(desc, start, end);
    }

    /**
     * Parses a one-based index from text.
     *
     * @param s Text that should contain a positive integer.
     * @return Parsed one-based index.
     * @throws FocusException If the text is empty or not a number.
     */
    private static int parseIndex(String s) throws FocusException {
        if (s.isEmpty()) {
            throw new FocusException("     Index required.");

        }
        try {
            return Integer.parseInt(s.trim()); }
        catch (NumberFormatException e) {
            throw new FocusException("     Index must be a number.");
        }
    }

}
