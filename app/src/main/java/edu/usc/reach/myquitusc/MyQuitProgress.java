package edu.usc.reach.myquitusc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.ValueDependentColor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;


public class MyQuitProgress extends Activity {


    public void formatDialog(AlertDialog dialog) {
        Button posButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        posButton.setTextColor(getResources().getColor(R.color.ActiveText));
        Button negButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negButton.setTextColor(getResources().getColor(R.color.ActiveText));
        Button neuButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        neuButton.setTextColor(getResources().getColor(R.color.ActiveText));

        int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = dialog.findViewById(dividerId);
        divider.setBackgroundColor(getResources().getColor(R.color.AppBar));

        int textViewId = dialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
        TextView tv = (TextView) dialog.findViewById(textViewId);
        tv.setTextColor(getResources().getColor(R.color.AppBar));
    }

    private void runGraphView(final int typePull){
        int[] smokedCigs = countWeeklyCigs(typePull);
        double[] moneyCigs = countWeeklyMoney(typePull);
        String[] dateLabels = countWeekDays();
        String label;
        switch(typePull){
            case 1: label = "Cigarettes Smoked"; break;
            case 2: label = "Cigarettes Resisted"; break;
            case 3: label = "Money Saved"; break;
            case 4: label = "Money Spent"; break;
            case 5: label = "Money Earned from EMA"; break;
            default: label = "My Progress";
        }


        GraphViewSeries smokeSeries = new GraphViewSeries(new GraphView.GraphViewData[] {
                new GraphView.GraphViewData(1, (double) smokedCigs[0])
                , new GraphView.GraphViewData(2, (double) smokedCigs[1])
                , new GraphView.GraphViewData(3, (double) smokedCigs[2])
                , new GraphView.GraphViewData(4, (double) smokedCigs[3])
                , new GraphView.GraphViewData(5, (double) smokedCigs[4])
                , new GraphView.GraphViewData(6, (double) smokedCigs[5])
                , new GraphView.GraphViewData(7, (double) smokedCigs[6])
        });

        GraphViewSeries moneySeries = new GraphViewSeries(new GraphView.GraphViewData[]{
                new GraphView.GraphViewData(1, moneyCigs[0])
                , new GraphView.GraphViewData(2, moneyCigs[1])
                , new GraphView.GraphViewData(3, moneyCigs[2])
                , new GraphView.GraphViewData(4, moneyCigs[3])
                , new GraphView.GraphViewData(5, moneyCigs[4])
                , new GraphView.GraphViewData(6, moneyCigs[5])
                , new GraphView.GraphViewData(7, moneyCigs[6])
        });

        GraphView graphView = new BarGraphView(
                this // context
                , label // heading
        );
        smokeSeries.getStyle().setValueDependentColor(new ValueDependentColor() {
            @Override
            public int get(GraphViewDataInterface graphViewDataInterface) {
                switch(typePull){
                    case 1: return Color.RED;
                    case 2: return Color.BLUE;
                    default: return 0;
                }
            }
        });
        moneySeries.getStyle().setValueDependentColor(new ValueDependentColor() {
            @Override
            public int get(GraphViewDataInterface graphViewDataInterface) {
                switch(typePull){
                    case 3: return Color.BLUE;
                    case 4: return Color.RED;
                    case 5: return Color.GREEN;
                    default: return 0;
                }
            }
        });


        graphView.setHorizontalLabels(dateLabels);
        if(typePull==1 || typePull ==2) {
            graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
                @Override
                public String formatLabel(double v, boolean b) {
                    return "" + ((int) v);
                }
            });
            graphView.getGraphViewStyle().setNumVerticalLabels(labelNumsInt(getMaxInt(smokedCigs)));
            graphView.addSeries(smokeSeries); // data
        }
        else{
            //graphView.getGraphViewStyle().setNumVerticalLabels(labelNumsDouble(getMaxDouble((moneyCigs))));
            graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
                @Override
                public String formatLabel(double v, boolean b) {
                    final StringBuilder mBuilder = new StringBuilder();
                    final java.util.Formatter mFmt = new java.util.Formatter(mBuilder);
                    final Object[] mArgs = new Object[1];
                    mArgs[0] = v;
                    mBuilder.delete(0, mBuilder.length());
                    mFmt.format("%.2f", mArgs);
                    return "$" + mFmt.toString();
                }
            });
            graphView.addSeries(moneySeries);
        }


        LinearLayout progressUSC = (LinearLayout) findViewById(R.id.linearLayout);
        progressUSC.addView(graphView);
    }

    private static int labelNumsDouble(double maxMoney){
        if(maxMoney>10){
            return 10;
        }
        else {
            int max = ((int) maxMoney);
            return max+1;
        }
    }

    private static int labelNumsInt(int maxCigs){
        if(maxCigs>10){
            return 10;
        }
        else {
            return maxCigs+1;
        }
    }

    private static double getMaxDouble(double[] moneyCigs) {
        double count = 0;
        for(double i:moneyCigs){
            if(i>count){
                count=i;
            }
        }
        return count;
    }

    private static int getMaxInt(int[] smokedCigs) {
        int count = 0;
        for(int i:smokedCigs){
            if(i>count){
                count=i;
            }
        }
        return count;
    }

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

    public static double[] countWeeklyMoney(int typeOfPull) {
        double[] cigSmokeArray = new double[7];

        Calendar todayCal = Calendar.getInstance();
        int dayofWeek = todayCal.DAY_OF_WEEK;
        todayCal.add(todayCal.DATE,(-1*(dayofWeek-1)));
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        int count = 0;

        while (count < 7) {
            todayCal.add(todayCal.DATE,(count));
            Date dayOfWeek = todayCal.getTime();
            try {
                switch(typeOfPull){
                    case 3:
                        double pullCost = Double.valueOf(MyQuitCSVHelper.pullLoginStatus("TypicalCigCost"));
                        cigSmokeArray[count] = MyQuitCSVHelper.pullCigAvoided(sdf.format(dayOfWeek))*pullCost;
                        break;
                    case 4:
                        double pushCost = Double.valueOf(MyQuitCSVHelper.pullLoginStatus("TypicalCigCost"));
                        cigSmokeArray[count] = MyQuitCSVHelper.pullCigarette(sdf.format(dayOfWeek))*pushCost;
                        break;
                    case 5:
                        if(MyQuitCSVHelper.pullEMACounts(sdf.format(dayOfWeek))>3) {
                            cigSmokeArray[count] = MyQuitCSVHelper.pullEMACounts(sdf.format(dayOfWeek)) + 3;
                        }
                        else {
                            cigSmokeArray[count] = MyQuitCSVHelper.pullEMACounts(sdf.format(dayOfWeek));
                        }
                        break;
                    default:
                        cigSmokeArray[count] = MyQuitCSVHelper.pullCigarette(sdf.format(dayOfWeek));
                        break;
                }

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

    public static int[] countWeeklyCigs(int typeOfPull) {
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
                switch(typeOfPull){
                    case 1:
                        cigSmokeArray[count] = MyQuitCSVHelper.pullCigarette(sdf.format(dayOfWeek));
                        break;
                    case 2:
                        cigSmokeArray[count] = MyQuitCSVHelper.pullCigAvoided(sdf.format(dayOfWeek));
                        break;
                    default:
                        cigSmokeArray[count] = MyQuitCSVHelper.pullCigarette(sdf.format(dayOfWeek));
                        break;
                }

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
        if (MyQuitCSVHelper.pullLoginStatus("TypicalCigCost") == null){
            final String numbers[] = new String[100];
        double count = 2;
        for (int i = 0; i < 100; i++) {
            count = count + 0.10;
            final StringBuilder mBuilder = new StringBuilder();
            final java.util.Formatter mFmt = new java.util.Formatter(mBuilder);
            final Object[] mArgs = new Object[1];
            mArgs[0] = count;
            mBuilder.delete(0, mBuilder.length());
            mFmt.format("%.2f", mArgs);
            numbers[i] = mFmt.toString();
        }
        final NumberPicker cigCostPicker = new NumberPicker(this);
        cigCostPicker.setMinValue(0);
        cigCostPicker.setMaxValue(numbers.length - 1);
        cigCostPicker.setDisplayedValues(numbers);
        cigCostPicker.invalidate();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("How much do you usually pay per pack of 20 cigarettes?")
                .setView(cigCostPicker)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double cigCost = Double.valueOf(numbers[cigCostPicker.getValue()]) / 20;
                        MyQuitCSVHelper.logLoginEvents("TypicalCigCost", String.valueOf(cigCost), MyQuitCSVHelper.getFulltime());
                    }
                })
                .setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            formatDialog(dialog);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_progress);

        runGraphView(1);

        Button smokedCigs = (Button) findViewById(R.id.cigSmokeStatus);
        Button avoidedCigs = (Button) findViewById(R.id.cigSavedStatus);
        Button savedCigs = (Button) findViewById(R.id.moneySavedStatus);
        Button spentCigs = (Button) findViewById(R.id.moneySpentStatus);
        Button studyMoney = (Button) findViewById(R.id.moneyEMAStatus);


        smokedCigs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout progressUSC = (LinearLayout) findViewById(R.id.linearLayout);
                progressUSC.removeAllViews();
                runGraphView(1);
            }
        });

        avoidedCigs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout progressUSC = (LinearLayout) findViewById(R.id.linearLayout);
                progressUSC.removeAllViews();
                runGraphView(2);
            }
        });

        savedCigs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout progressUSC = (LinearLayout) findViewById(R.id.linearLayout);
                progressUSC.removeAllViews();
                runGraphView(3);
            }
        });


        spentCigs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout progressUSC = (LinearLayout) findViewById(R.id.linearLayout);
                progressUSC.removeAllViews();
                runGraphView(4);
            }
        });

        studyMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout progressUSC = (LinearLayout) findViewById(R.id.linearLayout);
                progressUSC.removeAllViews();
                runGraphView(5);
            }
        });




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
