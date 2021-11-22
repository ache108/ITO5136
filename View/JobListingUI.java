package View;

import java.io.IOException;
import java.util.Date;

public class JobListingUI {

    //Accepts input from recruiters about job details, then directs them to job listing control to add the job
    public void inputJobDetails()
            throws IOException
    {
        Input input = new Input();
        System.out.println("\nPlease provide the following details.\n(* indicates a mandatory field)");
        String msg = "\nPlease enter ";
        String jobTitle = input.acceptString(msg + "the job title *");
        String jobCategory = input.acceptString(msg + "the job category *");
        String jobLocation = input.acceptString(msg + "the location of the job *");
        String jobHours = input.acceptString(msg + "the job type (Full time, Contract, Part time) *");
        String jobPay = input.acceptString(msg + "the compensation per annum");
        String jobSkills = input.acceptString(msg + "the skills required for the job"); //MIGHT NEED TO REDO THIS TO ALLOW FOR ADDING MULTIPLE KEYWORDS
        String jobDescription = input.acceptString(msg + "the job description *");
        Date appDeadline = input.acceptDate(msg + "the application deadline *");
        displayJobDetails(jobTitle, jobCategory, jobLocation, jobHours, jobPay, jobSkills, jobDescription, appDeadline);
        boolean jobAd = advertiseJob();

        // Send to Job Listing Controller to create new job
        Control.JobListingCtrl.addNewJob(jobTitle, jobCategory, jobLocation, jobHours, jobPay, jobSkills, jobDescription, appDeadline, jobAd);

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

    //Displays the details of the job provided
    public void displayJobDetails(String jobTitle, String jobCategory, String jobLocation, String jobHours, String jobPay, String jobSkills, String jobDescription, Date appDeadline)
    {
        System.out.println("\nJob title: " + jobTitle);
        System.out.println("Job category: " + jobCategory);
        System.out.println("Location: " + jobLocation);
        System.out.println("Hours: " + jobHours);
        System.out.println("Compensation: " + jobPay);
        System.out.println("Skills required: " + jobSkills);
        System.out.println("Description:\n" + jobDescription + "\n");
        System.out.println("Application deadline: " + appDeadline + "\n");
    }
}
