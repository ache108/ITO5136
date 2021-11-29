package Control;

import Model.*;
import View.JobSeekerUI;
import View.LogInUI;

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

    public static Model.JobSeeker getCurrentJobSeeker()
            throws IOException, ParseException
    {
        Model.User curUser = Control.UserCntrl.getCurrentUser();
        Control.FileIO file = new Control.FileIO(Control.JSS.JSDETAILS);

        String[] numJob = file.readFile("\n").split("\n");
        String workType = "";
        String workRes = "";
        String wage = "";
        String strSkills = "";

        for (int i = numJob.length - 1; i >= 0; i--) {
            String[] details = numJob[i].split(";");
            if (details[0].equals(curUser.userName)) //(display only profile for this user only)
            {
                wage = details[8];
                workType = details[9];
                workRes = details[10];
                strSkills = details[11];
                break;
            }
        }
        double hrlyWage = Double.parseDouble(wage);
        //.replace('[', ' ').replace(']', ' ').trim()
        String[] skillsSplit = strSkills.split(",");

        ArrayList<String> usrSkills = new ArrayList<String>();
        for (int i=0; i< skillsSplit.length; i++)
        {
            usrSkills.add(skillsSplit[i]);
        }

        Model.JobSeeker js = new JobSeeker(curUser, hrlyWage, workType, workRes, usrSkills);
        return js;
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

    //Method to link with Job Listing Control.
    public static void searchJob() throws IOException, ParseException {
        JobListingCtrl jlc = new JobListingCtrl();

        jlc.viewJLFromJS();
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



}
