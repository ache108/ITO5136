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
        // remove old entry from file, set new status and re write to file
        removeOldJobIntFromFile(intvw);
        setInterviewStatus("accepted", intvw);
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

        if (allInterviews.length > 1)
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
                    if ((intJobUser.equals(userName)) && (!intStatusStr.equals("rejected")))
                        ji.add(new Model.Interview(intDate, officeLocation, respDate, intJobId, intJobUser, intJobRec, intStatusStr));
                    else
                    if ((intJobRec.equals(userName)) && (!intStatusStr.equals("rejected")))
                        ji.add(new Model.Interview(intDate, officeLocation, respDate, intJobId, intJobUser, intJobRec, intStatusStr));
            }
        return ji;
    }

    public static void proposeNewInterviewDate()
    {

    }


    public static void setInterviewStatus(String newStatus, Model.Interview intvw)
    {
        intvw.setInterviewStatus(newStatus);
    }

    public static void viewJobSeekerInterviews()
    {
        // filter function and send to ui screen
    }

    public static void viewRecruiterInterviews()
    {
        // filter function and send to ui screen
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
