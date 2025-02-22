package edu.usc.reach.myquituscnew;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class MyQuitLoginActivity extends Activity implements LoaderCallbacks<Cursor> {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "myquit@usc.edu:myquitusc"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private TextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ImageView mClickToSetDate;
    private TextView mInstructionText;
    private static Boolean mStudyTest;

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

    public static boolean confirmPreStudy() throws ParseException{
        Calendar now = Calendar.getInstance();
        Date nowTime = now.getTime();
        Date quitTime = sdf.parse(MyQuitCSVHelper.pullLoginStatus("MyQuitDate"));
        return(nowTime.before(quitTime));
    }

    void buildWelcomeScreen(boolean eventSet){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String message = "Welcome to MyQuit USC\n" + "Let's set up your account and log in.";
        if(eventSet) { message = "Now, let's set up your quit date.";}
        builder.setMessage(message)
                .setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        formatDialog(dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_login);
        Intent pullCurrentIntent = getIntent();
        boolean eventSet = pullCurrentIntent.getBooleanExtra("DateSet",false);
        buildWelcomeScreen(eventSet);


        mEmailView = (TextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mClickToSetDate = (ImageView) findViewById(R.id.splash_logo_IV);
        mInstructionText = (TextView) findViewById(R.id.instructions_guide);

        if(!eventSet) {
            mInstructionText.setVisibility(View.GONE);
            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
                        attemptLogin();
                        return true;
                    }
                    return false;
                }
            });

            mEmailSignInButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin();
                }
            });
        }
        else {
            mPasswordView.setVisibility(View.GONE);
            mEmailView.setVisibility(View.GONE);
            mEmailSignInButton.setVisibility(View.GONE);
            mLoginFormView.setVisibility(View.GONE);
            mProgressView.setVisibility(View.GONE);
            //mInstructionText.setText("Click the Q to set MyQuit Date");
           // mClickToSetDate.setOnClickListener(new OnClickListener() {
               // @Override
               // public void onClick(View v) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment older = getFragmentManager().findFragmentByTag("quitdate");
                    if (older != null) {
                        ft.remove(older);
                    }
                    ft.addToBackStack(null);
                    DialogFragment quitDateIntent = QuitDateDialog.newInstance();
                    quitDateIntent.show(ft, "quitdate");

                //}
           // });
        }


    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            //focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        if(password.equalsIgnoreCase("myquitusctest")){
            mStudyTest = true;
            return true;
        }
        else if(password.equalsIgnoreCase("myquitusceraseme")){
            MyQuitCSVHelper.deleteAndRefresh(true,495030);
            finish();
            return false;
        }
        else {
            mStudyTest = false;
            return password.equalsIgnoreCase("myquitusc");
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    public void showProgress(final boolean show) {

            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            MyQuitCSVHelper.logLoginEvents("UserName",mEmail,MyQuitCSVHelper.getFulltime());
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Intent launchLogin = new Intent(getApplicationContext(), MyQuitLoginActivity.class);
                launchLogin.putExtra("DateSet", true);
                launchLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"Username logged","NA",MyQuitCSVHelper.getFulltime());
                finish();
                startActivity(launchLogin);
             } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
               // mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


    public static class QuitDateDialog extends DialogFragment {
        // String timeTitle;

        static QuitDateDialog newInstance() {
            QuitDateDialog tdf = new QuitDateDialog();

            //  Bundle args = new Bundle();
            //  args.putString("timeCode", timeCode);
            //  tdf.setArguments(args);
            return tdf;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //timeTitle = getArguments().getString("timeCode");

            View v = inflater.inflate(R.layout.fragment_fragment_quit_date_picker, container, false);



            getDialog().setTitle("I will quit smoking on...");

            final DatePicker quitDatePicker = (DatePicker) v.findViewById(R.id.datePicker);
            quitDatePicker.setCalendarViewShown(false);
            quitDatePicker.setSpinnersShown(true);
            Button confirmDate = (Button) v.findViewById(R.id.datePickerButton);
            //,,


            confirmDate.setText("Click to confirm My Quit date!");
            confirmDate.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DecimalFormat doubleDec = new DecimalFormat("00");
                    String month = doubleDec.format(quitDatePicker.getMonth() + 1);
                    String day = doubleDec.format(quitDatePicker.getDayOfMonth());
                    String year = String.valueOf(quitDatePicker.getYear());
                    String test = month + "/" + day + "/" + year;
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage(("I will quit smoking on: " + test))
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        DecimalFormat doubleDec = new DecimalFormat("00");
                                        String month = doubleDec.format(quitDatePicker.getMonth() + 1);
                                        String day = doubleDec.format(quitDatePicker.getDayOfMonth());
                                        String year = String.valueOf(quitDatePicker.getYear());
                                        String test = month + "/" + day + "/" + year;
                                        Date selectedDate = sdf.parse(test);
                                        Calendar futureCal = Calendar.getInstance();
                                        futureCal.add(Calendar.DAY_OF_MONTH, 6);
                                        Date futureDate = futureCal.getTime();
                                        if (selectedDate.after(futureDate) || mStudyTest) {
                                            MyQuitCSVHelper.logLoginEvents("MyQuitDate", test, MyQuitCSVHelper.getFulltime());
                                            MyQuitExperienceSampling.scheduleAllRandomEMA(test);
                                            Intent launchLogin = new Intent(getView().getContext(), MyQuitPrePlanArray.class);
                                            launchLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            MyQuitPHP.postTrackerEvent(MyQuitCSVHelper.pullLoginStatus("UserName"),"Quit date set",test,MyQuitCSVHelper.getFulltime());
                                            dismiss();
                                            getActivity().finish();
                                            startActivity(launchLogin);
                                        } else {
                                            Toast.makeText(getView().getContext(), "Please select a later date", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getView().getContext(), "Parse exception, please login again", Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getView().getContext(), "Please insert SD card", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
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
            });
            /*
            confirmDate.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DecimalFormat doubleDec = new DecimalFormat("00");
                    String month = doubleDec.format(quitDatePicker.getMonth() + 1);
                    String day = doubleDec.format(quitDatePicker.getDayOfMonth());
                    String year = String.valueOf(quitDatePicker.getYear());
                    String test = month + "/" + day + "/" + year;
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        Date selectedDate = sdf.parse(test);
                        Calendar futureCal = Calendar.getInstance();
                        futureCal.roll(Calendar.DAY_OF_MONTH, 1);
                        Date futureDate = futureCal.getTime();
                        if (selectedDate.after(futureDate)) {
                            MyQuitCSVHelper.logLoginEvents("MyQuitDate", test, MyQuitCSVHelper.getFulltime());
                            dismiss();
                            getActivity().finish();
                            Intent launchLogin = new Intent(v.getContext(), MyQuitLoginActivity.class);
                            launchLogin.putExtra("DateSet", true);
                            launchLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(launchLogin);
                        } else {
                            Toast.makeText(v.getContext(), "Please select a later date", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(), "Please select a later date", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });*/


            return v;
        }
    }

}



