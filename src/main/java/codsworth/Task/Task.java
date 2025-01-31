package codsworth.Task;

/**
 * Represents task. A <code>Task</code> object corresponds to a task represented by a name
 */
public class Task {
    protected String description;
    protected String taskType;
    protected boolean isDone;

    /**
     * Initalisation method of Task
     *
     * @param description Name of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.taskType = "";
    }

    public void setDoneOrUndone(String type) {
        this.isDone = type.equals("mark");
    }

    public String getTaskType() {
        return this.taskType;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return isDone
                ? "[X] " + this.description
                : "[ ] " + this.description;
    }
}
