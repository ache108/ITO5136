package Control;

import Model.JobListing;
import View.Input;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class JobListingCtrl {

    public ArrayList<Model.JobListing> jobList;

    public static void addNewJob(String jobId, String jobTitle, String jobCategory, String jobLocation, String jobHours, String jobPay, ArrayList<String> jobSkills, String jobDescription, Date appDeadline, boolean jobAd)
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

    /*
    //parse recruiter's jobs in joblistings.csv into 2d array

    public String[][] parseJobDetails(int rcID)
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
    }*/

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
            ArrayList<String> skillList = new ArrayList<String>();

            jl.jobId = details[0];
            jl.jobTitle = details[1];
            jl.jobCategory = details[2];
            jl.jobLocation = details[3];
            jl.jobHours = details[4];
            jl.jobPay = details[5];
            for (int j = 6; j < details.length - 3; j++)
            {
                skillList.add(details[j]);
            }
            jl.jobSkills = skillList;
            jl.jobDescription = details[details.length - 3];
            jl.appDeadline = dateFormat.parse(details[details.length - 2]);
            jl.jobAd = Boolean.parseBoolean(details[details.length - 1]);
            //}

            jobList.add(new JobListing(jl.jobId, jl.jobTitle, jl.jobCategory, jl.jobLocation, jl.jobHours, jl.jobPay, jl.jobSkills, jl.jobDescription, jl.appDeadline, jl.jobAd));

        }

        return jobList;
    }

    //Take ArrayList and print out abbreviated job list
    public void printJobList(ArrayList<Model.JobListing> jobList)
            throws IOException, FileNotFoundException, ParseException
    {
        SimpleDateFormat dateShortFormat = new SimpleDateFormat("dd-MMM-yyyy");
        System.out.println("--------------------------------");
        for (int i = 0; i < jobList.size(); i++)
        {
            System.out.println("Job " + (i+1) + ": ");
            System.out.println(jobList.get(i).getJobTitle());
            System.out.println("Application deadline: " + dateShortFormat.format(jobList.get(i).getAppDeadline()));
            System.out.println("Advertise: " + jobList.get(i).labelJobAd());
            System.out.println("Job skills are: ");
            for (int j = 0; j < jobList.get(i).getJobSkills().size(); j++)
            {
                String skill = jobList.get(i).getJobSkills().get(j);
                if (skill.charAt(0) == '[') {
                    skill = skill.substring(1);
                } else if (skill.charAt(0) == ' ') {
                    skill = skill.substring(1);
                }
                if (skill.charAt(skill.length() - 1) == ']') {
                    skill = skill.substring(0, skill.length() - 1);
                }
                System.out.println("- " + skill);
            }
            System.out.println("--------------------------------");
        }

    }

    public static void writeNewJobToFile(String infoToWrite, String fileName)
            throws IOException
    {
        FileIO fName = new FileIO(fileName);
        fName.appendFile(infoToWrite);
    }

}
