package Control;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class UserCntrl
{

    public static Model.User addNewUser(String nUsrName, String nUsrEmail, String nUsrFName, String nUsrLName, String nUsrCity, String nUsrState, Date nUsrDOB, boolean nUsrPublic )
    {
        // new user is created
        Model.User newUser = new Model.User(nUsrName, nUsrEmail, nUsrFName, nUsrLName, nUsrCity, nUsrState, nUsrDOB, nUsrPublic );
        return newUser;
    }

    public void resetPasswd()
    {

    }

    public static Model.User getCurrentUser()
            throws IOException, ParseException
    {
        // return all information from UserDetails file for logged in User
        String username = Control.LogInCtrl.getRcUsername();
        String email = "";
        String fName = "";
        String lName = "";
        String city = "";
        String state = "";
        String dob = "";
        String profilePublic = "";

        Date userDob = null;

        Control.FileIO file = new Control.FileIO(Control.JSS.JSDETAILS);

        String[] numJob = file.readFile("\n").split("\n");

        for (int i = 0; i < numJob.length; i++)
        {
            String[] details = numJob[i].split(";");
            if (details[0].equals(username))
            {
                email = details[1];
                fName = details[2];
                lName = details[3];
                city = details[4];
                state = details[5];
                dob = details[6];
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH);
                userDob = format.parse(details[6]);
                profilePublic = details[7];
            }
        }

        boolean pubProfile = Boolean.parseBoolean(profilePublic);

        Model.User curUser = new Model.User (username, email, fName, lName, city, state, userDob, pubProfile);
        return curUser;
    }

    public static Model.User getUser(String userName)
            throws IOException, ParseException
    {
        // return all information from UserDetails file passed in user
        String email = "";
        String fName = "";
        String lName = "";
        String city = "";
        String state = "";
        String dob = "";
        String profilePublic = "";

        Control.FileIO file = new Control.FileIO(Control.JSS.JSDETAILS);

        String[] numJob = file.readFile("\n").split("\n");

        for (int i = numJob.length - 1; i >= 0; i--)
        {
            String[] details = numJob[i].split(";");
            if (details[0].equals(userName))
            {
                email = details[1];
                fName = details[2];
                lName = details[3];
                city = details[4];
                state = details[5];
                dob = details[6];
                profilePublic = details[7];
                break;
            }
        }

        boolean pubProfile = Boolean.parseBoolean(profilePublic);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
        Date userDob = dateFormat.parse(dob);

        Model.User curUser = new Model.User (userName, email, fName, lName, city, state, userDob, pubProfile);
        return curUser;
    }

    public static void writeNewUserToFile(String infoToWrite, String fileName)
            throws IOException
    {
        FileIO fName = new FileIO(fileName);
        fName.appendFile(infoToWrite);
    }

}