package Control;

import View.JobListingUI;
import View.JobSeekerUI;
import View.LogInUI;
import View.RecruiterUI;
import Control.LogInCtrl;

import java.io.IOException;

public class RecruiterCtrl {

    //Recruiter home page
    public void runRCHome() throws IOException {
        LogInUI ui = new View.LogInUI();
        LogInCtrl lic = new LogInCtrl();
        RecruiterUI rc = new View.RecruiterUI();
        JobListingUI jl = new View.JobListingUI();

        int choiceRC= rc.displayRCHome();
        switch (choiceRC)
        {
            case 1:
                //link to create job
                jl.inputJobDetails();
                break;
            case 2:
                //link to view job listings
            case 3:
                //link to view company profile
                rc.getCompanyCreds();
            case 4:
                //link to view interview offers
            case 0:
                //logging out
                ui.displayMsg("Logging out\n");
                lic.start();
        }
    }

}
