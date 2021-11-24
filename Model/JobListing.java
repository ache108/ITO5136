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
    public int matchingScore;

    public JobListing()
    {
        //Probably don't want a blank job
    }

    /*
    //FOR TESTING
    public JobListing(String jobTitle, String jobCategory, String jobLocation, String jobHours, String jobPay, ArrayList<String> jobSkills, String jobDescription)
    {

        this.jobTitle = jobTitle;
        this.jobCategory = jobCategory;
        this.jobLocation = jobLocation;
        this.jobHours = jobHours;
        this.jobPay = jobPay;
        this.jobSkills = jobSkills;
        this.jobDescription = jobDescription;
        jobAd = true;
        this.matchingScore = 0;
    }
    */

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
        this.matchingScore = 0;
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
        System.out.println("\nJob title: " + jobTitle);
        System.out.println("Job category: " + jobCategory);
        System.out.println("Location: " + jobLocation);
        System.out.println("Hours: " + jobHours);
        System.out.println("Compensation: " + jobPay);
        System.out.println("Skills required: ");
        for (int i = 0; i < jobSkills.size(); i++)
        {
            String skill = jobSkills.get(i);
            if (i < jobSkills.size() - 1) {
                if (skill.charAt(0) == '[') {
                    skill = skill.substring(1);
                } else if (skill.charAt(0) == ' ') {
                    skill = skill.substring(1);
                }
                if (skill.charAt(skill.length() - 1) == ']') {
                    skill = skill.substring(0, skill.length() - 1);
                }
                System.out.print(skill + ", ");
            } else {
                if (skill.charAt(0) == '[') {
                    skill = skill.substring(1);
                } else if (skill.charAt(0) == ' ') {
                    skill = skill.substring(1);
                }
                if (skill.charAt(skill.length() - 1) == ']') {
                    skill = skill.substring(0, jobSkills.get(i).length() - 2);
                }
                System.out.println(skill + ".");
            }

        }
        System.out.println("Description:\n" + jobDescription + "\n");
        System.out.println("Application deadline: " + dateShortFormat.format(appDeadline) + "\n");
        System.out.println("Matching score: " + matchingScore);
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
