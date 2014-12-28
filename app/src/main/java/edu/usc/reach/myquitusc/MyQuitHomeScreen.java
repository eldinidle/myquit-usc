package edu.usc.reach.myquitusc;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyQuitHomeScreen extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_home_screen);
        boolean creatStruc = MyQuitCSVHelper.createStructure();
        if (!creatStruc) {
            Toast.makeText(getApplicationContext(), "Warning: Directory not created", Toast.LENGTH_LONG).show();
        }

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todayLaunch = new Intent(v.getContext(), MyQuitCalendar.class);
                Calendar todayDate = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String todayDateSDF = sdf.format(todayDate.getTime());
                todayLaunch.putExtra("Date",todayDateSDF);
                MyQuitHomeScreen.this.overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
                startActivity(todayLaunch);

            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Intent otherLaunch = new Intent(view.getContext(), MyQuitCalendar.class);
                String stringYear = String.format("%04d", year);
                String stringMonth = String.format("%02d", (month+1));
                String stringDOM = String.format("%02d", dayOfMonth);
                String otherDateSDF = stringMonth + "/" +stringDOM + "/" + stringYear;
                otherLaunch.putExtra("Date",otherDateSDF);
                MyQuitHomeScreen.this.overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
                startActivity(otherLaunch);
            }
        });

        Button oopsSmoke = (Button) findViewById(R.id.oopsSmoked);
        oopsSmoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyQuitCSVHelper.logCigarette(MyQuitCSVHelper.getFulltime());
                Toast.makeText(getApplicationContext(),"Thank you for reporting your cigarette", Toast.LENGTH_LONG).show();
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

        return super.onOptionsItemSelected(item);
    }
}
