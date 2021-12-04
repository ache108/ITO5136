package Control;

import java.io.IOException;
import java.text.ParseException;

public class JSS
{
    //all filenames as public constants
    public static final String JSLOGIN = "Files/jsLoginCredentials.txt";
    public static final String RCLOGIN = "Files/rcLoginCredentials.txt";
    public static final String JSDETAILS = "Files/jsUserDetails.txt";
    public static final String RCDETAILS = "Files/rcUserDetails.txt";
    public static final String RCCOMPDETAILS = "Files/rcCompanyDetails.txt";
    public static final String JSSJOBLIST = "Files/jobListings.txt";
    public static final String JSSJOBCATEGORY = "Files/jobCategories.txt";
    public static final String JOBAPPLICATIONS = "Files/jobApplications.txt";
    public static final String JOBINTERVIEWS = "Files/jobInterviews.txt";


    public static void main(String[] args) throws IOException, ParseException {
        JSS jss = new JSS();
        jss.start();
    }

    //Initialise any classes/files, direct to log-in controller to begin
    public void start() throws IOException, ParseException {
        //Code to verify that all db files are available ???
        //Code to activate any objects that need to be active at the start of the program

        LogInCtrl lic = new LogInCtrl();
        lic.start();

        /*//FOR TESTING MATCHING SCORES AND SORT
        Control.JobListingCtrl jlc = new Control.JobListingCtrl();
        JobListing reqs = new JobListing("me", "0000001", "Software Engineer", "Management", "Perth", "Full Time", "80000", new ArrayList<String>(), "We are looking to hire someone!", new Date(), true);
        for(JobListing jl: jlc.jobList)
        {
            System.out.println(jl.getJobId() + " " + jl.getMatchingScore());
        }
        jlc.matchJobs(reqs);
        for(JobListing jl: jlc.jobList)
        {
            System.out.println(jl.getJobId() + " " + jl.getMatchingScore());
        }
        */

        //Any code to close program (close files etc.)
    }
}