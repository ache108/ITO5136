package View;

import Control.*;
import Model.*;
import View.Input;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.io.*;
import java.util.Locale;

public class JobSeekerUI extends View.UserUI
{

    public static void jobSeekerInputs(String userName)
            throws IOException
    {
        // get user inputs
        Model.User newUser = View.UserUI.userRegisterScreen(userName);
        // get extra job seeker inputs
        Input input = new View.Input();
        String wrkType = "";
        String wrkResidency = "";
        boolean verifiedInput;
        String msg = "Please enter ";

        do {
            wrkType = input.acceptString(msg + "the type of work you are looking for (Full time, Contract, Part time)");
            verifiedInput = userVerifyInputs(wrkType);
        } while (!verifiedInput);

        do {
            wrkResidency = input.acceptString(msg + "your Australian residency status (Visaholder, resident, citizen)");
            verifiedInput = userVerifyInputs(wrkResidency);
        } while (!verifiedInput);

        ArrayList <String> iptSkills = jobSeekerSkillInput();
        double wrkHrlyRate = input.acceptDouble(msg + "your desired hourly salary rate.");

        // Send to Controller to create new user on Model
        Control.JobSeekerCtrl.createNewJobSeeker(newUser, wrkHrlyRate, wrkType, wrkResidency, iptSkills);
    }

    public static ArrayList<String> jobSeekerSkillInput()
    {
        // assumes at least one skill is added
        boolean addAnotherSkill = true;
        boolean charInputCheck = true;
        ArrayList <String> iptSkills = new ArrayList<String>();
        Input input = new Input();
        do {
            String iptSkill = input.acceptString("Please enter a skill to add to your profile.");
            iptSkills.add(iptSkill);
            // add another skill?
            char userRepsonse = input.acceptChar("To add another skill please enter y. \nTo complete the list please enter n");
            do {
                if (userRepsonse == 'y') {
                    addAnotherSkill = true;
                    charInputCheck = false;
                } else if (userRepsonse == 'n') {
                    addAnotherSkill = false;
                    charInputCheck = false;
                } else {
                    System.out.println("Please enter y or n!");
                    charInputCheck = true;
                }
            } while (charInputCheck);
        } while (addAnotherSkill);

        return iptSkills;
    }

    public static void jobSeekerRegisterScreen(String userName)
            throws IOException, ParseException {
        jobSeekerInputs(userName);
        Control.JobSeekerCtrl.runJSHome();
    }

    public static void displayJSDetails()
            throws IOException, FileNotFoundException, ParseException
    {
            Control.FileIO file = new Control.FileIO(Control.JSS.JSDETAILS);

            String[] numJob = file.readFile("\n").split("\n");

            System.out.print("\n              VIEW PROFILE\n"
                + "--------------------------------------------");

            for (int i = 0; i < numJob.length; i++)
            {
                String[] details = numJob[i].split(";");
                if (details[0].equals(Control.LogInCtrl.getRcUsername())) //(display only profile for this user only)
                {
                    System.out.println("\nEmail:                " + details[1]);
                    System.out.println("First Name:           " + details[2]);
                    System.out.println("Last Name:            " + details[3]);
                    System.out.println("City:                 " + details[4]);
                    System.out.println("State:                " + details[5]);
                    System.out.println("Date of Birth:        " + details[6]);
                    System.out.println("Public Profile:       " + details[7]);
                    System.out.println("Desired Yearly Wage:  " + details[8]);
                    System.out.println("Work Type:            " + details[9]);
                    System.out.println("Work Residency:       " + details[10]);
                    Control.JobSeekerCtrl.displaySkills2();
                }
            }
            Control.JobSeekerCtrl.editJSOptions();
    }

    public static int editJSOptionsDisplay()
    {
        Input input = new Input();
        String msg = "\n\n         EDIT JOB SEEKER PROFILE\n"
                + "--------------------------------------------\n"
                + "Press 1 to edit your email\n"
                + "Press 2 to edit your first name\n"
                + "Press 3 to edit your last name\n"
                + "Press 4 to edit your city\n"
                + "Press 5 to edit your state\n"
                + "Press 6 to edit your date of birth\n"
                + "Press 7 to edit whether to make your profile public or private\n"
                + "Press 8 to edit your desired yearly wage\n"
                + "Press 9 to edit your work type\n"
                + "Press 10 to edit your work residency\n"
                + "Press 11 to edit your skills\n"
                + "Press 0 to go back";
        return input.acceptInt(msg, 0, 11);
    }



    public static int displayJSHome()
    {
        Input input = new Input();
        String msg = "----------------------------------\n"
                +    "|      JOB SEEKER HOME PAGE      |\n"
                +    "----------------------------------\n"
                + "Press 1 to search for jobs\n"
                + "Press 2 to view and edit your profile\n"
                + "Press 3 to view your interview offers\n"
                + "Press 4 to view applications\n"
                + "Press 0 to log out";
        return input.acceptInt(msg, 0, 4);
    }

    //Retrieve user input search criteria keywords and returns a Job Listing object.
    public Model.JobListing inputSearchKeywords()
            throws IOException
    {
        System.out.println("\n            SEARCH FOR JOBS\n"
                         + "--------------------------------------------");
        Input input = new Input();
        Control.JobSeekerCtrl jsc = new JobSeekerCtrl();
        JobListingUI jlu = new JobListingUI();
        Model.JobListing req = new JobListing();
        JobListingCtrl jlc = new JobListingCtrl();

        FileIO file = new FileIO(JSS.JSSJOBCATEGORY);
        String[] list = file.readFile("\n").split("\n");

        System.out.println("\nPlease enter keywords for each respective fields to start your search.");
        req.setJobTitle(input.acceptString("\nJob title: "));

        System.out.println("\n-----------------");
        jlu.displayJobCategories();
        System.out.println("-----------------");
        String jobCat = jlu.returnJobCategory(input.acceptInt("\nPlease select the job category *", 1, list.length));
        req.setJobCategory(jobCat);

        req.setJobLocation(input.acceptString("\nLocation of the job:"));
        req.setJobHours(input.acceptString("\nJob type (Full time, Contract, Part time):"));
        req.setJobPay(input.acceptString("\nJob compensation per annum"));
        //req.setJobSkills(/*JOB SEEKER SKILL SET IN PROFILE*/);

        return req;
    }
}