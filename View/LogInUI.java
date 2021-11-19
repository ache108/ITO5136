package View;
import View.Input;

public class LogInUI
{
    //get username
    public String inputUsrName()
    {
        Input input = new Input();
        return input.acceptString("Please enter your username:");
    }

    //get email
    public String inputEmail()
    {
        Input input = new Input();
        return input.acceptString("Please enter your email:");
    }

    //get password
    public String inputPassword()
    {
        Input input = new Input();
        return input.acceptString("Please enter your password:");
    }

    //display welcome screen and return user input
    public int displayWelcomeScreen()
    {
        Input input = new Input();
        String msg = "      WELCOME\n"
                + "Press 1 to log in as a Job Seeker\n"
                + "Press 2 to log in as a Recruiter\n"
                + "Press 3 to register as a Job Seeker\n"
                + "Press 4 to register as a Recruiter\n"
                + "Press 5 to Exit Application";
        return input.acceptInt(msg, 1, 5);
    }
}
