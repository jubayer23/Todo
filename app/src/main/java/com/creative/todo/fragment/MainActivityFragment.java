package com.creative.todo.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.creative.event.R;
import com.creative.todo.Utility.DateTimeUtil;
import com.creative.todo.adapter.eventAdapter;
import com.creative.todo.appdata.AppConstant;
import com.creative.todo.appdata.AppController;
import com.creative.todo.model.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jubayer on 8/8/2017.
 */

public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private List<Event> events = new ArrayList<>();
    private RecyclerView recyclerView;
    private eventAdapter eventAdapter;
    private FloatingActionButton btn_add;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        // Initialize the layout view ids
        init(view);

        // initialize listView adapter
        initAdapter();

        //load all event from database and show them in the UI
        initializeUi();

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        btn_add = (FloatingActionButton) view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
    }


    private void initAdapter() {
        eventAdapter = new eventAdapter(events, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(eventAdapter);
        eventAdapter.notifyDataSetChanged();

    }


    private void initializeUi() {

        // Receive all event from the database
        events.addAll(AppController.getSQLiteDbInstance().getAllEvents());
        eventAdapter.notifyDataSetChanged();

    }

    /**
     * After adding new event we need to update the UI again
     * */
    private void updateUi(Event event) {
        events.add(event);
        eventAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_add) {
            // Method for adding new event to the database
            showDialogForAddEvent();
        }
    }


    private void showDialogForAddEvent() {
        final Dialog dialog_start = new Dialog(getActivity(),
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_start.setCancelable(true);
        dialog_start.setContentView(R.layout.dialog_add_event);
        final String[] event_date = {""};


        final EditText ed_todo = (EditText) dialog_start.findViewById(R.id.ed_todo);
        final Button btn_datepicker = (Button) dialog_start.findViewById(R.id.btn_datepicker);
        Button btn_submit = (Button) dialog_start.findViewById(R.id.btn_submit);
        final Spinner sp_priority = (Spinner) dialog_start.findViewById(R.id.sb_priority);


        ArrayAdapter spAdapterTags =
                new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_spinner_item,
                        AppConstant.priorities); //selected item will look like a spinner set from XML
        spAdapterTags.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_priority.setAdapter(spAdapterTags);


        final DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date date = calendar.getTime();

                btn_datepicker.setText(AppConstant.getDateFormat().format(date));
                event_date[0] = AppConstant.getDateTimeFormat().format(date);
            }
        };

        btn_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.callBack(ondate);
                datePicker.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_todo.getText().toString().isEmpty()) {
                    ed_todo.setError("fill up");
                    return;
                }

                if (event_date[0].isEmpty()) {
                    Toast.makeText(getActivity(), "please select a event data!", Toast.LENGTH_LONG).show();
                    return;
                }

                String note = ed_todo.getText().toString();
                int priority = sp_priority.getSelectedItemPosition();

                Event event = new Event(note, AppConstant.STATUS_NOT_COMPLETED, priority, DateTimeUtil.getCurrentDateTime(), event_date[0]);
                AppController.getSQLiteDbInstance().addEvent(event);

                updateUi(event);

                dialog_start.dismiss();
            }
        });

        dialog_start.show();

    }

}
