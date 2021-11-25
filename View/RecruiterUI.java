package View;
import Model.JobListing;
import View.Input;
import java.io.*;
import Control.RecruiterCtrl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

import Model.CompanyListing;

public class RecruiterUI extends View.UserUI
{

    public static void recruiterRegisterScreen(String iptName)
            throws IOException
    {
        saveRCDetails();
        RecruiterUI.displayRCHome();
    }

    public static void saveRCDetails()
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
        // Send to Controller to create new profile
        Control.CompanyCtrl.addNewRC(rcID, usrCompany, usrCompAddress, usrCompEmail, usrCompPhone, usrCompDescr);
    }

    //display company details
    public static void displayCompany()
            throws IOException, FileNotFoundException, ParseException
    {
        Control.FileIO file = new Control.FileIO("Files/rcUserDetails.txt");

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
        Control.CompanyCtrl.editCompanyListing();
    }

    public static int editCompanyOptions()
    {
        Input input = new Input();
        String msg = "      EDIT COMPANY LISTING\n"
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
        String msg = "      RECRUITER HOME PAGE\n"
                + "Press 1 to create job listing\n"
                + "Press 2 to view job listing\n"
                + "Press 3 to view company profile\n"
                + "Press 4 to view interview offers\n"
                + "Press 0 to log out";
        return input.acceptInt(msg, 0, 4);
    }
}