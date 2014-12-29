package edu.usc.reach.myquitusc;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MyQuitProgress extends Activity {


    public static String[] countWeekDays() {
        String[] cigDateArray = new String[7];

        Calendar todayCal = Calendar.getInstance();
        int dayofWeek = todayCal.DAY_OF_WEEK;
        todayCal.add(todayCal.DATE,(-1*(dayofWeek-1)));
        SimpleDateFormat presentSDF = new SimpleDateFormat("MM/dd");
        int count = 0;

        while (count < 7) {
            todayCal.add(todayCal.DATE,(count));
            Date dayOfWeek = todayCal.getTime();
            cigDateArray[count] = presentSDF.format(dayOfWeek);
            todayCal.add(todayCal.DATE,(-1*count));
            count++;
        }
        return cigDateArray;
    }

    public static int[] countWeeklyCigs() {
        int[] cigSmokeArray = new int[7];

        Calendar todayCal = Calendar.getInstance();
        int dayofWeek = todayCal.DAY_OF_WEEK;
        todayCal.add(todayCal.DATE,(-1*(dayofWeek-1)));
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        int count = 0;

        while (count < 7) {
            todayCal.add(todayCal.DATE,(count));
            Date dayOfWeek = todayCal.getTime();
            try {
                cigSmokeArray[count] = MyQuitCSVHelper.pullCigarette(sdf.format(dayOfWeek));
                todayCal.add(todayCal.DATE,(-1*count));
                count++;
            } catch (IOException e) {
                cigSmokeArray[count] = 0;
                todayCal.add(todayCal.DATE,(-1*count));
                count++;
                e.printStackTrace();
            }

        }
        return cigSmokeArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_progress);
        int[] smokedCigs = countWeeklyCigs();
        String[] dateLabels = countWeekDays();


        GraphViewSeries exampleSeries = new GraphViewSeries(new GraphView.GraphViewData[] {
                new GraphView.GraphViewData(1, (double) smokedCigs[0])
                , new GraphView.GraphViewData(2, (double) smokedCigs[1])
                , new GraphView.GraphViewData(3, (double) smokedCigs[2])
                , new GraphView.GraphViewData(4, (double) smokedCigs[3])
                , new GraphView.GraphViewData(5, (double) smokedCigs[4])
                , new GraphView.GraphViewData(6, (double) smokedCigs[5])
                , new GraphView.GraphViewData(7, (double) smokedCigs[6])
        });

        GraphView graphView = new BarGraphView(
                this // context
                , "My Progress" // heading
        );
        graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
            @Override
            public String formatLabel(double v, boolean b) {
                return ""+((int) v);
            }
        });
        graphView.getGraphViewStyle().setNumVerticalLabels(10);
        graphView.setHorizontalLabels(dateLabels);
        graphView.addSeries(exampleSeries); // data


        LinearLayout progressUSC = (LinearLayout) findViewById(R.id.linearLayout);
        progressUSC.addView(graphView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_quit_progress, menu);
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
