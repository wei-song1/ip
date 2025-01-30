package codsworth.Task;

public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
        this.taskType = "todo";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
