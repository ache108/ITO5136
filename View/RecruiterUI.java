package View;
import Model.JobListing;
import View.Input;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

import Model.CompanyListing;

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

       /*public static String generateRCID(String filename)
            throws IOException, FileNotFoundException
    {
        Control.FileIO file = new Control.FileIO(filename);

        String[] lines = file.readFile("\n").split("\n");
        int numJob = lines.length + 1;
        String rcID = String.format("%08d", numJob);

        return rcID;
    }*/

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
        String rcID = Control.LogInCtrl.getRcUsername();
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

    //display company details
    public ArrayList<CompanyListing> displayCompany()
            throws IOException, FileNotFoundException, ParseException
    {
        Control.FileIO file = new Control.FileIO("Files/rcUserDetails.txt");
        ArrayList<Model.CompanyListing> list = new ArrayList<>();

        String[] numJob = file.readFile("\n").split("\n");

        for (int i = 0; i < numJob.length; i++)
        {
            String[] details = numJob[i].split(",");
            if (details[0].equals(Control.LogInCtrl.getRcUsername())) //(display only profile for this user only)
            {
                System.out.println("\nCompany name: " + details[1]);
                System.out.println("Company Address: " + details[2]);
                System.out.println("Company Email: " + details[3]);
                System.out.println("Company Phone Number: " + details[4]);
                System.out.println("Company Description: " + details[5]);
            }
        }
        Control.RecruiterCtrl.runRCHome();
        return list;
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