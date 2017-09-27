package net.n256coding.calendarapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import net.n256coding.calendarapp.Helper.ReminderActivator;
import net.n256coding.calendarapp.Models.Task;

public class ReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        if(getIntent() == null)
            finish();
    }

    public void RemindMeAgain(View view){
        Task task = (Task)getIntent().getSerializableExtra("task");
        ReminderActivator.postponeReminder(ReminderActivity.this, task, 1);
        finish();
    }

    public void StopReminder(View view){
        Task task = (Task)getIntent().getSerializableExtra("task");
        ReminderActivator.suspendReminder(ReminderActivity.this, task);
        finish();
    }
}
