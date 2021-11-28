package View;
import Control.FileIO;
import Control.JSS;
import Control.JobListingCtrl;
import Control.LogInCtrl;
import Model.JobListing;
import View.Input;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class JobListingUI {

    //Accepts input from recruiters about job details, then directs them to job listing control to add the job
    public void inputJobDetails()
            throws IOException
    {
        Model.JobListing jl = new Model.JobListing();
        Control.JobListingCtrl jlc = new Control.JobListingCtrl();
        Input input = new Input();

        FileIO file = new FileIO(JSS.JSSJOBCATEGORY);
        String[] list = file.readFile("\n").split("\n");

        System.out.println("\nPlease provide the following details.\n(* indicates a mandatory field)");
        String msg = "\nPlease ";
        jl.setJobTitle(input.acceptString(msg + "enter the job title *"));

        //Display list of job categories and allow recruiter to choose from or add new ones to the system.
        displayJobCategories();
        String jobCat = returnJobCategory(input.acceptInt(msg + "select the job category *", 1, list.length));
        if (jobCat.equals("Other"))
        {
            jl.setJobCategory(jlc.addJobCategory());
        } else {
            jl.setJobCategory(jobCat);
        }

        jl.setJobLocation(input.acceptString(msg + "enter the location of the job *"));
        jl.setJobHours(input.acceptString(msg + "enter the job type (Full time, Contract, Part time) *"));
        jl.setJobPay(input.acceptString(msg + "enter the compensation per annum"));
        jl.setJobSkills(jobListingSkillInput());
        jl.setJobDescription(input.acceptString(msg + "enter the job description *"));
        jl.setAppDeadline(input.acceptDate(msg + "enter the application deadline *"));
        jl.displayJobDetails();
        jl.setJobAd(advertiseJob());
        System.out.println(jl.labelJobAd());
        jl.setJobId(JobListingCtrl.generateJobID(JSS.JSSJOBLIST));
        jl.setJobRC(LogInCtrl.getRcUsername());

        // Send to Job Listing Controller to create new job
        jlc.addNewJob(jl.getJobRC(), jl.getJobId(), jl.getJobTitle(), jl.getJobCategory(), jl.getJobLocation(), jl.getJobHours(), jl.getJobPay(), jl.getJobSkills(), jl.getJobDescription(), jl.getAppDeadline(), jl.getJobAd());

    }

    //Display list of Job Categories we have on system
    public void displayJobCategories() throws IOException {
        FileIO file = new FileIO(JSS.JSSJOBCATEGORY);
        String[] list = file.readFile("\n").split("\n");

        for (int i = 0; i < list.length; i++)
        {
            System.out.println((i+1) + ": " + list[i]);
        }

    }

    //Return the string job category based on user input number
    public String returnJobCategory(int num) throws IOException {
        FileIO file = new FileIO(JSS.JSSJOBCATEGORY);
        String[] list = file.readFile("\n").split("\n");

        String jobCat = list[num - 1];

        return jobCat;
    }

    //Asking if recruiter wants to advertise job
    public static boolean advertiseJob()
    {
        boolean charInputCheck = true;
        boolean isAdvertised = false;
        Input input = new Input();
        char userResponse;

        do {
            userResponse = input.acceptChar("Do you want to advertise this job? (y/n)\n(This allows job seekers to view the job in their search results.)");
            if (userResponse == 'y') {
                charInputCheck = false;
                isAdvertised = true;
            } else if (userResponse == 'n') {
                charInputCheck = false;
                isAdvertised = false;
            } else {
                System.out.println("Please enter y or n!");
                charInputCheck = true;
            }
        } while (charInputCheck);

        return isAdvertised;
    }

    //displays abbreviated list of jobs posted by the recruiter
    public void displayJobList()
            throws IOException, FileNotFoundException, ParseException
    {
        Model.JobListing jl = new JobListing();
        Control.JobListingCtrl jlc = new Control.JobListingCtrl();
        jlc.printJobList();
    }

    public ArrayList<String> jobListingSkillInput()
    {
        // assumes at least one skill is added
        boolean addAnotherSkill = true;
        boolean charInputCheck = true;
        ArrayList <String> iptSkills = new ArrayList<String>();
        Input input = new Input();
        do {
            String iptSkill = input.acceptString("Please enter a skill that this job requires.");
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

    public static int chooseJobListing(int max)
    {
        Input input = new Input();
        int jobChose = input.acceptInt("Please enter the job number to view the job listing.\nAlternatively, press 0 to go back.", 0, max);
        return jobChose;
    }

    //Display the options RC has when editing job listing
    public static int editJobOptions()
    {
        Input input = new Input();
        String msg = "      EDIT JOB LISTING\n"
                + "Press 1 to edit job title\n"
                + "Press 2 to edit job category\n"
                + "Press 3 to edit job location\n"
                + "Press 4 to edit job hours\n"
                + "Press 5 to edit job compensation\n"
                + "Press 6 to edit job's required skills\n"
                + "Press 7 to edit job description\n"
                + "Press 8 to edit application deadline\n"
                + "Press 9 to edit job advertisement status\n"
                + "Press 0 to go back";
        return input.acceptInt(msg, 0, 9);
    }

    //Display the options RC has when they view job listing
    public static int manageJobOptions()
    {
        Input input = new Input();
        String msg = "Press 1 to edit job listing\n"
                + "Press 2 to view applications\n"
                + "Press 3 to invite candidates\n"
                + "Press 0 to go back";
        return input.acceptInt(msg, 0, 3);
    }

}
