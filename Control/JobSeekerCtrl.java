package Control;

import View.JobListingUI;
import View.JobSeekerUI;
import View.LogInUI;

public class JobSeekerCtrl {

    //Job Seeker home page
    public void runJSHome()
    {
        LogInUI ui = new View.LogInUI();
        LogInCtrl lic = new LogInCtrl();
        JobSeekerUI js = new View.JobSeekerUI();

        int choiceJS = js.displayJSHome();
        switch (choiceJS)
        {
            case 1:
                //link to search for job

            case 2:
                //link to view profile
            case 3:
                //link to view interview
            case 4:
                //link to view applications
            case 0:
                //logging out
                ui.displayMsg("Logging out\n");
                lic.start();
        }
    }

}
