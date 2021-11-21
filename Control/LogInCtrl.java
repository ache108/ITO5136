package Control;
import Control.JSS;
import Control.FileIO;
import View.LogInUI;
import View.JobSeekerUI;
import View.RecruiterUI;
import java.io.*;

public class LogInCtrl
{

    private boolean checkUsrCred(int usrType, String iptName, String iptPwd)
    {
        LogInUI ui = new LogInUI();

        String filename = "";
        if(usrType == 1)
            filename = JSS.JSLOGIN;
        if(usrType == 2)
            filename = JSS.RCLOGIN;

        try
        {
            String[][] loginCreds = getLoginCreds(filename);
            for(int i = 0; i < loginCreds.length; i++)
            {
                if(iptName.equals(loginCreds[i][0]))
                    if(iptPwd.equals(loginCreds[i][1]))
                        return true;
                    else
                    {
                        ui.displayMsg("Incorrect password");
                        return false;
                    }
            }
            ui.displayMsg("User not found");
            return false;
        }
        catch(FileNotFoundException e)
        {
            ui.displayMsg("Error: file not found");
            return false;
        }
        catch(IOException i)
        {
            ui.displayMsg("Error: there was a problem reading the file");
            return false;
        }
    }

    private String[][] getLoginCreds(String filename)
            throws IOException, FileNotFoundException
    {
        FileIO file = new FileIO(filename);

        String[] lines = file.readFile(";").split(";");
        String[][] output = new String[lines.length][2];
        for(int i = 0; i < lines.length; i++)
        {
            output[i] = lines[i].split(",");
        }

        return output;
    }

    private void logIn(int usrType)
    {
        //display log in screen, get user input
        //access job seeker usernames/passwords from file
        //if successful, direct to job seeker controller
        //if unsuccessful, loop (user chooses to try again or go back etc.)
        LogInUI ui = new View.LogInUI();

        String username = ui.inputUsrName();
        String passwd = ui.inputUsrPwd();
        boolean verifiedUsr = checkUsrCred(usrType, username, passwd);

        if(verifiedUsr)
        {
            ui.displayMsg("Logging in as " + username);

            if(usrType == 1)
            {
                //call jobseekerctrl
            }
            if(usrType == 2)
            {
                //call recruiterctrl
            }
        }
        else
        {
            start();
        }
    }

    //control flow of use case
    public void start()
    {
        LogInUI ui = new View.LogInUI();
        int logInType = ui.displayWelcomeScreen();
        switch (logInType)
        {
            case 1:
            case 2:
                logIn(logInType);
                break;
            case 3:
                JobSeekerUI.jobSeekerRegisterScreen();
                break;
            case 4:
                RecruiterUI.recruiterRegisterScreen();
                break;
            case 5:
                ui.displayMsg("Exiting Program");
                break;
        }
    }
}
