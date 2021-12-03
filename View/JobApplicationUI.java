package View;

import Control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class JobApplicationUI {
    public static int applicationSubmitted()
    {
        Input input = new Input();
        String msg = "\n            JOB APPLICATION       \n"
                + "--------------------------------------------\n"
                + "Thank you for Submitting your Application!\n"
                + "The recruiter has received your application and will reach out if you proceed to the next round \n"
                + "Press 1 to continue";

        return input.acceptInt(msg, 1, 1);
    }

    // act like a confirmation screen with all existing user fields on Submission as required
    public static int applyForJobScreen(String userName,
                                        String jobId,
                                        String jobTitle,
                                        String jobLocation,
                                        String jobHours,
                                        String jobCat,
                                        String jobDesc,
                                        String jobPay,
                                        int jobMatch,
                                        String jobRC,
                                        Date jobDeadline,
                                        ArrayList<String> skills,
                                        String firstName,
                                        String lastName,
                                        String email,
                                        ArrayList<String> userSkills
    )
    {
        // add details received from controller
        Input input = new Input();
        String msg = "            JOB APPLICATION           \n"
                + "--------------------------------------------\n"
                + "               JOB DETAILS \n"
                + "----------------------------------------\n"
                + "Job ID:                      " + jobId + " \n"
                + "Job Name:                    " + jobTitle + " \n"
                + "Job Location:                "  + jobLocation  + " \n"
                + "Job Pay:                     "  + jobPay  + " \n"
                + "Job Hours:                   " + jobHours + " \n"
                + "Job Category:                " + jobCat + " \n"
                + "Advertisement Closing Date:  " + jobDeadline + "\n"
                + "Job Description              " + jobDesc + "\n"
                + "Job Skills:                  " + Arrays.toString(skills.toArray()).replace('[', ' ').replace(']', ' ').trim() + "\n"
                + "Matching Score:              " + jobMatch  + "\n"
                + "Recruiter:                   " + jobRC + "\n\n"
                + "              YOUR DETAILS \n"
                + "----------------------------------------\n"
                + "First Name:            " + firstName + " \n"
                + "Last Name:             " + lastName + " \n"
                + "Email:                 " + email + " \n"
                + "Skills:                " + Arrays.toString(userSkills.toArray()).replace('[', ' ').replace(']', ' ').trim() + " \n"
                + "Experience:            " + " \n"
                + "Username:              " + userName  + " \n"
                + "--------------------------------\n"
                + "Press 1 to Submit Application\n"
                + "Press 0 to go back\n"
                + "Press 2 to Edit your Profile and update your information\n"
                + "--------------------------------\n";

        return input.acceptInt(msg, 0, 2);
    }

    public static int chooseJobApplication(int max)
    {
        Input input = new Input();
        int chosenJobApp = input.acceptInt("Please enter the job number you wish to revoke your application.\nAlternatively, press 0 to go back.", 0, max);
        return chosenJobApp;
    }

    public static void revokeJobApplication(String jobId)
    {
        String msg = "-------------------------------------\n"
                + "       REVOKE JOB APPLICATION        \n"
                + "------------------------------------\n"
                + "Application for Job Id + " + jobId + " has been revoked. \n"
                + "--------------------------------\n";
        System.out.println(msg);
    }

    public static int viewJobApplicationJobSeeker(ArrayList<Model.JobApplication> ja)
    {
        Input input = new Input();
        String jobAppMsg = "";
        for (int i = 0; i < ja.size(); i++)
        {
            String appMsg = Control.JobApplicationCtrl.displayArrayListJobApplication(ja.get(i));
            jobAppMsg += appMsg;
        }

        String msg = "-------------------------------------\n"
                + "       JOB APPLICATIONS SUBMITTED        \n"
                + "------------------------------------\n"
                + jobAppMsg
                + "------------------------------------\n"
                + "Press 1 to Revoke Application\n"
                + "Press 0 to go back\n"
                + "--------------------------------\n";

        return input.acceptInt(msg, 0, 1);
    }

    public static int viewJobApplicationRecruiter(Model.JobSeeker js, Model.JobListing jl)
    {
        Input input = new Input();
        String msg = "-------------------------------------\n"
                + "       JOB APPLICATION SUBMITTED FOR JOB ID " +  jl.getJobId() + " TITLE " + jl.getJobTitle()  + "     \n"
                + "------------------------------------\n"
                + "Applicant First Name: " + js.firstName + "\n"
                + "Applicant Last Name: " + js.lastName + "\n"
                + "Applicant Skills: " + js.skillsList + "\n"
                + "Applicant Qualifications: " + "\n"
                + "Applicant Experience: " + "\n"
                + "Applicant Salary Expectations : " + js.hourlyWageRate + "\n"
                + "Applicant Matching Score: " + jl.getMatchingScore() + "\n"
                + "------------------------------------\n"
                + "Press 1 to invite job seeker for an interview"
                + "Press 2 to reject application"
                + "Press 0 to go to next application \n"
                + "--------------------------------\n";
        return input.acceptInt(msg, 0, 2);
    }

    public static int viewJobApplicationRecruiterScreen()
    {
        Input input = new Input();
        String msg = "-------------------------------------\n"
                + "       JOB APPLICATIONS SUBMITTED        \n"
                + "------------------------------------\n"
                + "Press 1 to view the applications for a specific Job\n"
                + "Press 0 to go back\n"
                + "--------------------------------\n";
        return input.acceptInt(msg, 0, 2);
    }

    public static int viewJobApplicationRecruiterSpecificScreen()
    {
        Input input = new Input();
        String msg = "-------------------------------------\n"
                + "       JOB APPLICATIONS SUBMITTED        \n"
                + "------------------------------------\n"
                + "Please enter the Job Id you want to view applicants for \n"
                + "Press 0 to go back\n"
                + "--------------------------------\n";
        return input.acceptInt(msg, 0, 2);
    }
}
