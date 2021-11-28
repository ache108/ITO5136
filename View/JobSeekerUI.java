package View;
import Control.*;
import Model.JobSeeker;
import View.Input;

import java.text.ParseException;
import java.util.ArrayList;
import java.io.*;

public class JobSeekerUI extends View.UserUI
{
    public static void jobSeekerInputs(String userName)
            throws IOException
    {
        // get user inputs
        Model.User newUser = View.UserUI.userRegisterScreen(userName);
        // get extra job seeker inputs
        Input input = new View.Input();
        String wrkType = "";
        String wrkResidency = "";
        boolean verifiedInput;
        String msg = "Please enter ";

        do {
            wrkType = input.acceptString(msg + "the type of work you are looking for (Full time, Contract, Part time)");
            verifiedInput = userVerifyInputs(wrkType);
        } while (!verifiedInput);

        do {
            wrkResidency = input.acceptString(msg + "your Australian residency status (Visaholder, resident, citizen)");
            verifiedInput = userVerifyInputs(wrkResidency);
        } while (!verifiedInput);

        ArrayList <String> iptSkills = jobSeekerSkillInput();
        double wrkHrlyRate = input.acceptDouble(msg + "your desired hourly salary rate.");

        // TO DO CV

        // Send to Controller to create new user on Model
        Control.JobSeekerCtrl.createNewJobSeeker(newUser, wrkHrlyRate, wrkType, wrkResidency, iptSkills);
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

    public static void jobSeekerRegisterScreen(String userName)
            throws IOException, ParseException {
        jobSeekerInputs(userName);
        Control.JobSeekerCtrl.runJSHome();
    }

    public static void displayJSDetails()
            throws IOException, FileNotFoundException, ParseException
    {
            Control.FileIO file = new Control.FileIO(Control.JSS.JSDETAILS);

            String[] numJob = file.readFile("\n").split("\n");

            for (int i = numJob.length - 1; i >= 0; i--)
            {
                String[] details = numJob[i].split(";");
                if (details[0].equals(Control.LogInCtrl.getRcUsername())) //(display only profile for this user only)
                {
                    System.out.println("\nEmail: " + details[1]);
                    System.out.println("First Name: " + details[2]);
                    System.out.println("Last Name: " + details[3]);
                    System.out.println("City: " + details[4]);
                    System.out.println("State: " + details[5]);
                    System.out.println("Date of Birth: " + details[6]);
                    System.out.println("Public Profile: " + details[7]);
                    System.out.println("Desired Hourly Wage: " + details[8]);
                    System.out.println("Work Type: " + details[9]);
                    System.out.println("Work Residency: " + details[10]);
                    System.out.println("Skills: " + details[11]);
                    break;
                }
            }
            editJSOptions();
    }

    public static int editJSOptionsDisplay()
    {
        Input input = new Input();
        String msg = "      \nEDIT JOB SEEKER PROFILE\n"
                + "Press 1 to edit your email\n"
                + "Press 2 to edit your first name\n"
                + "Press 3 to edit your last name\n"
                + "Press 4 to edit your city\n"
                + "Press 5 to edit your state\n"
                + "Press 6 to edit your date of birth\n"
                + "Press 7 to edit whether to make your profile public or private\n"
                + "Press 8 to edit your desired hourly wage\n"
                + "Press 9 to edit your work type\n"
                + "Press 10 to edit your work residency\n"
                + "Press 11 to edit your skills\n"
                + "Press 0 to go back";
        return input.acceptInt(msg, 0, 11);
    }

    //incomplete case 11
    public static void editJSOptions()
            throws IOException, FileNotFoundException, ParseException
    {
        View.Input input = new View.Input();
        String username = Control.LogInCtrl.getRcUsername();
        String email = "";
        String fName = "";
        String lName = "";
        String city = "";
        String state = "";
        String dob = "";
        String profilePublic = "";
        String wage = "";
        String workType = "";
        String workRes = "";
        String skills = "";

        Control.FileIO file = new Control.FileIO(Control.JSS.JSDETAILS);

        String[] numJob = file.readFile("\n").split("\n");

        for (int i = numJob.length - 1; i >= 0; i--)
        {
            String[] details = numJob[i].split(";");
            if (details[0].equals(Control.LogInCtrl.getRcUsername())) //(display only profile for this user only)
            {
                email = details[1];
                fName = details[2];
                lName = details[3];
                city = details[4];
                state = details[5];
                dob = details[6];
                profilePublic = details[7];
                wage = details[8];
                workType = details[9];
                workRes = details[10];
                skills = details[11];
                break;
            }
        }
        int detailNo = editJSOptionsDisplay();
        boolean verifiedInput = false;
        boolean case0 = false;
        String msg = "\nPlease enter ";
        do {
            switch (detailNo)
            {
                case 1:
                    //edit email
                    do {
                        email = input.acceptString(msg + "your email");
                        verifiedInput = View.UserUI.userVerifyInputs(email);
                    } while (!verifiedInput);
                    break;
                case 2:
                    //edit first name
                    do {
                        fName = input.acceptString(msg + "your first name");
                        verifiedInput = View.UserUI.userVerifyInputs(fName);
                    } while (!verifiedInput);
                    break;
                case 3:
                    //edit last name
                    do {
                        lName = input.acceptString(msg + "your last name");
                        verifiedInput = View.UserUI.userVerifyInputs(lName);
                    } while (!verifiedInput);
                    break;
                case 4:
                    //edit city
                    do {
                        city = input.acceptString(msg + "your city");
                        verifiedInput = View.UserUI.userVerifyInputs(city);
                    } while (!verifiedInput);
                    break;
                case 5:
                    //edit state
                    do {
                        state = input.acceptString(msg + "your state");
                        verifiedInput = View.UserUI.userVerifyInputs(state);
                    } while (!verifiedInput);
                    break;
                case 6:
                    //edit dob
                    do {
                        dob = input.acceptString(msg + "your date of birth");
                        verifiedInput = View.UserUI.userVerifyInputs(dob);
                    } while (!verifiedInput);
                    break;
                case 7:
                    //edit public profile
                    do {
                        profilePublic = input.acceptString(msg + "whether to make your profile public or private");
                        verifiedInput = View.UserUI.userVerifyInputs(profilePublic);
                    } while (!verifiedInput);
                    break;
                case 8:
                    //edit wage
                    do {
                        wage = input.acceptString(msg + "your desired hourly wage");
                        verifiedInput = View.UserUI.userVerifyInputs(wage);
                    } while (!verifiedInput);
                    break;
                case 9:
                    //edit work type
                    do {
                        workType = input.acceptString(msg + "your work type");
                        verifiedInput = View.UserUI.userVerifyInputs(workType);
                    } while (!verifiedInput);
                    break;
                case 10:
                    //edit work residency
                    do {
                        workRes = input.acceptString(msg + "your work residency");
                        verifiedInput = View.UserUI.userVerifyInputs(workRes);
                    } while (!verifiedInput);
                    break;
                case 11:
                    //edit skills
                        case0 = true;
                        editSkills();
                    break;
                case 0:
                    //Go back
                    Control.JobSeekerCtrl.runJSHome();
                    break;
                default:
                    String home = input.acceptString("Going back");
                    View.UserUI.userVerifyInputs(home);
                    break;

            }
            if (case0 = false)
            {
                String wrJS = username + ";" + email + ";" + fName + ";" + lName + ";" + city + ";" + state + ";" + dob + ";" + profilePublic + ";" + wage + ";" + workType + ";" + workRes + ";" + skills;
                //add updated listing, old listing stays
                Control.UserCntrl.writeNewUserToFile(wrJS, Control.JSS.JSDETAILS);
                displayJSDetails();
            }
        } while (!verifiedInput);
    }

    public static int displayEditSkills()
            throws IOException, FileNotFoundException, ParseException
    {
        Input input = new Input();
        String msg = "      EDIT SKILLS\n"
                + "Press 1 to add a skill\n"
                + "Press 2 to remove a skill\n"
                + "Press 0 to go back";
        return input.acceptInt(msg, 0, 2);
    }

    public static void displaySkills()
            throws IOException, FileNotFoundException, ParseException
    {
        Control.FileIO file = new Control.FileIO(Control.JSS.JSDETAILS);

        String[] numJob = file.readFile("\n").split("\n");

        for (int i = numJob.length - 1; i >= 0; i--)
        {
            String[] details = numJob[i].split(";");
            if (details[0].equals(Control.LogInCtrl.getRcUsername())) //(display only profile for this user only)
            {
                String[] skill = details[details.length - 1].split(",");
                for (int j = 0; j < skill.length; j++)
                {
                    System.out.println(j + 1 + ": " + skill[j]);
                }
                break;
            }
        }
    }

    //incomplete
    public static void editSkills()
            throws IOException, FileNotFoundException, ParseException
    {
        Input input = new Input();
        int detailNo = displayEditSkills();
        boolean verifiedInput = false;
        String add = "";
        String remove = "";
        do {
            switch (detailNo)
            {
                case 1:
                    //add skill
                    do {
                        add = input.acceptString("Please enter the index to remove a skill");
                        verifiedInput = View.UserUI.userVerifyInputs(add);
                        } while (!verifiedInput);
                    break;
                case 2:
                    //remove skill
                    do {
                        displaySkills();
                        remove = input.acceptString("Please enter the index to remove a skill");
                        verifiedInput = View.UserUI.userVerifyInputs(remove);
                    } while (!verifiedInput);
                    break;
                case 0:
                    //Go back
                    editJSOptions();
                    break;
                default:
                    String home = input.acceptString("Going back");
                    View.UserUI.userVerifyInputs(home);
                    break;

            }

        } while (!verifiedInput);
    }

    public static int displayJSHome()
    {
        Input input = new Input();
        String msg = "      JOB SEEKER HOME PAGE\n"
                + "Press 1 to search for jobs\n"
                + "Press 2 to view and edit your profile\n"
                + "Press 3 to view your interview offers\n"
                + "Press 4 to view applications\n"
                + "Press 0 to log out";
        return input.acceptInt(msg, 0, 4);
    }

    //Retrieve user input search criteria keywords and returns an array list.
    public ArrayList<String> inputSearchKeywords() throws IOException {
        System.out.println("      SEARCH FOR JOBS");
        Input input = new Input();
        ArrayList<String> searchKeywords = new ArrayList<String>();
        Control.JobSeekerCtrl jsc = new JobSeekerCtrl();
        JobListingUI jlu = new JobListingUI();
        boolean addKeyword = true;
        boolean charInputCheck = true;
        do {
            String keyword = input.acceptString("Please enter keywords to search for jobs.\nKeywords can be job titles, locations, job types (hours), and compensation level.");
            searchKeywords.add(keyword);
            // add another keyword
            char userResponse = input.acceptChar("To add another keyword please enter y. \nTo complete the list please enter n");
            do {
                if (userResponse == 'y') {
                    addKeyword = true;
                    charInputCheck = false;
                } else if (userResponse == 'n') {
                    addKeyword = false;
                    charInputCheck = false;
                } else {
                    System.out.println("Please enter y or n!");
                    charInputCheck = true;
                }
            } while (charInputCheck);
        } while (addKeyword);

        FileIO file = new FileIO(JSS.JSSJOBCATEGORY);
        String[] list = file.readFile("\n").split("\n");

        jlu.displayJobCategories();
        searchKeywords.add(jlu.returnJobCategory(input.acceptInt( "Please select the job category: ", 1, list.length)));

        return searchKeywords;

    }
}