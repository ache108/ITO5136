package View;

import Control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class InterviewUI {

    public static int acceptInterviewScreen()
    {
        View.Input userInput = new View.Input();
        String msg = "-------------------------------------\n"
                + "       JOB INTERVIEW        \n"
                + "------------------------------------\n"
                + "Interview has been accepted. \n"
                + "          GOOD LUCK !!       \n"
                + "------------------------------------\n"
                + "Press 0 to continue\n"
                + "--------------------------------\n";
        return userInput.acceptInt(msg, 0, 0);
    }

    public static Date createNewInterview()
    {
        View.Input userInput = new View.Input();
        return userInput.acceptDateTime("Please enter the date in the format dd-mm-yyyy hh:mm am/pm you want to schedule the interview for.");
    }

    public static int optionInterview() {
        View.Input userInput = new View.Input();
        String msg = "             INTERVIEW        \n"
                + "-------------------------------------\n"
                + "Press 1 to offer interview\n"
                + "Press 0 to go back\n"
                + "-------------------------------------\n";
        return userInput.acceptInt(msg, 0, 1);

    }

    public static int rejectInterviewScreen()
    {
        View.Input userInput = new View.Input();
        String msg = "-------------------------------------\n"
                + "       JOB INTERVIEW        \n"
                + "------------------------------------\n"
                + "Interview has been rejected. "
                + "------------------------------------\n"
                + "Press 0 to continue\n"
                + "--------------------------------\n";
        return userInput.acceptInt(msg, 0, 0);
    }

    public static int viewInterviewDetailsJS(Model.Interview intvw)
    {
        View.Input userInput = new View.Input();
        String msg = "-------------------------------------\n"
                + "       JOB INTERVIEW INVITATION FOR JOB ID " + intvw.getInterviewJobId() +" \n"
                + "------------------------------------\n"
                + "Interview Date :  " + intvw.getInterviewDate() +" \n"
                + "Interview Location :  " + intvw.getInterviewLocation() +" \n"
                + "Interview Response Date :  " + intvw.getInterviewInviteResponseDate() +" \n"
                + "Interview Status: " + intvw.getInterviewStatus() + " \n"
                + "------------------------------------\n"
                + "Press 1 to accept the interview \n"
                + "Press 2 to reject the interview \n"
                + "Press 3 to continue \n"
                + "Press 0 to return home\n"
                + "--------------------------------\n";
        return userInput.acceptInt(msg, 0, 3);
    }

    public static int viewInterviewInvitationsRC(Model.Interview intvw)
    {
        View.Input userInput = new View.Input();
        String msg = "-------------------------------------\n"
                + "       JOB INTERVIEW INVITATION FOR JOB ID " + intvw.getInterviewJobId() +" \n"
                + "------------------------------------\n"
                + "Interview Date :  " + intvw.getInterviewDate() +" \n"
                + "Interview Location :  " + intvw.getInterviewLocation() +" \n"
                + "Interview Response Date :  " + intvw.getInterviewInviteResponseDate() +" \n"
                + "Interview Status: " + intvw.getInterviewStatus() + " \n"
                + "------------------------------------\n"
                + "Press 0 to go back\n"
                + "Press 1 to continue \n"
                + "--------------------------------\n";
        return userInput.acceptInt(msg, 0, 1);
    }
}
