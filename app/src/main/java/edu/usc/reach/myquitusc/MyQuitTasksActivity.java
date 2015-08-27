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
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;


public class MyQuitTasksActivity extends Activity {
    static final String[] TASKS_LIST =
            new String[] { "Sleeping", "Clubbing"};
    String[] pulledTimes;

//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String[] NEW_TASKS_LIST = MyQuitPlanHelper.pullTasksList(getApplicationContext(),false);
        final String[] NEW_INTENTS_LIST = MyQuitPlanHelper.pullIntentsList(getApplicationContext(),false);
         // MyQuitPlanHelper.taperPullBaseList(getApplicationContext(), 11, 17); // MyQuitPlanHelper.pullTasksList(getApplicationContext());

        Intent calendarCall = getIntent();
        final String timeTitle = calendarCall.getStringExtra("timeCode");
        final int positionTitle = calendarCall.getIntExtra("positionTime",0);
        final String callingDate = calendarCall.getStringExtra("calledDate");
        final boolean prePlanCall = calendarCall.getBooleanExtra("PrePlan",false);
        final boolean fromHome = calendarCall.getBooleanExtra("FromHome",false);
        final boolean weekEnd = calendarCall.getBooleanExtra("Weekend",false);
        try {
            pulledTimes = MyQuitCSVHelper.pullDateTimes(callingDate);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"Warning: Please reinsert your SD card",Toast.LENGTH_LONG).show();
            Intent launchBack = new Intent();
            if(!prePlanCall) {
                launchBack = new Intent(this, MyQuitCalendar.class);
            }
            else if(prePlanCall){
                    launchBack = new Intent(this, MyQuitPrePlanCalendar.class);
            }
            launchBack.putExtra("Date", callingDate);
            launchBack.putExtra("FocusPosition",positionTitle);
            launchBack.putExtra("Weekend", weekEnd);
            launchBack.putExtra("FromHome",fromHome);
            launchBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MyQuitTasksActivity.this.overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_top);
            startActivity(launchBack);
            e.printStackTrace();
        }
        setContentView(R.layout.activity_my_quit_tasks);
        ListView tasksView = (ListView) findViewById(R.id.tasksList);
        ArrayAdapter<String> tasksArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, NEW_TASKS_LIST);
        if(NEW_TASKS_LIST==null) {
            tasksArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, TASKS_LIST);
        }
        tasksView.setAdapter(tasksArrayAdapter);
        tasksView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pulledTimes[positionTitle] = MyQuitCSVHelper.defaultTimes[positionTitle] + " - " + NEW_TASKS_LIST[position];
                if(NEW_TASKS_LIST==null) {
                    pulledTimes[positionTitle] = MyQuitCSVHelper.defaultTimes[positionTitle] + " - " + TASKS_LIST[position];
                }

                try {
                    MyQuitCSVHelper.pushDateTimes(callingDate, pulledTimes);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(view.getContext(), "Warning: Not Synced", Toast.LENGTH_LONG);
                }
                Intent launchBack = new Intent();
                if(!prePlanCall) {
                    launchBack = new Intent(view.getContext(), MyQuitCalendar.class);
                }
                else if(prePlanCall){
                    launchBack = new Intent(view.getContext(), MyQuitPrePlanCalendar.class);
                }
                launchBack.putExtra("Date",callingDate);
                launchBack.putExtra("FocusPosition",positionTitle);
                launchBack.putExtra("Weekend",weekEnd);
                launchBack.putExtra("FromHome",fromHome);
                launchBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MyQuitTasksActivity.this.overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_top);
                MyQuitPHP.postCalendarEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),callingDate,MyQuitCSVHelper.defaultTimes[positionTitle],NEW_TASKS_LIST[position],NEW_INTENTS_LIST[position],MyQuitCSVHelper.getFulltime());
                startActivity(launchBack);
             }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_quit_tasks, menu);
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
}
