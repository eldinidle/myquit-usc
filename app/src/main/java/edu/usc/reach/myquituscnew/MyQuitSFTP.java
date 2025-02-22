package edu.usc.reach.myquituscnew;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

/**
 * Created by Eldin Dzubur on 1/12/15.
 */
public class MyQuitSFTP {
    public static boolean sftpUpload(String userName, String filePath) {
        FTPClient uscHost = new FTPClient();

        try {
            uscHost.connect("", 22);
            uscHost.login("", "");
            //MyQuitCSVHelper.logEMAEvents("Login success", MyQuitCSVHelper.getFulltime());
            try {
                uscHost.changeWorkingDirectory("/" + userName);
               // MyQuitCSVHelper.logEMAEvents("Directory change success", MyQuitCSVHelper.getFulltime());
            }
            catch (Exception de) {
                uscHost.makeDirectory("/" + userName);
                uscHost.changeWorkingDirectory("/" + userName);
              //  MyQuitCSVHelper.logEMAEvents("Made directory success", MyQuitCSVHelper.getFulltime());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
