package edu.usc.reach.myquitusc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class MyQuitPlans extends Activity  {


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

    private void callAllListeners(Button nextButton, Button backButton,
                                  Button statusBar, Button suggestButton,
                                  EditText customIntent, TextView presentSituation,
                                  Button removeCustom, int oldPosition) {
        setBackBaseListListener(backButton,nextButton,statusBar, suggestButton,
                customIntent, presentSituation, removeCustom, oldPosition);
        setNextBaseListListener(nextButton,backButton,statusBar, suggestButton,
                customIntent, presentSituation, removeCustom, oldPosition);
        setPushBaseListListener(statusBar);
        setSuggestButtonListener(suggestButton, oldPosition);
        setEditTextListener(getApplicationContext(),customIntent,oldPosition);
        setSituationTextView(presentSituation, oldPosition);
        setRemoveCustomButton(removeCustom,oldPosition);
    }

    private void setRemoveCustomButton(Button removeCustomSituation, int indexPosition) {
    /* // Disables add custom
        final int oldPosition = indexPosition;
        if(oldPosition>17){
            removeCustomSituation.setVisibility(View.VISIBLE);
            removeCustomSituation.setClickable(true);
            removeCustomSituation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RemoveCustomSituationFragmentDialog removeSituation  =
                            new RemoveCustomSituationFragmentDialog()
                            .newInstance(oldPosition);
                    removeSituation.show(getFragmentManager(), "removeSituation");
                }
            });
        }
        else {
            removeCustomSituation.setVisibility(View.INVISIBLE);
            removeCustomSituation.setClickable(false);
        }
        */
    }

    private void setSituationTextView(TextView presentSituation, int indexPosition){
        String pullAll = MyQuitPlanHelper.pullSituationList(getApplicationContext(),true)[indexPosition];
        presentSituation.setText(pullAll);
    }

    private void setSuggestButtonListener(Button suggestButton, int indexPosition){
        final int oldPosition = indexPosition;
        suggestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentsFragmentDialog suggestIt = new IntentsFragmentDialog().newInstance(oldPosition);
                suggestIt.show(getFragmentManager(), "intentSuggest");
            }
        });

    }


    private void setEditTextListener(Context context, final EditText customIntent, int indexPosition) {
        String[] pullFull = MyQuitPlanHelper.pullBaseList(context.getApplicationContext(),true).
                get(indexPosition);
        final int oldPosition = indexPosition;
        TextView.OnEditorActionListener listener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) ||
                        (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if(MyQuitPlanHelper.pushIntentBaseList(v.getContext(),oldPosition,customIntent
                            .getText().toString(),true)) {
                        MyQuitPlanHelper.pushIntentBaseList(v.getContext(),oldPosition,customIntent
                                .getText().toString(),true);
                        InputMethodManager MyQuitInput = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        MyQuitInput.hideSoftInputFromWindow(getCurrentFocus()
                                        .getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    else {
                        InputMethodManager MyQuitInput = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        MyQuitInput.hideSoftInputFromWindow(getCurrentFocus()
                                        .getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return true;
            }
        };
        if(pullFull[2].length()>5) {
            customIntent.setImeOptions(EditorInfo.IME_ACTION_DONE);
            customIntent.setSingleLine(true);
            customIntent.setText(pullFull[2]);
            customIntent.setOnEditorActionListener(listener);
        }
        else {
            customIntent.setImeOptions(EditorInfo.IME_ACTION_DONE);
            customIntent.setSingleLine(true);
            customIntent.setText("");
            customIntent.setOnEditorActionListener(listener);
        }
    }

    private void setNextBaseListListener(final Button nextButton, final Button backButton,
                                         final Button statusBar, final Button suggestButton,
                                         final EditText customIntent, final TextView presentSituation,
                                         final Button removeCustom, final int indexPosition) {
      /* if(indexPosition==(MyQuitPlanHelper.pullSizeBaseList(getApplicationContext())-1)){
            final int oldPosition = indexPosition;
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setText("Add");
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MyQuitPlanHelper.listDone(getApplicationContext())) {
                        CustomIntentFragmentDialog newSituation  = new CustomIntentFragmentDialog().newInstance(oldPosition);
                        newSituation.show(getFragmentManager(), "customSituation");
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Please enter longer answer.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

       */ if(indexPosition==(MyQuitPlanHelper.pullSizeBaseList(getApplicationContext(),true)-1)){
            // nextButton.setText("Add situation");//TODO: Add custom situation
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setText("Finish");
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callAllListeners(nextButton,backButton,statusBar,suggestButton,customIntent,presentSituation,
                            removeCustom,indexPosition);
                    if(MyQuitPlanHelper.listDone(getApplicationContext(),true)) {

                        MyQuitPlanHelper.convertBaseList(getApplicationContext());
                        try {
                            MyQuitCSVHelper.pushDateTimes("DEFAULT_WEEKDAY", MyQuitCSVHelper.defaultTimes);
                            MyQuitCSVHelper.pushDateTimes("DEFAULT_WEEKEND", MyQuitCSVHelper.defaultTimes);
                            Intent homeLaunch = new Intent(v.getContext(), MyQuitPrePlanCalendar.class);
                            homeLaunch.putExtra("Date", "DEFAULT_WEEKDAY");
                            homeLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(homeLaunch);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Please review all answers.",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        else{
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setText("Next");
            final int oldPosition = indexPosition + 1;
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callAllListeners(nextButton,backButton,statusBar,suggestButton, customIntent,
                            presentSituation, removeCustom, oldPosition);
                }
            });
        }
    }

    private void setBackBaseListListener(final Button backButton, final Button nextButton,
                                         final Button statusBar, final Button suggestButton,
                                         final EditText customIntent, final TextView presentSituation,
                                         final Button removeCustom, int indexPosition) {
        if(indexPosition==0){
            backButton.setText("");
            backButton.setClickable(false);
            backButton.setVisibility(View.INVISIBLE);
        }
        else{
            backButton.setText("Back");
            backButton.setClickable(true);
            backButton.setVisibility(View.VISIBLE);
            final int oldPosition = indexPosition - 1;
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callAllListeners(nextButton,backButton,statusBar, suggestButton, customIntent,
                            presentSituation, removeCustom,  oldPosition);
               }
            });
        }
    }
    private void setPushBaseListListener(Button statusBar) {
        if(MyQuitPlanHelper.listDone(getApplicationContext(),true)){
            statusBar.setClickable(true);
            statusBar.setText(MyQuitPlanHelper.getTextCompletion(getApplicationContext(),true));
            statusBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if(MyQuitPlanHelper.pushBaseList(getApplicationContext(),MyQuitPlanHelper.baseList)){
                        MyQuitPlanHelper.convertBaseList(getApplicationContext());
                    try {
                        MyQuitCSVHelper.pushDateTimes("DEFAULT_WEEKDAY", MyQuitCSVHelper.defaultTimes);
                        MyQuitCSVHelper.pushDateTimes("DEFAULT_WEEKEND", MyQuitCSVHelper.defaultTimes);
                        Intent homeLaunch = new Intent(v.getContext(), MyQuitPrePlanCalendar.class);
                        homeLaunch.putExtra("Date","DEFAULT_WEEKDAY");
                        homeLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(homeLaunch);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                   // }
                }
            });
        }
        else {
            statusBar.setClickable(false);
            statusBar.setText(MyQuitPlanHelper.getTextCompletion(getApplicationContext(),true));
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_plans);
        Intent pickupIntents = getIntent();
        int dialogPosition = pickupIntents.getIntExtra("Custom Position",0);
        Button statusBar = (Button) findViewById(R.id.statusBar); // Ties buttons to XML
        Button backButton = (Button) findViewById(R.id.previousIntent);
        Button nextButton = (Button) findViewById(R.id.nextIntent);
        Button suggestOne = (Button) findViewById(R.id.suggestButton);
        TextView showSituation = (TextView) findViewById(R.id.showSituation);
        EditText customIntent = (EditText) findViewById(R.id.customIntent);
        Button removeSituation = (Button) findViewById(R.id.eraseCustomSituation);//Finish tying to XML
        TextView planTitle = (TextView) findViewById(R.id.plansTitle);
        int numberOfThings = MyQuitPlanHelper.pullSizeBaseList(getApplicationContext(),true);
        planTitle.setText("MyQuit "+ numberOfThings);
        removeSituation.setVisibility(View.INVISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("We will now look at each of the " + numberOfThings + " situations " +
                "you have selected and think about something you could do in each situation to " +
                "stop yourself from smoking.")
                .setPositiveButton("Got it!",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        formatDialog(dialog);



        //Initial onCreate setup
        //if(dialogPosition == 0){
        //    MyQuitPlanHelper.pushBaseList(getApplicationContext(), MyQuitPlanHelper.baseList);
        //}
        callAllListeners(nextButton, backButton, statusBar, suggestOne,
                customIntent, showSituation, removeSituation, dialogPosition);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_quit_plans, menu);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(("Do you want to restart MyQuit Plans and pick a new set of situations?"))
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent startPlan = new Intent(getApplicationContext(), MyQuitPrePlanArray.class);
                        startPlan.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(startPlan);
                        finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        formatDialog(dialog);
    }


    public static class IntentsFragmentDialog extends DialogFragment {
        int position;


        static IntentsFragmentDialog newInstance(int currentPosition) {
            IntentsFragmentDialog tdf = new IntentsFragmentDialog();

            Bundle args = new Bundle();
            args.putInt("position", currentPosition);
            tdf.setArguments(args);

            return tdf;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            position = getArguments().getInt("position");
            final String[] suggestList = MyQuitPlanHelper.suggestList(getActivity(),position,true);
            getDialog().setTitle(Html.fromHtml("<font color='#004D40'>I will...</font>"));
            View v = inflater.inflate(R.layout.fragment_intents_list1, container, false);
            ListView intentsView = (ListView) v.findViewById(R.id.allIntentSuggestions);
            ArrayAdapter<String> tasksArrayAdapter = new ArrayAdapter<>(v.getContext(),
                    android.R.layout.simple_list_item_activated_1,
                    suggestList);
            intentsView.setAdapter(tasksArrayAdapter);
            intentsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pposition, long id) {
                    if(MyQuitPlanHelper.pushIntentBaseList(getActivity(),
                            position,suggestList[pposition],true)){
                        EditText rename = (EditText) getActivity().findViewById(R.id.customIntent);
                        rename.setText(suggestList[pposition]);
                        getDialog().dismiss();
                    }
                    else {

                    }
                }
            });
            return v;
        }
    }

    public static class CustomIntentFragmentDialog extends DialogFragment {
        int position;


        static CustomIntentFragmentDialog newInstance(int currentPosition) {
            CustomIntentFragmentDialog tdf = new CustomIntentFragmentDialog();

            Bundle args = new Bundle();
            args.putInt("position", currentPosition);
            tdf.setArguments(args);

            return tdf;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            position = getArguments().getInt("position");
            getDialog().setTitle(Html.fromHtml("<font color='#004D40'>If I am...</font>"));
            View v = inflater.inflate(R.layout.fragment_custom_intent, container, false);
            final EditText editNewIntent = (EditText) v.findViewById(R.id.editNewCustomIntent);
            Button cancelCustomIntent = (Button) v.findViewById(R.id.cancelCustomIntent);
            final Button acceptCustomIntent = (Button) v.findViewById(R.id.acceptCustomIntent);

            MyQuitPlanHelper.extendBaseListOne(getActivity(),true);

            cancelCustomIntent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyQuitPlanHelper.shrinkBaseList(getActivity(),position+1,true);
                    getDialog().dismiss();
                }
            });

            editNewIntent.setHint("If I am...");
            editNewIntent.setImeOptions(EditorInfo.IME_ACTION_DONE);
            editNewIntent.setSingleLine(true);
            TextView.OnEditorActionListener customListener = new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) ||
                            (actionId == EditorInfo.IME_ACTION_DONE)) {
                        if(MyQuitPlanHelper.pushLabelBaseList(v.getContext(),position+1,editNewIntent
                                .getText().toString(),true)) {
                            MyQuitPlanHelper.pushLabelBaseList(v.getContext(),position+1,editNewIntent
                                    .getText().toString(),true);
                            InputMethodManager MyQuitInput = (InputMethodManager)
                                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            MyQuitInput.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                        else {
                            InputMethodManager MyQuitInput = (InputMethodManager)
                                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            MyQuitInput.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                    return true;
                }
            };
            editNewIntent.setOnEditorActionListener(customListener);


            acceptCustomIntent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] pullFull = MyQuitPlanHelper.pullBaseList(getActivity(),true).
                            get(position+1);
                     if(pullFull[0].length()>5){
                        Intent reLaunchCustom = new Intent(getActivity(), MyQuitPlans.class);
                        reLaunchCustom.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        reLaunchCustom.putExtra("Custom Position",(position+1));
                        getDialog().dismiss();
                        getActivity().finish();
                        startActivity(reLaunchCustom);
                    }
                    else {
                        Toast.makeText(getActivity(),"Please enter longer label or press Done.",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            return v;
        }
    }

    public static class RemoveCustomSituationFragmentDialog extends DialogFragment {
        int position;


        static RemoveCustomSituationFragmentDialog newInstance(int currentPosition) {
            RemoveCustomSituationFragmentDialog tdf = new RemoveCustomSituationFragmentDialog();

            Bundle args = new Bundle();
            args.putInt("position", currentPosition);
            tdf.setArguments(args);

            return tdf;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            position = getArguments().getInt("position");
            getDialog().setTitle(Html.fromHtml("<font color='#004D40'>Really remove situation?</font>"));
            View v = inflater.inflate(R.layout.fragment_remove_custom, container, false);
            Button cancelErase = (Button) v.findViewById(R.id.eraseCancel);
            Button acceptErase = (Button) v.findViewById(R.id.eraseConfirm);


            cancelErase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().dismiss();
                }
            });

            acceptErase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyQuitPlanHelper.shrinkBaseList(getActivity(), position,true);
                    Intent reLaunchCustom = new Intent(getActivity(), MyQuitPlans.class);
                    reLaunchCustom.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    reLaunchCustom.putExtra("Custom Position",(position-1));
                    getDialog().dismiss();
                    getActivity().finish();
                    startActivity(reLaunchCustom);
                }
            });


            return v;
        }
    }
}
