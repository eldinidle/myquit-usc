package edu.usc.reach.myquitusc;
/**
 *
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyQuitProgressNew extends Activity {

    private CombinedChart mChart;
    private final int itemcount = 24;

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
        MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"Application Interaction","My Progress",MyQuitCSVHelper.getFulltime());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_quit_progress_new);

        mChart = (CombinedChart) findViewById(R.id.newprogresschart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);




        // draw bars behind lines
        mChart.setDrawOrder(new DrawOrder[]{
                DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.CANDLE, DrawOrder.LINE, DrawOrder.SCATTER
        });

        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(-5f); // this replaces setStartAtZero(true)
        rightAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DecimalFormat formatDecimal = new DecimalFormat("###,###,##0.00"); // use one decimal
                return "$"+formatDecimal.format(value);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(-5f); // this replaces setStartAtZero(true)

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf(value);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        CombinedData data = new CombinedData();

        data.setData(generateLineData());
        data.setData(generateBarData());
        //data.setValueTypeface(mTfLight);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.setVisibleXRange(7, 7);
        mChart.moveViewTo(itemcount - 7, 0, YAxis.AxisDependency.LEFT);
        mChart.invalidate();
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < itemcount; index++)
            entries.add(new Entry(index, index * 0.5f));

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);


        return d;
    }

    private BarData generateBarData() {


        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();

        for (int index = 0; index < itemcount; index++) {


            // stacked
            entries2.add(new BarEntry(index, new float[]{0+(0.75f)*index, 0-(0.25f)*index}));
        }


        BarDataSet set2 = new BarDataSet(entries2, "");
        set2.setStackLabels(new String[]{"Stack 1", "Stack 2"});
        set2.setColors(new int[]{Color.rgb(61, 165, 255), Color.rgb(23, 197, 255)});
        set2.setValueTextColor(Color.rgb(61, 165, 255));
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        //float barWidth = 1.00f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set2);
        //d.setBarWidth(barWidth);

        // make this BarData object grouped
        //d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
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


    public static int[] countWeeklyCigs(int typeOfPull) {
        Calendar todayCal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String lastStringDate = sdf.format(todayCal.getTime());
        Date lastDate = todayCal.getTime();

        String firstTime = MyQuitCSVHelper.pullFirstLoginTime("UserName");
        String firstStringDate = firstTime.split(" ")[0];
        Date firstDate = todayCal.getTime();
        try {
            firstDate = sdf.parse(firstStringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar firstCal = Calendar.getInstance();
        firstCal.setTime(firstDate);
        todayCal.setTime(lastDate);

        int maxDays = todayCal.get(todayCal.DATE) - firstCal.get(firstCal.DATE);


        int[] cigSmokeArray = new int[maxDays];


        //int dayofWeek = todayCal.DAY_OF_WEEK;
        //todayCal.add(todayCal.DATE,(-1*(dayofWeek-1)));


        int count = 0;

        while (count < maxDays) {
            firstCal.add(firstCal.DATE,(count));
            Date dayOfWeek = firstCal.getTime();
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

                firstCal.add(firstCal.DATE,(-1*count));
                count++;
            } catch (IOException e) {
                cigSmokeArray[count] = 0;
                firstCal.add(firstCal.DATE,(-1*count));
                count++;
                e.printStackTrace();
            }

        }
        return cigSmokeArray;
    }




}