package edu.usc.reach.myquitusc;

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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import edu.usc.reach.myquitusc.Surveys.MyQuitCalendarSuccessSurvey;
import edu.usc.reach.myquitusc.Surveys.MyQuitCheckSuccessSurvey;
import edu.usc.reach.myquitusc.Surveys.MyQuitEndOfDaySurvey;
import edu.usc.reach.myquitusc.Surveys.MyQuitOffSuccessSurvey;
import edu.usc.reach.myquitusc.Surveys.MyQuitRandomSurvey;
import edu.usc.reach.myquitusc.Surveys.MyQuitSmokeSurvey;


public class MyQuitEMA extends Activity {

    protected static final int KEY_SINGLE_CHOICE = 1;
    protected static final int KEY_TEXT_ENTRY = 2;
    protected static final int KEY_NUMERICAL_ENTRY = 3;

    @SuppressWarnings("ResourceType")
    void addButtonBeyondTwo(int qstring, RadioGroup answers, RadioButton buttonName, String[] questionString, int sessionID, int aID, int position, boolean receiver,
                            int surveyLength, int survey) {
        buttonName.setText(questionString[qstring]);
        buttonName.setId(aID);
            radioButtonListenerAdapter(buttonName, sessionID, aID, position, receiver, surveyLength, survey);
            answers.addView(buttonName);
    }

    public static void passThroughUpload(int finalSessionID, int survey) {
        Log.d("MQU-PHP","Survey is" + String.valueOf(survey));
        switch (survey) {
            case 1: MyQuitPHP.postEMAEvent(MyQuitEMAHelper.returnLastEMASurvey(MyQuitCSVHelper.getFullDate(),
                    finalSessionID, survey, MyQuitCSVHelper.pullLastEventArray("intentPresented",MyQuitCSVHelper.ROGUE_EMA_KEY)[2],
                    MyQuitCSVHelper.pullLastEventArray("intentPresented",MyQuitCSVHelper.ROGUE_EMA_KEY)[3])); break;
            case 2: MyQuitPHP.postEMAEvent(MyQuitEMAHelper.returnLastEMASurvey(MyQuitCSVHelper.getFullDate(),
                    finalSessionID, survey, MyQuitCSVHelper.pullLastEventArray("intentPresented",MyQuitCSVHelper.CALENDAR_EMA_KEY)[2],
                    MyQuitCSVHelper.pullLastEventArray("intentPresented",MyQuitCSVHelper.CALENDAR_EMA_KEY)[3])); break;
            case 3: MyQuitPHP.postEMAEvent(MyQuitEMAHelper.returnLastEMASurvey(MyQuitCSVHelper.getFullDate(),
                    finalSessionID, survey, "","")); break;
            case 4: MyQuitPHP.postEMAEvent(MyQuitEMAHelper.returnLastEMASurvey(MyQuitCSVHelper.getFullDate(),
                    finalSessionID, survey, "","")); break;
            case 5: MyQuitPHP.postEMAEvent(MyQuitEMAHelper.returnLastEMASurvey(MyQuitCSVHelper.getFullDate(),
                    finalSessionID, survey, "", "")); break;
            /*case 4: MyQuitPHP.postEMAEvent(MyQuitEMAHelper.returnLastEMASurvey(MyQuitCSVHelper.getFullDate(),
                    finalSessionID, survey, MyQuitCSVHelper.pullLastEventArray("intentPresented",MyQuitCSVHelper.RANDOM_EMA_KEY)[2],
                    MyQuitCSVHelper.pullLastEventArray("intentPresented",MyQuitCSVHelper.RANDOM_EMA_KEY)[3])); break;
            case 5: MyQuitPHP.postEMAEvent(MyQuitEMAHelper.returnLastEMASurvey(MyQuitCSVHelper.getFullDate(),
                    finalSessionID, survey, MyQuitCSVHelper.pullLastEventArray("intentPresented",MyQuitCSVHelper.SMOKE_EMA_KEY)[2],
                    MyQuitCSVHelper.pullLastEventArray("intentPresented",MyQuitCSVHelper.SMOKE_EMA_KEY)[3])); break; */
            case 6: MyQuitPHP.postEMAEvent(MyQuitEMAHelper.returnLastEMASurvey(MyQuitCSVHelper.getFullDate(),
                    finalSessionID, survey, MyQuitCSVHelper.pullLastEventArray("intentPresented",MyQuitCSVHelper.OFF_EMA_KEY)[2],
                    MyQuitCSVHelper.pullLastEventArray("intentPresented",MyQuitCSVHelper.OFF_EMA_KEY)[3])); break;
        }
    }

    void numericalDecision(LinearLayout surveySetup, TextView question,int sessionID, int position,
                           final int survey) {
        surveySetup.addView(question);
        final NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setValue(-1);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(96);
        surveySetup.addView(numberPicker);
        createLowerButtons(this, sessionID, KEY_NUMERICAL_ENTRY, numberPicker, survey, position,retrieveSurveyLength(survey));
    }

    void textDecision(LinearLayout surveySetup, TextView question,int sessionID, int position,
                      final int survey, RadioGroup answers) {
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
        createLowerButtons(this, sessionID, KEY_TEXT_ENTRY, answers, survey, position,retrieveSurveyLength(survey));
    }

    void elseDecision(final int finalSessionID, final int survey, LinearLayout surveySetup, TextView question) {
        surveySetup.addView(question);

        Button nextButton = (Button) findViewById(R.id.nextEMA);
        Button previousButton = (Button) findViewById(R.id.prevousEMA);
        previousButton.setVisibility(View.GONE);
        nextButton.setText("End survey!");
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MyQuitPHP.postEMAEvent(MyQuitEMAHelper.returnLastEMASurvey(MyQuitCSVHelper.getFullDate(),
                //        finalSessionID,survey,MyQuitCSVHelper.pullLastEvent(MyQuitCSVHelper.ROGUE_EMA_KEY)[2],
                //        MyQuitCSVHelper.pullLastEvent(MyQuitCSVHelper.ROGUE_EMA_KEY)[3]));
                MyQuitCSVHelper.logEMAEvents(survey, "emaFinished", MyQuitCSVHelper.getFulltime());
                try {
                    finalPushExitSurvey(survey, finalSessionID);
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
                passThroughUpload(finalSessionID,survey); // TODO: Double check this is supposed to be here
                finish();
            }
        });
    }

    public static int retrieveSurveyLength(int surveyID) {
        switch(surveyID){
            case 1: return MyQuitCheckSuccessSurvey.KEY_SURVEY_LENGTH;
            case 2: return MyQuitCalendarSuccessSurvey.KEY_SURVEY_LENGTH;
            case 3: return MyQuitEndOfDaySurvey.KEY_SURVEY_LENGTH;
            case 4: return MyQuitRandomSurvey.KEY_SURVEY_LENGTH;
            case 5: return MyQuitSmokeSurvey.KEY_SURVEY_LENGTH;
            case 6: return MyQuitOffSuccessSurvey.KEY_SURVEY_LENGTH;
            default: return 0;
        }
    }

    void closeIfMissedSurvey(int surveyID) {
        String[] pullLast = MyQuitCSVHelper.pullLastEvent(surveyID);
        if(pullLast != null && pullLast[0].equalsIgnoreCase("emaMissedSurvey")){
            finish();
        }
    }

    void finalPushExitSurvey(int surveyID, int sessionID) throws IOException {
        MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"EMA Complete",String.valueOf(surveyID),MyQuitCSVHelper.getFulltime());
            MyQuitEMAHelper.pushSpecificAnswer(MyQuitCSVHelper.getFullDate(),
                    MyQuitCSVHelper.getTimeOnly(),
                    sessionID, "Completed", retrieveSurveyEnd(surveyID),
                    false, retrieveSurveyLength(surveyID),surveyID);
    }

    void pushTextForSurvey(int surveyID, int thisSession, EditText entryText, int qPosition) throws IOException {
            MyQuitEMAHelper.pushSpecificAnswer(MyQuitCSVHelper.getFullDate(),
                    MyQuitCSVHelper.getTimeOnly(), thisSession, entryText.getText().toString(),
                    qPosition, false, retrieveSurveyLength(surveyID), surveyID);
            InputMethodManager MyQuitInput = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            MyQuitInput.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static List<String[]> retrieveQuestionList(int surveyID, Context context) {
        switch(surveyID){
            case 1:
                MyQuitCheckSuccessSurvey survey1 = new MyQuitCheckSuccessSurvey();
                return survey1.getQuestions;
            case 2:
                MyQuitCalendarSuccessSurvey survey2 = new MyQuitCalendarSuccessSurvey();
                return survey2.getQuestions;
            case 3:
                MyQuitEndOfDaySurvey survey3 = new MyQuitEndOfDaySurvey();
                return survey3.getQuestions(context);
            case 4:
                MyQuitRandomSurvey survey4 = new MyQuitRandomSurvey();
                return survey4.getQuestions;
            case 5:
                MyQuitSmokeSurvey survey5 = new MyQuitSmokeSurvey();
                return survey5.getQuestions;
            default:
                MyQuitOffSuccessSurvey survey6 = new MyQuitOffSuccessSurvey();
                return survey6.getQuestions;
        }
    }

    public static int retrieveSurveyEnd(int surveyID) {
        switch(surveyID){
            case 1: return MyQuitCheckSuccessSurvey.KEY_END_SURVEY;
            case 2: return MyQuitCalendarSuccessSurvey.KEY_END_SURVEY;
            case 3: return MyQuitEndOfDaySurvey.KEY_END_SURVEY;
            case 4: return MyQuitRandomSurvey.KEY_END_SURVEY;
            case 5: return MyQuitSmokeSurvey.KEY_END_SURVEY;
            case 6: return MyQuitOffSuccessSurvey.KEY_END_SURVEY;
            default: return 0;
        }
    }

    private static String validateLengthTest(int surveyID, int sessionID, int position) {
        return MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID);
    }

    private static int validatePrevious(int surveyID, int position,
                                        boolean naTrue, boolean paTrue, boolean pssTrue, boolean ccTrue, boolean anhedoniaTrue) {
        switch (surveyID) {
            case 1:
                return MyQuitCheckSuccessSurvey.validatePreviousPosition(position);
            case 2:
                return MyQuitCalendarSuccessSurvey.validatePreviousPosition(position);
            case 3:
                return MyQuitEndOfDaySurvey.validatePreviousPosition(position);
            case 4:
                return MyQuitRandomSurvey.validatePreviousPosition(position,naTrue,paTrue,pssTrue,ccTrue,anhedoniaTrue);
            case 5:
                return MyQuitSmokeSurvey.validatePreviousPosition(position,naTrue,paTrue,pssTrue,ccTrue,anhedoniaTrue);
            case 6:
                return MyQuitOffSuccessSurvey.validatePreviousPosition(position);
            default:
                return 0;
        }
    }

    private static int validateNext(int surveyID, int position, int sessionID,
                                    boolean naTrue, boolean paTrue, boolean pssTrue, boolean ccTrue, boolean anhedoniaTrue) {
        switch (surveyID) {
            case 1:
                try {
                    Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                    int sendAId =  Integer.valueOf(MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                    //Log.d("MY-QUIT-USC", "New is" + MyQuitCheckSuccessSurvey.validateNextPosition(position,
                    //        MyQuitEMAHelper.pullSpecificAnswer(MyQuitCSVHelper.getFullDate(), sessionID, position,surveyID)));
                    return MyQuitCheckSuccessSurvey.validateNextPosition(position,
                            sendAId);
                } catch (NumberFormatException nfe) {
                   // Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                   // Log.d("MY-QUIT-USC", "Answer is " + MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                   // Log.d("MY-QUIT-USC", "New is" + MyQuitCheckSuccessSurvey.validateNextPosition(position,
                   //         MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID)));
                    return MyQuitCheckSuccessSurvey.validateNextPosition(position,
                            MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                }
            case 2:
                try {
                    Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                    int sendAId =  Integer.valueOf(MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                    //Log.d("MY-QUIT-USC", "New is" + MyQuitCalendarSuccessSurvey.validateNextPosition(position,
                    //        MyQuitEMAHelper.pullSpecificAnswer(MyQuitCSVHelper.getFullDate(), sessionID, position,surveyID)));
                    return MyQuitCalendarSuccessSurvey.validateNextPosition(position,
                            sendAId);
                } catch (NumberFormatException nfe) {
                    //Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                    //Log.d("MY-QUIT-USC", "Answer is " + MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                    //Log.d("MY-QUIT-USC", "New is" + MyQuitCalendarSuccessSurvey.validateNextPosition(position,
                    //        MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID)));
                    return MyQuitCalendarSuccessSurvey.validateNextPosition(position,
                            MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                }
            case 3:
                try {
                    Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                    int sendAId =  Integer.valueOf(MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                    //Log.d("MY-QUIT-USC", "New is" + MyQuitEndOfDaySurvey.validateNextPosition(position,
                    //        MyQuitEMAHelper.pullSpecificAnswer(MyQuitCSVHelper.getFullDate(), sessionID, position,surveyID)));
                    return MyQuitEndOfDaySurvey.validateNextPosition(position,
                            sendAId);
                } catch (NumberFormatException nfe) {
                    //Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                    //Log.d("MY-QUIT-USC", "Answer is " + MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                    //Log.d("MY-QUIT-USC", "New is" + MyQuitEndOfDaySurvey.validateNextPosition(position,
                    //        MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID)));
                    return MyQuitEndOfDaySurvey.validateNextPosition(position,
                            MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                }
            case 4:
                try {
                    Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                    int sendAId =  Integer.valueOf(MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                    //Log.d("MY-QUIT-USC", "New is" + MyQuitEndOfDaySurvey.validateNextPosition(position,
                    //        MyQuitEMAHelper.pullSpecificAnswer(MyQuitCSVHelper.getFullDate(), sessionID, position,surveyID)));
                    return MyQuitRandomSurvey.validateNextPosition(position,
                            sendAId,naTrue,paTrue,pssTrue,ccTrue,anhedoniaTrue);
                } catch (NumberFormatException nfe) {
                    //Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                    //Log.d("MY-QUIT-USC", "Answer is " + MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                    //Log.d("MY-QUIT-USC", "New is" + MyQuitEndOfDaySurvey.validateNextPosition(position,
                    //        MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID)));
                    return MyQuitRandomSurvey.validateNextPosition(position,
                            MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID),naTrue,paTrue,pssTrue,ccTrue,anhedoniaTrue);
                }
            case 5:
                try {
                    Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                    int sendAId =  Integer.valueOf(MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                    //Log.d("MY-QUIT-USC", "New is" + MyQuitEndOfDaySurvey.validateNextPosition(position,
                    //        MyQuitEMAHelper.pullSpecificAnswer(MyQuitCSVHelper.getFullDate(), sessionID, position,surveyID)));
                    return MyQuitSmokeSurvey.validateNextPosition(position,
                            sendAId,naTrue,paTrue,pssTrue,ccTrue,anhedoniaTrue);
                } catch (NumberFormatException nfe) {
                    //Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                    //Log.d("MY-QUIT-USC", "Answer is " + MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                    //Log.d("MY-QUIT-USC", "New is" + MyQuitEndOfDaySurvey.validateNextPosition(position,
                    //        MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID)));
                    return MyQuitSmokeSurvey.validateNextPosition(position,
                            MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                }
            case 6:
                try {
                    Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                    int sendAId =  Integer.valueOf(MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                    //Log.d("MY-QUIT-USC", "New is" + MyQuitCheckSuccessSurvey.validateNextPosition(position,
                    //        MyQuitEMAHelper.pullSpecificAnswer(MyQuitCSVHelper.getFullDate(), sessionID, position,surveyID)));
                    return MyQuitOffSuccessSurvey.validateNextPosition(position,
                            sendAId);
                } catch (NumberFormatException nfe) {
                    // Log.d("MY-QUIT-USC", "Overwriting survey position to " + position);
                    // Log.d("MY-QUIT-USC", "Answer is " + MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                    // Log.d("MY-QUIT-USC", "New is" + MyQuitCheckSuccessSurvey.validateNextPosition(position,
                    //         MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID)));
                    return MyQuitOffSuccessSurvey.validateNextPosition(position,
                            MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), sessionID, position, surveyID));
                }
            default:
               return 0;
        }
    }

    private static int retrieveSessionID(int surveyID) {
        switch(surveyID){
            case 1: return MyQuitEMAHelper.pullLastSessionID(MyQuitCSVHelper.getFullDate(),
                    MyQuitCheckSuccessSurvey.KEY_SURVEY_SUCCESS);
            case 2: return MyQuitEMAHelper.pullLastSessionID(MyQuitCSVHelper.getFullDate(),
                    MyQuitCalendarSuccessSurvey.KEY_SURVEY_SUCCESS);
            case 3: return MyQuitEMAHelper.pullLastSessionID(MyQuitCSVHelper.getFullDate(),
                    MyQuitEndOfDaySurvey.KEY_SURVEY_SUCCESS);
            case 4: return MyQuitEMAHelper.pullLastSessionID(MyQuitCSVHelper.getFullDate(),
                    MyQuitRandomSurvey.KEY_SURVEY_SUCCESS);
            case 5: return MyQuitEMAHelper.pullLastSessionID(MyQuitCSVHelper.getFullDate(),
                    MyQuitSmokeSurvey.KEY_SURVEY_SUCCESS);
            case 6: return MyQuitEMAHelper.pullLastSessionID(MyQuitCSVHelper.getFullDate(),
                    MyQuitOffSuccessSurvey.KEY_SURVEY_SUCCESS);
            default: return 0;
        }
    }

    void radioButtonListenerAdapter(RadioButton rButton, final int surID, final int aID, final int qPosition, final boolean oldSurvey, final int surveyLength, final int survey){
        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MyQuitEMAHelper.pushSpecificAnswer(MyQuitCSVHelper.getFullDate(),
                            MyQuitCSVHelper.getTimeOnly(),
                            surID, aID, qPosition, false, surveyLength, survey);
                    Log.d("MY-QUIT-USC","Pushed session"+surID+" with "+ "question:" +
                            qPosition + " on answer:" + aID + ". New survey is" + oldSurvey + " and length =" + surveyLength );
                } catch (IOException e) {
                    Log.d("MY-QUIT-USC", "Something's wrong...");
                }
            }
        });
    }

    public void createLowerButtons(Context context, final int surID, final int questionKey, final NumberPicker answerSelection,
                                   final int surveyKey, final int position, final int surveyLength){
        final Intent nextSurvey = new Intent(this, MyQuitEMA.class);
        final Intent previousSurvey = new Intent(this, MyQuitEMA.class);
        Button nextButton = (Button) findViewById(R.id.nextEMA);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int aID = answerSelection.getValue();
                if(aID>-1){
                    try {
                        MyQuitEMAHelper.pushSpecificAnswer(MyQuitCSVHelper.getFullDate(),MyQuitCSVHelper.getTimeOnly(),surID,aID,position,false,surveyLength,surveyKey);
                        nextSurvey.putExtra("Survey", surveyKey);
                        nextSurvey.putExtra("SessionID", surID);
                        nextSurvey.putExtra("Position", position);
                        nextSurvey.putExtra("Receiver", false);
                        nextSurvey.putExtra("Next", true);
                        finish();
                        startActivity(nextSurvey);
                        Log.d("MY-QUIT-USC", "Sending survey" + surveyKey + " position " + position + " receiver false next");
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Please select an answer.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please select an answer.", Toast.LENGTH_SHORT).show();
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
                            surID, 9999, position, false, surveyLength, surveyKey);
                } catch (IOException e) {
                    Log.d("MY-QUIT-USC","Something's wrong...");
                    e.printStackTrace();
                }
                startActivity(previousSurvey);
                Log.d("MY-QUIT-USC", "Sending survey" + surveyKey + " position " + position + " receiver false previous");
            }
        });
    }

    public void createLowerButtons(Context context, final int surID, final int questionKey, RadioGroup answerSelection,
                                   final int surveyKey, final int position, final int surveyLength){
            final Intent nextSurvey = new Intent(this, MyQuitEMA.class);
            final Intent previousSurvey = new Intent(this, MyQuitEMA.class);
            Button nextButton = (Button) findViewById(R.id.nextEMA);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(questionKey == KEY_SINGLE_CHOICE) {
                       try {
                           int test = Integer.valueOf(MyQuitEMAHelper.pullSpecificAnswerString(MyQuitCSVHelper.getFullDate(), surID, position, surveyKey));
                           if(test<2000) {
                               nextSurvey.putExtra("Survey", surveyKey);
                               nextSurvey.putExtra("SessionID", surID);
                               nextSurvey.putExtra("Position", position);
                               nextSurvey.putExtra("Receiver", false);
                               nextSurvey.putExtra("Next", true);
                               finish();
                               startActivity(nextSurvey);
                               Log.d("MY-QUIT-USC", "Sending survey" + surveyKey + " position " + position + " receiver false next");
                           }
                           else {
                               Toast.makeText(getApplicationContext(), "Please select an answer.", Toast.LENGTH_SHORT).show();
                           }
                       } catch (Exception neo) {
                           Toast.makeText(getApplicationContext(), "Please select an answer.", Toast.LENGTH_SHORT).show();
                       }
                   }
                   if(questionKey == KEY_TEXT_ENTRY){
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
                                surID, 9999, position, false, surveyLength, surveyKey);
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
        int survey = pickSurveyInfo.getIntExtra("Survey", 0);
        Log.d("MY-QUIT-USC","it is" + getIntent().getIntExtra("Survey", 0));
        boolean receiver = pickSurveyInfo.getBooleanExtra("Receiver", true);
        int sessionID = 0;
        closeIfMissedSurvey(survey);
        if (receiver) {
            sessionID = retrieveSessionID(survey);
        }
        else if (!receiver) {
            sessionID = pickSurveyInfo.getIntExtra("SessionID", 0);
        }
        Log.d("MY-QUIT-USC","received session ID is" + survey + pickSurveyInfo.getIntExtra("SessionID",0) + "and"
        + "and" + sessionID);
        boolean next = pickSurveyInfo.getBooleanExtra("Next", false);
        boolean previous = pickSurveyInfo.getBooleanExtra("Previous", false);

        boolean naTrue=false;
        boolean paTrue=false;
        boolean pssTrue=false;
        boolean ccTrue=false;
        boolean anhedoniaTrue=false;

        if(survey==MyQuitRandomSurvey.KEY_SURVEY_SUCCESS) {
             naTrue = MyQuitEMAHelper.pullLastSessionMarker(MyQuitCSVHelper.getFullDate(),
                    MyQuitRandomSurvey.KEY_SURVEY_SUCCESS, MyQuitRandomSurvey.KEY_NA);
             paTrue = MyQuitEMAHelper.pullLastSessionMarker(MyQuitCSVHelper.getFullDate(),
                    MyQuitRandomSurvey.KEY_SURVEY_SUCCESS, MyQuitRandomSurvey.KEY_PA);
             pssTrue = MyQuitEMAHelper.pullLastSessionMarker(MyQuitCSVHelper.getFullDate(),
                    MyQuitRandomSurvey.KEY_SURVEY_SUCCESS, MyQuitRandomSurvey.KEY_PSS);
             ccTrue = MyQuitEMAHelper.pullLastSessionMarker(MyQuitCSVHelper.getFullDate(),
                    MyQuitRandomSurvey.KEY_SURVEY_SUCCESS, MyQuitRandomSurvey.KEY_CC);
             anhedoniaTrue = MyQuitEMAHelper.pullLastSessionMarker(MyQuitCSVHelper.getFullDate(),
                    MyQuitRandomSurvey.KEY_SURVEY_SUCCESS, MyQuitRandomSurvey.KEY_ANHEDONIA);
        }
        else if(survey==MyQuitSmokeSurvey.KEY_SURVEY_SUCCESS) {
              naTrue = MyQuitEMAHelper.pullLastSessionMarker(MyQuitCSVHelper.getFullDate(),
                    MyQuitSmokeSurvey.KEY_SURVEY_SUCCESS, MyQuitSmokeSurvey.KEY_NA);
              paTrue = MyQuitEMAHelper.pullLastSessionMarker(MyQuitCSVHelper.getFullDate(),
                    MyQuitSmokeSurvey.KEY_SURVEY_SUCCESS, MyQuitSmokeSurvey.KEY_PA);
              pssTrue = MyQuitEMAHelper.pullLastSessionMarker(MyQuitCSVHelper.getFullDate(),
                    MyQuitSmokeSurvey.KEY_SURVEY_SUCCESS, MyQuitSmokeSurvey.KEY_PSS);
              ccTrue = MyQuitEMAHelper.pullLastSessionMarker(MyQuitCSVHelper.getFullDate(),
                    MyQuitSmokeSurvey.KEY_SURVEY_SUCCESS, MyQuitSmokeSurvey.KEY_CC);
              anhedoniaTrue = MyQuitEMAHelper.pullLastSessionMarker(MyQuitCSVHelper.getFullDate(),
                    MyQuitSmokeSurvey.KEY_SURVEY_SUCCESS, MyQuitSmokeSurvey.KEY_ANHEDONIA);
        }

        int position;

        if(previous) {
            position = validatePrevious(survey,pickSurveyInfo.getIntExtra("Position", 0),naTrue,paTrue,pssTrue,ccTrue,anhedoniaTrue);
            Log.d("MY-QUIT-USC","Overwriting survey position to " +position);
            Log.d("MY-QUIT-USC","PREVIOUS RECEIVED " + position);
        }
        else if(next){
            position = validateNext(survey,pickSurveyInfo.getIntExtra("Position", 0),sessionID,
                    naTrue, paTrue, pssTrue, ccTrue, anhedoniaTrue);
            Log.d("MY-QUIT-USC","NEXT RECEIVED " + position);
        }
        else {
            position = pickSurveyInfo.getIntExtra("Position", 0);
        }


        Log.d("MY-QUIT-USC","Receiving survey" + previous + next + receiver + " at " + position);


        LinearLayout surveySetup = (LinearLayout) findViewById(R.id.QuestionsAnswersLayout);


     //   if (survey == MyQuitCheckSuccessSurvey.KEY_SURVEY_SUCCESS) {
        List<String[]> Survey = retrieveQuestionList(survey,getApplicationContext());
        int surveyLength = Survey.size();

        RadioGroup answers = new RadioGroup(this);
        String[] questionString= Survey.get(position);
        TextView question = new TextView(this);
        question.setText(questionString[0]);

        if (questionString.length > 1) {
            Log.d("MQU-MQU","questionstring is" + questionString[1]);
            if (!questionString[1].equalsIgnoreCase("TEXT_ENTRY") & !questionString[1].equalsIgnoreCase("NUMERICAL_ENTRY") ) {
                RadioButton answer1 = new RadioButton(this);
                answer1.setText(questionString[1]);
                answer1.setId(1001);
                radioButtonListenerAdapter(answer1, sessionID, 1001, position, receiver, surveyLength, survey);
                answers.addView(answer1);
                RadioButton answer2 = new RadioButton(this);
                Log.d("MQU-MQU","questionstring is wrong " + questionString[1]);
                answer2.setText(questionString[2]);
                answer2.setId(1002);
                radioButtonListenerAdapter(answer2, sessionID, 1002, position, receiver, surveyLength, survey);
                answers.addView(answer2);
                try {
                    RadioButton answer3 = new RadioButton(this);
                    addButtonBeyondTwo(3, answers,answer3,questionString,sessionID,1003,position,receiver,surveyLength,survey);
                    RadioButton answer4 = new RadioButton(this);
                    addButtonBeyondTwo(4, answers,answer4,questionString,sessionID,1004,position,receiver,surveyLength,survey);
                    RadioButton answer5 = new RadioButton(this);
                    addButtonBeyondTwo(5, answers,answer5,questionString,sessionID,1005,position,receiver,surveyLength,survey);
                    RadioButton answer6 = new RadioButton(this);
                    addButtonBeyondTwo(6, answers,answer6,questionString,sessionID,1006,position,receiver,surveyLength,survey);
                    RadioButton answer7 = new RadioButton(this);
                    addButtonBeyondTwo(7, answers,answer7,questionString,sessionID,1007,position,receiver,surveyLength,survey);
                    RadioButton answer8 = new RadioButton(this);
                    addButtonBeyondTwo(8, answers,answer8,questionString,sessionID,1008,position,receiver,surveyLength,survey);
                    RadioButton answer9 = new RadioButton(this);
                    addButtonBeyondTwo(9, answers,answer9,questionString,sessionID,1009,position,receiver,surveyLength,survey);
                    RadioButton answer10 = new RadioButton(this);
                    addButtonBeyondTwo(10, answers,answer10,questionString,sessionID,1010,position,receiver,surveyLength,survey);
                    RadioButton answer11 = new RadioButton(this);
                    addButtonBeyondTwo(11, answers,answer10,questionString,sessionID,1011,position,receiver,surveyLength,survey);
                    RadioButton answer12 = new RadioButton(this);
                    addButtonBeyondTwo(12, answers,answer10,questionString,sessionID,1012,position,receiver,surveyLength,survey);
                    RadioButton answer13 = new RadioButton(this);
                    addButtonBeyondTwo(13, answers,answer10,questionString,sessionID,1013,position,receiver,surveyLength,survey);
                    RadioButton answer14 = new RadioButton(this);
                    addButtonBeyondTwo(14, answers,answer10,questionString,sessionID,1014,position,receiver,surveyLength,survey);
                    RadioButton answer15 = new RadioButton(this);
                    addButtonBeyondTwo(15, answers,answer10,questionString,sessionID,1015,position,receiver,surveyLength,survey);
                    RadioButton answer16 = new RadioButton(this);
                    addButtonBeyondTwo(16, answers,answer10,questionString,sessionID,1016,position,receiver,surveyLength,survey);
                    RadioButton answer17 = new RadioButton(this);
                    addButtonBeyondTwo(17, answers,answer10,questionString,sessionID,1017,position,receiver,surveyLength,survey);
                    RadioButton answer18 = new RadioButton(this);
                    addButtonBeyondTwo(18, answers,answer10,questionString,sessionID,1018,position,receiver,surveyLength,survey);
                    RadioButton answer19 = new RadioButton(this);
                    addButtonBeyondTwo(19, answers,answer10,questionString,sessionID,1019,position,receiver,surveyLength,survey);
                    RadioButton answer20 = new RadioButton(this);
                    addButtonBeyondTwo(20, answers,answer10,questionString,sessionID,1020,position,receiver,surveyLength,survey);
                    RadioButton answer21 = new RadioButton(this);
                    addButtonBeyondTwo(21, answers,answer10,questionString,sessionID,1021,position,receiver,surveyLength,survey);
                    RadioButton answer22 = new RadioButton(this);
                    addButtonBeyondTwo(22, answers,answer10,questionString,sessionID,1022,position,receiver,surveyLength,survey);
                    RadioButton answer23 = new RadioButton(this);
                    addButtonBeyondTwo(23, answers,answer10,questionString,sessionID,1023,position,receiver,surveyLength,survey);
                    RadioButton answer24 = new RadioButton(this);
                    addButtonBeyondTwo(24, answers,answer10,questionString,sessionID,1024,position,receiver,surveyLength,survey);
                } catch (Exception a) {}
                surveySetup.addView(question);
                surveySetup.addView(answers);
                createLowerButtons(this, sessionID, KEY_SINGLE_CHOICE, answers, survey, position,retrieveSurveyLength(survey));
            } else if (questionString[1].equalsIgnoreCase("TEXT_ENTRY")) {
                textDecision(surveySetup, question, sessionID, position, survey, answers);
            }
            else if (questionString[1].equalsIgnoreCase("NUMERICAL_ENTRY")) {
                numericalDecision(surveySetup, question, sessionID, position, survey);
            }
        }
        else {
            elseDecision(sessionID,survey,surveySetup,question);
        }

        // }
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
