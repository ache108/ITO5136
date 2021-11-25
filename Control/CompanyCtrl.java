package Control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class CompanyCtrl {

    public static void addNewRC(String rcID, String usrCompany, String usrCompAddress, String usrCompEmail, String usrCompPhone, String usrCompDescr)
            throws IOException, FileNotFoundException
    {
        // new job is created
        Model.CompanyListing newJob = new Model.CompanyListing(rcID, usrCompany, usrCompAddress, usrCompEmail, usrCompPhone, usrCompDescr);

        // write inputs to file now or pass and save them all in a single turn?
        String rcDetails = rcID + "," + usrCompany + "," + usrCompAddress+ "," + usrCompEmail + "," + usrCompPhone+ "," + usrCompDescr;
        writeNewRCToFile(rcDetails, Control.JSS.RCCOMPDETAILS);

    }


    public static void editCompanyListing() throws IOException, ParseException {

        View.Input input = new View.Input();
        Model.CompanyListing cl = new Model.CompanyListing();
        String rcID = Control.LogInCtrl.getRcUsername();
        String usrCompany = "";
        String usrCompAddress = "";
        String usrCompEmail = "";
        String usrCompPhone = "";
        String usrCompDescr = "";
        Control.FileIO file = new Control.FileIO(Control.JSS.RCCOMPDETAILS);

        String[] numJob = file.readFile("\n").split("\n");

        for (int i = 0; i < numJob.length; i++)
        {
            String[] details = numJob[i].split(",");
            if (details[0].equals(Control.LogInCtrl.getRcUsername())) //(display only profile for this user only)
            {
                usrCompany = details[1];
                usrCompAddress = details[2];
                usrCompEmail = details[3];
                usrCompPhone = details[4];
                usrCompDescr = details[5];
            }
        }

        int detailNo = View.RecruiterUI.editCompanyOptions();
        switch (detailNo)
        {
            case 1:
                //edit company name
                usrCompany = input.acceptString("Please enter new company name: ");
                break;
            case 2:
                //edit company address
                usrCompAddress = input.acceptString("Please enter new company address: ");
                break;
            case 3:
                //edit company email
                usrCompEmail = input.acceptString("Please enter new company email: ");
                break;
            case 4:
                //edit company phone number
                usrCompPhone = input.acceptString("Please enter new company phone number: ");
                break;
            case 5:
                //edit company description
                usrCompDescr = input.acceptString("Please enter new company description: ");
                break;
            case 0:
                //Go back
                Control.RecruiterCtrl.runRCHome();
                break;

        }
        String rcDetails = rcID + "," + usrCompany + "," + usrCompAddress+ "," + usrCompEmail + "," + usrCompPhone+ "," + usrCompDescr;
        //remove old listing
        removeListing();
        writeNewRCToFile(rcDetails, Control.JSS.RCCOMPDETAILS);
        View.RecruiterUI.displayCompany();
    }

    //code in progress
    public static void removeListing()
            throws IOException, FileNotFoundException, ParseException
    {
        Control.FileIO file = new Control.FileIO(Control.JSS.RCCOMPDETAILS);

        String[] numJob = file.readFile("\n").split("\n");

        for (int i = 0; i < numJob.length; i++)
        {
            String[] details = numJob[i].split(",");
            if (details[0].equals(Control.LogInCtrl.getRcUsername()))
            {

            }
        }
    }

    public static void writeNewRCToFile(String infoToWrite, String fileName)
            throws IOException
    {
        Control.FileIO fName = new Control.FileIO(fileName);
        fName.appendFile(infoToWrite);
    }
}
