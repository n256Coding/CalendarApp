package net.n256coding.calendarapp.Models;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import net.n256coding.calendarapp.Database.TaskDB;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nishan on 9/19/2017.
 */

public class Task implements Serializable{
    private int task_id;
    private String task_name;
    private String task_description;
    private String task_location;
    private String task_participants;
    private Date task_date;
    private boolean is_all_day_task;
    private Date task_start;
    private Date task_end;
    private Date task_notification_time;
    private String task_notification_sound;



    public Task() {
    }

    public Task(int task_id, String task_name, String task_description, String task_location,
                String task_participants, Date task_date, boolean is_all_day_task,
                Date task_start, Date task_end, Date task_notification_time) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_description = task_description;
        this.task_location = task_location;
        this.task_participants = task_participants;
        this.task_date = task_date;
        this.is_all_day_task = is_all_day_task;
        this.task_start = task_start;
        this.task_end = task_end;
        this.task_notification_time = task_notification_time;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }

    public String getTask_location() {
        return task_location;
    }

    public void setTask_location(String task_location) {
        this.task_location = task_location;
    }

    public String getTask_participants() {
        return task_participants;
    }

    public void setTask_participants(String task_participants) {
        this.task_participants = task_participants;
    }

    public Date getTask_date() {
        return task_date;
    }

    public void setTask_date(Date task_date) {
        this.task_date = task_date;
    }

    public boolean is_all_day_task() {
        return is_all_day_task;
    }

    public void setIs_all_day_task(boolean is_all_day_task) {
        this.is_all_day_task = is_all_day_task;
    }

    public Date getTask_start() {
        return task_start;
    }

    public void setTask_start(Date task_start) {
        this.task_start = task_start;
    }

    public Date getTask_end() {
        return task_end;
    }

    public void setTask_end(Date task_end) {
        this.task_end = task_end;
    }

    public Date getTask_notification_time() {
        return task_notification_time;
    }

    public void setTask_notification_time(Date task_notification_time) {
        Calendar taskDate = Calendar.getInstance();
        Calendar taskNotificationTime = Calendar.getInstance();
        taskDate.setTime(getTask_date());
        taskNotificationTime.setTime(task_notification_time);
        taskDate.set(Calendar.HOUR_OF_DAY, taskNotificationTime.get(Calendar.HOUR_OF_DAY));
        taskDate.set(Calendar.MINUTE, taskNotificationTime.get(Calendar.MINUTE));
        taskDate.set(Calendar.SECOND, taskNotificationTime.get(Calendar.SECOND));
        this.task_notification_time = taskDate.getTime();
    }

    public String getTask_notification_sound() {
        return task_notification_sound;
    }

    public void setTask_notification_sound(String task_notification_sound) {
        this.task_notification_sound = task_notification_sound;
    }

    public static List<Task> getAllTasks(Context context){
        List<Task> tasks = new ArrayList<>();
        TaskDB taskDB = new TaskDB(context);
        Cursor cursor = taskDB.selectAll();
        while (cursor.moveToNext()) {
            Task tempTask = new Task();
            tempTask.setTask_id(cursor.getInt(0));
            tempTask.setTask_name(cursor.getString(1));
            tempTask.setTask_description(cursor.getString(2));

                tempTask.setTask_date(DateEx.getDateOfDate(cursor.getString(3)));
                tempTask.setTask_start(DateEx.getDateOfTime(cursor.getString(5)));
                tempTask.setTask_end(DateEx.getDateOfTime(cursor.getString(6)));

/*
                Calendar taskDate = Calendar.getInstance();
                Calendar taskNotificationTime = Calendar.getInstance();
                taskDate.setTime(DateEx.getDateOfDate(cursor.getString(3)));
                taskNotificationTime.setTime(DateEx.getDateOfTime(cursor.getString(9)));
                taskDate.set(Calendar.HOUR_OF_DAY, taskNotificationTime.get(Calendar.HOUR_OF_DAY));
                taskDate.set(Calendar.MINUTE, taskNotificationTime.get(Calendar.MINUTE));
                taskDate.set(Calendar.SECOND, taskNotificationTime.get(Calendar.SECOND));
*/
                tempTask.setTask_notification_time(DateEx.getDateOfDateTime(cursor.getString(9)));

            tempTask.setTask_participants(cursor.getString(4));
            tempTask.setTask_location(cursor.getString(7));
            tempTask.setIs_all_day_task(Boolean.valueOf(cursor.getString(8)));
            tempTask.setTask_notification_sound(cursor.getString(10));
            tasks.add(tempTask);
        }
        return tasks;
    }

    public static Task getTaskById(int taskId, Context context){
        Task tempTask = new Task();
        TaskDB taskDB = new TaskDB(context);
        Cursor cursor = taskDB.selectByTaskId(taskId);
        while (cursor.moveToNext()) {
            tempTask.setTask_date(DateEx.getDateOfDate(cursor.getString(3)));
            tempTask.setTask_start(DateEx.getDateOfTime(cursor.getString(5)));
            tempTask.setTask_end(DateEx.getDateOfTime(cursor.getString(6)));


            Calendar taskDate = Calendar.getInstance();
            Calendar taskNotificationTime = Calendar.getInstance();
            taskDate.setTime(DateEx.getDateOfDate(cursor.getString(3)));
            taskNotificationTime.setTime(DateEx.getDateOfTime(cursor.getString(9)));
            taskDate.set(Calendar.HOUR_OF_DAY, taskNotificationTime.get(Calendar.HOUR_OF_DAY));
            taskDate.set(Calendar.MINUTE, taskNotificationTime.get(Calendar.MINUTE));
            taskDate.set(Calendar.SECOND, taskNotificationTime.get(Calendar.SECOND));

            tempTask.setTask_notification_time(taskDate.getTime());
            tempTask.setTask_participants(cursor.getString(4));
            tempTask.setTask_location(cursor.getString(7));
            tempTask.setIs_all_day_task(Boolean.valueOf(cursor.getString(8)));
            tempTask.setTask_notification_sound(cursor.getString(10));
        }
        return tempTask;
    }

    public static Task[] getTasksByDate(){
        return new Task[5];
    }

    public static Task[] getTasksByDateRange(){
        return new Task[5];
    }

}
