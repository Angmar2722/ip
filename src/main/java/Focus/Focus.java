package Focus;

import java.io.IOException;

public class Focus {

    public static void main(String[] args) {

        Ui ui = new Ui();
        TaskStorage storage = new TaskStorage("data/Focus.txt");
        TaskList taskList;

        try {
            taskList = storage.loadTasks();
        } catch (IOException e) {
            ui.showError("No stored task list found!");
            taskList = new TaskList();
        }

        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            String fullCommand = ui.readCommand();
            ui.printLine();
            try {
                FocusCommand cmd = InputParser.parse(fullCommand);
                cmd.execute(taskList);

                if (cmd.isMutating()) {
                    try {
                        storage.saveTasks(taskList);
                    } catch (IOException ioe) {
                        ui.showError("Error saving taskList.");
                    }
                }

                isExit = cmd.isExit();
            } catch (FocusException fe) {
                ui.showError(fe.getMessage());
            } catch (Exception e) {
                ui.showError("Unexpected error: " + e.getMessage());
            }
        }

    }

}
