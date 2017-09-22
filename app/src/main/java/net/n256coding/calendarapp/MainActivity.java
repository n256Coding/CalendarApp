package net.n256coding.calendarapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    Button btnToday, btnViewTask;
    DatePickerDialog.OnDateSetListener dateSetListener;
    FloatingActionButton fabAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        btnToday = (Button) findViewById(R.id.btnToday);
        btnViewTask = (Button) findViewById(R.id.btnViewTask);
        fabAddTask = (FloatingActionButton) findViewById(R.id.fabAddTask);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Toast.makeText(getApplicationContext(), i+"/"+(i1+1)+"/"+i2, Toast.LENGTH_LONG).show();
            }
        });

        calendarView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getApplicationContext(), "Long press", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        /*
        calendarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout()
            {
                Toast.makeText(getApplicationContext(), "Long press", Toast.LENGTH_LONG).show();
            }
        });*/

        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.setDate(new Date().getTime(), true, true);
            }
        });


        btnViewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentViewTask = new Intent(MainActivity.this, ViewTaskActivity.class);
                startActivity(intentViewTask);
            }
        });

        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddTask = new Intent(MainActivity.this, AddTaskActivity.class);
                intentAddTask.putExtra("selectedDate", calendarView.getDate());
                startActivity(intentAddTask);
            }
        });
    }
}
