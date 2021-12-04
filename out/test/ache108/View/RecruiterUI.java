package View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class RecruiterUI extends View.UserUI
{

    public static void recruiterRegisterScreen(String iptName)
            throws IOException
    {
        recruiterInputs(iptName);
        RecruiterUI.displayRCHome();
    }

    public static void recruiterInputs(String userName)
            throws IOException
    {
        // get user inputs and personal details for the recruiter
        Model.User newUser = View.UserUI.userRegisterScreen(userName);

        // Get extra inputs from Recruiter
        Input input = new Input();
        System.out.println("\nPlease provide the following details");
        String msg = "\nPlease enter ";
        boolean verifiedInput;
        String usrCompany = "";
        String usrCompAddress = "";
        String usrCompEmail = "";
        String usrCompPhone = "";
        String usrCompDescr = "";

        do {
            usrCompany = input.acceptString(msg + "the company's name");
            verifiedInput = userVerifyInputs(usrCompany);
        } while (!verifiedInput);

        do {
            usrCompAddress = input.acceptString(msg + "the company's address");
            verifiedInput = userVerifyInputs(usrCompAddress);
        } while (!verifiedInput);

        do {
            usrCompEmail = input.acceptString(msg + "the company's email");
            verifiedInput = userVerifyInputs(usrCompEmail);
        } while (!verifiedInput);

        // make this a number input?
        do {
            usrCompPhone = input.acceptString(msg + "the company's phone number");
            verifiedInput = userVerifyInputs(usrCompPhone);
        } while (!verifiedInput);

        do {
            usrCompDescr = input.acceptString(msg + "a brief description about the company");
            verifiedInput = userVerifyInputs(usrCompDescr);
        } while (!verifiedInput);


        // Send to Controller to create new company profile
        Control.CompanyCtrl.addNewRC(userName, usrCompany, usrCompAddress, usrCompEmail, usrCompPhone, usrCompDescr);

        // Send to Controller to create new Recruiter Profile
        Control.RecruiterCtrl.createNewRecruiter(newUser);
    }

    //display company details
    public static void displayCompany()
            throws IOException, FileNotFoundException, ParseException
    {
        Control.FileIO file = new Control.FileIO(Control.JSS.RCCOMPDETAILS);

        String[] numJob = file.readFile("\n").split("\n");
        System.out.print("\n            VIEW COMPANY PROFILE\n"
            + "--------------------------------------------");
        for (int i = 0; i < numJob.length; i++)
        {
            String[] details = numJob[i].split(",");
            if (details[0].equals(Control.LogInCtrl.getRcUsername())) //(display only profile for this user only)
            {
                System.out.println("\nCompany name:           " + details[1]);
                System.out.println("Company Address:        " + details[2]);
                System.out.println("Company Email:          " + details[3]);
                System.out.println("Company Phone Number:   " + details[4]);
                System.out.println("Company Description:    " + details[5]);
            }
        }
        Control.CompanyCtrl.editCompanyListing();
    }

    /*public static void editCompanyListing() throws IOException, ParseException {

        View.Input input = new View.Input();
        String rcID = Control.LogInCtrl.getRcUsername();
        String usrCompany = "";
        String usrCompAddress = "";
        String usrCompEmail = "";
        String usrCompPhone = "";
        String usrCompDescr = "";
        Control.FileIO file = new Control.FileIO(Control.JSS.RCCOMPDETAILS);

        String[] numJob = file.readFile("\n").split("\n");
        for (int i = 0; i < numJob.length; i++)
        {
            String[] details = numJob[i].split(",");
            if (details[0].equals(Control.LogInCtrl.getRcUsername())) //(display only profile for this user only)
            {
                usrCompany = details[1];
                usrCompAddress = details[2];
                usrCompEmail = details[3];
                usrCompPhone = details[4];
                usrCompDescr = details[5];
            }
        }

        int detailNo = View.RecruiterUI.editCompanyOptions();
        boolean verifiedInput = false;
        boolean case0 = false;
        String msg = "\nPlease enter ";
        do {
            switch (detailNo)
            {
                case 1:
                    //edit company name
                    do {
                        usrCompany = input.acceptString(msg + "the company's name");
                        verifiedInput = View.UserUI.userVerifyInputs(usrCompany);
                    } while (!verifiedInput);
                    break;
                case 2:
                    //edit company address
                    do {
                        usrCompAddress = input.acceptString(msg + "the company's address");
                        verifiedInput = View.UserUI.userVerifyInputs(usrCompAddress);
                    } while (!verifiedInput);
                    break;
                case 3:
                    //edit company email
                    do {
                        usrCompEmail = input.acceptString(msg + "the company's email");
                        verifiedInput = View.UserUI.userVerifyInputs(usrCompEmail);
                    } while (!verifiedInput);
                    break;
                case 4:
                    //edit company phone number
                    do {
                        usrCompPhone = input.acceptString(msg + "the company's phone number");
                        verifiedInput = View.UserUI.userVerifyInputs(usrCompPhone);
                    } while (!verifiedInput);
                    break;
                case 5:
                    //edit company description
                    do {
                        usrCompDescr = input.acceptString(msg + "a brief description about the company");
                        verifiedInput = View.UserUI.userVerifyInputs(usrCompDescr);
                    } while (!verifiedInput);
                    break;
                case 0:
                    //Go back
                    case0 = true;
                    Control.RecruiterCtrl.runRCHome();
                    break;
                default:
                    String rcHome = input.acceptString("Going back");
                    View.UserUI.userVerifyInputs(rcHome);
                    break;

            }
            if (case0 == false)
            {
                String rcDetails = rcID + "," + usrCompany + "," + usrCompAddress + "," + usrCompEmail + "," + usrCompPhone + "," + usrCompDescr;
                Control.CompanyCtrl.writeNewRCToFile(rcDetails, Control.JSS.RCCOMPDETAILS);
                removeTxtRcLine();
                View.RecruiterUI.displayCompany();
            }
        } while (!verifiedInput);
    }

    public static void removeTxtRcLine()
            throws IOException, FileNotFoundException, ParseException
    {
        Control.FileIO file = new Control.FileIO(Control.JSS.RCDETAILS);

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

    }*/

    public static int editCompanyOptions()
    {
        Input input = new Input();
        String msg = "\n           EDIT COMPANY LISTING\n"
                + "--------------------------------------------\n"
                + "Press 1 to edit company profile name\n"
                + "Press 2 to edit company profile address\n"
                + "Press 3 to edit company profile email\n"
                + "Press 4 to edit company profile phone number\n"
                + "Press 5 to edit company profile description\n"
                + "Press 0 to go back";
        return input.acceptInt(msg, 0, 5);
    }


    public static int displayRCHome()
    {
        Input input = new Input();
        String msg = "----------------------------------\n"
                +    "|       RECRUITER HOME PAGE      |\n"
                + "----------------------------------\n"
                + "Press 1 to create job listing\n"
                + "Press 2 to view job listing\n"
                + "Press 3 to view and edit company profile\n"
                + "Press 4 to view applications\n"
                + "Press 5 to view interview offers\n"
                + "Press 0 to log out";
        return input.acceptInt(msg, 0, 4);
    }
}