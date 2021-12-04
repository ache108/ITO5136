package Control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static View.JobApplicationUI.viewJobApplicationRecruiterSpecificScreen;

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
                Control.JobSeekerCtrl.editJSOptions();
                break;
        }
    }

    public static String displayJobApplication(Model.JobApplication ja)
    {
        String msg = "Job Id:  " + ja.getJobApplicationJobId() + " was submitted on " + ja.getJobApplicationAppDate() + " and has a status of " + ja.getJobApplicationStatus() + ".\n";
        return msg;
    }

    public static Model.JobSeeker getJobApplicationJobSeekerInfo(String userName)
            throws IOException, ParseException
    {
        Model.JobSeeker js = Control.JobSeekerCtrl.getJobSeeker(userName);
        return js;
    }

    public static Model.JobListing getJobApplicationJobListingInfo(String jobId)
    {
        Model.JobListing jl = Control.JobListingCtrl.filterSpecificJobListingByID(jobId);
        return jl;
    }

    public static ArrayList<Model.JobApplication> parseJobApplicationTextFile(String userType)
            throws IOException, FileNotFoundException, ParseException
    {
        Control.FileIO file = new Control.FileIO(Control.JSS.JOBAPPLICATIONS);
        String[] allJobApplications = file.readFile("\n").split("\n");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH);
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
                String jobAppStatus = jobAppDetails[5];

                if (userType.equals("JS")) {
                    if ((jobAppUserName.equals(userName)) && (jobActive) && jobAppStatus.equals("submitted")) {
                        ja.add(new Model.JobApplication(jobId, jobAppUserName, jobRecruiter, jobAppSubDate));
                    }
                }
                else {
                    if ((jobRecruiter.equals(userName)) && (jobActive)) {
                        ja.add(new Model.JobApplication(jobId, jobAppUserName, jobRecruiter, jobAppSubDate));
                    }
                }
            }
        return ja;
    }

    public static void removeOldJobAppFromFile(Model.JobApplication ja)
        throws IOException
    {
        Control.FileIO file = new Control.FileIO(Control.JSS.JOBAPPLICATIONS);
        String[] list = file.readFile("\n").split("\n");

        String id = ja.getJobApplicationJobId();
        String userName = ja.getJobApplicationJSUserName();

        for (int i = 0; i < list.length; i++)
        {
            String[] jobApp = list[i].split(";");
            // job id and user id to make sure delete right line
            if ((id.equals(jobApp[0])) && (userName.equals(jobApp[1])))
            {
                file.removeLine(list[i]);
                break;
            }
        }
    }

    public static void revokeApplicationScreen(Model.JobApplication ja)
            throws IOException, ParseException
    {
        String jobId = ja.getJobApplicationJobId();
        removeOldJobAppFromFile(ja);
        setJobApplicationToInactive(ja);
        String fileInfo = writeInfoAsString(ja);
        writeJobApplicationToFile(fileInfo);
        View.JobApplicationUI.revokeJobApplication(jobId);

    }

    public static void setJobApplicationToInactive(Model.JobApplication ja)
    {
        ja.setJobApplicationInactive(false);
    }

    public static void setJobApplicationStatus(Model.JobApplication ja, String newStatus) {ja.setJobApplicationStatus(newStatus);}

    public static void viewJSApplication()
            throws IOException, FileNotFoundException, ParseException
    {
        String userType = "JS";
        int userResponse = 0;
        ArrayList<Model.JobApplication> ja = parseJobApplicationTextFile(userType);
        int jaSize = ja.size();
        if (jaSize > 0) {
            // loop through each application and offer option to revoke
            for (int i = 0; i < jaSize; i++) {
                Model.JobApplication j = ja.get(i);
                userResponse = View.JobApplicationUI.viewJobApplicationJobSeeker(j);
                switch (userResponse) {
                    case 1:
                        // revoke application
                        revokeApplicationScreen(j);
                        continue;
                    case 2:
                        // move to next application
                        continue;
                    case 0:
                        // go back
                        Control.JobSeekerCtrl.runJSHome();
                }
            }
        }
        else {
            System.out.println("No Job Applications found!");
        }
        Control.JobSeekerCtrl.runJSHome();
    }

    /*public static void viewRCApplication()
            throws IOException, FileNotFoundException, ParseException
    {
        // filters job to only those for the recruiter
        String userType = "RC";
        ArrayList<Model.JobApplication> ja = parseJobApplicationTextFile(userType);
        int userChoice = View.JobApplicationUI.viewJobApplicationRecruiterScreen();
        switch(userChoice)
        {
            case 1:
                // view specific job applicants
                viewRCSpecificApplication(ja);
            case 0:
                // back to recruiter home menu
                Control.RecruiterCtrl.runRCHome();
        }
    }*/

    public static void viewRCSpecificApplication(ArrayList<Model.JobListing> jobList, ArrayList<Model.JobApplication> rcJobApps)
            throws IOException, FileNotFoundException, ParseException
    {
        JobListingCtrl jlc = new JobListingCtrl();
        int jobChose = viewJobApplicationRecruiterSpecificScreen(jobList.size());
        int recruiterAction = 0;
        if (jobChose == 0)
        {
            // return user home
            Control.RecruiterCtrl.runRCHome();
        }
        else {
            String jobID = jobList.get(jobChose - 1).getJobId();
            Model.JobListing jl = getJobApplicationJobListingInfo(jobID);
            for (int i = 0; i < rcJobApps.size(); i++) {
                String appJobID = rcJobApps.get(i).getJobApplicationJobId();
                if (jobID.equals(appJobID)) {
                    Model.JobApplication ja = rcJobApps.get(i);
                    String appUserName = ja.getJobApplicationJSUserName();
                    // for each applicant for their job, get their user info and show recruiter
                    Model.JobSeeker js = getJobApplicationJobSeekerInfo(appUserName);
                    recruiterAction = View.JobApplicationUI.viewJobApplicationRecruiter(js, jl);
                    String fileInfo = "";

                    switch (recruiterAction) {
                        case 1:
                            // invite for interview
                            removeOldJobAppFromFile(ja);
                            setJobApplicationStatus(ja, "accepted");
                            fileInfo = writeInfoAsString(ja);
                            writeJobApplicationToFile(fileInfo);
                            Control.InterviewCtrl.createNewInterview(ja);
                            continue;
                        case 2:
                            removeOldJobAppFromFile(ja);
                            setJobApplicationStatus(ja, "reviewed and not proceeded with");
                            fileInfo = writeInfoAsString(ja);
                            writeJobApplicationToFile(fileInfo);
                            continue;
                        case 0:
                            continue;

                    }
                    System.out.println("All Applicants processed!");
                    viewRCSpecificApplication(jobList, rcJobApps);
                }
            }

        }
    }

    /*public static void viewRCSpecificApplication(ArrayList<Model.JobApplication> rcJobApps)
            throws IOException, FileNotFoundException, ParseException
    {
        String jobID = View.JobApplicationUI.viewJobApplicationRecruiterSpecificScreen();
        int recruiterAction = 0;
        if (jobID.equals("0"))
        {
            // return user home
            Control.RecruiterCtrl.runRCHome();
        }
        else {
            // verify jobID is in file
            boolean jobIDInFile = Control.JobListingCtrl.verifyJobIDInFile(jobID);
            if (jobIDInFile)
            {
                Model.JobListing jl = getJobApplicationJobListingInfo(jobID);
               for (int i = 0; i < rcJobApps.size(); i++)
               {
                   Model.JobApplication ja = rcJobApps.get(i);
                   String appUserName = ja.getJobApplicationJSUserName();
                   // for each applicant for their job, get their user info and show recruiter
                   Model.JobSeeker js = getJobApplicationJobSeekerInfo(appUserName);
                   recruiterAction = View.JobApplicationUI.viewJobApplicationRecruiter(js, jl);
                   String fileInfo = "";

                   switch(recruiterAction)
                   {
                       case 1:
                           // invite for interview
                           removeOldJobAppFromFile(ja);
                           setJobApplicationStatus( ja,"accepted");
                           fileInfo = writeInfoAsString(ja);
                           writeJobApplicationToFile(fileInfo);
                           Control.InterviewCtrl.createNewInterview(ja);
                           continue;
                       case 2:
                           removeOldJobAppFromFile(ja);
                           setJobApplicationStatus(ja,"reviewed and not proceeded with");
                           fileInfo = writeInfoAsString(ja);
                           writeJobApplicationToFile(fileInfo);
                           continue;
                       case 0:
                           continue;

                   }
                   System.out.println("All Applicants processed!");
                   viewRCSpecificApplication(rcJobApps);
               }
            }
            else
            {
                // Job ID not found. reload screen.
                System.out.println("Job Id Not found. Please try again");
                viewRCSpecificApplication(rcJobApps);
            }
        }
    }*/

    public static void writeJobApplicationToFile(String infoToWrite)
            throws IOException
    {
        Control.FileIO fName = new Control.FileIO(Control.JSS.JOBAPPLICATIONS);
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
        msg += ";" + ja.getJobApplicationStatus();

        return msg;
    }
}
