package edu.usc.reach.myquituscnew;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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


public class MyQuitPrePlanCalendar extends Activity {

    private String getCalledDate(){
        Intent pickupDate = getIntent();
        return pickupDate.getStringExtra("Date");
    }

    private int getFocusPosition(){
        Intent pickupDate = getIntent();
        return pickupDate.getIntExtra("FocusPosition",0);
    }

    private boolean getWeekEnd() {
        Intent pickupDate = getIntent();
        return pickupDate.getBooleanExtra("Weekend", false);
    }

    private boolean getInstruct() {
        Intent pickupDate = getIntent();
        return pickupDate.getBooleanExtra("Instruct",false);
    }

    private boolean getFromHome() {
        Intent pickupDate = getIntent();
        return pickupDate.getBooleanExtra("FromHome",false);
    }


    void runHomeButton(){

        Button homeButton = (Button) findViewById(R.id.calendarHome);
        homeButton.setText("Submit");
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean weekEnd = getWeekEnd();
                boolean fromHome = getFromHome();

                if (weekEnd) {
                    if (MyQuitAutoAssign.minimumLabelConfirm("DEFAULT_WEEKEND")) {
                        MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"Default Calendar Changed","NA",MyQuitCSVHelper.getFulltime());
                        if(!fromHome){setEndOfDay();}
                        else{
                            MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"Default Weekend Changed","NA",MyQuitCSVHelper.getFulltime());
                            Intent homeLaunch = new Intent(getApplicationContext(), MyQuitHomeScreen.class);
                            homeLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            MyQuitPrePlanCalendar.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                            startActivity(homeLaunch);
                        }
                    } else {
                        Toast.makeText(v.getContext(), "Please enter at least " +
                                        MyQuitAutoAssign.minimumLabels + " labels in the day",
                                Toast.LENGTH_LONG).show();
                    }
                }
                if (!weekEnd) {
                    if (MyQuitAutoAssign.minimumLabelConfirm("DEFAULT_WEEKDAY")) {
                        if(!fromHome) {
                            Intent homeLaunch = new Intent(v.getContext(), MyQuitPrePlanCalendar.class);
                            homeLaunch.putExtra("Date", "DEFAULT_WEEKEND");
                            homeLaunch.putExtra("Weekend", true);
                            homeLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(homeLaunch);
                        }
                        else {
                            MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"Default Weekday Changed","NA",MyQuitCSVHelper.getFulltime());
                            Intent homeLaunch = new Intent(getApplicationContext(), MyQuitHomeScreen.class);
                            homeLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            MyQuitPrePlanCalendar.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                            startActivity(homeLaunch);
                        }
                    } else {
                        Toast.makeText(v.getContext(), "Please enter at least " +
                                        MyQuitAutoAssign.minimumLabels + " labels in the day",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    void runTodayView(){

        int focusPosition = getFocusPosition();
        ListView todayView = (ListView) findViewById(R.id.listView);
        timeArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, pulledTimes);
        todayView.setAdapter(timeArrayAdapter);
        todayView.setSelection(focusPosition);
        todayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean weekEnd = getWeekEnd();
                boolean fromHome = getFromHome();
                String calledDate = getCalledDate();
                if ((pulledTimes[position].matches(MyQuitCSVHelper.defaultTimes[position]))) {
                    Intent taskLaunch = new Intent(view.getContext(), MyQuitTasksActivity.class);
                    taskLaunch.putExtra("PrePlan", true);
                    taskLaunch.putExtra("FromHome", fromHome);
                    taskLaunch.putExtra("Weekend", weekEnd);
                    taskLaunch.putExtra("timeCode", pulledTimes[position]);
                    taskLaunch.putExtra("positionTime", position);
                    taskLaunch.putExtra("calledDate", calledDate);
                    MyQuitPrePlanCalendar.this.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
                    startActivity(taskLaunch);
                    //DialogFragment tasksFragment = TasksFragmentDialog.newInstance(pulledTimes[position], position, calledDate);
                    //tasksFragment.show(getFragmentManager(), "dialog");
                }
                else {
                    Toast.makeText(getApplicationContext(),"Hold down to erase first before adding.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        todayView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(!(pulledTimes[position].matches(MyQuitCSVHelper.defaultTimes[position]))) {
                    String calledDate = getCalledDate();
                    boolean fromHome = getFromHome();
                    boolean weekEnd = getWeekEnd();
                    pulledTimes[position] = MyQuitCSVHelper.defaultTimes[position];
                    MyQuitPHP.postCalendarEvent(MyQuitCSVHelper.pullLoginStatus("UserName"), calledDate, MyQuitCSVHelper.defaultTimes[position], "DELETED", "DELETED", MyQuitCSVHelper.getFulltime());
                    try {
                        MyQuitCSVHelper.pushDateTimes(calledDate, pulledTimes);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(view.getContext(), "Warning: Not Synced", Toast.LENGTH_LONG);
                    }
                    Intent launchBack = new Intent(view.getContext(), MyQuitPrePlanCalendar.class);
                    launchBack.putExtra("Date", calledDate);
                    launchBack.putExtra("FocusPosition", position);
                    launchBack.putExtra("Weekend", weekEnd);
                    launchBack.putExtra("FromHome", fromHome);
                    launchBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    MyQuitPrePlanCalendar.this.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
                    startActivity(launchBack);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Sorry, this entry is already empty...",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private void setEndOfDay(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[] hourList = {"8:00 PM","8:30 PM","9:00 PM","9:30 PM","10:00 PM","10:30 PM","11:00 PM","11:30 PM"};
        //String[] hourList = new String[]{"8:00 PM","9:00 PM","10:00 PM","11:00 PM"};
        //ArrayAdapter<String> hoursAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_activated_1,hourList);
        builder.setTitle(("Select preferred time to receive your evening survey:"))
                .setCancelable(false)
                .setItems(hourList,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyQuitCSVHelper.logLoginEvents("EOD Prompt",String.valueOf(which),MyQuitCSVHelper.getFulltime());
                        MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"EOD Prompt Selected",String.valueOf(which),MyQuitCSVHelper.getFulltime());
                        Intent homeLaunch = new Intent(getApplicationContext(), MyQuitHomeScreen.class);
                        homeLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        MyQuitPrePlanCalendar.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                        MyQuitCSVHelper.logLoginEvents("completedPlans", MyQuitCSVHelper.getFulltime());
                        startActivity(homeLaunch);
                        dialog.dismiss();
                    }
                });

                /*.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,hourList)
                        ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyQuitCSVHelper.logLoginEvents("EOD Prompt",String.valueOf(which+20),MyQuitCSVHelper.getFulltime());
                MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"EOD Prompt Selected",String.valueOf(which+20),MyQuitCSVHelper.getFulltime());
                Intent homeLaunch = new Intent(getApplicationContext(), MyQuitHomeScreen.class);
                homeLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MyQuitPrePlanCalendar.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                MyQuitCSVHelper.logLoginEvents("completedPlans", MyQuitCSVHelper.getFulltime());
                startActivity(homeLaunch);
                dialog.dismiss();
            }
        });*/
        AlertDialog dialog = builder.create();
        dialog.show();
        formatDialog(dialog);
    }

    public void formatDialog(AlertDialog dialog) {
        Button posButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        posButton.setTextColor(getResources().getColor(R.color.ActiveText));
        Button negButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negButton.setTextColor(getResources().getColor(R.color.ActiveText));
        Button neuButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        neuButton.setTextColor(getResources().getColor(R.color.ActiveText));

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = dialog.findViewById(dividerId);
            divider.setBackgroundColor(getResources().getColor(R.color.AppBar));
        }

        int textViewId = dialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
        TextView tv = (TextView) dialog.findViewById(textViewId);
        tv.setTextColor(getResources().getColor(R.color.AppBar));
    }

    private void showInstruction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(("Please select each hour you wish to schedule. If you change your mind, hold down the hour to clear your entry."))
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        formatDialog(dialog);
    }

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
          String calledDate = pickupDate.getStringExtra("Date");
          int focusPosition = pickupDate.getIntExtra("FocusPosition",0);
          boolean weekEnd = pickupDate.getBooleanExtra("Weekend",false);
          boolean instruct = pickupDate.getBooleanExtra("Instruct",false);
          boolean fromHome = pickupDate.getBooleanExtra("FromHome",false);

        Log.d("MQU-PREPLAN","Date" + calledDate + "focusPos" + focusPosition + "weekend is " + weekEnd + "instruct is " + instruct + "fromHome is" + fromHome);

        if(instruct){showInstruction();}

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

        runTodayView( );

        runHomeButton( );



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
