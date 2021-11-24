package Control;
import java.util.Date;
import java.io.*;
import Control.FileIO;

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