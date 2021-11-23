package View;

import Control.FileIO;
import Control.JobListingCtrl;
import Model.JobListing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class JobListingUI {

    //Accepts input from recruiters about job details, then directs them to job listing control to add the job
    public void inputJobDetails()
            throws IOException
    {
        Model.JobListing jl = new Model.JobListing();
        Control.JobListingCtrl jlc = new Control.JobListingCtrl();
        Input input = new Input();
        System.out.println("\nPlease provide the following details.\n(* indicates a mandatory field)");
        String msg = "\nPlease enter ";
        jl.setJobTitle(input.acceptString(msg + "the job title *"));
        jl.setJobCategory(input.acceptString(msg + "the job category *"));
        jl.setJobLocation(input.acceptString(msg + "the location of the job *"));
        jl.setJobHours(input.acceptString(msg + "the job type (Full time, Contract, Part time) *"));
        jl.setJobPay(input.acceptString(msg + "the compensation per annum"));
        jl.setJobSkills(input.acceptString(msg + "the skills required for the job")); //MIGHT NEED TO REDO THIS TO ALLOW FOR ADDING MULTIPLE KEYWORDS
        jl.setJobDescription(input.acceptString(msg + "the job description *"));
        jl.setAppDeadline(input.acceptDate(msg + "the application deadline *"));
        jl.displayJobDetails();
        jl.setJobAd(advertiseJob());
        System.out.println(jl.labelJobAd());
        jl.setJobId(JobListingCtrl.generateJobID("Files/jobListings.txt"));

        // Send to Job Listing Controller to create new job
        Control.JobListingCtrl.addNewJob(jl.jobId, jl.jobTitle, jl.jobCategory, jl.jobLocation, jl.jobHours, jl.jobPay, jl.jobSkills, jl.jobDescription, jl.appDeadline, jl.jobAd);

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
        jlc.printJobList(jlc.parseFromCSV());

    }

}
