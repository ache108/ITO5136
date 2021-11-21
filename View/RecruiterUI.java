package View;
import View.Input;

public class RecruiterUI extends View.UserUI
{
    public static void recruiterInputs()
    {
        Input input = new Input();
        String usrCompany = input.acceptString("Please enter the Company you work for.");
    }

    public static void recruiterRegisterScreen()
    {
        View.UserUI.userRegisterScreen();
        recruiterInputs();
    }
}