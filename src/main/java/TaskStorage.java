import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Handles loading tasks from a file and saving tasks to a file.
 */
public class TaskStorage {

    private final String FILEPATH;

    private TaskParser taskParser = new TaskParser();

    public TaskStorage(String filePath) {
        this.FILEPATH = filePath;
    }

    public void saveTasks(TaskList taskList) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH))) {
            for (Task task : taskList.getTasks()) {
                writer.write(task.toStorageString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }

    }

    public TaskList loadTasks() throws IOException {

        TaskList taskList = new TaskList();
        File storedFiled = new File(FILEPATH);

        if (!storedFiled.exists()) {
            return taskList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                taskList.addTask(TaskParser.parse(line), true);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return taskList;

    }

    public static void main(String[] args) {

        TaskStorage taskStorage = new TaskStorage("data/Focus.txt");
        TaskList taskList = null;

        try {
            taskList = taskStorage.loadTasks();
        } catch (IOException e) {
            System.out.println("Could not load task list!");
        }

    }

}
