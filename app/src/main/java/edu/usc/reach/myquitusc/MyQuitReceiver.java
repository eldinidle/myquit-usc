package edu.usc.reach.myquitusc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.util.Calendar;
import org.apache.commons.net.ftp.FTPClient;

public class MyQuitReceiver extends BroadcastReceiver {
    public MyQuitReceiver() {
    }

    public static boolean sftpUpload(String userName, String filePath) {
        FTPClient uscHost = new FTPClient();

        try {
            uscHost.connect("ftp://mysmoke.usc.edu", 22);
            uscHost.login("MyQuitUSCMobilePhone", "thisisthepassword");
            MyQuitCSVHelper.logEvent("Login success",MyQuitCSVHelper.getFulltime());
            try {
                uscHost.changeWorkingDirectory("/" + userName);
                MyQuitCSVHelper.logEvent("Directory change success",MyQuitCSVHelper.getFulltime());
            }
            catch (Exception de) {
                uscHost.makeDirectory("/" + userName);
                uscHost.changeWorkingDirectory("/" + userName);
                MyQuitCSVHelper.logEvent("Made directory success",MyQuitCSVHelper.getFulltime());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar realTime = Calendar.getInstance();



        throw new UnsupportedOperationException("Not yet implemented");
    }
}
