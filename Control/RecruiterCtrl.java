package Control;

import View.JobListingUI;
import View.JobSeekerUI;
import View.LogInUI;
import View.RecruiterUI;
import Control.LogInCtrl;

import java.io.IOException;
import java.text.ParseException;

public class RecruiterCtrl {

    //Recruiter home page
    public void runRCHome() throws IOException, ParseException {
        LogInUI ui = new View.LogInUI();
        LogInCtrl lic = new LogInCtrl();
        RecruiterUI rc = new View.RecruiterUI();
        JobListingUI jl = new View.JobListingUI();
        JobListingCtrl jlc = new JobListingCtrl();

        int choiceRC= rc.displayRCHome();
        switch (choiceRC)
        {
            case 1:
                //link to create job
                jl.inputJobDetails();
                break;
            case 2:
                //link to view job listings
                jl.displayJobList();
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

}
