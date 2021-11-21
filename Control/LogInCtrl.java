package Control;
import View.LogInUI;
import View.RegisterUI;

public class LogInCtrl
{

    private void logIn(int usrType)
    {
        //display log in screen, get user input
        //access job seeker usernames/passwords from file
        //if successful, direct to job seeker controller
        //if unsuccessful, loop (user chooses to try again or go back etc.)
        LogInUI ui = new View.LogInUI();

        boolean verifiedUsr = true;
        do {
            String username = ui.inputUsrName();
            String passwd = ui.inputUsrPwd();
            verifiedUsr = rcUsrCheck(username, passwd);
        } while (verifiedUsr);

        if (usrType == 1)
        {
            // direct to Job seeker controller
        }
        else
        {
            // direct to recruiter controller
        }
    }

    private boolean rcUsrCheck(String iptName, String iptPwd)
    {
        // check username and passwords here?
        // do we store in file?
        String testName = "Test";
        String testPwd = "Testy#123";

        if (testName.equals(iptName) && testPwd.equals(iptPwd))
        {
            // Print out welcome!
            System.out.println("Welcome " + iptName);
            return false;
        }
        else
        {
            System.out.println("Error logging in. Please check your username & password.");
            return true;
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
                logIn(logInType);
                break;
            case 2:
                logIn(logInType);
                break;
            case 3:
                RegisterUI.userRegisterScreen(logInType);
                break;
            case 4:
                RegisterUI.userRegisterScreen(logInType);
                break;
            case 5:
                System.out.println(logInType);
                break;
        }
    }
}
