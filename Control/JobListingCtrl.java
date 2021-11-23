package Control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public class JobListingCtrl {

    public static void addNewJob(String jobTitle, String jobCategory, String jobLocation, String jobHours, String jobPay, String jobSkills, String jobDescription, Date appDeadline, boolean jobAd)
            throws IOException, FileNotFoundException
    {
        // new job is created
        Model.JobListing newJob = new Model.JobListing(jobTitle, jobCategory, jobLocation, jobHours, jobPay, jobSkills, jobDescription, appDeadline, jobAd );

        // write inputs to file now or pass and save them all in a single turn?
        String jobDetails = generateJobID("Files/jobListings.txt") + "," + jobTitle + "," + jobCategory + "," + jobLocation + "," + jobHours + "," + jobPay + "," + jobSkills + "," + jobDescription + "," + appDeadline + "," + jobAd;
        writeNewJobToFile(jobDetails, "Files/jobListings.txt");

    }

    //Generate unique 8-digit ID to each job listing
    private static String generateJobID(String filename)
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

    public static void writeNewJobToFile(String infoToWrite, String fileName)
            throws IOException
    {
        FileIO fName = new FileIO(fileName);
        fName.appendFile(infoToWrite);
    }

}
