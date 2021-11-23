package Model;

import java.util.Date;

public class CompanyListing {

    public String rcID;
    public String usrCompany;
    public String usrCompAddress;
    public String usrCompEmail;
    public String usrCompPhone;
    public String usrCompDescr;

    public CompanyListing()
    {
        //Probably don't want a blank rc
    }
    public CompanyListing(String rcID, String usrCompany, String usrCompAddress, String usrCompEmail, String usrCompPhone, String usrCompDescr)
    {
        rcID = this.rcID;
        usrCompany = this.usrCompany;
        usrCompAddress = this.usrCompAddress;
        usrCompEmail = this.usrCompEmail;
        usrCompPhone = this.usrCompPhone;
        usrCompDescr = this.usrCompDescr;

    }

    public String display()
    {
        return usrCompany + "," + usrCompAddress + "," + usrCompEmail + "," + usrCompPhone + "," + usrCompDescr;
    }

    // Get methods

    public String getRCID() { return this.rcID; }

    public String getCompany() { return this.usrCompany; }

    public String getCompAddress() { return this.usrCompAddress; }

    public String getCompEmail() { return this.usrCompEmail; }

    public String getCompPhone() { return this.usrCompPhone; }

    public String getCompDescr() { return this.usrCompDescr; }


    //Mutator methods

    public void setRCID(String rcID) { rcID = this.rcID; }

    public void setCompany(String usrCompany) { usrCompany = this.usrCompany; }

    public void setCompAddress(String usrCompAddress) { usrCompAddress = this.usrCompAddress; }

    public void setCompEmail(String usrCompEmail) {usrCompEmail = this.usrCompEmail; }

    public void setCompPhone(String usrCompPhone) { usrCompPhone = this.usrCompPhone; }

    public void setCompDescr(String usrCompDescr) { usrCompDescr = this.usrCompDescr;}

}
