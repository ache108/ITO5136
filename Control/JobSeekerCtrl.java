package Control;

import Model.JobSeeker;
import Model.User;
import View.Input;
import View.JobListingUI;
import View.JobSeekerUI;
import View.LogInUI;
import Control.LogInCtrl;
import Control.UserCntrl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class JobSeekerCtrl {

    public static void createNewJobSeeker(Model.User newUser, double hrlyRate, String wrkType, String wrkResidency, ArrayList<String> iptSkills)
            throws IOException
    {
        // send to model to create
        Model.JobSeeker js = new JobSeeker(newUser, hrlyRate, wrkType, wrkResidency, iptSkills);
        // write Output to File
        String wrJS = writeJSString(js);
        UserCntrl.writeNewUserToFile(wrJS, Control.JSS.JSDETAILS);
    }

    //Job Seeker home page
    public static void runJSHome() throws IOException, ParseException {
        LogInUI ui = new View.LogInUI();
        LogInCtrl lic = new LogInCtrl();
        JobSeekerUI js = new View.JobSeekerUI();

        int choiceJS = js.displayJSHome();
        switch (choiceJS)
        {
            case 1:
                //link to search for job
                searchJob();
                break;
            case 2:
                //link to view profile
                js.displayJSDetails();
                break;
            case 3:
                //link to view interview
                break;
            case 4:
                //link to view applications
                break;
            case 0:
                //logging out
                ui.displayMsg("Logging out\n");
                lic.start();
        }
    }

    public static String writeJSString(JobSeeker js)
    {
        String msg = "";
        msg += js.userName;
        msg += ";" + js.userEmail;
        msg += ";" + js.firstName;
        msg += ";" + js.lastName;
        msg += ";" + js.city;
        msg += ";" + js.state;
        msg += ";" + js.dateOfBirth;
        msg += ";" + js.publicProfile;
        msg += ";" + js.hourlyWageRate;
        msg += ";" + js.wrkType;
        msg += ";" + js.residencyType;
        msg += ";" + js.skillsList + ";";

        return msg;
    }

    //Method to call search keyword input and job category input, and return array list containing all the keywords.
    //Unsure if array list is best way to work with the existing matching score?
    //Might change the method to call another method to perform search and return results.
    public static void searchJob() throws IOException, ParseException {
        View.JobSeekerUI jsu = new JobSeekerUI();
        JobListingCtrl jlc = new JobListingCtrl();
        View.JobListingUI jlu = new JobListingUI();

        ArrayList<String> searchKeywords = jsu.inputSearchKeywords();

        //Need to write code on filtering results using searchKeywords.

        jlc.viewJLFromJS();
    }

}
