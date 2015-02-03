package edu.usc.reach.myquitusc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.DialogPreference;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MyQuitPrePlanArray extends ActionBarActivity {


    private final int END_KEY = 4;
    private static ArrayList<String[]> finalBuiltArrayList = new ArrayList<String[]>();
    private static ArrayAdapter<String> situationAdapter;
    private static String changingString[];
    private static ArrayList<String> changingArrayList = new ArrayList<String>();
    private static ListView situationList;

    /**
     * Social situations
     */
    protected final int SOCIAL_KEY = 1;
    private final String socialSituations = "Carefully think about SOCIAL situations where you are most likely to smoke a cigarette. " +
            "Choose all that apply or create your own if necessary.";

    private static String[] outWithFriends = new String[] {"Going out with friends",
            "If I am going out with friends, I will...",""};
    private static String[] partyBar = new String[] {"Party or bar",
            "If I am at a party or a bar, I will...",""};
    private static String[] aroundSmokers = new String[] {"Around other smokers",
            "If I am around other people who smoke, I will...",""};
    private static String[] offerCigarette = new String[] {"Someone will offer cigarette",
            "If someone offers me a cigarette, I will...",""};
    private static String[] nonSmokingVenue = new String[] {"At a non-smoking venue",
            "If I am about to go to places where smoking is not allowed, I will...",""};
    private static String[] iAmDrinking = new String[] {"I'm drinking",
            "If I am drinking, I will...",""};

    private static ArrayList<String[]> baseSocialList = new ArrayList(Arrays.asList(outWithFriends,partyBar,
            aroundSmokers,offerCigarette,nonSmokingVenue,iAmDrinking));

    /**
     * Routine situations
     */
    protected final int ROUTINE_KEY = 2;
    private final String routineSituations = "Next, think about ROUTINE situations where you are most likely to smoke a cigarette. " +
            "Choose all that apply or create your own if necessary.";

    private static String[] wakeUpActivity = new String[] {"Waking up",
            "If I get up in the morning, I will...",""};
    private static String[] mealFinished = new String[] {"Eating a meal",
            "If I just finished a meal, I will...",""};
    private static String[] coffeeOrTea = new String[] {"Having coffee or tea",
            "If I am having coffee or tea, I will...",""};
    private static String[] onABreak = new String[] {"On a break",
            "If I am having a break, I will...",""};
    private static String[] inACarActivity = new String[] {"In a car",
            "If I am in the car, I will...",""};
    private static String[] bedTimeActivity = new String[] {"Going to bed",
            "If I am about to go to bed, I will...",""};

    private static ArrayList<String[]> baseRoutineList = new ArrayList(Arrays.asList(wakeUpActivity,mealFinished,
            coffeeOrTea,onABreak,inACarActivity, bedTimeActivity));

    /**
     * Emotion situations
     */
    protected final int EMOTION_KEY = 3;
    private final String emotionSituations = "Finally, think about moments of EMOTION where you are most likely to smoke a cigarette. " +
            "Choose all that apply or create your own if necessary.";

    private static String[] underStress = new String[] {"I'm feeling depressed",
            "If I am feeling depressed, I will...",""};
    private static String[] feelingDepressed = new String[] {"I'm feeling angry/frustrated",
            "If I am feeling angry or frustrated, I will...",""};
    private static String[] desireCig = new String[] {"I'm desiring a cigarette",
            "If I am desiring a cigarette, I will...",""};
    private static String[] enjoyingSmoking = new String[] {"Someone is smoking and enjoying it",
            "If I see someone smoking and enjoying it, I will...",""};
    private static String[] gainedWeight = new String[] {"I noticed I gained weight",
            "If I noticed that I have gained weight, I will...",""};
    private static String[] iAmBored = new String[] {"I'm bored",
            "If I am bored, I will...",""};

    private static ArrayList<String[]> baseEmotionList = new ArrayList(Arrays.asList(underStress,feelingDepressed,
            desireCig,enjoyingSmoking,gainedWeight, iAmBored));


    String[] pullFromList(List<String[]> arrayName, int location){
        String[] returnList = new String[arrayName.size()];
        int count = 0;
        for(String[] line:arrayName){
            returnList[count] = line[location];
            count++;
        }
        return returnList;
    }

    void situationHelper(String[] finalPush, int arrayType) {
        switch(arrayType){
            case SOCIAL_KEY:
                baseSocialList.add(finalPush);
                changingString = pullFromList(baseSocialList,0);
                break;
            case ROUTINE_KEY:
                baseRoutineList.add(finalPush);
                changingString = pullFromList(baseRoutineList,0);
                break;
            case EMOTION_KEY:
                baseEmotionList.add(finalPush);
                changingString = pullFromList(baseEmotionList,0);
                break;
            default: break;
        }
        changingArrayList.clear();
        changingArrayList.addAll(Arrays.asList(changingString));
        situationAdapter.notifyDataSetChanged();
    }


    public void addSituation(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText textEntry = new EditText(this);
        textEntry.setSingleLine(true);

        builder.setTitle("If I am...")
                .setView(textEntry)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String finalEntry = textEntry.getText().toString();
                        if(finalEntry.length()>5){
                            String[] finalPush = new String[] {finalEntry, "If I am "+finalEntry+" I will...",""};
                            situationHelper(finalPush,getType());
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Please enter a longer situation",Toast.LENGTH_LONG);
                        }
                    }
                })
                .setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();

    }

    public void nextSituation(View v){
        if(getType()!=END_KEY && situationList.getCheckedItemCount()>2){
            SparseBooleanArray finalList = situationList.getCheckedItemPositions();
            for (int i = 0; i < situationList.getAdapter().getCount(); i++) {
                if (finalList.get(i)) {
                    finalBuiltArrayList.add(chooseBaseList(getType()).get(i));
                }
            }
            Intent launchLogin = new Intent(this, MyQuitPrePlanArray.class);
            launchLogin.putExtra("Type",getType()+1);
            finish();
            startActivity(launchLogin);
        }
        else if(getType()==END_KEY) {

        }
        else {
            Toast.makeText(getApplicationContext(),"Let's pick at least three!",Toast.LENGTH_LONG).show();
        }
    }

    private final String chooseText(int arrayType) {
        switch(arrayType){
            case SOCIAL_KEY: return socialSituations;
            case ROUTINE_KEY: return routineSituations;
            case EMOTION_KEY: return emotionSituations;
            default:
                return "Let's take a look at your final choices.";
        }
    }

    private ArrayList<String[]> chooseBaseList(int arrayType) {
        switch(arrayType){
            case SOCIAL_KEY: return baseSocialList;
            case ROUTINE_KEY: return baseRoutineList;
            case EMOTION_KEY: return baseEmotionList;
            default:
                return finalBuiltArrayList;
        }
    }

     void buildAlert(int arrayType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(chooseText(arrayType))
                .setPositiveButton("Got it!",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

    int getType() {
        return getIntent().getIntExtra("Type",SOCIAL_KEY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_pre_plan_array);
        buildAlert(getType());
        changingString = pullFromList(chooseBaseList(getType()),0);
        changingArrayList.clear();
        changingArrayList.addAll(Arrays.asList(changingString));
        if(getType()==END_KEY){
            situationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    changingArrayList);
        }
        else {
            situationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,
                    changingArrayList);
        }
        situationList = (ListView) findViewById(R.id.situationListView);
        situationList.setAdapter(situationAdapter);
        if(getType()==END_KEY){
            situationList.setChoiceMode(ListView.CHOICE_MODE_NONE);
        }






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_quit_pre_plan_array, menu);
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

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Remember, the app won't work until this is complete!",
                Toast.LENGTH_LONG).show();
    }
}
