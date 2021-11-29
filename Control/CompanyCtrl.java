package Control;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CompanyCtrl {

    public static void addNewRC(String rcID, String usrCompany, String usrCompAddress, String usrCompEmail, String usrCompPhone, String usrCompDescr)
            throws IOException, FileNotFoundException
    {
        // new job is created
        //Model.CompanyListing newJob = new Model.CompanyListing(rcID, usrCompany, usrCompAddress, usrCompEmail, usrCompPhone, usrCompDescr);

        // write inputs to file now or pass and save them all in a single turn?
        String rcDetails = rcID + "," + usrCompany + "," + usrCompAddress+ "," + usrCompEmail + "," + usrCompPhone+ "," + usrCompDescr;
        writeNewRCToFile(rcDetails, Control.JSS.RCCOMPDETAILS);

    }

    public static void writeNewRCToFile(String infoToWrite, String fileName)
            throws IOException
    {
        Control.FileIO fName = new Control.FileIO(fileName);
        fName.appendFile(infoToWrite);
    }
}
