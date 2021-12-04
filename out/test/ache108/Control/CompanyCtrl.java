package Control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static String getCompanyLocation(String rcID)
            throws IOException, ParseException
    {
        // return only office location as string
        String compLocation = "";

        Control.FileIO file = new Control.FileIO(Control.JSS.RCCOMPDETAILS);
        String[] numCompanies = file.readFile("\n").split("\n");

        for (int i = 0; i< numCompanies.length; i--)
        {
            String[] details = numCompanies[i].split(",");
            if (details[0].equals(rcID)) //(get company location for the recruiter only)
            {
                compLocation = details[2];
                break;
            }
        }
        return compLocation;
    }

    public static void writeNewRCToFile(String infoToWrite, String fileName)
            throws IOException
    {
        Control.FileIO fName = new Control.FileIO(fileName);
        fName.appendFile(infoToWrite);
    }
}
