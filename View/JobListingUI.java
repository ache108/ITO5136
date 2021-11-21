package View;

import java.util.Date;

public class JobListingUI {

    private String jobTitle;
    private String jobCategory;
    private String jobLocation;
    private String jobHours;
    private String jobPay;
    private String jobSkills;
    private String jobDescription;
    private Date appDeadline;

    public void obtainJobDetails()
    {
        Input input = new Input();
        System.out.println("\nPlease provide the following details.\n(* indicates a mandatory field)");
        String msg = "\nPlease enter ";
        jobTitle = input.acceptString(msg + "the job title *");
        jobCategory = input.acceptString(msg + "the job category *");
        jobLocation = input.acceptString(msg + "the location of the job *");
        jobHours = input.acceptString(msg + "the job type (Full time, Contract, Part time) *");
        jobPay = input.acceptString(msg + "the compensation per annum");
        jobSkills = input.acceptString(msg + "the skills required for the job"); //MIGHT NEED TO REDO THIS TO ALLOW FOR ADDING MULTIPLE KEYWORDS
        jobDescription = input.acceptString(msg + "the job description *");
        appDeadline = input.acceptDate(msg + "the application deadline *");
        displayJobDetails();
        advertiseJob();
    }

    public static void advertiseJob()
    {
        boolean charInputCheck = true;
        Input input = new Input();
        char userResponse;

        do {
            userResponse = input.acceptChar("Do you want to advertise this job? (y/n)\n(This allows job seekers to view the job in their search results.)");
            if (userResponse == 'y') {
                charInputCheck = false;
                //put job as public so that it can be called when js search jobs
            } else if (userResponse == 'n') {
                charInputCheck = false;
                //put job as private so that job can be saved without appearing on searches
            } else {
                System.out.println("Please enter y or n!");
                charInputCheck = true;
            }
        } while (charInputCheck);
    }

    public void displayJobDetails()
    {
        System.out.println("\nJob title: " + jobTitle);
        System.out.println("Job category: " + jobCategory);
        System.out.println("Location: " + jobLocation);
        System.out.println("Hours: " + jobHours);
        System.out.println("Compensation: " + jobPay);
        System.out.println("Skills required: " + jobSkills);
        System.out.println("Description:\n" + jobDescription);
        System.out.println("Application deadline: " + appDeadline + "\n");
    }
}
