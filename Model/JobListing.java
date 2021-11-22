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

    public void setJobTitle(String jobTitle) { jobTitle = this.jobTitle; }

    public void setJobCategory(String jobCategory) { jobCategory = this.jobCategory; }

    public void setJobLocation(String jobLocation) { jobLocation = this.jobLocation; }

    public void setJobHours(String jobHours) { jobHours = this.jobHours; }

    public void setJobPay(String jobPay) { jobPay = this.jobPay; }

    public void setJobSkills(String jobSkills) { jobSkills = this.jobSkills; }

    public void setJobDescription(String jobDescription) { jobDescription = this.jobDescription; }

    public void setAppDeadline(Date appDeadline) { appDeadline = this.appDeadline; }

    public void setJobAd(boolean jobAd) { jobAd = this.jobAd; }

}
