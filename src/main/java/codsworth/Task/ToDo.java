package codsworth.Task;

/**
 * Represents the ToDo task. A <code>Task</code> object corresponds to a task represented by a name
 * e.g., <code>todo [task name]</code>
 */
public class ToDo extends Task {
    /**
     * Initalisation method of ToDo
     *
     * @param description Name of the task.
     */
    public ToDo(String description) {
        super(description);
        this.taskType = "todo";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
