package Control;

import Model.*;
import View.JobSeekerUI;
import View.LogInUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JobSeekerCtrl {

    private static ArrayList<String> newList;
    private static int count;
    private static int max;

    public JobSeekerCtrl()
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

    public static void createNewJobSeeker(Model.User newUser, double hrlyRate, String wrkType, String wrkResidency, ArrayList<String> iptSkills)
            throws IOException
    {
        // send to model to create
        Model.JobSeeker js = new Model.JobSeeker(newUser, hrlyRate, wrkType, wrkResidency, iptSkills);
        // write Output to File
        String wrJS = writeJSString(js);
        Control.UserCntrl.writeNewUserToFile(wrJS, Control.JSS.JSDETAILS);
    }

    public static Model.JobSeeker getCurrentJobSeeker()
            throws IOException, ParseException
    {
        Model.User curUser = Control.UserCntrl.getCurrentUser();
        Control.FileIO file = new Control.FileIO(Control.JSS.JSDETAILS);

        String[] numJob = file.readFile("\n").split("\n");
        String workType = "";
        String workRes = "";
        String wage = "";
        String strSkills = "";

        for (int i = numJob.length - 1; i >= 0; i--) {
            String[] details = numJob[i].split(";");
            if (details[0].equals(curUser.userName)) //(display only profile for this user only)
            {
                wage = details[8];
                workType = details[9];
                workRes = details[10];
                strSkills = details[11];
                break;
            }
        }
        double hrlyWage = Double.parseDouble(wage);
        //.replace('[', ' ').replace(']', ' ').trim()
        String[] skillsSplit = strSkills.split(",");

        ArrayList<String> usrSkills = new ArrayList<String>();
        for (int i=0; i< skillsSplit.length; i++)
        {
            usrSkills.add(skillsSplit[i]);
        }

        Model.JobSeeker js = new Model.JobSeeker(curUser, hrlyWage, workType, workRes, usrSkills);
        return js;
    }

    public static Model.JobSeeker getJobSeeker(String userName)
            throws IOException, ParseException
    {
        Model.User userData = Control.UserCntrl.getUser(userName);
        Control.FileIO file = new Control.FileIO(Control.JSS.JSDETAILS);

        String[] numJob = file.readFile("\n").split("\n");
        String workType = "";
        String workRes = "";
        String wage = "";
        String strSkills = "";

        for (int i = numJob.length - 1; i >= 0; i--) {
            String[] details = numJob[i].split(";");
            if (details[0].equals(userName)) //(display only profile for this user only)
            {
                wage = details[8];
                workType = details[9];
                workRes = details[10];
                strSkills = details[11];
                break;
            }
        }
        double hrlyWage = Double.parseDouble(wage);
        //.replace('[', ' ').replace(']', ' ').trim()
        String[] skillsSplit = strSkills.split(",");

        ArrayList<String> usrSkills = new ArrayList<String>();
        for (int i=0; i< skillsSplit.length; i++)
        {
            usrSkills.add(skillsSplit[i]);
        }

        Model.JobSeeker js = new Model.JobSeeker(userData, hrlyWage, workType, workRes, usrSkills);
        return js;
    }

    //Job Seeker home page
    public static void runJSHome() throws IOException, ParseException {
        LogInUI ui = new View.LogInUI();
        LogInCtrl lic = new LogInCtrl();
        JobSeekerUI js = new View.JobSeekerUI();

        int choiceJS = js.displayJSHome();
        switch (choiceJS)
        {
            case 1:
                //link to search for job
                searchJob();
                break;
            case 2:
                //link to view and edit profile
                js.displayJSDetails();
                Control.JobSeekerCtrl.editJSOptions();
                break;
            case 3:
                //link to view interview
                Control.InterviewCtrl.viewJobSeekerInterviews();
                break;
            case 4:
                //link to view applications
                Control.JobApplicationCtrl.viewJSApplication();
                break;
            case 0:
                //logging out
                ui.displayMsg("Logging out\n");
                lic.start();
        }
    }

    //Method to link with Job Listing Control.
    public static void searchJob() throws IOException, ParseException {
        Control.JobListingCtrl jlc = new Control.JobListingCtrl();

        jlc.viewJLFromJS();
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
        int detailNo = View.JobSeekerUI.editJSOptionsDisplay();
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
                View.JobSeekerUI.displayJSDetails();
            }
        } while (!verifiedInput);
    }

    public static int displayEditSkills()
            throws IOException, FileNotFoundException, ParseException
    {
        View.Input input = new View.Input();
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
                sb.deleteCharAt(0);
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
        View.Input input = new View.Input();
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

    public static String writeJSString(Model.JobSeeker js)
    {
        String msg = "";
        msg += js.userName;
        msg += ";" + js.userEmail;
        msg += ";" + js.firstName;
        msg += ";" + js.lastName;
        msg += ";" + js.city;
        msg += ";" + js.state;
        msg += ";" + js.dateOfBirth;
        msg += ";" + js.publicProfile;
        msg += ";" + js.hourlyWageRate;
        msg += ";" + js.wrkType;
        msg += ";" + js.residencyType;
        msg += ";" + js.skillsList;

        return msg;
    }



}
