package edu.usc.reach.myquitusc;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

import org.w3c.dom.Text;

import java.io.IOException;


public class MyQuitPlans extends Activity  {



    private void callAllListeners(Button nextButton, Button backButton,
                                  Button statusBar, Button suggestButton,
                                  EditText customIntent, int oldPosition) {
        setBackBaseListListener(backButton,nextButton,statusBar, suggestButton, customIntent, oldPosition);
        setNextBaseListListener(nextButton,backButton,statusBar, suggestButton, customIntent, oldPosition);
        setPushBaseListListener(statusBar);
        setSuggestButtonListener(suggestButton, oldPosition);
        setEditTextListener(getApplicationContext(),customIntent,oldPosition);
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
        String[] pullFull = MyQuitPlanHelper.pullBaseList(context.getApplicationContext()).
                get(indexPosition);
        final int oldPosition = indexPosition;
        TextView.OnEditorActionListener listener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) ||
                        (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if(MyQuitPlanHelper.pushIntentBaseList(v.getContext(),oldPosition,customIntent
                            .getText().toString())) {
                        MyQuitPlanHelper.pushIntentBaseList(v.getContext(),oldPosition,customIntent
                                .getText().toString());
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
                                         final EditText customIntent, int indexPosition) {
        if(indexPosition==(MyQuitPlanHelper.pullSizeBaseList(getApplicationContext())-1)){
            // nextButton.setText("Add situation");//TODO: Add custom situation
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setText("Finish");
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MyQuitPlanHelper.listDone(getApplicationContext())) {
                        Intent homeLaunch = new Intent(getApplicationContext(), MyQuitPrePlanCalendar.class);
                        homeLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        MyQuitCSVHelper.logLoginEvents("completedPlans", MyQuitCSVHelper.getFulltime());
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Please enter longer answer.",Toast.LENGTH_SHORT).show();
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
                    callAllListeners(nextButton,backButton,statusBar,suggestButton, customIntent, oldPosition);
                }
            });
        }
    }

    private void setBackBaseListListener(final Button backButton, final Button nextButton,
                                         final Button statusBar, final Button suggestButton,
                                         final EditText customIntent, int indexPosition) {
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
                    callAllListeners(nextButton,backButton,statusBar, suggestButton, customIntent, oldPosition);
               }
            });
        }
    }
    private void setPushBaseListListener(Button statusBar) {
        if(MyQuitPlanHelper.listDone(getApplicationContext())){
            statusBar.setClickable(true);
            statusBar.setText(MyQuitPlanHelper.getTextCompletion(getApplicationContext()));
            statusBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MyQuitPlanHelper.pushBaseList(getApplicationContext(),MyQuitPlanHelper.baseList)){
                        Intent homeLaunch = new Intent(v.getContext(), MyQuitPrePlanCalendar.class);
                        homeLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        MyQuitCSVHelper.logLoginEvents("completedPlans",MyQuitCSVHelper.getFulltime());
                        finish();
                    }
                }
            });
        }
        else {
            statusBar.setClickable(false);
            statusBar.setText(MyQuitPlanHelper.getTextCompletion(getApplicationContext()));
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_plans);
        //Intent pickupIntents = getIntent();
        //pickupIntents.getStringArrayListExtra("Implementation Intents");
        Button statusBar = (Button) findViewById(R.id.statusBar); // Ties buttons to XML
        Button backButton = (Button) findViewById(R.id.previousIntent);
        Button nextButton = (Button) findViewById(R.id.nextIntent);
        Button suggestOne = (Button) findViewById(R.id.suggestButton);
        TextView showSituation = (TextView) findViewById(R.id.showSituation);
        EditText customIntent = (EditText) findViewById(R.id.customIntent); //Finish tying to XML

        //Initial onCreate setup
        MyQuitPlanHelper.pushBaseList(getApplicationContext(), MyQuitPlanHelper.baseList);
        callAllListeners(nextButton, backButton, statusBar, suggestOne, customIntent, 0);




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
        Toast.makeText(getApplicationContext(),"Please complete plans before closing application",Toast.LENGTH_SHORT).show();
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
            getDialog().setTitle("I will...");
            View v = inflater.inflate(R.layout.fragment_intents_list1, container, false);
            ListView intentsView = (ListView) v.findViewById(R.id.allIntentSuggestions);
            ArrayAdapter<String> tasksArrayAdapter = new ArrayAdapter<>(v.getContext(),
                    android.R.layout.simple_list_item_activated_1,
                    MyQuitPlanHelper.suggestList);
            intentsView.setAdapter(tasksArrayAdapter);
            intentsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pposition, long id) {
                    if(MyQuitPlanHelper.pushIntentBaseList(getActivity(),
                            position,MyQuitPlanHelper.suggestList[pposition])){
                        EditText rename = (EditText) getActivity().findViewById(R.id.customIntent);
                        rename.setText(MyQuitPlanHelper.suggestList[pposition]);
                        getDialog().dismiss();
                    }
                    else {

                    }
                }
            });
            return v;
        }
    }
}
