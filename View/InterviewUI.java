package View;

import Control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class InterviewUI {
    public static Date createNewInterview()
    {
        View.Input userInput = new View.Input();
        return userInput.acceptDateTime("Please enter the date in the format dd/mm/yyyy hh:mm you want to schedule the interview for.");
    }

    public static int rejectInterviewScreen()
    {
        View.Input userInput = new View.Input();
        String msg = "-------------------------------------\n"
                + "       JOB INTERVIEW        \n"
                + "------------------------------------\n"
                + "Interview for .... has been rejected. "
                + "------------------------------------\n"
                + "Press 0 to continue\n"
                + "--------------------------------\n";
        return userInput.acceptInt(msg, 0, 0);
    }

    public static int viewInterviewDetailsJS()
    {
        View.Input userInput = new View.Input();
        String msg = "-------------------------------------\n"
                + "       JOB INTERVIEW INVITATION FOR JOB ID .....       \n"
                + "------------------------------------\n"
                + "INTERVIEW DETAILS"
                + "------------------------------------\n"
                + "Press 1 to accept the interview \n"
                + "Press 0 to continue to next interview or return home\n"
                + "Press 2 to propose a new date or time \n"
                + "Press 3 to reject decline \n"
                + "--------------------------------\n";
        return userInput.acceptInt(msg, 0, 3);
    }

    public static int viewInterviewDetailsRC()
    {
        View.Input userInput = new View.Input();
        String msg = "-------------------------------------\n"
                + "       JOB INTERVIEW INVITATIONS        \n"
                + "------------------------------------\n"
                +"Interview details"
                + "------------------------------------\n"
                + "Press 0 to go back\n"
                + "Press 1 to propose a new date or time \n"
                + "--------------------------------\n";
        return userInput.acceptInt(msg, 0, 2);
    }
}
