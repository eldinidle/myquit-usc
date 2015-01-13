package edu.usc.reach.myquitusc;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyQuitHomeScreen extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_home_screen);
        boolean creatStruc = MyQuitCSVHelper.createStructure();

        if(MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
            AlarmManager alarmMgr;
            PendingIntent alarmIntent;
            alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent loopAlarm = new Intent(this, MyQuitReceiver.class);
            alarmIntent = PendingIntent.getBroadcast(this, 0, loopAlarm, 0);
            Calendar currentTime = Calendar.getInstance();
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, currentTime.getTimeInMillis(),
                    1000 * 60 * 2, alarmIntent);
        }

        if (!creatStruc) {
            Toast.makeText(getApplicationContext(), "Warning: Directory not created", Toast.LENGTH_LONG).show();
        }


        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarViewOut);
        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
                    Intent todayLaunch = new Intent(v.getContext(), MyQuitCalendar.class);
                    Calendar todayDate = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String todayDateSDF = sdf.format(todayDate.getTime());
                    todayLaunch.putExtra("Date", todayDateSDF);
                    MyQuitHomeScreen.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                    startActivity(todayLaunch);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please complete your MyQuit 15 Plan first!",Toast.LENGTH_SHORT).show();
                }

            }
        });

            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    if(MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
                        Intent otherLaunch = new Intent(view.getContext(), MyQuitCalendar.class);
                        String stringYear = String.format("%04d", year);
                        String stringMonth = String.format("%02d", (month + 1));
                        String stringDOM = String.format("%02d", dayOfMonth);
                        String otherDateSDF = stringMonth + "/" + stringDOM + "/" + stringYear;
                        otherLaunch.putExtra("Date", otherDateSDF);
                        MyQuitHomeScreen.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                        startActivity(otherLaunch);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Please complete your MyQuit 15 Plan first!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        Button oopsSmoke = (Button) findViewById(R.id.oopsSmoked);
        oopsSmoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
                    Calendar smokeDate = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        MyQuitCSVHelper.pushCigarette(sdf.format(smokeDate.getTime()), MyQuitCSVHelper.getFulltime());
                        MyQuitCSVHelper.logRogueEvent(MyQuitCSVHelper.getFulltime());
                        Toast.makeText(getApplicationContext(), ("Thank you for reporting your smoking."), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Please insert SD card or restart application.", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please complete your MyQuit 15 Plan first!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button progressTracker = (Button) findViewById(R.id.progressButton);
        progressTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
                    Intent openProgress = new Intent(getApplicationContext(), MyQuitProgress.class);
                    MyQuitHomeScreen.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                    startActivity(openProgress);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please complete your MyQuit 15 Plan first!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button gonnaSmoke = (Button) findViewById(R.id.smokeNow);
        gonnaSmoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment older = getFragmentManager().findFragmentByTag("impintent");
                    if (older != null) {
                        ft.remove(older);
                    }
                    ft.addToBackStack(null);

                    // Create and show the dialog.
                    DialogFragment impIntent = ImpIntentDialog.newInstance();
                  //  MyQuitCSVHelper.logEMAEvents("intentPresented", MyQuitCSVHelper.getFulltime());
                    impIntent.show(ft, "impintent");
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please complete your MyQuit 15 Plan first!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button launchPlans = (Button) findViewById(R.id.quitPlans);
        launchPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
                    Toast.makeText(getApplicationContext(),"You've already completed this portion of the study!",Toast.LENGTH_SHORT).show();;
                }
                else {
                    Intent startPlan = new Intent(getApplicationContext(), MyQuitPlans.class);
                    startPlan.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    MyQuitHomeScreen.this.overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
                    startActivity(startPlan);
                    finish();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_quit_home_screen, menu);
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
        if (id == R.id.renewFolders) {
            MyQuitCSVHelper.deleteAndRefresh(true,495030);
            finish();
            return true;
        }
        if (id == R.id.loginToApp) {
            Intent launchLogin = new Intent(this, MyQuitLoginActivity.class);
            startActivity(launchLogin);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class ImpIntentDialog extends DialogFragment {
        // String timeTitle;

        static ImpIntentDialog newInstance() {
            ImpIntentDialog tdf = new ImpIntentDialog();

          //  Bundle args = new Bundle();
          //  args.putString("timeCode", timeCode);
          //  tdf.setArguments(args);
            return tdf;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //timeTitle = getArguments().getString("timeCode");
            final String[] NEW_TASKS_LIST = MyQuitPlanHelper.pullTasksList(getActivity());
            final String[] NEW_INTENTS_LIST = MyQuitPlanHelper.pullIntentsList(getActivity());



            View v = inflater.inflate(R.layout.fragment_imp_intent, container, false);

            getDialog().setTitle(Html.fromHtml("<font color='#004D40'>What are you doing now?</font>"));

            final ListView activityList = (ListView) v.findViewById(R.id.chooseCraveEvent);
            final Button closeImp = (Button) v.findViewById(R.id.confirmIntent);
            final TextView intentView = (TextView) v.findViewById(R.id.viewIntent);
            intentView.setVisibility(View.GONE);
            closeImp.setVisibility(View.GONE);

            ArrayAdapter<String> tasksArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, NEW_TASKS_LIST);
            if(NEW_TASKS_LIST==null) {
                tasksArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, MyQuitTasksActivity.TASKS_LIST);
            }

            activityList.setAdapter(tasksArrayAdapter);
            activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String pulledActivity;
                    try {
                       pulledActivity = NEW_TASKS_LIST[position];
                   }
                   catch (Exception neo) {
                        pulledActivity = MyQuitTasksActivity.TASKS_LIST[position];
                   }
                   MyQuitCSVHelper.logCraveEvent(pulledActivity,MyQuitCSVHelper.getFulltime());
                   getDialog().setTitle(Html.fromHtml("<font color='#004D40'>Instead of smoking...</font>"));
                    ArrayAdapter<String> blankAA = new ArrayAdapter<>(getActivity(),
                            android.R.layout.simple_list_item_activated_1, new String[] {""});
                activityList.setAdapter(blankAA);
                    activityList.setClickable(false);
                   activityList.setVisibility(View.INVISIBLE);
                 intentView.setText(NEW_INTENTS_LIST[position]);
                  intentView.getLayout();
                  intentView.setVisibility(View.VISIBLE);
                 intentView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                   closeImp.setVisibility(View.VISIBLE);
                }
            });


            closeImp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyQuitCSVHelper.logEMAEvents("intentPresented", MyQuitCSVHelper.getFulltime());
                    getDialog().dismiss();
                }
            });

            return v;
        }
    }

    public static class RogueButtonDialog extends DialogFragment {
        // String timeTitle;

        static RogueButtonDialog newInstance() {
            RogueButtonDialog tdf = new RogueButtonDialog();

            //  Bundle args = new Bundle();
            //  args.putString("timeCode", timeCode);
            //  tdf.setArguments(args);
            return tdf;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //timeTitle = getArguments().getString("timeCode");

            View v = inflater.inflate(R.layout.fragment_rogue_event, container, false);

            getDialog().setTitle(Html.fromHtml("<font color='#004D40'>What were you doing?</font>"));


            return v;
        }
    }
}
