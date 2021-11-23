package Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JobListing {

    public String jobId;
    public String jobTitle;
    public String jobCategory;
    public String jobLocation;
    public String jobHours;
    public String jobPay;
    public ArrayList<String> jobSkills;
    public String jobDescription;
    public Date appDeadline;
    public boolean jobAd;

    public JobListing()
    {
        //Probably don't want a blank job
    }

    public JobListing(String jobId, String jobTitle, String jobCategory, String jobLocation, String jobHours, String jobPay, ArrayList<String> jobSkills, String jobDescription, Date appDeadline, boolean jobAd)
    {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobCategory = jobCategory;
        this.jobLocation = jobLocation;
        this.jobHours = jobHours;
        this.jobPay = jobPay;
        this.jobSkills = jobSkills;
        this.jobDescription = jobDescription;
        this.appDeadline = appDeadline;
        this.jobAd = jobAd;
    }

    // Get methods

    public String getJobId() { return this.jobId; }

    public String getJobTitle() { return this.jobTitle; }

    public String getJobCategory() { return this.jobCategory; }

    public String getJobLocation() { return this.jobLocation; }

    public String getJobHours() { return this.jobHours; }

    public String getJobPay() { return this.jobPay; }

    public ArrayList<String> getJobSkills() { return this.jobSkills; }

    public String getJobDescription() { return this.jobDescription; }

    public Date getAppDeadline() { return this.appDeadline; }

    public boolean getJobAd() { return this.jobAd; }

    //Mutator methods

    public void setJobId(String jobId) { this.jobId = jobId; }

    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public void setJobCategory(String jobCategory) { this.jobCategory = jobCategory; }

    public void setJobLocation(String jobLocation) { this.jobLocation = jobLocation; }

    public void setJobHours(String jobHours) { this.jobHours = jobHours; }

    public void setJobPay(String jobPay) { this.jobPay = jobPay; }

    public void setJobSkills(ArrayList<String> jobSkills) { this.jobSkills = jobSkills; }

    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }

    public void setAppDeadline(Date appDeadline) { this.appDeadline = appDeadline; }

    public void setJobAd(boolean jobAd) { this.jobAd = jobAd; }

    //Displays the details of the job
    public void displayJobDetails()
    {
        SimpleDateFormat dateShortFormat = new SimpleDateFormat("dd-MMM-yyyy");
        System.out.println("\nJob title: " + jobTitle);
        System.out.println("Job category: " + jobCategory);
        System.out.println("Location: " + jobLocation);
        System.out.println("Hours: " + jobHours);
        System.out.println("Compensation: " + jobPay);
        System.out.println("Skills required: ");
        for (int i = 0; i < jobSkills.size(); i++)
        {
            if (i < jobSkills.size() - 1) {
                System.out.print(jobSkills.get(i) + ", ");
            } else {
                System.out.println(jobSkills.get(i) + ".");
            }
        }
        System.out.println("Description:\n" + jobDescription + "\n");
        System.out.println("Application deadline: " + dateShortFormat.format(appDeadline) + "\n");
    }

    public String labelJobAd()
    {
        String adStatus = "";
        if (this.jobAd == true) {
            adStatus = "Public";
        } else {
            adStatus = "Private";
        }

        return adStatus;
    }

}
