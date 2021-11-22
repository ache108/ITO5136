package Model;

public class Recruiter extends User {
    private String company;

    public Recruiter()
    {
        // No default recruiters
    }

    public Recruiter(String rcCompany)
    {
        // Do I need to add all other user attributes here too?
        company = rcCompany;
    }

    // adding these as we normally need display methods but will need to update depending on what we want to show
    public void displayCompany()
    {
        System.out.println("Works for " + this.company);
    }

    public String getCompany()
    {
        return this.company;
    }

    public void setCompany(String newCompany)
    {
        company = newCompany;
    }
}
