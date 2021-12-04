package View;
import View.Input;

public class LogInUI
{
    //get username
    public String inputUsrName()
    {
        Input input = new Input();
        return input.acceptString("\nPlease enter your username:");
    }

    //get username
    public String inputUsrPwd()
    {
        Input input = new Input();
        return input.acceptString("\nPlease enter your password:");
    }

    public int displayChoice(String msg, int min, int max)
    {
        Input input = new Input();
        return input.acceptInt(msg, min, max);
    }

    public void displayMsg(String msg)
    {
        System.out.println(msg);
    }

    //display welcome screen and return user input
    public int displayWelcomeScreen()
    {
        Input input = new Input();
        String msg = "----------------------------\n"
                +    "|      WELCOME TO JSS      |\n"
                + "----------------------------\n"
                + "To navigate, please enter a number and press enter to proceed through each page.\n\n"
                + "Press 1 to log in as a Job Seeker\n"
                + "Press 2 to log in as a Recruiter\n"
                + "Press 3 to register as a Job Seeker\n"
                + "Press 4 to register as a Recruiter\n"
                + "Press 5 to Exit Application";
        return input.acceptInt(msg, 1, 5);
    }
}
