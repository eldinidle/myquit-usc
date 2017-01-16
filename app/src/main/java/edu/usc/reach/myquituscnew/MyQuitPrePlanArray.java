package edu.usc.reach.myquituscnew;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MyQuitPrePlanArray extends Activity {


    private final int FAIL_KEY = 5;
    private final int END_KEY = 4;
    private static ArrayList<String[]> socialBuiltArrayList = new ArrayList<String[]>();
    private static ArrayList<String[]> emotionBuiltArrayList = new ArrayList<String[]>();
    private static ArrayList<String[]> routineBuiltArrayList = new ArrayList<String[]>();
    private static ArrayList<String[]> finalBuiltArrayList(){
        ArrayList<String[]> tempHoldALS = new ArrayList<String[]>();
        for(String[] iterate:socialBuiltArrayList){
            tempHoldALS.add(iterate);
        }
        for(String[] iterate:routineBuiltArrayList){
            tempHoldALS.add(iterate);
        }
        for(String[] iterate:emotionBuiltArrayList){
            tempHoldALS.add(iterate);
        }
        return tempHoldALS;
    }
    //private static ArrayList<String[]> finalBuiltArrayList = new ArrayList<String[]>();
    private static ArrayAdapter<String> situationAdapter;
    private static String changingString[];
    private static ArrayList<String> changingArrayList = new ArrayList<String>();
    private static ListView situationList;

    /**
     * Social situations
     */
    protected final int SOCIAL_KEY = 1;
    private final String socialSituations = "Carefully think about situations where you may be WITH OTHER PEOPLE.\n" +
            "Choose at least 3 where you are most likely to smoke a cigarette.\n" +
            "Or, create your own if necessary.";

    private static String[] outWithFriends = MyQuitPlanHelper.outWithFriends;
    private static String[] partyBar = MyQuitPlanHelper.partyBar;
    private static String[] aroundSmokers = MyQuitPlanHelper.aroundSmokers;
    private static String[] offerCigarette = MyQuitPlanHelper.offerCigarette;
    private static String[] nonSmokingVenue = MyQuitPlanHelper.nonSmokingVenue;
    private static String[] iAmDrinking = MyQuitPlanHelper.iAmDrinking;

    private static ArrayList<String[]> baseSocialList = new ArrayList(Arrays.asList(outWithFriends,partyBar,
            aroundSmokers,offerCigarette,nonSmokingVenue,iAmDrinking));

    /**
     * Routine situations
     */
    protected final int ROUTINE_KEY = 2;
    private final String routineSituations = "Carefully think about ROUTINE ACTIVITIES that you may experience throughout your day.\n" +
            "Choose at least 3 where you are most likely to smoke a cigarette.\n" +
            "Or, create your own if necessary.\n";

    private static String[] wakeUpActivity = MyQuitPlanHelper.wakeUpActivity;
    private static String[] mealFinished = MyQuitPlanHelper.mealFinished;
    private static String[] coffeeOrTea = MyQuitPlanHelper.coffeeOrTea;
    private static String[] onABreak = MyQuitPlanHelper.onABreak;
    private static String[] inACarActivity = MyQuitPlanHelper.inACarActivity;
    //private static String[] bedTimeActivity = MyQuitPlanHelper.bedTimeActivity;

    private static ArrayList<String[]> baseRoutineList = new ArrayList(Arrays.asList(wakeUpActivity,mealFinished,
            coffeeOrTea,onABreak,inACarActivity));



    /**
     * Emotion situations
     */
    protected final int EMOTION_KEY = 3;
    private final String emotionSituations = "Carefully think about some EMOTIONS that you may experience throughout your day.\n" +
            "Choose at least 3 where you are most likely to smoke a cigarette.\n" +
            "Or, create your own if necessary.";

    private static String[] underStress = MyQuitPlanHelper.underStress;
    private static String[] feelingDepressed = MyQuitPlanHelper.feelingDepressed;
    private static String[] desireCig = MyQuitPlanHelper.desireCig;
    private static String[] enjoyingSmoking = MyQuitPlanHelper.enjoyingSmoking;
    private static String[] gainedWeight = MyQuitPlanHelper.gainedWeight;
    private static String[] iAmBored = MyQuitPlanHelper.iAmBored;

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

    final int[] returnShuffledArray(){
        int[] keyArray = new int[] {1,2,3};
        Collections.shuffle(Arrays.asList(keyArray));
        return keyArray;
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

    public void goHome() {
        finish();
        startActivity(new Intent(this, MyQuitHomeScreen.class));
    }

    public void previousSituation(View v) {
            Intent launchLogin = new Intent(this, MyQuitPrePlanArray.class);
            launchLogin.putExtra("Type", getType() - 1);
            if(getType()==SOCIAL_KEY){
                goHome();
            }
            else {
                finish();
                startActivity(launchLogin);
            }
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
                        if (finalEntry.length() > 5) {
                            String[] finalPush = new String[]{finalEntry, "If I am " + finalEntry + " I will...", ""};
                            situationHelper(finalPush, getType());
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter a longer situation", Toast.LENGTH_LONG);
                        }
                    }
                })
                .setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

        formatDialog(dialog);

    }

    public void nextSituation(View v){
        if(getType()!=END_KEY && situationList.getCheckedItemCount()>2){
            SparseBooleanArray finalList = situationList.getCheckedItemPositions();
            switch(getType()){
                case SOCIAL_KEY: socialBuiltArrayList.clear(); break;
                case ROUTINE_KEY: routineBuiltArrayList.clear(); break;
                case EMOTION_KEY: emotionBuiltArrayList.clear(); break;
                default: break;
            }
            for (int i = 0; i < situationList.getAdapter().getCount(); i++) {
                if (finalList.get(i)) {
                    switch(getType()){
                        case SOCIAL_KEY:
                            socialBuiltArrayList.add(chooseBaseList(getType()).get(i));
                            break;
                        case ROUTINE_KEY:
                            routineBuiltArrayList.add(chooseBaseList(getType()).get(i));
                            break;
                        case EMOTION_KEY:
                            emotionBuiltArrayList.add(chooseBaseList(getType()).get(i));
                            break;
                        default: break;
                    }

                }
            }
            Intent launchLogin = new Intent(this, MyQuitPrePlanArray.class);
            launchLogin.putExtra("Type",getType()+1);
            finish();
            startActivity(launchLogin);
        }
        else if(getType()==END_KEY) {
            Intent launchLogin = new Intent(this, MyQuitPlans.class);
            launchLogin.putExtra("FinalList",finalBuiltArrayList());
            MyQuitPlanHelper.pushBaseList(getApplicationContext(),finalBuiltArrayList(),true);
            finish();
            startActivity(launchLogin);
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
                return finalBuiltArrayList();
        }
    }

     void buildAlert(int arrayType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(chooseText(arrayType))
                .setPositiveButton("Got it!",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
         AlertDialog dialog = builder.create();
         dialog.show();
         formatDialog(dialog);
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
            Button customSituation = (Button) findViewById(R.id.submitOwn);
            customSituation.setVisibility(View.GONE);
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
        Intent launchLogin = new Intent(this, MyQuitPrePlanArray.class);
        launchLogin.putExtra("Type", getType() - 1);
        if(getType()==SOCIAL_KEY){
            goHome();
        }
        else {
            finish();
            startActivity(launchLogin);
        }
    }
}
