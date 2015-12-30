package open.sam;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    public List<String> getTasks() {
        List<String> tasks = new ArrayList<>();
        for (int loop = 0; loop < 10; loop++) {
            tasks.add("task " + loop);
        }
        return tasks;
    }
}
