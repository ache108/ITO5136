package Model;

import java.util.Date;

public class User
{
    public String userName;
    public String userEmail;
    public String firstName;
    public String lastName;
    public String city;
    public String state;
    public Date dateOfBirth;
    public String passwd;
    public boolean publicProfile;

    public User()
    {
        // we don't want to have a blank user being created with no information?
    }

    public User(String iptName, String iptEmail, String iptFirstName, String iptLastName, String iptCity, String iptState, Date iptDOB, boolean publicProfile)
    {
        userName = iptName;
        userEmail = iptEmail;
        firstName = iptFirstName;
        lastName = iptLastName;
                city = iptCity;
                state = iptState;
        dateOfBirth  = iptDOB;
        this.publicProfile = publicProfile;
    }

    public Date getDateOfBirth()
    {
        return this.dateOfBirth;
    }

    public String getCity() {return this.city;}

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public String getPasswd()
    {
        return this.passwd;
    }

    public String getState() {return this.state;}

    public String getUserEmail()
    {
        return this.userEmail;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public boolean getProfilePublic()
    {
        return this.publicProfile;
    }

    // Mutator Methods. All public so they can be accessed from JobSeeker and Recruiter Classes
    public void setDateOfBirth(Date iptDate)
    {
        dateOfBirth = iptDate;
    }

    public void setCity(String iptCity) {city = iptCity;}

    public void setFirstName(String iptFirstName)
    {
        firstName = iptFirstName;
    }

    public void setLastName(String iptLastName)
    {
        lastName = iptLastName;
    }

    public void setPasswd(String iptPass)
    {
        passwd = iptPass;
    }

    public void setState(String iptState) {state = iptState;}

    public void setUserEmail(String iptEmail)
    {
        userEmail = iptEmail;
    }

    public void setUserName(String iptUsrName)
    {
        userName = iptUsrName;
    }

    public void setProfilePublic(boolean iptProfile)
    {
        publicProfile = iptProfile;
    }
}