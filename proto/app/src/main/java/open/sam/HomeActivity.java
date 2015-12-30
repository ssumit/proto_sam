package open.sam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class HomeActivity extends BaseActivity {

    private RecyclerView _recyclerView;
    private TaskManager _taskManager;
    private HomeScreenAdapter adapter;

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
        adapter = new HomeScreenAdapter(_taskManager.getTasks().toArray(new String[_taskManager.getTasks().size()]));
        _recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.home_screen_toolbar);
        setSupportActionBar(toolbar);
        attachListenersForAddingSimpleTodo();
    }

    private void attachListenersForAddingSimpleTodo() {
        findViewById(R.id.simple_todo_add_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = (EditText) findViewById(R.id.simple_todo_edittext);
                        String todo = editText.getText().toString().trim();
                        if (todo.length() > 0) {
                            _taskManager.addTask(todo);
                            adapter.setData(_taskManager.getTasks().toArray(new String[_taskManager.getTasks().size()]));
                            adapter.notifyDataSetChanged();
                        }
                        editText.setText("");
                    }
                });
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.home_screen_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_todo:
                Intent intent = new Intent(HomeActivity.this, AddTaskActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}