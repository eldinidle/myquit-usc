package edu.usc.reach.myquitusc.DatabaseHelpers;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import edu.usc.reach.myquitusc.MyQuitCalendarHelper;
import edu.usc.reach.myquitusc.MyQuitHomeScreen;
import edu.usc.reach.myquitusc.R;

public class MyQuitAdmin extends Activity {





/**
    private ListAdapter plannedSituationListAdapter() {
        MyQuitDatabaseHandler MQdb = MyQuitDatabaseHandler.getInstance(MyQuitHomeScreen.mainDBContext);
        List<String[]> plannedSituation = PlannedSituation.toArray(MQdb.getAllPlannedSituations());
        String[] activated = new String[plannedSituation.size()];
        int count = 0;
        for(String[] act: plannedSituation) {
            Log.d("MQU-SQL","Logging" + act[2]);
            activated[count] = act[2];
            count++;
        }
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,activated);
        return listAdapter;
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_admin);
        //ListView adminGridView = (ListView) findViewById(R.id.adminGridView);
        //adminGridView.setAdapter(plannedSituationListAdapter());


    }



}
