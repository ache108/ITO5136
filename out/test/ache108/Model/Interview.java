package Model;

import java.time.LocalDate;
import java.util.Date;

public class Interview {

    private Date interviewDate;
    private String interviewLocation;
    private LocalDate interviewInviteResponseDate;
    private String jobId;
    private String jobSeekerUserName;
    private String recruiterUserName;
    private String interviewStatus;

    public Interview()
    {
        // no default constructor
    }

    public Interview(Date interviewDate, String officeLocation, LocalDate interviewInviteResponseDate,  String jobId, String jobSeekerUserName, String recruiterUserName, String status)
    {
        this.interviewDate = interviewDate;
        interviewLocation = officeLocation;
        this.interviewInviteResponseDate = interviewInviteResponseDate ;
        this.jobId = jobId;
        this.jobSeekerUserName = jobSeekerUserName;
        this.recruiterUserName = recruiterUserName;
        this.interviewStatus = status;
    }

    public Date getInterviewDate()
    {
        return this.interviewDate;
    }

    public String getInterviewLocation()
    {
        return this.interviewLocation;
    }

    public String getInterviewJobId()
    {
        return this.jobId;
    }

    public String getInterviewJobSeekerUserName()
    {
        return this.jobSeekerUserName;
    }

    public String getInterviewRecruiter()
    {
        return this.recruiterUserName;
    }

    public LocalDate getInterviewInviteResponseDate()
    {
        return this.interviewInviteResponseDate;
    }

    public String getInterviewStatus(){ return this.interviewStatus; }

    public void setInterviewDate(Date newDate)
    {
        this.interviewDate = newDate;
    }

    // unlikely to be updated unless new office
    public void setInterviewLocation(String newLocation)
    {
        this.interviewLocation = newLocation;
    }

    // DANGEROUS! Will reassign interviews to new Job ID... USE WITH CAUTION
    public void setInterviewJobId(String newJobID)
    {
        this.jobId = newJobID;
    }

    // DANGEROUS! Will reassign interviews to new User ID... USE WITH CAUTION
    public void setInterviewJobSeekerUserName(String newUserName)
    {
        this.jobSeekerUserName = newUserName;
    }

    // DANGEROUS! Will reassign interviews to new Recruiter... USE WITH CAUTION
    public void setInterviewRecruiter(String newRecruiter)
    {
        this.recruiterUserName = newRecruiter;
    }

    public void setInterviewInviteResponseDate(LocalDate newDate)
    {
        this.interviewInviteResponseDate = newDate;
    }

    public void setInterviewStatus(String newStatus) {this.interviewStatus = newStatus; }
}
