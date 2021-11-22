package View;
import View.Input;
import java.util.ArrayList;
import java.io.*;

public class JobSeekerUI extends View.UserUI
{
    public static void jobSeekerInputs()
    {
        Input input = new View.Input();
        String msg = "Please enter ";
        String wrkType = input.acceptString(msg + "the type of work you are looking for (Full time, Contract, Part time)");
        String wrkResidency = input.acceptString(msg + "your Australian residency status (Visaholder, resident, citizen)");
        ArrayList <String> iptSkills = jobSeekerSkillInput();
        Double wrkHrlyRate = input.acceptDouble(msg + "your desired hourly salary rate.");
        // TO DO CV
        // Pass to Job Seeker controller to save?
    }

    public static ArrayList<String> jobSeekerSkillInput()
    {
        // assumes at least one skill is added
        boolean addAnotherSkill = true;
        boolean charInputCheck = true;
        ArrayList <String> iptSkills = new ArrayList<String>();
        Input input = new Input();
        do {
            String iptSkill = input.acceptString("Please enter a skill to add to your profile.");
            iptSkills.add(iptSkill);
            // add another skill?
            char userRepsonse = input.acceptChar("To add another skill please enter y. \nTo complete the list please enter n");
            do {
                if (userRepsonse == 'y') {
                    addAnotherSkill = true;
                    charInputCheck = false;
                } else if (userRepsonse == 'n') {
                    addAnotherSkill = false;
                    charInputCheck = false;
                } else {
                    System.out.println("Please enter y or n!");
                    charInputCheck = true;
                }
            } while (charInputCheck);
        } while (addAnotherSkill);

        return iptSkills;
    }

    public static void jobSeekerRegisterScreen()
            throws IOException
    {
        View.UserUI.userRegisterScreen();
        jobSeekerInputs();
    }

    public int displayJSHome()
    {
        Input input = new Input();
        String msg = "      JOB SEEKER HOME PAGE\n"
                + "Press 1 to search for jobs\n"
                + "Press 2 to view your profile\n"
                + "Press 3 to view your interview offers\n"
                + "Press 4 to view applications\n"
                + "Press 0 to log out";
        return input.acceptInt(msg, 0, 4);
    }
}