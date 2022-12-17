package com.example.group_project;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.group_project.CalendarUtils.daysInMonthArray;
import static com.example.group_project.CalendarUtils.monthYearFromDate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener{


    Button weeklyAction,nextMonthAction,previousMonthAction;
    TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Calendar");

        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);

        monthYearText = view.findViewById(R.id.monthYearTV);

        weeklyAction = view.findViewById(R.id.weeklyAction);
        weeklyAction.setOnClickListener(this::weeklyAction);

        nextMonthAction = view.findViewById(R.id.nextMonthAction);
        nextMonthAction.setOnClickListener(this::nextMonthAction);

        previousMonthAction = view.findViewById(R.id.previousMonthAction);
        previousMonthAction.setOnClickListener(this::previousMonthAction);

        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray();

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }
    public void weeklyAction(View view)
    {
        startActivity(new Intent(getActivity(), WeekViewActivity.class));
    }

}








