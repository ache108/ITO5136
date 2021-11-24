package Model;
import java.util.ArrayList;

public class JobSeeker extends User {
    public double hourlyWageRate;
    public String wrkType;
    public String residencyType;
    public ArrayList<String> skillsList;

    public JobSeeker() {
        // we need attributes to actually create a jobSeeker Profile
    }

    public JobSeeker(Model.User newUser, double iptRate, String iptWrkType, String iptResidency, ArrayList<String> iptSkills) {
        this.userName = newUser.userName;
        this.userEmail = newUser.userEmail;
        this.firstName = newUser.firstName;
        this.lastName = newUser.lastName;
        this.city = newUser.city;
        this.state = newUser.state;
        this.dateOfBirth  = newUser.dateOfBirth;
        this.publicProfile = newUser.publicProfile;
        this.hourlyWageRate = iptRate;
        this.wrkType = iptWrkType;
        this.residencyType = iptResidency;
        this.skillsList = iptSkills;
    }

    public void addSkillToList(String newSkill)
    {
        this.skillsList.add(newSkill);
    }

    public void displayAllSkills()
    {
        System.out.println("Skills List currently includes: ");
        for (String skill : this.skillsList)
        {
            System.out.println(skill);
        }
    }

    public double getHourlyWageRate()
    {
        return this.hourlyWageRate;
    }

    public int getIndexSkillFromList(String skillToIndex)
    {
        return this.skillsList.indexOf(skillToIndex);
    }

    public String getResidency()
    {
        return this.residencyType;
    }

    public ArrayList<String> getSkillList()
    {
        return this.skillsList;
    }

    public String getSkillFromList(int index)
    {
        return this.skillsList.get(index);
    }

    public String getWorkType()
    {
        return this.wrkType;
    }

    public void removeSkillFromList(int index)
    {
        this.skillsList.remove(index);
    }

    public void setHourlyWageRate(double iptRate)
    {
        hourlyWageRate = iptRate;
    }

    public void setResidencyType(String iptResidency)
    {
        residencyType = iptResidency;
    }

    public void setSkillsList(ArrayList<String> iptSkillsList)
    {
        skillsList = iptSkillsList;
    }

    public void setWrkType(String iptWrkType)
    {
        wrkType = iptWrkType;
    }
}
