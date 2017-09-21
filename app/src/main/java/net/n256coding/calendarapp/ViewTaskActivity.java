package net.n256coding.calendarapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import net.n256coding.calendarapp.Database.TaskDB;
import net.n256coding.calendarapp.Models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewTaskActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Controlls Definition
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_Tasks);
        layoutManager = new LinearLayoutManager(ViewTaskActivity.this);



        TaskDB taskDB = new TaskDB(ViewTaskActivity.this);
        Cursor cursor = taskDB.selectAll();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        List<Task> taskList = new ArrayList<>();
        while (cursor.moveToNext()){
            Task task = new Task();
            task.setTask_id(cursor.getInt(0));
            task.setTask_name(cursor.getString(1));
            task.setTask_description(cursor.getString(2));
            try {
                task.setTask_date(sdfDate.parse(cursor.getString(3)));
                task.setTask_start(sdfTime.parse(cursor.getString(5)));
                task.setTask_end(sdfTime.parse(cursor.getString(6)));
                task.setTask_notification_time(sdfTime.parse(cursor.getString(9)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            task.setTask_participants(cursor.getString(4));
            task.setTask_location(cursor.getString(7));
            task.setIs_all_day_task(Boolean.valueOf(cursor.getString(8)));
            task.setTask_notification_sound(cursor.getString(10));
            taskList.add(task);
        }

        adapter = new DataAdapter(ViewTaskActivity.this, taskList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(ViewTaskActivity.this, "Task Long click", Toast.LENGTH_SHORT).show();
                PopupMenu popupMenu = new PopupMenu(ViewTaskActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_add_remove, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.menuItem_editTask){
                            Toast.makeText(ViewTaskActivity.this, "Ready to modify", Toast.LENGTH_SHORT).show();
                        }else if(item.getItemId() == R.id.menuItem_removeTask){
                            if(true){
                                Toast.makeText(ViewTaskActivity.this, "Task Removed", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(ViewTaskActivity.this, "Task not Removed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }

}