package View;
import View.Input;
import java.io.*;
import java.util.Date;

import static Model.CompanyListing.*;

public class RecruiterUI extends View.UserUI
{
    public static void recruiterInputs()
    {
        Input input = new Input();
        String usrCompany = input.acceptString("Please enter the company you work for.");
        String usrCompAddress = input.acceptString("Please enter the address of your company");
        String usrCompEmail = input.acceptString("Please enter the email for your company");
        String usrCompPhone = input.acceptString("Please enter the phone number for your company");
        String usrCompDescr = input.acceptString("Please enter a brief description of your company");
    }

       public static String generateRCID(String filename)
            throws IOException, FileNotFoundException
    {
        Control.FileIO file = new Control.FileIO(filename);

        String[] lines = file.readFile("\n").split("\n");
        int numJob = lines.length + 1;
        String rcID = String.format("%08d", numJob);

        return rcID;
    }

    public static void recruiterRegisterScreen()
            throws IOException
    {
        View.UserUI.rcRegisterScreen();
        //recruiterInputs();
        RecruiterUI.displayRCHome();
    }

    public void saveRCDetails()
            throws IOException
    {
        Input input = new Input();
        System.out.println("\nPlease provide the following details");
        String msg = "\nPlease enter ";
        String usrCompany = input.acceptString(msg + "the company's name");
        String usrCompAddress = input.acceptString(msg + "the company's address");
        String usrCompEmail = input.acceptString(msg + "the company's email");
        String usrCompPhone = input.acceptString(msg + "the company's phone number");
        String usrCompDescr = input.acceptString(msg + "a brief description about the company");
        String rcID = generateRCID("Files/rcUserDetails.txt");
        displayCompanyDetails(usrCompany, usrCompAddress, usrCompEmail, usrCompPhone, usrCompDescr);
        // Send to Job Listing Controller to create new job
        Control.CompanyCtrl.addNewRC(rcID, usrCompany, usrCompAddress, usrCompEmail, usrCompPhone, usrCompDescr);
    }

    public String[][] getCompanyCreds(String filename)
            throws IOException, FileNotFoundException
    {
        //String filename = "Files/rcUserDetails.txt";
        Control.FileIO file = new Control.FileIO(filename);

        String[] lines = file.readFile(";").split(";");
        String[][] output = new String[lines.length][5];
        for(int i = 0; i < lines.length; i++)
        {
            output[i] = lines[i].split(",");
        }

        return output;

    }

    public void displayCompany()
    {
        View.LogInUI ui = new View.LogInUI();
        //String filename = "Files/rcUserDetails.txt";
        String username = ui.inputUsrName();
        String[][] detailCompany = {};
        //detailCompany = getCompanyCreds("Files/rcUserDetails.txt");

            for(int i = 0; i < detailCompany.length; i++)
                {
                    if(username.equals(detailCompany[i][0]))
                    {
                        String usrCompany = detailCompany[i][1];
                        String usrCompAddress = detailCompany[i][2];
                        String usrCompEmail = detailCompany[i][3];
                        String usrCompPhone = detailCompany[i][4];
                        String usrCompDescr = detailCompany[i][5];
                        displayCompanyDetails(usrCompany, usrCompAddress, usrCompEmail, usrCompPhone, usrCompDescr);
                    }
                }

    }

    public void displayCompanyDetails(String usrCompany, String usrCompAddress, String usrCompEmail, String usrCompPhone, String usrCompDescr)
    {
        System.out.println("\nCompany name: " + usrCompany);
        System.out.println("Company Address: " + usrCompAddress);
        System.out.println("Company Email: " + usrCompEmail);
        System.out.println("Company Phone Number: " + usrCompPhone);
        System.out.println("Company Description: " + usrCompDescr);
    }

    public static int displayRCHome()
    {
        Input input = new Input();
        String msg = "      RECRUITER HOME PAGE\n"
                + "Press 1 to create job listing\n"
                + "Press 2 to view job listing\n"
                + "Press 3 to view company profile\n"
                + "Press 4 to view interview offers\n"
                + "Press 0 to log out";
        return input.acceptInt(msg, 0, 4);
    }
}