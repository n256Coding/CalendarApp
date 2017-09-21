package net.n256coding.calendarapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;

import net.n256coding.calendarapp.Database.TaskDB;
import net.n256coding.calendarapp.Models.Task;

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
            intent.putExtra("task", (Parcelable) task);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, task.getTask_notification_time().getTime(), pendingIntent);
        }
    }
}
