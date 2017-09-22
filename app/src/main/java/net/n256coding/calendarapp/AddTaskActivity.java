package net.n256coding.calendarapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import net.n256coding.calendarapp.Database.TaskDB;
import net.n256coding.calendarapp.Models.DateEx;
import net.n256coding.calendarapp.Models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    EditText txtTaskName, txtTaskLocation, txtTaskDate, txtStartTime, txtEndTime;
    EditText txtTaskDescription, txtTaskParticipants;
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener startTimeSetListener, endTimeSetListener;
    Spinner spinnerSounds, spinnerTaskNotificationTime;
    ArrayAdapter<CharSequence> soundList;
    CheckBox chkAllDay;
    Button btnAddTask;

    int oldTaskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Controllers Definition
        txtTaskName = (EditText) findViewById(R.id.txtTaskName);
        txtTaskLocation = (EditText) findViewById(R.id.txtLocation);
        txtTaskDate = (EditText) findViewById(R.id.txtTaskDate);
        txtStartTime = (EditText) findViewById(R.id.txtStartTime);
        txtEndTime = (EditText) findViewById(R.id.txtEndTime);
        txtTaskDescription = (EditText) findViewById(R.id.txtTaskDescription);
        txtTaskParticipants = (EditText) findViewById(R.id.txtTaskParticipants);
        spinnerTaskNotificationTime = (Spinner) findViewById(R.id.spinnerTaskNotificationTime);
        spinnerSounds = (Spinner) findViewById(R.id.spinnerSounds);
        chkAllDay = (CheckBox) findViewById(R.id.chkAllDay);
        btnAddTask = (Button) findViewById(R.id.btnAddTask);

        //Variable Definition
        if(getIntent().getExtras().get("taskId") != null)
            oldTaskId = getIntent().getExtras().getInt("taskId");



        //if intent to modification
        if(oldTaskId != -1){
            TaskDB taskDB = new TaskDB(AddTaskActivity.this);
            Task tempTask = Task.getTaskById(oldTaskId, AddTaskActivity.this);
            txtTaskName.setText(tempTask.getTask_name());
            txtTaskDescription.setText(tempTask.getTask_description());
            txtTaskParticipants.setText(tempTask.getTask_participants());
            txtTaskLocation.setText(tempTask.getTask_location());
            txtTaskDate.setText(DateEx.getDateString(tempTask.getTask_date()));
            txtStartTime.setText(DateEx.getTimeString(tempTask.getTask_start()));
            txtEndTime.setText(DateEx.getTimeString(tempTask.getTask_end()));

            btnAddTask.setText("Update");
        }else{
            //Variables Definition
            final long selectedDate = (long)getIntent().getExtras().get("selectedDate");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(selectedDate));
            txtTaskDate.setText(DateEx.getDateString(calendar.getTime()));
        }
        soundList = ArrayAdapter.createFromResource(AddTaskActivity.this, R.array.sound_list, R.layout.support_simple_spinner_dropdown_item);
        soundList.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSounds.setAdapter(soundList);


        txtTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddTaskActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog,
                        dateSetListener,
                        DateEx.getYearOf(null),
                        DateEx.getMonthOf(null),
                        DateEx.getDayOf(null)
                        );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        txtStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddTaskActivity.this,
                        startTimeSetListener,
                        12,0,
                        false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        txtEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddTaskActivity.this,
                        endTimeSetListener,
                        12, 0,
                        false
                        );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = i+"-"+(i1+1)+"-"+i2;
                txtTaskDate.setText(date);
            }
        };

        startTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = i + ":" + i1;
                txtStartTime.setText(time);
            }
        };

        endTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = i + ":" + i1;
                txtEndTime.setText(time);
            }
        };

        spinnerSounds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MediaPlayer mediaPlayer = null;
                String soundName = adapterView.getItemAtPosition(i).toString().toLowerCase();
                switch (soundName){
                    case "buzz" :
                        mediaPlayer = MediaPlayer.create(AddTaskActivity.this, R.raw.buzz);
                        break;
                    case "gets in the way" :
                        mediaPlayer = MediaPlayer.create(AddTaskActivity.this, R.raw.gets_in_the_way);
                        break;
                    case "jingle bells" :
                        mediaPlayer = MediaPlayer.create(AddTaskActivity.this, R.raw.jingle_bells);
                        break;
                    case "rooster" :
                        mediaPlayer = MediaPlayer.create(AddTaskActivity.this, R.raw.rooster);
                        break;
                    case "siren" :
                        mediaPlayer = MediaPlayer.create(AddTaskActivity.this, R.raw.siren);
                        break;
                    case "system fault" :
                        mediaPlayer = MediaPlayer.create(AddTaskActivity.this, R.raw.system_fault);
                        break;
                }
                if(mediaPlayer != null)
                    mediaPlayer.start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        chkAllDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chkAllDay.isChecked()) {
                    txtStartTime.setText(DateEx.getTimeString(DateEx.getTodayMorning()));
                    txtEndTime.setText(DateEx.getTimeString(DateEx.getTodayMidNight()));
                    txtStartTime.setEnabled(false);
                    txtEndTime.setEnabled(false);
                }
                else{
                    txtStartTime.setEnabled(true);
                    txtEndTime.setEnabled(true);
                }
            }
        });


        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oldTaskId != -1){
                    finish();
                    return;
                }
                Task task = new Task();
                task.setTask_id(0);
                task.setTask_name(txtTaskName.getText().toString().trim());
                task.setTask_location(txtTaskLocation.getText().toString().trim());
                task.setTask_date(DateEx.getDateOfDate(txtTaskDate.getText().toString().trim()));
                task.setTask_description(txtTaskDescription.getText().toString().trim());
                task.setTask_start(DateEx.getDateOfTime(txtStartTime.getText().toString().trim()));
                task.setTask_end(DateEx.getDateOfTime(txtEndTime.getText().toString().trim()));
                task.setTask_participants(txtTaskParticipants.getText().toString().trim());
                task.setIs_all_day_task(chkAllDay.isChecked());
                task.setTask_notification_sound(spinnerSounds.getSelectedItem().toString().trim());
                String notify = (String) spinnerTaskNotificationTime.getSelectedItem();
                switch(notify){
                    case "On time" :
                        task.setTask_notification_time(task.getTask_start());
                        break;
                    case "Before 2 minutes" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 2));
                        break;
                    case "Before 5 minutes" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 5));
                        break;
                    case "Before 10 minutes" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 10));
                        break;
                    case "Before 30 minutes" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 30));
                        break;
                    case "Before 1 hour" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 60));
                        break;
                    case "Before 3 hours" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 180));
                        break;
                    case "Before 1 day" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 1440));
                        break;
                    default:
                        task.setTask_notification_time(task.getTask_start());
                        break;
                }

                TaskDB taskDB = new TaskDB(AddTaskActivity.this);
                if(taskDB.insert(task))
                    Toast.makeText(AddTaskActivity.this, "Data Inserted into database", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AddTaskActivity.this, "Error while inserting data", Toast.LENGTH_LONG).show();
            }
        });
    }
}
