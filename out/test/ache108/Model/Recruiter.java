package Model;

public class Recruiter extends User {

    public Recruiter()
    {
        // No default recruiters
    }

    public Recruiter(Model.User newUser)
    {
        this.userName = newUser.userName;
        this.userEmail = newUser.userEmail;
        this.firstName = newUser.firstName;
        this.lastName = newUser.lastName;
        this.city = newUser.city;
        this.state = newUser.state;
        this.dateOfBirth  = newUser.dateOfBirth;
        this.publicProfile = newUser.publicProfile;
    }
    // all company variables on company model
    // all mutator and set methods are on the user model
}
