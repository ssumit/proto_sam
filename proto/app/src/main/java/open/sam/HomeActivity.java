package open.sam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class HomeActivity extends BaseActivity {

    private RecyclerView _recyclerView;
    private TaskManager _taskManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _taskManager = new TaskManager();
        setContentView(R.layout.home_screen);
        _recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        _recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(linearLayoutManager);

        // specify an adapter (see also next example)
        HomeScreenAdapter adapter = new HomeScreenAdapter(_taskManager.getTasks().toArray(new String[_taskManager.getTasks().size()]));
        _recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.home_screen_toolbar);
        toolbar.inflateMenu(R.menu.home_screen_menu);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_todo:
                Intent intent = new Intent(HomeActivity.this, AddTaskActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}