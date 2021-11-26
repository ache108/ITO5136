package Control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import View.UserUI;

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
        //for loop read file from bottom to top, breaks once username is found for the first time
        for (int i = numJob.length - 1; i >= 0; i--)
        {
            String[] details = numJob[i].split(",");
            if (details[0].equals(Control.LogInCtrl.getRcUsername())) //(display only profile for this user only)
            {
                usrCompany = details[1];
                usrCompAddress = details[2];
                usrCompEmail = details[3];
                usrCompPhone = details[4];
                usrCompDescr = details[5];
                break;
            }
        }

        int detailNo = View.RecruiterUI.editCompanyOptions();
        boolean verifiedInput = false;
        String msg = "\nPlease enter ";
        do {
            switch (detailNo)
            {
                case 1:
                    //edit company name
                    do {
                        usrCompany = input.acceptString(msg + "the company's name");
                        verifiedInput = View.UserUI.userVerifyInputs(usrCompany);
                    } while (!verifiedInput);
                    break;
                case 2:
                    //edit company address
                    do {
                        usrCompAddress = input.acceptString(msg + "the company's address");
                        verifiedInput = View.UserUI.userVerifyInputs(usrCompAddress);
                    } while (!verifiedInput);
                    break;
                case 3:
                    //edit company email
                    do {
                        usrCompEmail = input.acceptString(msg + "the company's email");
                        verifiedInput = View.UserUI.userVerifyInputs(usrCompEmail);
                    } while (!verifiedInput);
                    break;
                case 4:
                    //edit company phone number
                    do {
                        usrCompPhone = input.acceptString(msg + "the company's phone number");
                        verifiedInput = View.UserUI.userVerifyInputs(usrCompPhone);
                    } while (!verifiedInput);
                    break;
                case 5:
                    //edit company description
                    do {
                        usrCompDescr = input.acceptString(msg + "a brief description about the company");
                        verifiedInput = View.UserUI.userVerifyInputs(usrCompDescr);
                    } while (!verifiedInput);
                    break;
                case 0:
                    //Go back
                    Control.RecruiterCtrl.runRCHome();
                    break;
                default:
                    String rcHome = input.acceptString("Going back");
                    View.UserUI.userVerifyInputs(rcHome);
                    break;

            }
            String rcDetails = rcID + "," + usrCompany + "," + usrCompAddress+ "," + usrCompEmail + "," + usrCompPhone+ "," + usrCompDescr;
            //add updated listing, old listing stays
            writeNewRCToFile(rcDetails, Control.JSS.RCCOMPDETAILS);
            View.RecruiterUI.displayCompany();
        } while (!verifiedInput);
    }


    public static void writeNewRCToFile(String infoToWrite, String fileName)
            throws IOException
    {
        Control.FileIO fName = new Control.FileIO(fileName);
        fName.appendFile(infoToWrite);
    }
}
