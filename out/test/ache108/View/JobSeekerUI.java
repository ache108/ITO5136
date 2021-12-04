package View;

import Control.*;
import Model.*;
import View.Input;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.io.*;
import java.util.Locale;

public class JobSeekerUI extends View.UserUI
{
    private static ArrayList<String> newList;
    private static int count;
    private static int max;

    public JobSeekerUI()
    {
        newList = new ArrayList<String>();
        count = 1;
        max = 0;
    }

    public ArrayList<String> getNewList()
    {
        return this.newList;
    }

    public void setNewList(ArrayList<String> newList)
    {
        newList = this.newList;
    }

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

            System.out.print("\n              VIEW PROFILE\n"
                + "--------------------------------------------");

            for (int i = 0; i < numJob.length; i++)
            {
                String[] details = numJob[i].split(";");
                if (details[0].equals(Control.LogInCtrl.getRcUsername())) //(display only profile for this user only)
                {
                    System.out.println("\nEmail:                " + details[1]);
                    System.out.println("First Name:           " + details[2]);
                    System.out.println("Last Name:            " + details[3]);
                    System.out.println("City:                 " + details[4]);
                    System.out.println("State:                " + details[5]);
                    System.out.println("Date of Birth:        " + details[6]);
                    System.out.println("Public Profile:       " + details[7]);
                    System.out.println("Desired Yearly Wage:  " + details[8]);
                    System.out.println("Work Type:            " + details[9]);
                    System.out.println("Work Residency:       " + details[10]);
                    displaySkills2();
                }
            }
            editJSOptions();
    }

    public static int editJSOptionsDisplay()
    {
        Input input = new Input();
        String msg = "\n\n         EDIT JOB SEEKER PROFILE\n"
                + "--------------------------------------------\n"
                + "Press 1 to edit your email\n"
                + "Press 2 to edit your first name\n"
                + "Press 3 to edit your last name\n"
                + "Press 4 to edit your city\n"
                + "Press 5 to edit your state\n"
                + "Press 6 to edit your date of birth\n"
                + "Press 7 to edit whether to make your profile public or private\n"
                + "Press 8 to edit your desired yearly wage\n"
                + "Press 9 to edit your work type\n"
                + "Press 10 to edit your work residency\n"
                + "Press 11 to edit your skills\n"
                + "Press 0 to go back";
        return input.acceptInt(msg, 0, 11);
    }

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
        Date dob = null;
        Boolean profilePublic = true;
        Double wage = 0.00;
        String workType = "";
        String workRes = "";

        Control.FileIO file = new Control.FileIO(Control.JSS.JSDETAILS);

        String[] numJob = file.readFile("\n").split("\n");

        for (int i = 0; i < numJob.length; i++)
        {
            String[] details = numJob[i].split(";");
            if (details[0].equals(Control.LogInCtrl.getRcUsername())) //(display only profile for this user only)
            {
                email = details[1];
                fName = details[2];
                lName = details[3];
                city = details[4];
                state = details[5];
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH);
                dob = dateFormat.parse(details[6]);
                profilePublic = Boolean.valueOf(details[7]);
                wage = Double.valueOf(details[8]);
                workType = details[9];
                workRes = details[10];
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
                        dob = input.acceptDate(msg + "your date of birth (dd-mm-yyyy)");
                        verifiedInput = View.UserUI.userVerifyInputs(dob.toString());
                    } while (!verifiedInput);
                    break;
                case 7:
                    //edit public profile
                    do {
                        profilePublic = input.acceptBoolean(msg + "whether to make your profile public (true or false)");
                        verifiedInput = View.UserUI.userVerifyInputs(profilePublic.toString());
                    } while (!verifiedInput);
                    break;
                case 8:
                    //edit wage
                    do {
                        wage = input.acceptDouble(msg + "your desired yearly wage");
                        verifiedInput = View.UserUI.userVerifyInputs(wage.toString());
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
                    editSkills();
                    break;
                case 0:
                    //Go back
                    case0 = true;
                    Control.JobSeekerCtrl.runJSHome();
                    break;
                default:
                    String home = input.acceptString("Going back");
                    View.UserUI.userVerifyInputs(home);
                    break;

            }
            if (case0 == false)
            {
                String wrJS = username + ";" + email + ";" + fName + ";" + lName + ";" + city + ";" + state + ";" + dob + ";" + profilePublic + ";" + wage + ";" + workType + ";" + workRes + ";" + newList;
                Control.UserCntrl.writeNewUserToFile(wrJS, Control.JSS.JSDETAILS);
                newList.clear();
                removeTxtLine();
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
        assignSkills();
        if (newList.get(0).equals("[]"))
        {
            newList.clear();
        }
        for (int i = 0; i < newList.size(); i++)
        {
            System.out.println(i + 1 + ": " + newList.get(i));
        }
    }

    public static void displaySkills2()
            throws IOException, FileNotFoundException, ParseException
    {
        assignSkills();
        if (newList.get(0).equals("[]"))
        {
            newList.clear();
        }
        System.out.print("Skills:               ");
        if (newList.size() == 1)
        {
            StringBuilder sb = new StringBuilder(newList.get(0));
            System.out.print(sb);
        }
        else if (newList.size() > 1)
        {
            for (int i = 0; i <= newList.size() - 1; i++)
            {
                System.out.print(newList.get(i));
                if (i != newList.size() - 1)
                {
                    System.out.print(", ");
                }
            }
        }
        else if (newList.size() == 0)
        {
            System.out.print("No skills added");
        }
    }

    public static void assignSkills()
            throws IOException, FileNotFoundException, ParseException
    {
        newList.clear();
        Control.FileIO file = new Control.FileIO(Control.JSS.JSDETAILS);

        String[] numJob = file.readFile("\n").split("\n");

        for (int i = 0; i < numJob.length; i++)
        {
            String[] details = numJob[i].split(";");
            if (details[0].equals(Control.LogInCtrl.getRcUsername())) //(display only profile for this user only)
            {
                String[] skill = details[details.length - 1].split(",");
                        StringBuilder sb = new StringBuilder(skill[0]);
                        if (newList.size() > 0)
                        {
                            boolean char0check = false;
                            boolean characheck = false;
                            while (char0check == false) {
                                if (sb.charAt(0) == '[')  // [ check
                                {
                                    sb.deleteCharAt(0);
                                } else {
                                    char0check = true;
                                }
                            }
                            while (characheck == false) {
                                if (sb.charAt(sb.length() - 1) == ']') // ] check
                                {
                                    sb.deleteCharAt(sb.length() - 1);
                                } else {
                                    characheck = true;
                                }
                            }
                        }
                        newList.add(sb.toString().trim());
                        newList.set(0, sb.toString());
                        for (int j = 1; j < skill.length - 1; j++)
                        {
                            newList.add(skill[j].trim());
                            count++;
                        }
                        int k = skill[skill.length - 1].length() - 1;
                        StringBuilder sb1 = new StringBuilder(skill[skill.length - 1]);
                        sb1.deleteCharAt(k);
                        if (skill.length > 1)
                        {
                            count++;
                            newList.add(sb1.toString().trim());
                        }
                        max = newList.size();

            }
        }
    }

    public static void editSkills()
            throws IOException, FileNotFoundException, ParseException
    {
        Input input = new Input();
        int detailNo = displayEditSkills();
        boolean verifiedInput = false;
        boolean sameSkill = false;
        String include = "";
        int delete = -1;
        do {
            switch (detailNo)
            {
                case 1:
                    //add skill
                    do {
                        sameSkill = false;
                        displaySkills();
                        include = input.acceptString("Please enter a new skill to add");
                        verifiedInput = View.UserUI.userVerifyInputs(include);
                        for (int i = 0; i < newList.size(); i++)
                        {
                            if (newList.get(i).equals(include))
                            {
                                System.out.println("Skill already added");
                                sameSkill = true;
                            }
                        }
                        if (sameSkill == false)
                        {
                            newList.add(include);
                        }
                        count++;
                        } while (!verifiedInput);
                    break;
                case 2:
                    //remove skill
                    do {
                        displaySkills();
                        if (newList.size() > 0)
                        {
                            delete = input.acceptInt("Please enter the index to remove a skill", 1, max);
                            verifiedInput = true;
                            newList.remove(delete - 1);
                        }
                        else
                        {
                            verifiedInput = true;
                            System.out.print("No skills to delete");
                        }
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

    public static void removeTxtLine()
            throws IOException, FileNotFoundException, ParseException
    {
        Control.FileIO file = new Control.FileIO(Control.JSS.JSDETAILS);

        String[] numJob = file.readFile("\n").split("\n");

        for (int i = 0; i < numJob.length; i++)
        {
            String[] details = numJob[i].split(";");
            if (details[0].equals(Control.LogInCtrl.getRcUsername())) //(display only profile for this user only)
            {
                Control.FileIO.removeLine(numJob[i]);
                break;
            }
        }

    }

    public static int displayJSHome()
    {
        Input input = new Input();
        String msg = "----------------------------------\n"
                +    "|      JOB SEEKER HOME PAGE      |\n"
                +    "----------------------------------\n"
                + "Press 1 to search for jobs\n"
                + "Press 2 to view and edit your profile\n"
                + "Press 3 to view your interview offers\n"
                + "Press 4 to view applications\n"
                + "Press 0 to log out";
        return input.acceptInt(msg, 0, 4);
    }

    //Retrieve user input search criteria keywords and returns a Job Listing object.
    public Model.JobListing inputSearchKeywords()
            throws IOException
    {
        System.out.println("\n            SEARCH FOR JOBS\n"
                         + "--------------------------------------------");
        Input input = new Input();
        Control.JobSeekerCtrl jsc = new JobSeekerCtrl();
        JobListingUI jlu = new JobListingUI();
        Model.JobListing req = new JobListing();
        JobListingCtrl jlc = new JobListingCtrl();

        FileIO file = new FileIO(JSS.JSSJOBCATEGORY);
        String[] list = file.readFile("\n").split("\n");

        System.out.println("\nPlease enter keywords for each respective fields to start your search.");
        req.setJobTitle(input.acceptString("\nJob title: "));

        System.out.println("\n-----------------");
        jlu.displayJobCategories();
        System.out.println("-----------------");
        String jobCat = jlu.returnJobCategory(input.acceptInt("\nPlease select the job category *", 1, list.length));
        req.setJobCategory(jobCat);

        req.setJobLocation(input.acceptString("\nLocation of the job:"));
        req.setJobHours(input.acceptString("\nJob type (Full time, Contract, Part time):"));
        req.setJobPay(input.acceptString("\nJob compensation per annum"));
        //req.setJobSkills(/*JOB SEEKER SKILL SET IN PROFILE*/);

        return req;
    }
}