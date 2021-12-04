package Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JobListing {

    private String jobRC;
    private String jobId;
    private String jobTitle;
    private String jobCategory;
    private String jobLocation;
    private String jobHours;
    private String jobPay;
    private ArrayList<String> jobSkills;
    private String jobDescription;
    private Date appDeadline;
    private boolean jobAd;
    private int matchingScore;

    public JobListing()
    {
        jobRC = "";
    }

    public JobListing(String jobRC, String jobId, String jobTitle, String jobCategory, String jobLocation, String jobHours, String jobPay, ArrayList<String> jobSkills, String jobDescription, Date appDeadline, boolean jobAd)
    {
        this.jobRC = jobRC;
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
        this.matchingScore = 0;
    }

    // Get methods

    public String getJobRC() { return this.jobRC; }

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

    public int getMatchingScore() { return this.matchingScore; }

    public void incrementMatchingScore(int amount)
    {
        this.matchingScore += amount;
    }

    //for comparing matching scores when sorting
    public boolean isGreaterThan(JobListing compJob)
    {
        int compScore = compJob.getMatchingScore();
        if(this.matchingScore > compScore)
            return true;
        else
            return false;
    }

    //Mutator methods

    public void setJobRC(String jobRC) { this.jobRC = jobRC; }

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

    public void setMatchingScore(int matchingScore) { this.matchingScore = matchingScore; }

    //Displays the details of the job
    public void displayJobDetails()
    {
        SimpleDateFormat dateShortFormat = new SimpleDateFormat("dd-MMM-yyyy");
        System.out.println("\nJOB TITLE:            " + jobTitle);
        System.out.println("JOB CATEGORY:         " + jobCategory);
        System.out.println("LOCATION:             " + jobLocation);
        System.out.println("HOURS:                " + jobHours);
        System.out.println("COMPENSATION:         " + jobPay);
        System.out.println("SKILLS REQUIRED:      ");
        displayJobSkills();
        System.out.println();
        System.out.println("DESCRIPTION:\n" + jobDescription + "\n");
        System.out.println("APPLICATION DEADLINE: " + dateShortFormat.format(appDeadline) + "\n");
        //System.out.println("MATCHING SCORE:       " + matchingScore);
    }

    public void displayMatchingScore()
    {
        System.out.println("MATCHING SCORE:       " + matchingScore);
    }

    public String labelJobAd(boolean gotAd)
    {
        String adStatus = "";
        if (gotAd == true) {
            adStatus = "Public";
        } else {
            adStatus = "Private";
        }

        return adStatus;
    }

    public void displayJobSkills()
    {
        for (int i = 0; i < getJobSkills().size(); i++)
        {
            System.out.println((i+1) + ": " + getJobSkills().get(i).replace('[', ' ').replace(']', ' ').trim());
        }

    }

}
