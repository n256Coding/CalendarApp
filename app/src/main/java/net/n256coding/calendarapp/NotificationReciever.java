package net.n256coding.calendarapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

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
    }
}
