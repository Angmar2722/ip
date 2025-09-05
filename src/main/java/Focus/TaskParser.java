package Focus;

public class TaskParser {

    public static Task parse(String unparsedLine) throws FocusException {

        String[] parts = unparsedLine.split(" \\| ");

        if (parts.length < 3) {
            throw new FocusException("Corrupted line: %s" + unparsedLine);
        }

        char firstChar = parts[0].charAt(0);
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task toRet;

        switch (firstChar) {

        case 'T':

            toRet = new ToDo(description);
            break;

        case 'D':

            if (parts.length < 4) {
                throw new FocusException("Deadline missing 'by': " + unparsedLine);
            }

            toRet = new Deadline(description, parts[3].trim());
            break;

        case 'E':

            if (parts.length < 4) {
                throw new FocusException("Event missing start/end: " + unparsedLine);
            }

            String[] times = parts[3].split(" - ", 2); // split into start and end

            if (times.length < 2) {
                throw new FocusException("Event time not in 'start - end' format: " + unparsedLine);
            }

            String eventStart = times[0].trim();
            String eventEnd = times[1].trim();

            toRet = new Event(description, eventStart, eventEnd);

            break;

        default:
            throw new FocusException("Unknown task type. Could not parse!");

        }

        if (isDone) {
            toRet.markAsDone();
        }

        return toRet;

    }

}
