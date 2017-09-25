package net.n256coding.calendarapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import net.n256coding.calendarapp.Database.TaskDB;
import net.n256coding.calendarapp.Models.Task;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nishan on 9/20/2017.
 */

public abstract class ReminderActivator {

    public static void RunActivator(Context context){
        List<Task> tasks = Task.getAllTasks(context);
        for (Task task : tasks) {
            Intent intent = new Intent(context, NotificationReciever.class);
            intent.putExtra("ui", new Intent(context, ReminderActivity.class));
            intent.putExtra("task", task);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //TODO sout task notification time
            System.out.println(task.getTask_notification_time());
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, task.getTask_notification_time().getTime(), pendingIntent);
        }
    }

    public static void RunActivator(Context context, Task task){
        Intent intent = new Intent(context, NotificationReciever.class);
        intent.putExtra("ui", new Intent(context, ReminderActivity.class));
        intent.putExtra("task", task);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        System.out.println("Test notification time "+task.getTask_notification_time().toString());
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, task.getTask_notification_time().getTime(), pendingIntent);
    }

    public static void PostponeReminder(Context context, Task task, int minutes){
        Intent intent = new Intent(context, NotificationReciever.class);
        intent.putExtra("ui", new Intent(context, ReminderActivity.class));
        intent.putExtra("task", task);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public static void ChangeReminder(Context context, Task task, int minutes){
        Intent intent = new Intent(context, NotificationReciever.class);
        intent.putExtra("ui", new Intent(context, ReminderActivity.class));
        intent.putExtra("task", task);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    //TODO RunActivator_TEST
    public static void RunActivator_TEST(Context context){
        Intent intent = new Intent(context, NotificationReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, new Date().getTime()+(5*1000), pendingIntent);
        Toast.makeText(context, "Alarm set to 5 seconds", Toast.LENGTH_SHORT).show();
    }

    public static void SuspendReminder(Context context, Task task){
        Intent intent = new Intent(context, NotificationReciever.class);
        intent.putExtra("ui", new Intent(context, ReminderActivity.class));
        intent.putExtra("task", task);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
