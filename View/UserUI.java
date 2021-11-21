package View;
import View.Input;
import java.util.Date;

public class UserUI
{
    // do we want to get users to confirm all input that they enter?

    //display input for registration forms & save details into Txt File
    public static void userRegisterScreen()
    {
        Input input = new Input();
        System.out.println("To register, we need to grab a few details off you");
        String msg = "Please enter your ";
        // All users need these fields
        String usrEmail = input.acceptString(msg + "email.");
        String usrFName = input.acceptString(msg + "First Name.");
        String usrLName = input.acceptString(msg + "Last Name.");
        String usrName = input.acceptString(msg + "username. This will be the name you will log in with.");
        String usrCity = input.acceptString(msg + "City where you want to browse for Jobs.");
        String usrState = input.acceptString(msg + "State where you want to browse for Jobs.");
        Date userDOB = input.acceptDate(msg + "DOB in the format dd-mm-yyyy");
        Boolean userPublic = input.acceptBoolean("Do you want your profile public to all users? \n Please enter y to make public or n to make it private");

        // write inputs to file now or pass and save them all in a single turn?
        // TO DO: VERIFY EMAIL OR USER NAME IS UNIQUE!
        // write method needs in file needs to create user id

    }
}