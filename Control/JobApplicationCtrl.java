package Control;

import Model.JobListing;
import Model.JobSeeker;
import Model.JobSeeker;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class JobApplicationCtrl {

    public static void applyForJob(Model.JobListing jl)
            throws IOException, ParseException
    {
        String jobId = jl.getJobId();
        String jobTitle = jl.getJobTitle();
        String jobLocation = jl.getJobLocation();
        String jobHours = jl.getJobHours();
        String jobCat = jl.getJobCategory();
        String jobDesc = jl.getJobDescription();
        String jobPay = jl.getJobPay();
        Date jobDeadline = jl.getAppDeadline();
        int jobMatch = jl.getMatchingScore();
        String jobRC = jl.getJobRC();
        ArrayList <String> jobSkills = jl.getJobSkills();

        // get current Job Information and current Jobseeker info
        Model.JobSeeker js = Control.JobSeekerCtrl.getCurrentJobSeeker();
        String firstName = js.getFirstName();
        String userName = js.getUserName();
        String lastName = js.getLastName();
        String email = js.getUserEmail();
        Date dateOfBirth = js.getDateOfBirth();
        ArrayList<String> userSkills = js.getSkillList();

        int applicationResponse = View.JobApplicationUI.applyForJobScreen(
                userName, jobId, jobTitle, jobLocation, jobHours, jobCat, jobDesc, jobPay,
                jobMatch, jobRC, jobDeadline, jobSkills, firstName, lastName, email, userSkills);

        switch (applicationResponse)
        {
            case 0:
                // return user back to job listing page
                Control.JobListingCtrl jControl = new Control.JobListingCtrl();
                jControl.openJobListing(jl);
                break;
            case 1:
                // only 1 can come back, getting input to delay screen so user is not rushed offscreen
                int userAppResponse = View.JobApplicationUI.applicationSubmitted();
                Date applicationDate = new Date();
                Model.JobApplication ja = new Model.JobApplication(jobId, userName, jobRC, applicationDate);
                String toWrite = writeInfoAsString(ja);
                writeJobApplicationToFile(toWrite);
                break;
            case 2:
                // return user to profile page so the user can edit their information
                View.JobSeekerUI.displayJSDetails();
                break;
        }
    }

    public Model.JobSeeker getJobApplicationJobSeekerInfo(String userName)
    {
        Model.JobSeeker js = new Model.JobSeeker();
        return js;
    }

    public Model.JobListing getJobApplicationJobListingInfo(String jobId)
    {
        Model.JobListing jl = new Model.JobListing();
        return jl;
    }

    public Model.Recruiter getJobApplicationRecruiterInfo(String recruiterUserName)
    {
        Model.Recruiter rc = new Model.Recruiter();
        return rc;
    }

    public static void writeJobApplicationToFile(String infoToWrite)
            throws IOException
    {
        FileIO fName = new FileIO(Control.JSS.JOBAPPLICATIONS);
        fName.appendFile(infoToWrite);
    }

    public static String writeInfoAsString(Model.JobApplication ja)
    {
        String msg = "";
        msg += ja.getJobApplicationJobId();
        msg += ";" + ja.getJobApplicationJSUserName();
        msg += ";" + ja.getRecruiterUserName();
        msg += ";" + ja.getJobApplicationAppDate();

        return msg;
    }
}
