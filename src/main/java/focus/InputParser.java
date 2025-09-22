package focus;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses raw user input into executable FocusCommand objects.
 */
public class InputParser {

    /** Throw an error for an empty description of a known command. */
    private static void emptyCommandError(String cmd) throws FocusException {
        throw new FocusException(String.format(
                "     OOPS!!! The description of a %s command cannot be empty.\n    ", cmd));
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
            throw new FocusException("     Empty command. Please type in a command!");
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
            if (args.isEmpty()) {
                emptyCommandError(cmd);
            }
            List<Integer> indexes = parseIndexes(args); // args can be "3" or "1 2 3"
            assert indexes.stream().allMatch(i -> i > 0) : "Indexes must be 1-based positive integers";
            int[] varargs = indexes.stream().mapToInt(Integer::intValue).toArray();
            return new MarkCommand(varargs);
        case "unmark":
            if (args.isEmpty()) {
                emptyCommandError(cmd);
            }
            indexes = parseIndexes(args); // args can be "3" or "1 2 3"
            assert indexes.stream().allMatch(i -> i > 0) : "Indexes must be 1-based positive integers";
            varargs = indexes.stream().mapToInt(Integer::intValue).toArray();
            return new UnmarkCommand(varargs);
        case "delete":
            if (args.isEmpty()) {
                emptyCommandError(cmd);
            }
            return new DeleteCommand(parseIndex(args));
        case "find":
            if (args.isEmpty()) {
                throw new FocusException("     Usage: find <keyword>\n");
            }
            return new FindCommand(args);
        case "bye":
            return new ByeCommand();
        default:
            throw new FocusException("OOPS!!! I'm sorry, but I don't know what that means :-(\n    ");
        }
    }


    /**
     * Parses a Deadline command in the form:
     * deadline [desc] /by yyyy-MM-dd.
     * Note: Used ChatGPT here to modify parseDeadline to handle local date time formats
     *
     * @param args Argument portion after the deadline keyword.
     * @return A command that adds the deadline.
     * @throws FocusException If the arguments are missing or malformed.
     */
    private static FocusCommand parseDeadline(String args) throws FocusException {

        final String deadlineBy = "/by";
        int byIdx = args.indexOf(deadlineBy);
        if (byIdx < 0) {
            throw new FocusException("     Usage: deadline <description> /by yyyy-MM-dd HHmm");
        }

        String desc = args.substring(0, byIdx).trim();
        String byRaw = args.substring(byIdx + deadlineBy.length()).trim();

        if (desc.isEmpty() || byRaw.isEmpty()) {
            throw new FocusException("     Usage: deadline <description> /by yyyy-MM-dd HHmm");
        }

        return new DeadlineCommand(desc, byRaw);

    }


    /**
     * Parses an Event command in the form:
     * event [desc] /from [start] /to [end].
     * Note: Used ChatGPT here to modify parseEvent to handle local date time formats
     *
     * @param args Argument portion after the {event} keyword.
     * @return A command that adds the event.
     * @throws FocusException If the arguments are missing or malformed.
     */
    private static FocusCommand parseEvent(String args) throws FocusException {

        final String eventFrom = "/from";
        final String eventTo = "/to";

        int fromIdx = args.indexOf(eventFrom);
        int toIdx = args.indexOf(eventTo);
        if (fromIdx < 0 || toIdx < 0 || toIdx <= fromIdx) {
            throw new FocusException("     Usage: event <description> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm");
        }

        String desc = args.substring(0, fromIdx).trim();
        if (desc.isEmpty()) {
            emptyCommandError("event");
        }

        String afterFrom = args.substring(fromIdx + eventFrom.length()).trim();
        int relTo = afterFrom.indexOf(eventTo);
        if (relTo < 0) {
            throw new FocusException("     Usage: event <description> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm");
        }

        String startRaw = afterFrom.substring(0, relTo).trim();
        String endRaw = args.substring(toIdx + eventTo.length()).trim();

        if (startRaw.isEmpty() || endRaw.isEmpty()) {
            throw new FocusException("     Usage: event <description> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm");
        }

        return new EventCommand(desc, startRaw, endRaw);

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
     * @throws FocusException If the text is empty or not a positive integer number.
     */
    private static List<Integer> parseIndexes(String s) throws FocusException {
        if (s == null || s.isBlank()) {
            throw new FocusException("     Index required.");
        }
        String[] stringList = s.trim().split("\\s+"); // supports "1 2 3" (any whitespace)
        List<Integer> toRet = new ArrayList<>(stringList.length);
        for (String t : stringList) {
            try {
                Integer checkInteger = Integer.parseInt(t);
                if (checkInteger <= 0) {
                    throw new FocusException("     Indices must be positive integers!");
                }
                toRet.add(checkInteger);
            } catch (NumberFormatException e) {
                throw new FocusException("     Indices must be numbers (got: \"" + t + "\").");
            }
        }
        return toRet;
    }

}
