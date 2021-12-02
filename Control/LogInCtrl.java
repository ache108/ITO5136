package Control;

import View.JobSeekerUI;
import View.LogInUI;
import View.RecruiterUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class LogInCtrl
{
    //mutuator & get methods can't be set here on the controller? Does this violate MVC and increase coupling?
    private static String rcUsername;
    public static String getRcUsername()
    {
        return rcUsername;
    }
    public void setRcUsername(String rcUsername)
    {
        rcUsername = this.rcUsername;
    }

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

    private void logIn(int usrType) throws IOException, ParseException {
        //display log in screen, get user input
        //access job seeker usernames/passwords from file
        //if successful, direct to job seeker controller
        //if unsuccessful, loop (user chooses to try again or go back etc.)
        LogInUI ui = new View.LogInUI();
        JobSeekerCtrl jsc = new JobSeekerCtrl();
        RecruiterCtrl rcc = new RecruiterCtrl();

        String username = ui.inputUsrName();
        setRcUsername(username);
        rcUsername = username;
        String passwd = ui.inputUsrPwd();
        boolean verifiedUsr = checkUsrCred(usrType, username, passwd);

        if(verifiedUsr)
        {
            ui.displayMsg("Logging in as " + username);

            if(usrType == 1)
            {
                jsc.runJSHome();
            }
            if(usrType == 2)
            {
                //My application won't run unless I have a catch statement here. Not sure if this is the best way??
                try {
                    rcc.runRCHome();
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            start();
        }
    }

    private void register(int usrType) throws IOException, ParseException {
        LogInUI ui = new LogInUI();

        String filename = "";
        if(usrType == 3)
            filename = JSS.JSLOGIN;
        if(usrType == 4)
            filename = JSS.RCLOGIN;
        String[][] usernames = {};
        try
        {
            usernames = getLoginCreds(filename);
            String iptName = "";
            boolean validName = true;
            do
            {
                validName = true;
                iptName = ui.inputUsrName();

                if(iptName.contains(",") || iptName.contains(";"))
                {
                    ui.displayMsg("Username cannot contain ',' or ';'");
                    validName = false;
                    continue;
                }

                for(int i = 0; i < usernames.length; i++)
                {
                    if(iptName.equals(usernames[i][0]))
                    {
                        int choice = ui.displayChoice("Username already exists.\nPress 1 to try another\nPress 0 to go back", 0, 1);
                        if(choice == 0)
                        {
                            start();
                            return;
                        }
                        if(choice == 1)
                        {
                            validName = false;
                            break;
                        }
                    }
                }
            }while(!validName);

            String iptPwd = ui.inputUsrPwd();
            updateFile(filename, iptName, iptPwd);
            setRcUsername(iptName);
            rcUsername = iptName;
            if(usrType == 3)
                JobSeekerUI.jobSeekerRegisterScreen(iptName);
            if(usrType == 4)
                RecruiterUI.recruiterRegisterScreen(iptName);
        }
        catch(Exception e)
        {
            ui.displayMsg("Error: there was a problem accessing the file");
            start();
        }
    }

    //control flow of use case
    public void start() throws IOException, ParseException {
        LogInUI ui = new View.LogInUI();
        int logInType = ui.displayWelcomeScreen();
        switch (logInType)
        {
            case 1:
            case 2:
                logIn(logInType);
                break;
            case 3:
            case 4:
                register(logInType);
                break;
            case 5:
                ui.displayMsg("Exiting Program");
                System.exit(0);
                break;
        }
    }

    private void updateFile(String filename, String usrname, String pwd)
            throws IOException
    {
        FileIO file = new FileIO(filename);
        file.appendFile(usrname + "," + pwd);
    }
}
