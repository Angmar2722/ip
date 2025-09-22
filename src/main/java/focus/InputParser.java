package focus;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses raw user input into executable FocusCommand objects.
 */
public class InputParser {

    /** Throw an error for an empty description of a known command. */
    private static void emptyCommandError(String cmd) throws FocusException {
        throw new FocusException(("     "
                    + "OOPS!!! The description of a %s cannot be empty.\n    "
                    + cmd));
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
            List<Integer> indexes = parseIndexes(args); // args can be "3" or "1 2 3"
            assert indexes.stream().allMatch(i -> i > 0) : "Indexes must be 1-based positive integers";
            int[] varargs = indexes.stream().mapToInt(Integer::intValue).toArray();
            return new MarkCommand(varargs);
        case "unmark":
            indexes = parseIndexes(args); // args can be "3" or "1 2 3"
            assert indexes.stream().allMatch(i -> i > 0) : "Indexes must be 1-based positive integers";
            varargs = indexes.stream().mapToInt(Integer::intValue).toArray();
            return new UnmarkCommand(varargs);
        case "delete":
            return new DeleteCommand(parseIndex(args));
        case "find":
            if (args.isEmpty()) {
                throw new FocusException("     Usage: find <keyword>\n");
            }
            return new FindCommand(args);
        case "bye":
            return new ByeCommand();
        default:
            throw new FocusException(String.format("     "
                    + "OOPS!!! I'm sorry, but I don't know what that means :-(\n    "));
        }
    }


    /**
     * Parses a Deadline command in the form:
     * deadline [desc] /by yyyy-MM-dd.
     *
     * @param args Argument portion after the deadline keyword.
     * @return A command that adds the deadline.
     * @throws FocusException If the arguments are missing or malformed.
     */
    private static FocusCommand parseDeadline(String args) throws FocusException {
        String[] seg = args.split("/by", 2);
        if (seg.length < 2) {
            throw new FocusException("     Use: deadline <desc> /by yyyy-MM-dd\n");
        }
        return new DeadlineCommand(seg[0].trim(), seg[1].trim());
    }


    /**
     * Parses an Event command in the form:
     * event [desc] /from [start] /to [end].
     *
     * @param args Argument portion after the {event} keyword.
     * @return A command that adds the event.
     * @throws FocusException If the arguments are missing or malformed.
     */
    private static FocusCommand parseEvent(String args) throws FocusException {
        String[] fromSplit = args.split("/from", 2);
        if (fromSplit.length < 2) {
            throw new FocusException("     Use: event <desc> /from <start> /to <end>\n");
        }
        String desc = fromSplit[0].trim();

        String[] toSplit = fromSplit[1].split("/to", 2);
        if (toSplit.length < 2) {
            throw new FocusException("     Use: event <desc> /from <start> /to <end>\n");
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
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            throw new FocusException("     Index must be a number.");
        }
    }

    /**
     * Parses a one-based single or multi-index from text.
     *
     * @param s Text that should contain one or more positive integers.
     * @return Parsed one-based index or multi-index (e.g. "1" or "1 2 3") stored in Integer list.
     * @throws FocusException If the text is empty or not a number.
     */
    private static List<Integer> parseIndexes(String s) throws FocusException {
        if (s == null || s.isBlank()) {
            throw new FocusException("     Index required.");
        }
        String[] stringList = s.trim().split("\\s+"); // supports "1 2 3" (any whitespace)
        List<Integer> toRet = new ArrayList<>(stringList.length);
        for (String t : stringList) {
            try {
                toRet.add(Integer.parseInt(t));
            } catch (NumberFormatException e) {
                throw new FocusException("     Indices must be numbers (got: \"" + t + "\").");
            }
        }
        return toRet;
    }

}
