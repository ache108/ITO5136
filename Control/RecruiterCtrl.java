package Control;

import Model.JobSeeker;
import Model.Recruiter;
import Model.User;
import View.JobListingUI;
import View.JobSeekerUI;
import View.LogInUI;
import View.RecruiterUI;
import Control.LogInCtrl;
import Control.JobListingCtrl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class RecruiterCtrl {
    public static void createNewRecruiter(Model.User newUser)
            throws IOException
    {
        // send to model to create new recruiter personal details
        Model.Recruiter rc = new Recruiter(newUser);
        // write Output
        String wrRC = writeRCString(rc);
        Control.UserCntrl.writeNewUserToFile(wrRC, Control.JSS.RCDETAILS);
    }
    //Recruiter home page
    public static void runRCHome() throws IOException, ParseException {
        LogInUI ui = new View.LogInUI();
        LogInCtrl lic = new LogInCtrl();
        RecruiterUI rc = new View.RecruiterUI();
        JobListingUI jlu = new View.JobListingUI();
        JobListingCtrl jlc = new Control.JobListingCtrl();

        int choiceRC= rc.displayRCHome();
        switch (choiceRC)
        {
            case 1:
                //link to create job
                jlu.inputJobDetails();
                break;
            case 2:
                //link to view job listings
                jlu.displayJobList();
                break;
            case 3:
                //link to view company profile
                rc.displayCompany();
                break;
            case 4:
                //link to view interview offers
            case 0:
                //logging out
                ui.displayMsg("Logging out\n");
                lic.start();
        }
    }

    public static String writeRCString(Recruiter newRecruiter)
    {
        String msg = "";
        msg += newRecruiter.userName;
        msg += ";" + newRecruiter.userEmail;
        msg += ";" + newRecruiter.firstName;
        msg += ";" + newRecruiter.lastName;
        msg += ";" + newRecruiter.city;
        msg += ";" + newRecruiter.state;
        msg += ";" + newRecruiter.dateOfBirth;
        return msg;
    }
}
