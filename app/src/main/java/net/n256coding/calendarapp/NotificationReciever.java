package net.n256coding.calendarapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.NotificationCompat;

import net.n256coding.calendarapp.Models.Task;

/**
 * Created by Nishan on 9/20/2017.
 */

public class NotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent uiIntent = (Intent) intent.getExtras().get("ui");
        Task task = (Task)intent.getExtras().get("task");
        uiIntent.putExtra("task", (Parcelable) task);
        context.startActivity(uiIntent);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, ReminderActivity.class), 0);
        Uri soundUri = Uri.parse("android.resource://"+context.getPackageName()+"/raw/"+"buzz");


        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("This is a notification")
                .setContentText("Content text")
                .setContentIntent(pendingIntent)
                .setTicker("You have task to done!")
                .setSound(soundUri);


    }
}
