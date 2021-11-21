package View;
import View.Input;
import java.io.*;

public class RecruiterUI extends View.UserUI
{
    public static void recruiterInputs()
    {
        Input input = new Input();
        String usrCompany = input.acceptString("Please enter the Company you work for.");
    }

    public static void recruiterRegisterScreen()
            throws IOException
    {
        View.UserUI.userRegisterScreen();
        recruiterInputs();
    }

    public int displayRCHome()
    {
        Input input = new Input();
        String msg = "      RECRUITER HOME PAGE\n"
                + "Press 1 to create job listing\n"
                + "Press 2 to view job listing\n"
                + "Press 3 to view company profile\n"
                + "Press 4 to view interview offers\n"
                + "Press 0 to log out";
        return input.acceptInt(msg, 0, 4);
    }
}