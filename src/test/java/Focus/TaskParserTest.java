package Focus;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class TaskParserTest {

    @Test
    // Parse Deadline storage line with correct date format
    void parseDeadline_Test() throws Exception {
        Task t = TaskParser.parse("D | 1 | submit report | 2025-09-10");
        assertTrue(t instanceof Deadline);
        assertEquals("submit report", t.getDescription());
        assertTrue(t.isDone());
        Deadline d = (Deadline) t;
        assertEquals(LocalDate.of(2025, 9, 10), d.getDeadline());
        assertEquals("D | 1 | submit report | 2025-09-10", d.toStorageString());
    }

    @Test
    // Parse Event storage line with start - end
    void parseEvent_Test() throws Exception {
        Task t = TaskParser.parse("E | 0 | project meeting | Mon 2pm - 4pm");
        assertTrue(t instanceof Event);
        assertEquals("project meeting", t.getDescription());
        assertFalse(t.isDone());
        assertEquals("E | 0 | project meeting | Mon 2pm - 4pm", t.toStorageString());
    }

}
