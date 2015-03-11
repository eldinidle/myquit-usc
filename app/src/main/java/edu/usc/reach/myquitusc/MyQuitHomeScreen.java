package edu.usc.reach.myquitusc;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyQuitHomeScreen extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_home_screen_backup);
        boolean creatStruc = MyQuitCSVHelper.createStructure();
        if(MyQuitCSVHelper.pullLoginStatus("UserName") !=null) {
            if (MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
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

            final CaldroidFragment caldroidFragment = new CaldroidFragment();
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt("month", cal.get(Calendar.MONTH) + 1);
            args.putInt("year", cal.get(Calendar.YEAR));
            caldroidFragment.setArguments(args);
            //Button rightArrow = caldroidFragment.getRightArrowButton();
            //rightArrow.setBackgroundResource(R.drawable.calendar_next_arrow);

            android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.add(R.id.calendarViewOut, caldroidFragment);
            t.commit();

            final CaldroidListener listener = new CaldroidListener() {

                @Override
                public void onSelectDate(Date date, View view) {
                    if (MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
                        Intent otherLaunch = new Intent(view.getContext(), MyQuitCalendar.class);
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        String otherDateSDF = sdf.format(date);
                        otherLaunch.putExtra("Date", otherDateSDF);
                        startActivity(otherLaunch);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please complete your MyQuit 15 Plan first!", Toast.LENGTH_SHORT).show();
                    }                }

                @Override
                public void onCaldroidViewCreated() {
                    // Supply your own adapter to weekdayGridView (SUN, MON, etc)

                    Button leftButton = caldroidFragment.getLeftArrowButton();
                    Button rightButton = caldroidFragment.getRightArrowButton();
                    TextView textView = caldroidFragment.getMonthTitleTextView();


                    // Do customization here
                }

            };
            caldroidFragment.setCaldroidListener(listener);

/*
            CalendarView calendarView = (CalendarView) findViewById(R.id.calendarViewOut);
            calendarView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
                        Intent todayLaunch = new Intent(v.getContext(), MyQuitCalendar.class);
                        Calendar todayDate = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        String todayDateSDF = sdf.format(todayDate.getTime());
                        todayLaunch.putExtra("Date", todayDateSDF);
                        MyQuitHomeScreen.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                        startActivity(todayLaunch);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please complete your MyQuit 15 Plan first!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    if (MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
                        Intent otherLaunch = new Intent(view.getContext(), MyQuitCalendar.class);
                        String stringYear = String.format("%04d", year);
                        String stringMonth = String.format("%02d", (month + 1));
                        String stringDOM = String.format("%02d", dayOfMonth);
                        String otherDateSDF = stringMonth + "/" + stringDOM + "/" + stringYear;
                        otherLaunch.putExtra("Date", otherDateSDF);
                        MyQuitHomeScreen.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                        startActivity(otherLaunch);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please complete your MyQuit 15 Plan first!", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/

            Button oopsSmoke = (Button) findViewById(R.id.oopsSmoked);
            String checkLastEvent = "NA";
            try {
                checkLastEvent = MyQuitCSVHelper.pullLastEvent(MyQuitCSVHelper.ROGUE_EMA_KEY)[0];
            }
            catch (NullPointerException neo) {
                neo.printStackTrace();
            }
            if((checkLastEvent.equalsIgnoreCase("intentPresented") ||
                    checkLastEvent.equalsIgnoreCase("emaPrompted") ||
                    checkLastEvent.equalsIgnoreCase("emaReprompted"))){
                oopsSmoke.setTextColor(Color.WHITE);
            }
            else {
                oopsSmoke.setTextColor(Color.BLACK);
                oopsSmoke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            Fragment older = getFragmentManager().findFragmentByTag("rogueintent");
                            if (older != null) {
                                ft.remove(older);
                            }
                            ft.addToBackStack(null);

                            // Create and show the dialog.
                            DialogFragment rogueIntent = RogueButtonDialog.newInstance();
                            //  MyQuitCSVHelper.logEMAEvents("intentPresented", MyQuitCSVHelper.getFulltime());
                            rogueIntent.show(ft, "rogueintent");
                        } else {
                            Toast.makeText(getApplicationContext(), "Please complete your MyQuit 15 Plan first!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            Button progressTracker = (Button) findViewById(R.id.progressButton);
            progressTracker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
                        Intent openProgress = new Intent(getApplicationContext(), MyQuitProgress.class);
                        MyQuitHomeScreen.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                        startActivity(openProgress);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please complete your MyQuit 15 Plan first!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Button gonnaSmoke = (Button) findViewById(R.id.smokeNow);
            gonnaSmoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
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
                    } else {
                        Toast.makeText(getApplicationContext(), "Please complete your MyQuit 15 Plan first!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Button launchPlans = (Button) findViewById(R.id.quitPlans);
            launchPlans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setMessage(("Once you complete your new plans, you will be asked to reassign your " +
                                "typical weekdays and weekends, do you wish to proceed?"))
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent startPlan = new Intent(getApplicationContext(), MyQuitPrePlanArray.class);
                                        startPlan.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(startPlan);
                                        finish();
                                    }
                                })
                                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).create().show();
                    } else {
                        Intent startPlan = new Intent(getApplicationContext(), MyQuitPrePlanArray.class);
                        startPlan.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(startPlan);
                        finish();
                    }
                }
            });
        }
        else {
            Intent launchLogin = new Intent(this, MyQuitLoginActivity.class);
            startActivity(launchLogin);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_quit_home_screen, menu);
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        Button oopsSmoke = (Button) findViewById(R.id.oopsSmoked);
        String checkLastEvent = "NA";
        try {
            checkLastEvent = MyQuitCSVHelper.pullLastEvent(MyQuitCSVHelper.ROGUE_EMA_KEY)[0];
        }
        catch (NullPointerException neo) {
            neo.printStackTrace();
        }
        if((checkLastEvent.equalsIgnoreCase("intentPresented") ||
                checkLastEvent.equalsIgnoreCase("emaPrompted") ||
                checkLastEvent.equalsIgnoreCase("emaReprompted"))){
            oopsSmoke.setTextColor(Color.WHITE);
        }
        else {
            oopsSmoke.setTextColor(Color.BLACK);
            oopsSmoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyQuitCSVHelper.pullLoginStatus("completedPlans") != null) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        Fragment older = getFragmentManager().findFragmentByTag("rogueintent");
                        if (older != null) {
                            ft.remove(older);
                        }
                        ft.addToBackStack(null);

                        /// Create and show the dialog.
                        DialogFragment rogueIntent = RogueButtonDialog.newInstance();
                        //  MyQuitCSVHelper.logEMAEvents("intentPresented", MyQuitCSVHelper.getFulltime());
                        rogueIntent.show(ft, "rogueintent");
                    } else {
                        Toast.makeText(getApplicationContext(), "Please complete your MyQuit 15 Plan first!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
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
        if (id == R.id.runCalledAction) {
            try {
                String versionName = getApplicationContext().getPackageManager()
                        .getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
                Toast.makeText(getApplicationContext(),"You are running version " + versionName,Toast.LENGTH_LONG).show();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
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
            final String[] NEW_TASKS_LIST = MyQuitPlanHelper.pullTasksList(getActivity(),false);
            final String[] NEW_INTENTS_LIST = MyQuitPlanHelper.pullIntentsList(getActivity(),false);



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
                    try {
                        pulledActivity = NEW_TASKS_LIST[position];
                    }
                    catch (Exception neo) {
                        pulledActivity = MyQuitTasksActivity.TASKS_LIST[position];
                    }
                    String intentLister = NEW_INTENTS_LIST[position];
                    MyQuitCSVHelper.logEMAEvents(MyQuitCSVHelper.ROGUE_EMA_KEY,"intentPresented",
                            MyQuitCSVHelper.getFulltime(),pulledActivity,intentLister);
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
                    //MyQuitCSVHelper.logEMAEvents("intentPresented", MyQuitCSVHelper.getFulltime());
                    getDialog().dismiss();
                    getActivity().finish();
                    Intent startPlan = new Intent(getActivity(), MyQuitHomeScreen.class);
                    startPlan.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(startPlan);
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
            final String[] NEW_TASKS_LIST = MyQuitPlanHelper.pullTasksList(getActivity(),false);
            final String[] NEW_INTENTS_LIST = MyQuitPlanHelper.pullIntentsList(getActivity(),false);

            View v = inflater.inflate(R.layout.fragment_rogue_event, container, false);

            getDialog().setTitle(Html.fromHtml("<font color='#004D40'>What were you doing?</font>"));

            ListView intentsRogue = (ListView) v.findViewById(R.id.rogueIntentList);

            ArrayAdapter<String> tasksArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, NEW_TASKS_LIST);
            if(NEW_TASKS_LIST==null) {
                tasksArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, MyQuitTasksActivity.TASKS_LIST);
            }

            intentsRogue.setAdapter(tasksArrayAdapter);
            intentsRogue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Calendar smokeDate = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        MyQuitCSVHelper.pushCigarette(sdf.format(smokeDate.getTime()), MyQuitCSVHelper.getFulltime());
                        MyQuitCSVHelper.logRogueEvent(MyQuitCSVHelper.getFulltime());
                        if(NEW_TASKS_LIST!=null) {
                            MyQuitPHP.postRogueEvent(MyQuitCSVHelper.pullLoginStatus("UserName"), NEW_TASKS_LIST[position], MyQuitCSVHelper.getFulltime());
                        }
                        else if(NEW_TASKS_LIST==null) {
                            MyQuitPHP.postRogueEvent(MyQuitCSVHelper.pullLoginStatus("UserName"), MyQuitTasksActivity.TASKS_LIST[position], MyQuitCSVHelper.getFulltime());
                        }
                        Toast.makeText(view.getContext(), ("Thank you for reporting your smoking."), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(view.getContext(), "Please insert SD card or restart application.", Toast.LENGTH_LONG).show();
                    }
                    getDialog().dismiss();
                }
            });


            return v;
        }
    }
}
