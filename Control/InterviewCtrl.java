package Control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Model.Interview;
import Model.JobApplication;
import View.Input;

public class InterviewCtrl
{
    public static void acceptInterviewOffer(Model.Interview intvw)
            throws IOException
    {
        LocalDate responseDate = intvw.getInterviewInviteResponseDate();
        LocalDate today = LocalDate.now();
        // check interview offer has not already expired, remove old entry from file, set new status and re write to file
        if (responseDate.isAfter(today))
        {
            removeOldJobIntFromFile(intvw);
            setInterviewStatus("accepted", intvw);
            int userResponse = View.InterviewUI.acceptInterviewScreen();
        }
        else {
            removeOldJobIntFromFile(intvw);
            System.out.println("Error! Interview response date has passed and offer has expired.");
            setInterviewStatus("expired", intvw);
        }
        String userInfo = writeInterviewToString(intvw);
        writeInterviewToFile(userInfo);
    }

    public static void createNewInterview(Model.JobApplication ja)
            throws IOException, ParseException
    {
        String jobId = ja.getJobApplicationJobId();
        String jsUserName = ja.getJobApplicationJSUserName();
        String rcUserName = ja.getRecruiterUserName();
        LocalDate responseDate = LocalDate.now().plusDays(10);
        Date interviewDate = View.InterviewUI.createNewInterview();
        String officeLocation = Control.CompanyCtrl.getCompanyLocation(rcUserName);
        Model.Interview intvw = new Interview(interviewDate, officeLocation , responseDate ,jobId, jsUserName, rcUserName, "invited");
        String infoToWrite = writeInterviewToString(intvw);
        writeInterviewToFile(infoToWrite);
    }

    public static void createNewInterviewWithoutApplication(Model.JobSeeker js, Model.JobListing jl)
            throws IOException, ParseException
    {
        String jobId = jl.getJobId();
        String jsUserName = js.userName;
        String rcUserName = jl.getJobRC();
        LocalDate responseDate = LocalDate.now().plusDays(10);
        Date interviewDate = View.InterviewUI.createNewInterview();
        String officeLocation = Control.CompanyCtrl.getCompanyLocation(rcUserName);
        Model.Interview intvw = new Interview(interviewDate, officeLocation , responseDate ,jobId, jsUserName, rcUserName, "invited");
        String infoToWrite = writeInterviewToString(intvw);
        writeInterviewToFile(infoToWrite);
    }

    public static void rejectInterviewOffer(Model.Interview intvw)
            throws IOException
    {
        // remove old entry from file, set new status and re-write to file
        // Don't worry about it being expired - rejected anyway
        removeOldJobIntFromFile(intvw);
        setInterviewStatus("rejected", intvw);
        String userInfo = writeInterviewToString(intvw);
        writeInterviewToFile(userInfo);
        int userResponse = View.InterviewUI.rejectInterviewScreen();
    }


    public static void removeOldJobIntFromFile(Model.Interview intvw)
            throws IOException
    {
        Control.FileIO file = new Control.FileIO(Control.JSS.JOBINTERVIEWS);
        String[] list = file.readFile("\n").split("\n");

        String jobId = intvw.getInterviewJobId();
        String userId = intvw.getInterviewJobSeekerUserName();

        for (int i = 0; i < list.length; i++)
        {
            String[] jobApp = list[i].split(";");
            // jobId and User Id both match to make sure we have the right line
            if ((jobId.equals(jobApp[3])) && (userId.equals(jobApp[4])))
            {
                file.removeLine(list[i]);
                break;
            }
        }
    }

    public static ArrayList<Model.Interview> parseInterviewCSV(String userType)
            throws IOException, FileNotFoundException, ParseException
    {
        Control.FileIO file = new Control.FileIO(Control.JSS.JOBINTERVIEWS);
        String[] allInterviews = file.readFile("\n").split("\n");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH);
        ArrayList<Model.Interview> ji = new ArrayList<Model.Interview>();
        String userName = Control.LogInCtrl.getRcUsername();

        if (allInterviews.length > 0)
            for (int i = 0; i < allInterviews.length; i++)
            {
                String[] jobIntDetails = allInterviews[i].split(";");
                String intDateStr = jobIntDetails[0];
                String officeLocation = jobIntDetails[1];
                String intRespDateStr = jobIntDetails[2];
                String intJobId = jobIntDetails[3];
                String intJobUser = jobIntDetails[4];
                String intJobRec = jobIntDetails[5];
                String intStatusStr = jobIntDetails[6];

                Date intDate = dateFormat.parse(intDateStr);
                LocalDate respDate = LocalDate.parse(intRespDateStr);

                if (userType.equals("JS"))
                    if ((intJobUser.equals(userName)) && (!intStatusStr.equals("expired")) && (!intStatusStr.equals("rejected")))
                        ji.add(new Model.Interview(intDate, officeLocation, respDate, intJobId, intJobUser, intJobRec, intStatusStr));
                else
                    // show all expired and rejected ones to the recruiter
                    if(intJobRec.equals(userName))
                        System.out.println("Did we make it here from the recrutier side");
                        ji.add(new Model.Interview(intDate, officeLocation, respDate, intJobId, intJobUser, intJobRec, intStatusStr));
            }
        System.out.println(ji.size());
        return ji;
    }

    public static void setInterviewStatus(String newStatus, Model.Interview intvw)
    {
        intvw.setInterviewStatus(newStatus);
    }

    public static void viewJobSeekerInterviews()
            throws IOException, ParseException
    {
        ArrayList<Model.Interview> intvwArrayList = parseInterviewCSV("JS");
        int intSize = intvwArrayList.size();
        if (intSize > 0)
        {
            for (int i = 0; i < intSize; i++)
            {
                Model.Interview mi = intvwArrayList.get(i);
                int userResponse = View.InterviewUI.viewInterviewDetailsJS(mi);
                switch(userResponse)
                {
                    case 1:
                        // accept the interview
                        acceptInterviewOffer(mi);
                        continue;
                    case 2:
                        //reject the interview
                        rejectInterviewOffer(mi);
                        continue;
                    case 3:
                        // proceed to next offer and do not update status
                        continue;
                    case 0:
                        // go home
                        break;
                }
            }
        }
        else
            System.out.println("No Interviews as of yet. Keep applying for roles!");

        Control.JobSeekerCtrl.runJSHome();
    }

    public static void viewRecruiterInterviews()
            throws IOException, ParseException
    {
        ArrayList<Model.Interview> intvwArrayList = parseInterviewCSV("RC");
        int intSize = intvwArrayList.size();
        if (intSize > 0)
        {
            for (int i = 0; i < intSize; i++) {
                Model.Interview mi = intvwArrayList.get(i);
                int userResponse = View.InterviewUI.viewInterviewInvitationsRC(mi);
                switch (userResponse) {
                    case 1:
                        // continue
                        continue;
                    case 2:
                        //go back home
                        break;
                }
            }
        }
        else
            System.out.println("No Interviews have been sent out yet.");

        Control.RecruiterCtrl.runRCHome();
    }

    public static void writeInterviewToFile(String interviewString)
            throws IOException
    {
        Control.FileIO fName = new Control.FileIO(Control.JSS.JOBINTERVIEWS);
        fName.appendFile(interviewString);
    }

    public static String writeInterviewToString(Model.Interview intvw)
    {
        String msg = intvw.getInterviewDate() + ";"
                    + intvw.getInterviewLocation() + ";"
                    + intvw.getInterviewInviteResponseDate() + ";"
                    + intvw.getInterviewJobId() + ";"
                    + intvw.getInterviewJobSeekerUserName() + ";"
                    + intvw.getInterviewRecruiter() + ";"
                    + intvw.getInterviewStatus();
        return msg;
    }
}
