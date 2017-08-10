package com.creative.todo.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.creative.event.R;
import com.creative.todo.Utility.DateTimeUtil;
import com.creative.todo.appdata.AppConstant;
import com.creative.todo.appdata.AppController;
import com.creative.todo.model.Event;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jubayer on 8/8/2017.
 */

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.MyViewHolder> {

    List<Event> events;
    private Context activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_todo, tv_created_at, tv_event_date, tv_priority;
        ImageView img_event_status, img_drop_menu, img_delete;

        public MyViewHolder(View view) {
            super(view);
            tv_todo = (TextView) view.findViewById(R.id.tv_todo);
            tv_created_at = (TextView) view.findViewById(R.id.tv_created_at);
            tv_event_date = (TextView) view.findViewById(R.id.tv_event_date);
            tv_priority = (TextView) view.findViewById(R.id.tv_priority);
            img_event_status = (ImageView) view.findViewById(R.id.img_event_status);
            img_drop_menu = (ImageView) view.findViewById(R.id.img_drop_menu);
            img_delete = (ImageView) view.findViewById(R.id.img_delete);
        }
    }


    public eventAdapter(List<Event> events, Context activity) {
        this.events = events;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_todo, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Event event = events.get(position);
        // Log.d("DEBUG",String.valueOf(events.size()));

        holder.tv_todo.setText(event.getNote());
        holder.tv_created_at.setText("Created at : " + event.getCreated_at());
        holder.tv_event_date.setText("Event date : " + event.getEvent_date());
        holder.tv_priority.setText( AppConstant.priorities[event.getPriority()]);
        holder.tv_priority.setTextColor(activity.getResources().getColor(getTextColorBaseOnPriority(event.getPriority())));
        holder.img_event_status.setImageResource(getImageResourceBaseOnEventStatus(event.getStatus(), event.getEvent_date()));
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent(position, event.getId());
            }
        });

        if (event.getStatus() == AppConstant.STATUS_COMPLETED) {
            holder.img_drop_menu.setVisibility(View.GONE);
        } else {
            holder.img_drop_menu.setVisibility(View.VISIBLE);
            holder.img_drop_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenu(v, holder.img_event_status, event);
                }
            });
        }


        holder.img_event_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpWindow(v,event.getEvent_date());
            }
        });


    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    private void deleteEvent(int list_position, int event_id) {
        AppController.getSQLiteDbInstance().deleteEvent(event_id);

        events.remove(list_position);

        notifyDataSetChanged();
    }

    private int getImageResourceBaseOnEventStatus(int status, String event_date) {

        switch (status) {

            case AppConstant.STATUS_COMPLETED:
                return R.drawable.ic_done_green_24dp;
            case AppConstant.STATUS_NOT_COMPLETED:
                try {
                    Date create_date = AppConstant.getDateFormat().parse(DateTimeUtil.getCurrentDateTime());
                    Date upcoming_date = AppConstant.getDateFormat().parse(event_date);

                    if (upcoming_date.after(create_date)) {
                        return R.drawable.icon_upcoming_event;
                    } else if(upcoming_date.equals(create_date)){
                        return R.drawable.ic_error_outline_black_24dp;
                    }else {
                        return R.drawable.ic_warning_black_24dp;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return R.drawable.icon_upcoming_event;
        }

        return R.drawable.icon_upcoming_event;
    }

    private int getTextColorBaseOnPriority(int priority){

        switch (priority){

            case AppConstant.PRIORITY_HIGH:
                return R.color.red;
            case AppConstant.PRIORITY_MEDIUM:
                return R.color.orange;
            default:
                return R.color.gray_dark;
        }

    }


    public void showMenu(final View view, final ImageView status_icon, final Event event) {
        PopupMenu menu = new PopupMenu(activity, view);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.item_make_default:

                        AppController.getSQLiteDbInstance().updateEventStatus(event.getId(), AppConstant.STATUS_COMPLETED);
                        status_icon.setImageResource(R.drawable.ic_done_green_24dp);
                        event.setStatus(AppConstant.STATUS_COMPLETED);
                        view.setVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });
        menu.inflate(R.menu.menu_event);
        menu.show();
    }


    public void showPopUpWindow(View v, String event_date) {
        PopupWindow popupwindow_obj =  popupDisplay(event_date);
        popupwindow_obj.showAsDropDown(v, -40, 18);
    }

    public PopupWindow popupDisplay(final String event_date) {

        final PopupWindow popupWindow = new PopupWindow(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_status_info, null);
        TextView tv_status_info = (TextView) view.findViewById(R.id.tv_status_info);

        String current_date = DateTimeUtil.getCurrentDateTime();

        try {
            Date startDate = AppConstant.getDateTimeFormat().parse(current_date);
            Date endDate = AppConstant.getDateTimeFormat().parse(event_date);

            String time_diff = DateTimeUtil.printDifference(startDate,endDate);

            tv_status_info.setText(time_diff.isEmpty() ? "Event will happen today!" :
                    "Remaining time : " + time_diff);
            Log.d("DEBUG_DIF",DateTimeUtil.printDifference(startDate,endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);

        return popupWindow;
    }
}
