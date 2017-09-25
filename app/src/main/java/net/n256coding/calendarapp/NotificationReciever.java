package net.n256coding.calendarapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import net.n256coding.calendarapp.Models.Task;

import java.io.Serializable;

/**
 * Created by Nishan on 9/20/2017.
 */

public class NotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Notification recieved", Toast.LENGTH_LONG).show();
        Task task = (Task)intent.getSerializableExtra("task");
        Intent uiIntent = new Intent(context, ReminderActivity.class);
        uiIntent.putExtra("task", task);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                uiIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri soundUri = Uri.parse("android.resource://"+context.getPackageName()+"/raw/"+"buzz");

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(task.getTask_name())
                .setWhen(System.currentTimeMillis())
                .setContentText(task.getTask_description()+"\nLocation: "+task.getTask_location())
                .setContentIntent(pendingIntent)
                .setTicker("You have task to be done!")
                .setSound(soundUri);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }
}
