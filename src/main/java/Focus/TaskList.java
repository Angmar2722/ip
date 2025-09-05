package Focus;

import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasks = new ArrayList<>();

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    public int size() {
        return this.tasks.size();
    }

    public Task getTaskAtIndex(int index) {
        return this.tasks.get(index);
    }

    public void markTaskAsDone(int i) {

        System.out.println("     Nice! I've marked this task as done:");
        tasks.get(i).markAsDone();
        System.out.printf("       %s\n", tasks.get(i));
        printLine();

    }

    public void markTaskAsNotDone(int i) {

        System.out.println("     OK, I've marked this task as not done yet:");
        this.tasks.get(i).markNotDone();
        System.out.printf("       %s\n", tasks.get(i));
        printLine();

    }

    public void printTaskList() {

        if (this.tasks.isEmpty()) {
            System.out.println("     No tasks in your list.");
            printLine();
            return;
        }

        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.printf("     %d.%s\n", i + 1, this.tasks.get(i));
        }

        printLine();

    }

    public void printMatchingWords(String keyword) {

        System.out.println("     Here are the matching tasks in your list:");
        String k = keyword.toLowerCase();
        int shown = 0;

        for (int i = 0; i < this.tasks.size(); i++) {
            Task currentTask = this.tasks.get(i);
            if (currentTask.getDescription().toLowerCase().contains(k)) {
                System.out.printf("     %d.%s\n", i + 1, currentTask);
                shown++;
            }
        }

        if (shown == 0) {
            System.out.println("     No matching tasks found.");
        }

        printLine();

    }

    public void deleteTask(int i) {

        String deletedTaskString = this.tasks.get(i).toString();
        this.tasks.remove(i);
        System.out.println("     Noted. I've removed this task:");
        System.out.printf("       %s\n", deletedTaskString);
        System.out.printf("     Now you have %d tasks in the list.\n", this.tasks.size());
        printLine();

    }

    public void addTask(Task task, boolean loading) {

        tasks.add(task);

        if (!loading) {
            System.out.println("     Got it. I've added this task:");
            System.out.printf("  %s\n", task);
            System.out.printf("     Now you have %d tasks in the list.\n", this.tasks.size());
            printLine();
        }

    }

}
