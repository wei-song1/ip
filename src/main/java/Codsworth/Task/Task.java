package Codsworth.Task;

public class Task {
    protected String description;
    protected String taskType;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.taskType = "";
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void setDoneOrUndone(String type) {
        if (type.equals("mark")) {
            this.isDone = true;
        } else {
            this.isDone = false;
        }
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
