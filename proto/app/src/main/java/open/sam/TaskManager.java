package open.sam;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<String> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(String task) {
        tasks.add(task);
    }

    public List<String> getTasks() {
        return tasks;
    }
}
