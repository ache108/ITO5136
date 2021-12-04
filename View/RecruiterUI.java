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