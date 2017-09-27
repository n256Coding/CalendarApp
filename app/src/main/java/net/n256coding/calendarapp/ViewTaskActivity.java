package net.n256coding.calendarapp;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.n256coding.calendarapp.Database.TaskDB;
import net.n256coding.calendarapp.Helper.DateEx;
import net.n256coding.calendarapp.Models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewTaskActivity extends AppCompatActivity {

    public static final String TAG = "ViewTaskActivity.java";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout_endDate;
    RadioButton rbOn, rbBetween;
    Button btnFilter;
    TextView tvDate, tvEndDate;

    DatePickerDialog.OnDateSetListener startDateSetListener;
    DatePickerDialog.OnDateSetListener endDateSetListener;

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
        relativeLayout_endDate = (RelativeLayout) findViewById(R.id.relativeLayout_endDate);
        rbOn = (RadioButton) findViewById(R.id.rbOn);
        rbBetween = (RadioButton) findViewById(R.id.rbBetween);
        btnFilter = (Button) findViewById(R.id.btnFilter);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);

        relativeLayout_endDate.setVisibility(View.INVISIBLE);
        List<Task> taskList = Task.getAllTasks(ViewTaskActivity.this);
        setRecyclerViewAdapter(taskList);

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

        rbBetween.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked)
                    relativeLayout_endDate.setVisibility(View.VISIBLE);
                else
                    relativeLayout_endDate.setVisibility(View.INVISIBLE);
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewTaskActivity.this,
                        startDateSetListener,
                        DateEx.getYearOf(null),
                        DateEx.getMonthOf(null)-1,
                        DateEx.getDayOf(null));
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewTaskActivity.this,
                        endDateSetListener,
                        DateEx.getYearOf(null),
                        DateEx.getMonthOf(null)-1,
                        DateEx.getDayOf(null));
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                tvDate.setText(year + "-" + (month+1) + "-" +day);
            }
        };

        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                tvEndDate.setText(year + "-" + (month+1) + "-" + day);
            }
        };

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbOn.isChecked()){
                    String date = null;
                    try {
                        date = DateEx.getFormatedDateString(tvDate.getText().toString().trim());
                    } catch (ParseException e) {
                        Log.e(TAG, "Error while parsing date on Filter date", e);
                    }
                    List<Task> tasks = Task.getTasksByDate(ViewTaskActivity.this, date);
                    setRecyclerViewAdapter(tasks);
                }else{
                    String startDate = null;
                    try {
                        startDate = DateEx.getFormatedDateString(tvDate.getText().toString().trim());
                    } catch (ParseException e) {
                        Log.e(TAG, "Error while parsing start date", e);
                    }
                    String endDate = null;
                    try {
                        endDate = DateEx.getFormatedDateString(tvEndDate.getText().toString().trim());
                    } catch (ParseException e) {
                        Log.e(TAG, "Error while parsing end date", e);
                    }
                    List<Task> tasks = Task.getTasksByDateRange(ViewTaskActivity.this, startDate, endDate);
                    setRecyclerViewAdapter(tasks);
                }
            }
        });

    }

    private void setRecyclerViewAdapter(List<Task> tasks){
        adapter = new DataAdapter(ViewTaskActivity.this, tasks);
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.setLayoutManager(layoutManager);
    }

}
