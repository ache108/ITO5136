package View;
import View.Input;
import View.RecruiterUI;
import java.util.Date;
import java.io.*;

public class UserUI
{
    // do we want to get users to confirm all input that they enter?

    //display input for registration forms & save details into Txt File
    public static void userRegisterScreen()
            throws IOException
    {
        Input input = new Input();
        System.out.println("To register, we need to grab a few details off you");
        String msg = "Please enter your ";
        // All users need these fields
        // Verify no response has comma's or it will break the write & read to file..
        String usrEmail = input.acceptString(msg + "email.");
        String usrFName = input.acceptString(msg + "First Name.");
        String usrLName = input.acceptString(msg + "Last Name.");
        String usrName = input.acceptString(msg + "username. This will be the name you will log in with.");
        String usrPwd = input.acceptString(msg + "password that you will use to log into the system.");
        String usrCity = input.acceptString(msg + "City where you want to browse for Jobs.");
        String usrState = input.acceptString(msg + "State where you want to browse for Jobs.");
        Date userDOB = input.acceptDate(msg + "DOB in the format dd-mm-yyyy");
        Boolean userPublic = input.acceptBoolean("Do you want your profile public to all users? \n Please enter y to make public or n to make it private");

        // Send to User Controller to create new user
        Control.UserCntrl.addNewUser(usrName, usrEmail, usrFName, usrLName, usrCity, usrState, usrPwd, userDOB, userPublic );

    }

    public static void rcRegisterScreen()
            throws IOException
    {
        Input input = new Input();
        System.out.println("To register, we need to grab a few details off you");
        String msg = "Please enter your company's";
        // All users need these fields
        // Verify no response has comma's or it will break the write & read to file..
        String usrCompany = input.acceptString(msg + "name.");
        String usrCompAddress = input.acceptString(msg + "address.");
        String usrCompEmail = input.acceptString(msg + "email.");
        String usrCompPhone = input.acceptString(msg + "phone number.");
        String usrCompDescr = input.acceptString(msg + "description.");
        String rcID = RecruiterUI.generateRCID("Files/rcUserDetails.txt");


        // Send to User Controller to create new user
        Control.CompanyCtrl.addNewRC(rcID, usrCompany, usrCompAddress, usrCompEmail, usrCompPhone, usrCompDescr);

    }
}