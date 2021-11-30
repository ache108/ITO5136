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
        String msg = "\n      JOB APPLICATION       \n"
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
        String msg = "-------------------------------\n"
                + "       JOB APPLICATION           \n"
                + "--------------------------------\n"
                + "        JOB DETAILS \n"
                + "Job ID: " + jobId + " \n"
                + "Job Name: " + jobTitle + " \n"
                + "Job Location: "  + jobLocation  + " \n"
                + "Job Pay: "  + jobPay  + " \n"
                + "Job Hours: " + jobHours + " \n"
                + "Job Category: " + jobCat + " \n"
                + "Job Advertisement Closing Date: " + jobDeadline + "\n"
                + "Job Description " + jobDesc + "\n"
                + "Job Skills: " + Arrays.toString(skills.toArray()).replace('[', ' ').replace(']', ' ').trim() + "\n"
                + "Matching Score: " + jobMatch  + "\n"
                + "Recruiter: " + jobRC + "\n"
                + "        YOUR DETAILS \n"
                + "First Name: " + firstName + " \n"
                + "Last Name: " + lastName + " \n"
                + "Email: " + email + " \n"
                + "Skills: " + Arrays.toString(userSkills.toArray()).replace('[', ' ').replace(']', ' ').trim() + " \n"
                + "Experience: " + " \n"
                + "Username: " + userName  + " \n"
                + "--------------------------------\n"
                + "Press 1 to Submit Application\n"
                + "Press 0 to go back\n"
                + "Press 2 to Edit your Profile and update your information\n"
                + "--------------------------------\n";

        return input.acceptInt(msg, 0, 2);
    }

    public static void viewJobApplicationJobSeeker()
    {

    }

    public static void viewJobApplicationRecruiter()
    {

    }
}
