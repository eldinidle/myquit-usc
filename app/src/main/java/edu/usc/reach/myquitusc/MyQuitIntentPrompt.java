package edu.usc.reach.myquitusc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MyQuitIntentPrompt extends Activity {


    void dropWindow() {
        if (MyQuitCalendarHelper.isWithinXNextHour(10) & !MyQuitCalendarHelper.
                returnIntentFromSituation(getApplicationContext(),true).
                equalsIgnoreCase("No Match") & !MyQuitCalendarHelper.isWithinXAfterHour(20)) {
            String hourSituation = MyQuitCalendarHelper.unassignHoursArray()[MyQuitCalendarHelper.assignArrayPosition(true)];
            String parsedHourSituation;
            try {
                parsedHourSituation = hourSituation.substring(3);
            }
            catch(StringIndexOutOfBoundsException soeo) {
                parsedHourSituation = "doing something";
                finish();
            }
            MyQuitCalendarHelper.setUpEMAPrompt(MyQuitCalendarHelper.assignArrayPosition(true),
                    parsedHourSituation,
                    MyQuitCalendarHelper.returnIntentFromSituation(getApplicationContext(),true));
            MyQuitCalendarHelper.setSession(getApplicationContext(),true,true);
        }
        else if (!MyQuitCalendarHelper.isWithinXNextHour(10) & !MyQuitCalendarHelper.
                returnIntentFromSituation(getApplicationContext(),false).
                equalsIgnoreCase("No Match") & MyQuitCalendarHelper.isWithinXAfterHour(20)){
            String hourSituation = MyQuitCalendarHelper.unassignHoursArray()[MyQuitCalendarHelper.assignArrayPosition(false)];
            String parsedHourSituation;
            try {
                parsedHourSituation = hourSituation.substring(3);
            }
            catch(StringIndexOutOfBoundsException soeo) {
                parsedHourSituation = "doing something";
                finish();
            }
            MyQuitCalendarHelper.setUpEMAPrompt(MyQuitCalendarHelper.assignArrayPosition(false),
                   parsedHourSituation, MyQuitCalendarHelper.
                            returnIntentFromSituation(getApplicationContext(),false));
            MyQuitCalendarHelper.setSession(getApplicationContext(),false,true);
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_intent_prompt);

        Button closeImp = (Button) findViewById(R.id.promptConfirmIntent);
        TextView intentView = (TextView) findViewById(R.id.intentPromptText);


        closeImp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropWindow();
            }
        });


        if (MyQuitCalendarHelper.isWithinXNextHour(10) & !MyQuitCalendarHelper.isWithinXAfterHour(20)) {
            String hourSituation = MyQuitCalendarHelper.unassignHoursArray()[MyQuitCalendarHelper.assignArrayPosition(true)];
            String parsedHourSituation;
            try {
                parsedHourSituation = hourSituation.substring(3);
            }
            catch(StringIndexOutOfBoundsException soeo) {
                parsedHourSituation = "doing something";
                finish();
            }
            intentView.setText("Instead of smoking when " + parsedHourSituation + ", you said you would "
                    + MyQuitCalendarHelper.returnIntentFromSituation(getApplicationContext(), true));
        }
        else if (!MyQuitCalendarHelper.isWithinXNextHour(10) & MyQuitCalendarHelper.isWithinXAfterHour(20)) {
            String hourSituation = MyQuitCalendarHelper.unassignHoursArray()[MyQuitCalendarHelper.assignArrayPosition(false)];
            String parsedHourSituation;
            try {
                parsedHourSituation = hourSituation.substring(3);
            }
            catch(StringIndexOutOfBoundsException soeo) {
                parsedHourSituation = "doing something";
                finish();
            }
            intentView.setText("Instead of smoking when " + parsedHourSituation + ", you said you would "
                    + MyQuitCalendarHelper.returnIntentFromSituation(getApplicationContext(), false));
        }
        else {
            finish();
        }






    }

    @Override
    public void onBackPressed() {
        dropWindow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_quit_intent_prompt, menu);
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
