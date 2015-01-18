package edu.usc.reach.myquitusc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MyQuitIntentPrompt extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quit_intent_prompt);

        Button closeImp = (Button) findViewById(R.id.promptConfirmIntent);
        TextView intentView = (TextView) findViewById(R.id.intentPromptText);

        closeImp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


     //   intentView.setText("Instead of smoking, let's try " +
       //         MyQuitCalendarHelper.returnIntentFromSituation(getApplicationContext()));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_quit_intent_prompt, menu);
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
}
