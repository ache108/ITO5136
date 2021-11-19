public class LogInUI
{
    //get username
    public String inputUsrName()
    {
        Input input = new Input();
        return input.acceptString(" LOG IN\nPlease enter your username:");
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
