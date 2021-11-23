package Control;
import Control.LogInCtrl;

public class JSS
{
    //all filenames as public constants
    public static final String JSLOGIN = "Files/jsLoginCredentials.txt";
    public static final String RCLOGIN = "Files/rcLoginCredentials.txt";
    public static final String JSDETAILS = "Files/jsUserDetails.txt";
    public static final String RCDETAILS = "Files/rcUserDetails.txt";
    public static final String JSJOBLIST = "Files/jobListings.txt";


    public static void main(String[] args)
    {
        JSS jss = new JSS();
        jss.start();
    }

    //Initialise any classes/files, direct to log-in controller to begin
    public void start()
    {
        //Code to verify that all db files are available ???
        //Code to activate any objects that need to be active at the start of the program

        LogInCtrl lic = new LogInCtrl();
        lic.start();

        //Any code to close program (close files etc.)
    }
}