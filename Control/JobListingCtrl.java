package Control;

import Model.JobListing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class JobListingCtrl {

    public ArrayList<Model.JobListing> jobList;

    public static void addNewJob(String jobId, String jobTitle, String jobCategory, String jobLocation, String jobHours, String jobPay, String jobSkills, String jobDescription, Date appDeadline, boolean jobAd)
            throws IOException, FileNotFoundException
    {
        // new job is created
        Model.JobListing newJob = new Model.JobListing(jobId, jobTitle, jobCategory, jobLocation, jobHours, jobPay, jobSkills, jobDescription, appDeadline, jobAd );

        // write inputs to file now or pass and save them all in a single turn?
        String jobDetails = jobId + "," + jobTitle + "," + jobCategory + "," + jobLocation + "," + jobHours + "," + jobPay + "," + jobSkills + "," + jobDescription + "," + appDeadline + "," + jobAd;
        writeNewJobToFile(jobDetails, "Files/jobListings.txt");

    }

    //Generate unique 8-digit ID to each job listing
    public static String generateJobID(String filename)
            throws IOException, FileNotFoundException
    {
        FileIO file = new FileIO(filename);

        String[] lines = file.readFile("\n").split("\n");
        int numJob = lines.length + 1;
        String jobID = String.format("%08d", numJob);

        return jobID;
    }

    //parse recruiter's jobs in joblistings.csv into 2d array
    public String[][] parseJobDetails(/*int rcID*/)
            throws IOException
    {
        FileIO file = new FileIO("Files/jobListings.txt");

        String[] jobList = file.readFile("\n").split("\n");
        int numJob = jobList.length;
        int numOfDetails = 10;

        String[][] jobArray = new String[numJob][numOfDetails];

        String[] tempJob = new String[numOfDetails];

        for (int i = 0; i < numJob; i++)
        {
            tempJob = jobList[i].split(",");
            for (int j = 0; j < numOfDetails; j++)
            {
                jobArray[i][j] = tempJob[j];
            }
        }

        return jobArray;
    }

    //Convert CSV to Array List of JL objects and return this Array List.
    //When rcID or rcUsername is identified, will filter out the objects called based on the specific rc.
    public ArrayList<JobListing> parseFromCSV(/*int rcID*/)
            throws IOException, FileNotFoundException, ParseException
    {
        FileIO file = new FileIO("Files/jobListings.txt");
        Model.JobListing jl = new Model.JobListing();
        ArrayList<Model.JobListing> jobList = new ArrayList<Model.JobListing>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");

        String[] numJob = file.readFile("\n").split("\n");

        for (int i = 0; i < numJob.length; i++) {
            //if (details[10] == rcID) {

            String[] details = numJob[i].split(",");

            jl.jobId = details[0];
            jl.jobTitle = details[1];
            jl.jobCategory = details[2];
            jl.jobLocation = details[3];
            jl.jobHours = details[4];
            jl.jobPay = details[5];
            jl.jobSkills = details[6];
            jl.jobDescription = details[7];
            jl.appDeadline = dateFormat.parse(details[8]);
            jl.jobAd = Boolean.parseBoolean(details[9]);
            //}

            jobList.add(new JobListing(jl.jobId, jl.jobTitle, jl.jobCategory, jl.jobLocation, jl.jobHours, jl.jobPay, jl.jobSkills, jl.jobDescription, jl.appDeadline, jl.jobAd));

        }

        return jobList;
    }

    //Take ArrayList and print out abbreviated job list
    public void printJobList(ArrayList<Model.JobListing> jobList)
            throws IOException, FileNotFoundException, ParseException
    {
        for (int i = 0; i < jobList.size(); i++)
        {
            System.out.println("Job " + (i+1) + ": ");
            System.out.println(jobList.get(i).getJobTitle());
            System.out.println("Application deadline: " + jobList.get(i).getAppDeadline());
            System.out.println("Advertise: " + jobList.get(i).labelJobAd() + "\n");
        }

    }

    public static void writeNewJobToFile(String infoToWrite, String fileName)
            throws IOException
    {
        FileIO fName = new FileIO(fileName);
        fName.appendFile(infoToWrite);
    }

}
