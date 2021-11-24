package View;
import View.Input;
import View.RecruiterUI;
import java.util.Date;
import java.io.*;
import Control.LogInCtrl;

public class UserUI
{
    //display input for registration forms & save details into Txt File
    public static Model.User userRegisterScreen(String iptName)
    {
        Input input = new Input();
        System.out.println("To register, we need to grab a few details off you");
        // All users need these fields
        String usrEmail = "";
        String usrFName = "";
        String usrLName = "";
        String usrCity = "";
        String usrState = "";
        String msg = "Please enter your ";
        boolean verifiedInput = false;

        do {
            usrEmail = input.acceptString(msg + "email.");
            verifiedInput = userVerifyInputs(usrEmail);
        } while (!verifiedInput);

        do {
            usrFName = input.acceptString(msg + "First Name.");
            verifiedInput = userVerifyInputs(usrFName);
        } while (!verifiedInput);


        do {
            usrLName = input.acceptString(msg + "Last Name.");
            verifiedInput = userVerifyInputs(usrLName);
        } while (!verifiedInput);

        do {
            usrCity = input.acceptString(msg + "City where you want to browse for Jobs.");
            verifiedInput = userVerifyInputs(usrCity);
        } while (!verifiedInput);

        do {
            usrState = input.acceptString(msg + "State where you want to browse for Jobs.");
            verifiedInput = userVerifyInputs(usrState);
        } while (!verifiedInput);

        Date userDOB = input.acceptDate(msg + "DOB in the format dd-mm-yyyy");
        Boolean userPublic = input.acceptBoolean("Do you want your profile public to all users? \n Please enter y to make public or n to make it private");

        Model.User newUser = Control.UserCntrl.addNewUser(iptName, usrEmail, usrFName, usrLName, usrCity, usrState, userDOB, userPublic);
        return newUser;
    }

    public static void rcRegisterScreen()
            throws IOException
    {
        Input input = new Input();
        System.out.println("To register, we need to grab a few details off you");
        String msg = "Please enter your company's ";
        // All users need these fields
        // Verify no response has comma's or it will break the write & read to file..
        String usrCompany = input.acceptString(msg + "name.");
        String usrCompAddress = input.acceptString(msg + "address.");
        String usrCompEmail = input.acceptString(msg + "email.");
        String usrCompPhone = input.acceptString(msg + "phone number.");
        String usrCompDescr = input.acceptString(msg + "description.");
        String rcID = Control.LogInCtrl.getRcUsername();

        // Send to User Controller to create new user
        Control.CompanyCtrl.addNewRC(rcID, usrCompany, usrCompAddress, usrCompEmail, usrCompPhone, usrCompDescr);

    }

    public static boolean userVerifyInputs(String iptString)
    {
        if(iptString.contains(",") || iptString.contains(";") || iptString.length() == 0)
        {
            System.out.println("Please remove commas and semi colons");
            return false;
        }
        return true;
    }
}