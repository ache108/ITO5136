public class LogInCtrl
{
    private void jsLogIn()
    {
        //display log in screen, get user input
        //access job seeker usernames/passwords from file
        //if successful, direct to job seeker controller
        //if unsuccessful, loop (user chooses to try again or go back etc.)
        LogInUI ui = new LogInUI();
        String username = ui.inputUsrName();

    }

    private void rcLogIn()
    {
        //display log in screen, get user input
        //access recruiter usernames/passwords from file
        //if successful, direct to recruiter controller
        //if unsuccessful, loop (user chooses to try again or go back etc.)
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
            case 4:
            case 5:
                System.out.println(logInType);
                break;
        }
    }
}
