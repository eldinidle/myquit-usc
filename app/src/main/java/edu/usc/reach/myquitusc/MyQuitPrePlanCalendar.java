package edu.usc.reach.myquitusc;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


public class MyQuitPrePlanCalendar extends Activity {

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Please complete plans before closing application", Toast.LENGTH_SHORT).show();
    }

    static ArrayAdapter<String> timeArrayAdapter;

    String[] pulledTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_calendar);
        Intent pickupDate = getIntent();
        final String calledDate = pickupDate.getStringExtra("Date");
        final int focusPosition = pickupDate.getIntExtra("FocusPosition",0);
        final boolean weekEnd = pickupDate.getBooleanExtra("Weekend",false);
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


        TextView titleCalendar = (TextView) findViewById(R.id.calendarTitle);
        if(!weekEnd) {
            titleCalendar.setText("Your Typical Weekday (Mon-Fri)");
        }
        if(weekEnd){
            titleCalendar.setText("Your Typical Weekend (Sat-Sun)");
        }
        ListView todayView = (ListView) findViewById(R.id.listView);
        timeArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, pulledTimes);
        todayView.setAdapter(timeArrayAdapter);
        todayView.setSelection(focusPosition);
        todayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent taskLaunch = new Intent(view.getContext(), MyQuitTasksActivity.class);
                    taskLaunch.putExtra("PrePlan", true);
                    taskLaunch.putExtra("Weekend", weekEnd);
                    taskLaunch.putExtra("timeCode", pulledTimes[position]);
                    taskLaunch.putExtra("positionTime", position);
                    taskLaunch.putExtra("calledDate", calledDate);
                    MyQuitPrePlanCalendar.this.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
                    startActivity(taskLaunch);
                //DialogFragment tasksFragment = TasksFragmentDialog.newInstance(pulledTimes[position], position, calledDate);
                //tasksFragment.show(getFragmentManager(), "dialog");
            }
        });

        todayView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pulledTimes[position] = MyQuitCSVHelper.defaultTimes[position];
                try {
                    MyQuitCSVHelper.pushDateTimes(calledDate, pulledTimes);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(view.getContext(), "Warning: Not Synced", Toast.LENGTH_LONG);
                }
                Intent launchBack = new Intent(view.getContext(), MyQuitPrePlanCalendar.class);
                launchBack.putExtra("Date",calledDate);
                launchBack.putExtra("FocusPosition",position);
                launchBack.putExtra("Weekend",weekEnd);
                launchBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MyQuitPrePlanCalendar.this.overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_top);
                startActivity(launchBack);
                return false;
            }
        });

        Button homeButton = (Button) findViewById(R.id.calendarHome);
        homeButton.setText("Hold Button to Submit");
        homeButton.setOnLongClickListener(new View.OnLongClickListener() {
                  @Override
                  public boolean onLongClick(View v) {
                      if(weekEnd) {
                          if(MyQuitAutoAssign.minimumLabelConfirm("DEFAULT_WEEKEND")) {
                              Intent homeLaunch = new Intent(v.getContext(), MyQuitHomeScreen.class);
                              homeLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                      | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                              MyQuitPrePlanCalendar.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                              MyQuitCSVHelper.logLoginEvents("completedPlans", MyQuitCSVHelper.getFulltime());
                              startActivity(homeLaunch);
                          }
                          else {
                              Toast.makeText(v.getContext(),"Please enter at least " +
                                      MyQuitAutoAssign.minimumLabels + " labels in the day",
                                      Toast.LENGTH_LONG).show();
                          }
                      }
                      if(!weekEnd){
                          if(MyQuitAutoAssign.minimumLabelConfirm("DEFAULT_WEEKDAY")) {
                              Intent homeLaunch = new Intent(v.getContext(), MyQuitPrePlanCalendar.class);
                              homeLaunch.putExtra("Date", "DEFAULT_WEEKEND");
                              homeLaunch.putExtra("Weekend", true);
                              homeLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                      | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                              finish();
                              startActivity(homeLaunch);
                          }
                          else {
                              Toast.makeText(v.getContext(),"Please enter at least " +
                                              MyQuitAutoAssign.minimumLabels + " labels in the day",
                                      Toast.LENGTH_LONG).show();
                          }
                      }
                      return false;
                  }
              });

        Button moveBack = (Button) findViewById(R.id.previousDay);
        moveBack.setVisibility(View.GONE);

        Button moveForward = (Button) findViewById(R.id.nextDay);
        moveForward.setVisibility(View.GONE);
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
