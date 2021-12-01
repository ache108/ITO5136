package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import Model.JobSeeker;
import Model.JobListing;

public class JobApplication {

    private String jobId;
    private String jobSeekerUserName;
    private String recruiterUserName;
    private Date applicationDate;
    private boolean applicationActive;

    public JobApplication()
    {
        // no default job Application
    }

    public JobApplication(String jobId, String jobSeekerUserName, String recruiterUserName, Date applicationDate)
    {
        this.jobId = jobId;
        this.jobSeekerUserName = jobSeekerUserName;
        this.recruiterUserName = recruiterUserName;
        this.applicationDate = applicationDate;
        this.applicationActive = true;
    }

    public boolean getJobApplicationActive() { return this.applicationActive; }

    public Date getJobApplicationAppDate()
    {
        return this.applicationDate;
    }

    public String getJobApplicationJobId()
    {
        return this.jobId;
    }

    public String getJobApplicationJSUserName()
    {
        return this.jobSeekerUserName;
    }

    public String getRecruiterUserName()
    {
        return this.recruiterUserName;
    }

    public void setJobApplicationInactive(boolean removeApplication) {this.applicationActive = removeApplication ; }

    // Dangerous & Use with caution. Will reassign job application submitted date.
    public void setJobApplicationAppDate(Date newDate)
    {
        this.applicationDate = newDate;
    }

    // Dangerous & Use with caution. Will reassign job ids in application to new job. Only use if you intend to remap applications to another Job id.
    public void setJobApplicationJobListingId(String newJobId)
    {
        this.jobId = newJobId;
    }

    // Dangerous & Use with caution. Will reassign jobs applications to new user. Only use if you intend to remap job applications to another user
    public void setJobApplicationJobSeekerId(String jsUserName)
    {
        this.jobSeekerUserName = jsUserName;
    }

    // Dangerous & Use with caution. Will reassign jobs applications to new recruiter. Only use if you intend to remap job applications to another recruiter
    public void setJobApplicationRecruiterId(String rcId)
    {
        this.recruiterUserName = rcId;
    }

}
