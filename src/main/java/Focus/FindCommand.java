package Focus;

public class FindCommand extends FocusCommand {

    private final String searchKeyword;

    public FindCommand(String keyword) {
        this.searchKeyword = keyword.toLowerCase();
    }

    @Override
    public void execute(TaskList taskList) {
        taskList.printMatchingWords(this.searchKeyword);
    }

}
