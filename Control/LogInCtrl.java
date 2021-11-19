package Control;
import View.LogInUI;

public class LogInCtrl
{
    private void jsLogIn()
    {
        //display log in screen, get user input
        //access job seeker usernames/passwords from file
        //if successful, direct to job seeker controller
        //if unsuccessful, loop (user chooses to try again or go back etc.)
        LogInUI ui = new LogInUI();
        System.out.println(" LOG IN");
        String username = ui.inputUsrName();

    }

    private void rcLogIn()
    {
        //display log in screen, get user input
        //access recruiter usernames/passwords from file
        //if successful, direct to recruiter controller
        //if unsuccessful, loop (user chooses to try again or go back etc.)
        LogInUI ui = new LogInUI();
        System.out.println(" LOG IN");
        String username = ui.inputUsrName();
    }

    private void jsRegister()
    {
        //display register screen, get user input
        //compares to all email and username from file
        //if successful (no repeats), direct to js login controller
        //if existing email or repeat username, loop (user chooses to try again or go back etc.)
        //if password doesn't fit requirement, loop
        LogInUI ui = new LogInUI();
        System.out.println(" REGISTER");
        String username = ui.inputUsrName();
    }

    private void rcRegister()
    {
        //display register screen, get user input
        //compares to all email and username from file
        //if successful (no repeats), direct to rc login controller
        //if existing email or repeat username, loop (user chooses to try again or go back etc.)
        //if password doesn't fit requirement, loop
        LogInUI ui = new LogInUI();
        System.out.println(" REGISTER");
        String username = ui.inputUsrName();
    }

    //control flow of use case
    public void start()
    {
        LogInUI ui = new LogInUI();
        int logInType = ui.displayWelcomeScreen();
        switch (logInType)
        {
            case 1:
                jsLogIn();
                break;
            case 2:
                rcLogIn();
                break;
            case 3:
                jsRegister();
                break;
            case 4:
                rcRegister();
                break;
            case 5:
                System.out.println(logInType);
                break;
        }
    }
}
