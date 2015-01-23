package edu.usc.reach.myquitusc;

import android.app.ActionBar;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;


public class MyQuitEMA extends Activity {


    public static int retrieveSurveyLength(int surveyID) {
        switch(surveyID){
            case 1: return MyQuitCheckSuccessSurvey.KEY_SURVEY_LENGTH;
            case 2: return MyQuitCalendarSuccessSurvey.KEY_SURVEY_LENGTH;
            default: return 0;
        }
    }

    void finalPushExitSurvey(int surveyID) throws IOException {
        switch(surveyID) {
            case 1: MyQuitEMAHelper.pushSpecificAnswer(MyQuitCSVHelper.getFullDate(),
                    MyQuitCSVHelper.getTimeOnly(),
                    MyQuitCheckSuccessSurvey.KEY_SURVEY_SUCCESS, "Completed", MyQuitCheckSuccessSurvey.KEY_END_SURVEY,
                    false, MyQuitCheckSuccessSurvey.KEY_SURVEY_LENGTH);
                    return;
            default: return;
        }
    }
    void pushTextForSurvey(int surveyID, int thisSession, EditText entryText, int qPosition) throws IOException {
        switch(surveyID){
            case 1: MyQuitEMAHelper.pushSpecificAnswer(MyQuitCSVHelper.getFullDate(),
                    MyQuitCSVHelper.getTimeOnly(), thisSession, entryText.getText().toString(),
                    qPosition, false, MyQuitCheckSuccessSurvey.KEY_SURVEY_LENGTH);
            InputMethodManager MyQuitInput = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            MyQuitInput.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
                return;
        }
    }

    public static List<String[]> retrieveQuestionList(int surveyID) {
        switch(surveyID){
            case 1: return MyQuitCheckSuccessSurvey.getQuestions;
            case 2: return MyQuitCalendarSuccessSurvey.getQuestions;
            default: return MyQuitCheckSuccessSurvey.getQuestions;
        }
    }

    public static int retrieveSurveyEnd(int surveyID) {
        switch(surveyID){
            case 1: return MyQuitCheckSuccessSurvey.KEY_END_SURVEY;
            case 2: return MyQuitCalendarSuccessSurvey.KEY_END_SURVEY;
            default: return 0;
        }
    }

    private static String validateLengthTest(int surveyID, int sessionID, int position) {
        switch (surveyID) {
            case 1:
                return MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position);
            default:
                return "";
        }
    }

    private static int validatePrevious(int surveyID, int position) {
        switch (surveyID) {
            case 1:
                return MyQuitCheckSuccessSurvey.validatePreviousPosition(position);
            default:
                return 0;
        }
    }

    private static int validateNext(int surveyID, int position, int sessionID) {
        switch (surveyID) {
            case 1:
                try {
                    Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                    return MyQuitCheckSuccessSurvey.validateNextPosition(position,
                            MyQuitEMAHelper.pullSpecificAnswer(MyQuitCSVHelper.getFullDate(), sessionID, position));
                } catch (NumberFormatException nfe) {
                    Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                    return MyQuitCheckSuccessSurvey.validateNextPosition(position,
                            MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position));
                }
            default:
                return 0;
        }
    }

    private static int retrieveSessionID(int surveyID) {
        switch(surveyID){
            case 1: return MyQuitEMAHelper.pullLastSessionID(MyQuitCSVHelper.getFullDate(),
                    MyQuitCheckSuccessSurvey.KEY_SURVEY_SUCCESS);
            default: return 0;
        }
    }

    void radioButtonListenerAdapter(RadioButton rButton, final int surID, final int aID, final int qPosition, final boolean oldSurvey, final int surveyLength){
        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MyQuitEMAHelper.pushSpecificAnswer(MyQuitCSVHelper.getFullDate(),
                            MyQuitCSVHelper.getTimeOnly(),
                            surID, aID, qPosition, false, surveyLength);
                    Log.d("MY-QUIT-USC","Pushed session"+surID+" with "+ "question:" +
                            qPosition + " on answer:" + aID + ". New survey is" + oldSurvey + " and length =" + surveyLength );
                } catch (IOException e) {
                    Log.d("MY-QUIT-USC", "Something's wrong...");
                }
            }
        });
    }

    public void createLowerButtons(Context context, final int surID, final boolean textEntry, RadioGroup answerSelection, final int surveyKey, final int position, final int surveyLength){
            final Intent nextSurvey = new Intent(this, MyQuitEMA.class);
            final Intent previousSurvey = new Intent(this, MyQuitEMA.class);
            Button nextButton = (Button) findViewById(R.id.nextEMA);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(!textEntry) {
                       try {
                         // int test = MyQuitEMAHelper.pullSpecificAnswer(MyQuitCSVHelper.getFullDate(), surID, position);
                           nextSurvey.putExtra("Survey", surveyKey);
                           nextSurvey.putExtra("SessionID", surID);
                           nextSurvey.putExtra("Position", position);
                           nextSurvey.putExtra("Receiver", false);
                           nextSurvey.putExtra("Next", true);
                           finish();
                           startActivity(nextSurvey);
                           Log.d("MY-QUIT-USC", "Sending survey" + surveyKey + " position " + position + " receiver false next");
                       } catch (Exception neo) {
                           Toast.makeText(getApplicationContext(), "Please select an answer.", Toast.LENGTH_SHORT).show();
                       }
                   }
                   if(textEntry){
                       try {
                           String test = validateLengthTest(surveyKey,surID,position);
                           if(test.length()>5) {
                               nextSurvey.putExtra("Survey", surveyKey);
                               nextSurvey.putExtra("SessionID", surID);
                               nextSurvey.putExtra("Position", position);
                               nextSurvey.putExtra("Receiver", false);
                               nextSurvey.putExtra("Next", true);
                               nextSurvey.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                       | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               finish();
                               startActivity(nextSurvey);
                               Log.d("MY-QUIT-USC", "Sending survey" + surveyKey + " position " + position + " receiver false next");
                           }
                           else {
                               Toast.makeText(getApplicationContext(), "Please enter a longer answer and press Done on keyboard.", Toast.LENGTH_SHORT).show();
                           }
                       } catch (Exception neo) {
                           Toast.makeText(getApplicationContext(), "Please enter a longer answer and press Done on keyboard.", Toast.LENGTH_SHORT).show();
                       }
                   }

                }
            });

            Button previousButton = (Button) findViewById(R.id.prevousEMA);
            previousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    previousSurvey.putExtra("Survey", surveyKey);
                    previousSurvey.putExtra("SessionID", surID);
                    previousSurvey.putExtra("Position", position);
                    previousSurvey.putExtra("Receiver", false);
                    previousSurvey.putExtra("Previous", true);
                    previousSurvey.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    try {
                        MyQuitEMAHelper.pushSpecificAnswer(MyQuitCSVHelper.getFullDate(),
                                MyQuitCSVHelper.getTimeOnly(),
                                surID, 9999, position, false, surveyLength);
                    } catch (IOException e) {
                        Log.d("MY-QUIT-USC","Something's wrong...");
                        e.printStackTrace();
                    }
                    startActivity(previousSurvey);
                    Log.d("MY-QUIT-USC", "Sending survey" + surveyKey + " position " + position + " receiver false previous");
                }
            });
        }

    @SuppressWarnings("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_em);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(22222);

        Intent pickSurveyInfo = getIntent();
        final int survey = pickSurveyInfo.getIntExtra("Survey", 0);
        boolean receiver = pickSurveyInfo.getBooleanExtra("Receiver", true);
        int sessionID = 0;
        if (receiver) {
            sessionID = retrieveSessionID(survey);
        }
        else if (!receiver) {
            sessionID = pickSurveyInfo.getIntExtra("SessionID", 0);
        }
        Log.d("MY-QUIT-USC","received session ID is" + pickSurveyInfo.getIntExtra("SessionID",0) + "and"
        + "and" + sessionID);
        boolean next = pickSurveyInfo.getBooleanExtra("Next", false);
        boolean previous = pickSurveyInfo.getBooleanExtra("Previous", false);
        int position;

        if(previous) {
            position = validatePrevious(survey,pickSurveyInfo.getIntExtra("Position", 0));
            Log.d("MY-QUIT-USC","Overwriting survey position to " +position);
        }
        else if(next){
            position = validateNext(survey,pickSurveyInfo.getIntExtra("Position", 0),sessionID);
        }
        else {
            position = pickSurveyInfo.getIntExtra("Position", 0);
        }


        Log.d("MY-QUIT-USC","Receiving survey" + previous + next + receiver + " at " + position);


        LinearLayout surveySetup = (LinearLayout) findViewById(R.id.QuestionsAnswersLayout);


        if (survey == MyQuitCheckSuccessSurvey.KEY_SURVEY_SUCCESS) {
            List<String[]> Survey = retrieveQuestionList(survey);
            int surveyLength = Survey.size();

            RadioGroup answers = new RadioGroup(this);
            String[] questionString= Survey.get(position);
            TextView question = new TextView(this);
            question.setText(questionString[0]);

            if (questionString.length > 1) {
                if (!questionString[1].equalsIgnoreCase("TEXT_ENTRY")) {
                    RadioButton answer1 = new RadioButton(this);
                    answer1.setText(questionString[1]);
                    answer1.setId(1001);
                    radioButtonListenerAdapter(answer1, sessionID, 1001, position, receiver, surveyLength);
                    answers.addView(answer1);
                    RadioButton answer2 = new RadioButton(this);
                    answer2.setText(questionString[2]);
                    answer2.setId(1002);
                    radioButtonListenerAdapter(answer2, sessionID, 1002, position, receiver, surveyLength);
                    answers.addView(answer2);
                    try {
                        RadioButton answer3 = new RadioButton(this);
                        answer3.setText(questionString[3]);
                        answer3.setId(1003);
                        radioButtonListenerAdapter(answer3, sessionID, 1003, position, receiver, surveyLength);
                        answers.addView(answer3);
                        try {
                            RadioButton answer4 = new RadioButton(this);
                            answer4.setText(questionString[4]);
                            answer4.setId(1004);
                            radioButtonListenerAdapter(answer4, sessionID, 1004, position, receiver, surveyLength);
                            answers.addView(answer4);
                            try {
                                RadioButton answer5 = new RadioButton(this);
                                answer5.setText(questionString[5]);
                                answer5.setId(1005);
                                radioButtonListenerAdapter(answer5, sessionID, 1005, position, receiver, surveyLength);
                                answers.addView(answer5);
                            } catch (Exception abc) {

                            }
                        } catch (Exception ab) {

                        }
                    } catch (Exception a) {

                    }

                    surveySetup.addView(question);
                    surveySetup.addView(answers);
                    createLowerButtons(this, sessionID, false, answers, survey, position,retrieveSurveyLength(survey));
                } else if (questionString[1].equalsIgnoreCase("TEXT_ENTRY")) {
                    surveySetup.addView(question);
                    final EditText entryText = new EditText(this);
                    final int thisSession = sessionID;
                    final int qPosition = position;
                    entryText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    entryText.setSingleLine(true);
                    TextView.OnEditorActionListener listener = new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) ||
                                    (actionId == EditorInfo.IME_ACTION_DONE)) {
                                try {
                                    pushTextForSurvey(survey,thisSession,entryText,qPosition);
                                    /* TODO: Deprecate
                                    MyQuitEMAHelper.pushSpecificAnswer(MyQuitCSVHelper.getFullDate(),
                                            MyQuitCSVHelper.getTimeOnly(), thisSession, entryText.getText().toString(),
                                            qPosition, false, MyQuitCheckSuccessSurvey.KEY_SURVEY_LENGTH);
                                            InputMethodManager MyQuitInput = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            MyQuitInput.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                            InputMethodManager.HIDE_NOT_ALWAYS);
                                            */
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            return true;
                        }
                    };
                    entryText.setOnEditorActionListener(listener);

                    surveySetup.addView(entryText);
                    createLowerButtons(this, sessionID, true, answers, survey, position,retrieveSurveyLength(survey));
                }
            }
            else {
                surveySetup.addView(question);

                Button nextButton = (Button) findViewById(R.id.nextEMA);
                Button previousButton = (Button) findViewById(R.id.prevousEMA);
                previousButton.setVisibility(View.GONE);
                nextButton.setText("End survey!");
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyQuitCSVHelper.logEMAEvents("emaFinished", MyQuitCSVHelper.getFulltime());
                        try {
                            finalPushExitSurvey(survey);
                            /* TODO: Deprecate
                            MyQuitEMAHelper.pushSpecificAnswer(MyQuitCSVHelper.getFullDate(),
                                    MyQuitCSVHelper.getTimeOnly(),
                                    MyQuitCheckSuccessSurvey.KEY_SURVEY_SUCCESS, "Completed", MyQuitCheckSuccessSurvey.KEY_END_SURVEY,
                                    false, MyQuitCheckSuccessSurvey.KEY_SURVEY_LENGTH);
                                    */
                        } catch (IOException e) {
                            Log.d("MY-QUIT-USC","Something's wrong here...");
                            e.printStackTrace();
                        }
                        finish();
                    }
                });

            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_quit_em, menu);
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
        Toast.makeText(getApplicationContext(),"Warning: Pressing the back button exits the survey",Toast.LENGTH_LONG).show();
    }


}
