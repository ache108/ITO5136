package Control;
import java.util.Date;
import java.io.*;
import Control.FileIO;

public class UserCntrl
{

    // pass in params to create new user & if jobSeeker or Recruiter
    public static void addNewUser(String nUsrName, String nUsrEmail, String nUsrFName, String nUsrLName, String nUsrCity, String nUsrState, String nUsrPwd, Date nUsrDOB, boolean nUsrPublic )
        throws IOException, FileNotFoundException
    {
        // new user is created
        Model.User newUser = new Model.User(nUsrName, nUsrEmail, nUsrFName, nUsrLName, nUsrCity, nUsrState, nUsrPwd, nUsrDOB, nUsrPublic );

       // write user details for profile to input
        String userDetails = nUsrName + "," + nUsrEmail + "," + nUsrFName + "," + nUsrLName + "," + nUsrCity + "," + nUsrState + "," + nUsrDOB + "," + nUsrPublic;
        writeNewUserToFile(userDetails, Control.JSS.JSDETAILS);

    }

    public void resetPasswd()
    {

    }

    public void showUser(String userName)
    {
        // return all information from UserDetails file to show user on home screen
    }

    public static void writeNewUserToFile(String infoToWrite, String fileName)
            throws IOException
    {
        FileIO fName = new FileIO(fileName);
        fName.appendFile(infoToWrite);
    }

}