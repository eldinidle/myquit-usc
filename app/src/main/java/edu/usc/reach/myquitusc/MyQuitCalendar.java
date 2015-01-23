package edu.usc.reach.myquitusc;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;


public class MyQuitCalendar extends Activity   {

    static ArrayAdapter<String> timeArrayAdapter;

    String[] pulledTimes;

    private static boolean isPositionTimeDisabled(int position) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_calendar);
        Intent pickupDate = getIntent();
        final String calledDate = pickupDate.getStringExtra("Date");
        final int focusPosition = pickupDate.getIntExtra("FocusPosition",0);
        try {
            pulledTimes = MyQuitCSVHelper.pullDateTimes(calledDate);
        } catch (IOException e) {
            pulledTimes = MyQuitCSVHelper.defaultTimes;
            e.printStackTrace();
            try {
                MyQuitCSVHelper.pushDateTimes(calledDate, pulledTimes);
            } catch (IOException e1) {
                e1.printStackTrace();
                Toast.makeText(getApplicationContext(),"Warning: Please reinsert your SD card",Toast.LENGTH_LONG);
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar dummyCal = Calendar.getInstance();
        Date actualDate = null;
        try {
            actualDate = sdf.parse(calledDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dummyCal.setTime(actualDate);
        dummyCal.add(Calendar.DATE, 1);
        final Date nextDate = dummyCal.getTime();
        dummyCal.add(Calendar.DATE,-2);
        final Date previousDate = dummyCal.getTime();

        TextView titleCalendar = (TextView) findViewById(R.id.calendarTitle);
        titleCalendar.setText(calledDate);
        ListView todayView = (ListView) findViewById(R.id.listView);
        timeArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, pulledTimes);
        todayView.setAdapter(timeArrayAdapter);
        todayView.setSelection(focusPosition);
        todayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MQU-CH","lock is" + MyQuitCalendarHelper.returnLockedHour(calledDate));
                if ((position > MyQuitCalendarHelper.returnLockedHour(calledDate)-1)) {
                    Intent taskLaunch = new Intent(view.getContext(), MyQuitTasksActivity.class);
                    taskLaunch.putExtra("timeCode", pulledTimes[position]);
                    taskLaunch.putExtra("positionTime", position);
                    taskLaunch.putExtra("calledDate", calledDate);
                    MyQuitCalendar.this.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
                    startActivity(taskLaunch);
                    //DialogFragment tasksFragment = TasksFragmentDialog.newInstance(pulledTimes[position], position, calledDate);
                    //tasksFragment.show(getFragmentManager(), "dialog");
                }
                else {
                    Toast.makeText(getApplicationContext(),"You can't edit this time anymore",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button homeButton = (Button) findViewById(R.id.calendarHome);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeLaunch = new Intent(v.getContext(), MyQuitHomeScreen.class);
                homeLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MyQuitCalendar.this.overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
                startActivity(homeLaunch);
            }
        });

        Button moveBack = (Button) findViewById(R.id.previousDay);
        moveBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todayLaunch = new Intent(v.getContext(), MyQuitCalendar.class);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String todayDateSDF = sdf.format(previousDate.getTime());
                todayLaunch.putExtra("Date",todayDateSDF);
                todayLaunch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MyQuitCalendar.this.overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
                startActivity(todayLaunch);
            }
        });

        Button moveForward = (Button) findViewById(R.id.nextDay);
        moveForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todayLaunch = new Intent(v.getContext(), MyQuitCalendar.class);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String todayDateSDF = sdf.format(nextDate.getTime());
                todayLaunch.putExtra("Date",todayDateSDF);
                todayLaunch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MyQuitCalendar.this.overridePendingTransition(R.anim.right_to_left,R.anim.left_to_right);
                startActivity(todayLaunch);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeLaunch = new Intent((getApplicationContext()), MyQuitHomeScreen.class);
        homeLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyQuitCalendar.this.overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
        startActivity(homeLaunch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_quit_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    public static class TasksFragmentDialog extends DialogFragment {
        String timeTitle;
        int positionTitle;
        String callingDate;

        static TasksFragmentDialog newInstance(String timeCode, int pOfTime, String callerDate) {
            TasksFragmentDialog tdf = new TasksFragmentDialog();

            Bundle args = new Bundle();
            args.putString("timeCode", timeCode);
            args.putInt("positionTime", pOfTime);
            args.putString("calledDate", callerDate);
            tdf.setArguments(args);

            return tdf;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            timeTitle = getArguments().getString("timeCode");
            positionTitle = getArguments().getInt("positionTime");
            callingDate = getArguments().getString("calledDate");
            getDialog().setTitle(timeTitle);
            View v = inflater.inflate(R.layout.fragment_tasks_list, container, false);
            ListView tasksView = (ListView) v.findViewById(R.id.allTasks);
            ArrayAdapter<String> tasksArrayAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_activated_1, TASKS_LIST);
            tasksView.setAdapter(tasksArrayAdapter);
            tasksView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pulledTimes[positionTitle] = timeTitle + " - " + TASKS_LIST[position];
                    timeArrayAdapter.notifyDataSetChanged();
                    try {
                        MyQuitCSVHelper.pushDateTimes(callingDate, pulledTimes);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),"Warning: Not Synced", Toast.LENGTH_LONG);
                    }
                    getDialog().dismiss();
                }
            });
            return v;
        }
    }
    */
}
