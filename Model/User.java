import java.util.Date;

public class User
{
    public int userId;
    public String userName;
    public String userEmail;
    public String firstName;
    public String lastName;
    public Date dateOfBirth;
    public String passwd;
    public boolean publicProfile;

    public User()
    {
        // we don't want to have a blank user being created with no information?
    }

    // userId comes from controller method and gets the last entry in file/db table
    // User is created with basic attributes and other are set as the user completes the fields?
    public User(int userId, String iptName, String iptEmail)
    {
        this.userId = userId;
        userName = iptName;
        userEmail = iptEmail;
    }
    // Get attributes & all public so we can access from Job Seeker and Recruiter Classes

    public Date getDateOfBirth()
    {
        return this.dateOfBirth;
    }

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

    public String getUserEmail()
    {
        return this.userEmail;
    }

    public int getUserId()
    {
        return this.userId;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public boolean getProfilePublic()
    {
        return this.publicProfile;
    }

    // set Methods. All public so they can be accessed from JobSeeker and Recruiter Classes
    public void setDateOfBirth(Date iptDate)
    {
        dateOfBirth = iptDate;
    }

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

    public void setUserEmail(String iptEmail)
    {
        userEmail = iptEmail;
    }

    // unlikely to need this to reset user id & only use with caution
    public void setUserId(int iptUserId)
    {
        userId = iptUserId;
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