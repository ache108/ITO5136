package Control;

import Model.JobApplication;
import Model.JobListing;
import Model.JobSeeker;
import Model.User;

import java.io.IOException;
import java.text.ParseException;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
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

    public static String displayArrayListJobApplication(Model.JobApplication ja)
    {
        String msg = "Job Id:  " + ja.getJobApplicationJobId() + " was submitted on " + ja.getJobApplicationAppDate() + ".\n";
        return msg;
    }

    public Model.JobSeeker getJobApplicationJobSeekerInfo(String userName)
            throws IOException, ParseException
    {
        Model.JobSeeker js = Control.JobSeekerCtrl.getJobSeeker(userName);
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

    public static ArrayList<Model.JobApplication> parseJobApplicationTextFile(String userType)
            throws IOException, FileNotFoundException, ParseException
    {
        Control.FileIO file = new FileIO(Control.JSS.JOBAPPLICATIONS);
        String[] allJobApplications = file.readFile("\n").split("\n");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
        ArrayList<Model.JobApplication> ja = new ArrayList<Model.JobApplication>();
        String userName = Control.LogInCtrl.getRcUsername();

        if (allJobApplications.length > 1)
            for (int i = 0; i < allJobApplications.length; i++)
            {
                String[] jobAppDetails = allJobApplications[i].split(";");
                String jobId = jobAppDetails[0];
                String jobAppUserName = jobAppDetails[1];
                String jobRecruiter = jobAppDetails[2];
                Date jobAppSubDate = dateFormat.parse(jobAppDetails[3]);
                boolean jobActive = Boolean.parseBoolean(jobAppDetails[4]);

                if (userType.equals("JS"))
                    if ((jobAppUserName.equals(userName)) && (jobActive))
                        ja.add(new Model.JobApplication(jobId, jobAppUserName, jobRecruiter, jobAppSubDate));
                else
                    if ((jobRecruiter.equals(userName)) && (jobActive))
                        ja.add(new Model.JobApplication(jobId, jobAppUserName, jobRecruiter, jobAppSubDate));
            }
        return ja;
    }

    public static void revokeApplicationScreen(ArrayList<Model.JobApplication> ja)
            throws IOException, ParseException
    {
        int jobAppSize = ja.size();
        int jobNum = 0;
        String jobId = "";

        jobNum = View.JobApplicationUI.chooseJobApplication(jobAppSize);
        jobId = String.valueOf(jobNum);
        for (int i = 0; i < jobAppSize; i++)
        {
            Model.JobApplication jobApp = ja.get(i);
            if (jobApp.getJobApplicationJobId().equals(jobId))
                setJobApplicationToInactive(jobApp);
                View.JobApplicationUI.revokeJobApplication(jobId);
        }
        Control.JobSeekerCtrl.runJSHome();
    }

    public static void setJobApplicationToInactive(Model.JobApplication ja)
    {
        ja.setJobApplicationInactive(false);
        // write updated record to file and remove old one
    }

    public static void viewJSApplication()
            throws IOException, FileNotFoundException, ParseException
    {
        String userType = "JS";
        int userResponse = 0;
        ArrayList<Model.JobApplication> ja = parseJobApplicationTextFile(userType);
        if (ja.size() > 0) {
            userResponse = View.JobApplicationUI.viewJobApplicationJobSeeker(ja);
            switch (userResponse) {
                case 1:
                    // revoke application
                    revokeApplicationScreen(ja);
                case 0:
                    // go back
                    Control.JobSeekerCtrl.runJSHome();
            }
        }
        else {
            System.out.println("No Job Applications found!");
            Control.JobSeekerCtrl.runJSHome();
        }
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
        msg += ";" + ja.getJobApplicationActive();

        return msg;
    }
}
