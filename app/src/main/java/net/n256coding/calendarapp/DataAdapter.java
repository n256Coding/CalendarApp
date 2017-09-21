package net.n256coding.calendarapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.n256coding.calendarapp.Database.TaskDB;
import net.n256coding.calendarapp.Models.Task;

import java.util.List;

/**
 * Created by Nishan on 9/19/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{
    private Context context;
    private List<Task> tasks;

    public DataAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtTaskName.setText(tasks.get(position).getTask_name());
        holder.txtTaskLocation.setText(tasks.get(position).getTask_location());
        holder.txtTaskDate.setText(tasks.get(position).getTask_date().toString());
        holder.txtTaskStartTime.setText(tasks.get(position).getTask_start().toString());
        final String taskName = tasks.get(position).getTask_name();
        final int taskId = tasks.get(position).getTask_id();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, taskName, Toast.LENGTH_SHORT).show();
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_add_remove, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.menuItem_editTask){
                            Toast.makeText(context, "Ready to modify", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, AddTaskActivity.class);
                            intent.putExtra("taskId", taskId);
                            context.startActivity(intent);
                        }else if(item.getItemId() == R.id.menuItem_removeTask){
                            TaskDB taskDB = new TaskDB(context);
                            if(taskDB.delete(taskId)){
                                tasks.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(context, "Task Removed", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(context, "Task not Removed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTaskName;
        private TextView txtTaskDate;
        private TextView txtTaskLocation;
        private TextView txtTaskStartTime;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            txtTaskName = (TextView) itemView.findViewById(R.id.txtTaskName);
            txtTaskDate = (TextView) itemView.findViewById(R.id.txtTaskDate);
            txtTaskLocation = (TextView) itemView.findViewById(R.id.txtTaskLocation);
            txtTaskStartTime = (TextView) itemView.findViewById(R.id.txtTaskStartTime);
        }
    }
}
