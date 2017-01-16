package edu.usc.reach.myquituscnew.DatabaseHelpers;

import android.app.Activity;
import android.os.Bundle;

import edu.usc.reach.myquituscnew.R;

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
