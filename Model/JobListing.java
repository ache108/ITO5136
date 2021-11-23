package Model;

import java.util.Date;

public class JobListing {

    public String jobTitle;
    public String jobCategory;
    public String jobLocation;
    public String jobHours;
    public String jobPay;
    public String jobSkills;
    public String jobDescription;
    public Date appDeadline;
    public boolean jobAd;

    public JobListing()
    {
        //Probably don't want a blank job
    }

    public JobListing(String jobTitle, String jobCategory, String jobLocation, String jobHours, String jobPay, String jobSkills, String jobDescription, Date appDeadline, boolean jobAd)
    {
        jobTitle = this.jobTitle;
        jobCategory = this.jobCategory;
        jobLocation = this.jobLocation;
        jobHours = this.jobHours;
        jobPay = this.jobPay;
        jobSkills = this.jobSkills;
        jobDescription = this.jobDescription;
        appDeadline = this.appDeadline;
        jobAd = this.jobAd;
    }

    // Get methods

    public String getJobTitle() { return this.jobTitle; }

    public String getJobCategory() { return this.jobCategory; }

    public String getJobLocation() { return this.jobLocation; }

    public String getJobHours() { return this.jobHours; }

    public String getJobPay() { return this.jobPay; }

    public String getJobSkills() { return this.jobSkills; }

    public String getJobDescription() { return this.jobDescription; }

    public Date getAppDeadline() { return this.appDeadline; }

    public boolean getJobAd() { return this.jobAd; }

    //Mutator methods

    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public void setJobCategory(String jobCategory) { this.jobCategory = jobCategory; }

    public void setJobLocation(String jobLocation) { this.jobLocation = jobLocation; }

    public void setJobHours(String jobHours) { this.jobHours = jobHours; }

    public void setJobPay(String jobPay) { this.jobPay = jobPay; }

    public void setJobSkills(String jobSkills) { this.jobSkills = jobSkills; }

    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }

    public void setAppDeadline(Date appDeadline) { this.appDeadline = appDeadline; }

    public void setJobAd(boolean jobAd) { this.jobAd = jobAd; }

    //Displays the details of the job
    public void displayJobDetails()
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

    public void displayJobAd()
    {
        String adStatus = "";
        if (this.jobAd == true) {
            adStatus = "Public\nThis means the job listing will be saved, and it will appear on job seekers' search results.";
        } else {
            adStatus = "Private\nThis means the job listing will be saved, but it will NOT appear on job seekers' search results.";
        }

        System.out.println("Job is: " + adStatus);
    }

}
