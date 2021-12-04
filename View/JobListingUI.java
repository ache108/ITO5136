package View;

import Control.FileIO;
//import Control.JSS;
import Control.LogInCtrl;
import Control.JobSeekerCtrl;
import Control.JobListingCtrl;
import Model.JobSeeker;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class JobListingUI {

    //Accepts input for new job category
    public String addJobCategory() throws IOException {
        Input input = new Input();

        String newCat = input.acceptString("Please enter a new category: ");

        return newCat;
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

    //Accepts user input when choosing job listing from list
    public static int chooseJobListing(int max)
    {
        Input input = new Input();
        int jobChose = input.acceptInt("Please enter the job number to view the job listing.\nAlternatively, press 0 to go back.", 0, max);
        return jobChose;
    }

    //Display list of Job Caftegories we have on system
    public void displayJobCategories() throws IOException {
        FileIO file = new FileIO(Control.JSS.JSSJOBCATEGORY);
        String[] list = file.readFile("\n").split("\n");

        for (int i = 0; i < list.length; i++)
        {
            System.out.println((i+1) + ": " + list[i]);
        }

    }

    //Display the options RC has when editing job listing
    public static int editJobOptions()
    {
        Input input = new Input();
        String msg = "Press 1 to edit job title\n"
                + "Press 2 to edit job category\n"
                + "Press 3 to edit job location\n"
                + "Press 4 to edit job hours\n"
                + "Press 5 to edit job compensation\n"
                + "Press 6 to edit job's required skills\n"
                + "Press 7 to edit job description\n"
                + "Press 8 to edit application deadline\n"
                + "Press 9 to edit job advertisement status\n"
                + "Press 0 to go back\n"
                + "--------------------------------------------";
        return input.acceptInt(msg, 0, 9);
    }

    public int getSkillNo(int max)
    {
        Input input = new Input();
        int skillNo = input.acceptInt("Please choose the skill you want to modify.\nAlternatively, press 0 to go back.", 0, max);
        return skillNo;
    }

    //Accepts input from recruiters about job details, then directs them to job listing control to add the job
    public void inputJobDetails()
            throws IOException, ParseException {
        Model.JobListing jl = new Model.JobListing();
        Control.JobListingCtrl jlc = new Control.JobListingCtrl();
        Input input = new Input();

        FileIO file = new FileIO(Control.JSS.JSSJOBCATEGORY);
        String[] list = file.readFile("\n").split("\n");

        System.out.println("\n        CREATE A NEW JOB\n"
            + "--------------------------------------------");
        System.out.println("\nPlease provide the following details.\n(Every field is mandatory.)");
        String msg = "\nPlease ";

        //Enter job title
        jl.setJobTitle(input.acceptMandatoryString(msg + "enter the job title:"));

        //Display list of job categories and allow recruiter to choose from or add new ones to the system.
        System.out.println("\n---------------------------");
        displayJobCategories();
        System.out.println("---------------------------");
        String jobCat = returnJobCategory(input.acceptInt(msg + "select the job category:", 1, list.length));
        if (jobCat.equals("Other"))
        {
            jl.setJobCategory(addJobCategory());
        } else {
            jl.setJobCategory(jobCat);
        }

        //Enter location
        jl.setJobLocation(input.acceptMandatoryString(msg + "enter the location of the job:"));

        //Enter job hours/type
        jl.setJobHours(input.acceptMandatoryString(msg + "enter the job type (Full time, Contract, Part time):"));

        //Enter job pay
        jl.setJobPay(input.acceptMandatoryString(msg + "enter the compensation per annum:"));

        //Enter job skills
        jl.setJobSkills(inputJobListingSkill());

        //Enter job description
        jl.setJobDescription(input.acceptMandatoryString(msg + "enter the job description:"));

        //Enter job application deadline
        //boolean validIpt = true;

        boolean validIpt = true;
        Date date = null;
        Date iptDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        do {
            try {
               iptDate = input.acceptDate(msg + "enter the application deadline in format dd-mm-yyyy:");
               validIpt = false;

            } catch (Exception e) {
                System.out.println("Error! Please enter date in format dd-mm-yyyy");
            }
        } while (validIpt);
        jl.setAppDeadline(iptDate);

        jl.displayJobDetails();

        //Enter job advertisement status
        jl.setJobAd(advertiseJob());

        //Generate job Id
        jl.setJobId(JobListingCtrl.generateJobID(Control.JSS.JSSJOBLIST));

        //Link job to RC
        jl.setJobRC(LogInCtrl.getRcUsername());

        // Send to Job Listing Controller to create new job
        jlc.addNewJob(jl.getJobRC(), jl.getJobId(), jl.getJobTitle(), jl.getJobCategory(), jl.getJobLocation(), jl.getJobHours(), jl.getJobPay(), jl.getJobSkills(), jl.getJobDescription(), jl.getAppDeadline(), jl.getJobAd());

        System.out.println("\n---------------------------\n"
                + " Job successfully created!\n"
                + "---------------------------");

        int proceedNo = input.acceptInt("Please press 1 to proceed.", 1, 1);

        if (proceedNo == 1)
        {
            jlc.viewJLFromRC();
        }
    }

    //Accepts input from users to add skills
    public ArrayList<String> inputJobListingSkill()
    {
        // assumes at least one skill is added
        boolean addAnotherSkill = true;
        boolean charInputCheck = true;
        ArrayList <String> iptSkills = new ArrayList<String>();
        Input input = new Input();
        do {
            String iptSkill = input.acceptString("\nPlease enter a skill the job requires:");
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

    //Display the options RC has when they view job listing
    public static int manageJobOptions()
    {
        Input input = new Input();
        String msg = "--------------------------------------------\n"
                + "Press 1 to edit job listing\n"
                + "Press 2 to invite candidates\n"
                + "Press 3 to delete job listing\n"
                + "Press 0 to go back\n"
                + "--------------------------------------------\n";
        return input.acceptInt(msg, 0, 3);
    }

    //Prints the options for editing a specific skill from a job listing
    public static int modifySkillOptions()
    {
        Input input = new Input();
        String msg = "\n--------------------------------------------\n"
                + "      EDIT SKILL\n"
                + "Press 1 to modify skill\n"
                + "Please 2 to delete skill\n"
                + "Press 0 to go back\n"
                + "--------------------------------------------\n";
        return input.acceptInt(msg, 0, 2);
    }

    public static int openJobOptions()
    {
        Input input = new Input();
        String msg = "--------------------------------------------\n"
                + "Press 1 to apply for this job\n"
                + "Press 0 to go back to homepage\n"
                + "--------------------------------------------\n";
        return input.acceptInt(msg, 0, 1);
    }

    //Return the string job category based on user input number
    public String returnJobCategory(int num) throws IOException {
        FileIO file = new FileIO(Control.JSS.JSSJOBCATEGORY);
        String[] list = file.readFile("\n").split("\n");

        String jobCat = list[num - 1];

        return jobCat;
    }

    //Prints the options for managing the skills of a specific job listing
    public static int openSkillMenu()
    {
        Input input = new Input();
        String msg = "\n--------------------------------------------\n"
                + "      EDIT JOB LISTING\n"
                + "Press 1 to add new skill\n"
                + "Press 2 to modify/delete a skill\n"
                + "Press 0 to go back\n"
                + "--------------------------------------------\n";
        return input.acceptInt(msg, 0, 2);
    }

    //Prints the job seekers who match with a job and allow user to select one to invite for interview
    public int selectJobSeeker(ArrayList<JobSeeker> js)
    {
        Input input = new Input();
        String msg = "-----------------------------------------\n";
        for(int i = 0; i < js.size(); i++)
        {
            msg += "Job Seeker " + (i + 1) + ":\n";
            msg += "Matching Score: " + js.get(i).getMatchingScore() + "\n";
            msg += "Job skills are:\n";

            for(int j = 0; j < js.get(i).getSkillListSize(); j++)
            {
                msg += (j + 1) + ": " + js.get(i).getSkillFromList(j) + "\n";
            }

            msg += "-----------------------------------------\n";
        }
        msg += "Please enter the job seeker number to view the job seeker.\n";
        msg += "Alternatively, press 0 to go back.";

        return input.acceptInt(msg, 0, js.size());
    }

}
