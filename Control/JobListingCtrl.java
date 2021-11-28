package Control;

import Model.JobListing;
import View.Input;
import Control.FileIO;
import View.JobListingUI;
import View.RecruiterUI;
import Control.MatchingCtrl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Objects;

import Control.RecruiterCtrl.*;

public class JobListingCtrl {

    private ArrayList<Model.JobListing> jobList;
    SimpleDateFormat dateShortFormat = new SimpleDateFormat("dd-MMM-yyyy");

    public JobListingCtrl()
    {
        try
        {
            this.jobList = parseFromCSV();
        }
        catch(Exception e)
        {
            this.jobList = new ArrayList<>();
        }
    }

    public static void addNewJob(String jobRC, String jobId, String jobTitle, String jobCategory, String jobLocation, String jobHours, String jobPay, ArrayList<String> jobSkills, String jobDescription, Date appDeadline, boolean jobAd)
            throws IOException, FileNotFoundException
    {
        // new job is created
        //Model.JobListing newJob = new Model.JobListing(jobRC, jobId, jobTitle, jobCategory, jobLocation, jobHours, jobPay, jobSkills, jobDescription, appDeadline, jobAd );

        // write inputs to file now or pass and save them all in a single turn?
        String jobDetails = Control.LogInCtrl.getRcUsername() + "," + jobId + "," + jobTitle + "," + jobCategory + "," + jobLocation + "," + jobHours + "," + jobPay + "," + jobSkills + "," + jobDescription + "," + appDeadline + "," + jobAd;
        writeNewLineToFile(jobDetails, Control.JSS.JSSJOBLIST);

    }

    public void removeOldJob(Model.JobListing jl) throws IOException {
        FileIO file = new FileIO(JSS.JSSJOBLIST);
        String[] list = file.readFile("\n").split("\n");

        String id = jl.getJobId();


        for (int i = 0; i < list.length; i++)
        {
            String[] details = list[i].split(",");
            if (id.equals(details[1]))
            {
                file.removeLine(list[i]);
                break;
            }
        }

    }

    //Options when editing job listing
    public void editJobListing(Model.JobListing jl) throws IOException, ParseException {
        View.JobListingUI jlu = new View.JobListingUI();
        View.Input input = new View.Input();
        FileIO file = new FileIO(Control.JSS.JSSJOBLIST);
        FileIO file2 = new FileIO(JSS.JSSJOBCATEGORY);
        String[] list = file2.readFile("\n").split("\n");

        String newString = "";
        Date newDate;
        int detailNo = JobListingUI.editJobOptions();
        switch (detailNo)
        {
            case 1:
                //edit job title
                System.out.println("Current job title: " + jl.getJobTitle());
                newString = input.acceptString("Please enter new job title: ");
                jl.setJobTitle(newString);
                break;
            case 2:
                //edit job category
                System.out.println("Current job category: " + jl.getJobCategory());
                jlu.displayJobCategories();
                newString = jlu.returnJobCategory(input.acceptInt("Please select new job category: ", 1, list.length));
                if (newString.equals("Other"))
                {
                    jl.setJobCategory(jlu.addJobCategory());
                } else {
                    jl.setJobCategory(newString);
                }
                break;
            case 3:
                //edit job location
                System.out.println("Current job location: " + jl.getJobLocation());
                newString = input.acceptString("Please enter new job location: ");
                jl.setJobLocation(newString);
                break;
            case 4:
                //edit job hours
                System.out.println("Current job hours: " + jl.getJobHours());
                newString = input.acceptString("Please enter new job hours: ");
                jl.setJobHours(newString);
                break;
            case 5:
                //edit job pay
                System.out.println("Current job compensation: " + jl.getJobPay());
                newString = input.acceptString("Please enter new job compensation: ");
                jl.setJobPay(newString);
                break;
            /*case 6:
                //edit job skills
                System.out.println("Current job title: " + jl.getJobSkills());
                newString = input.acceptString("Please enter new job skills: ");
                jl.setJobSkills(newString);//NEED TO WORK ON THIS*/
            case 7:
                //edit job description
                System.out.println("Current job description: " + jl.getJobDescription());
                newString = input.acceptString("Please enter new job description: ");
                jl.setJobDescription(newString);
                break;
            case 8:
                //edit application deadline
                System.out.println("Current job application deadline: " + jl.getAppDeadline());
                newDate = input.acceptDate("Please enter new application deadline: ");
                jl.setAppDeadline(newDate);
                break;
            case 9:
                //edit advertisement status
                System.out.println("Current job advertisement status: " + jl.getJobAd());
                boolean isAdvertised = View.JobListingUI.advertiseJob();
                jl.setJobAd(isAdvertised);
                break;
            case 0:
                //Go back
                manageJobListing(jl);
        }
        removeOldJob(jl);
        System.out.println("LET APPEND EDITED JOB");
        addNewJob(Control.LogInCtrl.getRcUsername(), jl.getJobId(), jl.getJobTitle(), jl.getJobCategory(), jl.getJobLocation(), jl.getJobHours(), jl.getJobPay(), jl.getJobSkills(), jl.getJobDescription(), jl.getAppDeadline(), jl.getJobAd());
        editJobListing(jl);
    }

    //Recursive method to filter out job listings that are not by the current logged in RC.
    public ArrayList<Model.JobListing> filterRCJob(ArrayList<Model.JobListing> jobList, String username)
    {
        for (int i = 0; i < jobList.size(); i++)
        {
            if (!jobList.get(i).getJobRC().equals(username))
            {
                jobList.remove(i);
                filterRCJob(jobList,username);
            } else {
                continue;
            }
        }
        return jobList;
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

    //Direct to each functionality related to job listing management
    public void manageJobListing(Model.JobListing jl) throws IOException, ParseException {
        JobListingUI jlu = new JobListingUI();
        int choice = JobListingUI.manageJobOptions();
        switch (choice)
        {
            case 1:
                //edit listing
                editJobListing(jl);
            case 2:
                //view applications
            case 3:
                //invite candidates
            case 0:
                //go back
                jlu.displayJobList();
        }
    }

    //Generate matching scores for all job listings and call method to sort them
    public void matchJobs(JobListing reqs)
    {
        MatchingCtrl mc = new MatchingCtrl();
        for(int i = 0; i < jobList.size(); i++)
        {
            if(jobList.get(i).getJobAd() == false)
                continue;
            if(mc.isMatch(jobList.get(i).getJobTitle(), reqs.getJobTitle()))
                jobList.get(i).incrementMatchingScore(1);
            if(mc.isMatch(jobList.get(i).getJobCategory(), reqs.getJobCategory()))
                jobList.get(i).incrementMatchingScore(1);
            if(mc.isMatch(jobList.get(i).getJobLocation(), reqs.getJobLocation()))
                jobList.get(i).incrementMatchingScore(1);
            if(mc.isMatch(jobList.get(i).getJobHours(), reqs.getJobHours()))
                jobList.get(i).incrementMatchingScore(1);
            if(mc.isMatch(jobList.get(i).getJobPay(), reqs.getJobPay()))
                jobList.get(i).incrementMatchingScore(1);
            if(mc.isMatch(jobList.get(i).getJobDescription(), reqs.getJobDescription()))
                jobList.get(i).incrementMatchingScore(1);

            ArrayList<String> jSkills = jobList.get(i).getJobSkills();
            for(int j = 0; j < jSkills.size(); j++)
            {
                ArrayList<String> reqSkills = reqs.getJobSkills();
                for(int k = 0; k < reqSkills.size(); k++)
                {
                    if(mc.isMatch(jSkills.get(j), reqSkills.get(k)))
                        jobList.get(i).incrementMatchingScore(1);
                }
            }
        }

        sortJobs();
    }

    /*
    //parse recruiter's jobs in joblistings.csv into 2d array

    public String[][] parseJobDetails(int rcID)
            throws IOException
    {
        FileIO file = new FileIO(JSS.JSSJOBLIST);

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
    public ArrayList<JobListing> parseFromCSV()
            throws IOException, FileNotFoundException, ParseException
    {
        FileIO file = new FileIO(Control.JSS.JSSJOBLIST);
        Model.JobListing jl = new Model.JobListing();
        jobList = new ArrayList<Model.JobListing>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");

        String[] numJob = file.readFile("\n").split("\n");

        for (int i = 0; i < numJob.length; i++) {

            String[] details = numJob[i].split(",");
            ArrayList<String> skillList = new ArrayList<String>();
            jl.setJobRC(details[0]);
            jl.setJobId(details[1]);
            jl.setJobTitle(details[2]);
            jl.setJobCategory(details[3]);
            jl.setJobLocation(details[4]);
            jl.setJobHours(details[5]);
            jl.setJobPay(details[6]);
            for (int j = 7; j < details.length - 3; j++)
            {
                skillList.add(details[j]);
            }
            jl.setJobSkills(skillList);
            jl.setJobDescription(details[details.length - 3]);
            jl.setAppDeadline(dateFormat.parse(details[details.length - 2]));
            jl.setJobAd(Boolean.parseBoolean(details[details.length - 1]));

            jobList.add(new JobListing(jl.getJobRC(), jl.getJobId(), jl.getJobTitle(), jl.getJobCategory(), jl.getJobLocation(), jl.getJobHours(), jl.getJobPay(), jl.getJobSkills(), jl.getJobDescription(), jl.getAppDeadline(), jl.getJobAd()));

        }

        return jobList;
    }

    //Take ArrayList and print out abbreviated job list
    public void printJobList()
            throws IOException, FileNotFoundException, ParseException
    {
        filterRCJob(jobList, Control.LogInCtrl.getRcUsername());

        System.out.println("--------------------------------");
        for (int i = 0; i < jobList.size(); i++)
        {
            System.out.println("Job " + (i+1) + ": ");
            System.out.println("Matching Score: " + jobList.get(i).getMatchingScore());
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

        viewJobListing(jobList, View.JobListingUI.chooseJobListing(jobList.size()));

    }

    //sort jobs based on matching score (descending order)
    public void sortJobs()
    {
        JobListing temp;
        for(int i = 0; i < jobList.size() - 1; i++)
        {
            for(int j = jobList.size() - 1; j > i; j--)
            {
                if(jobList.get(j).isGreaterThan(jobList.get(j - 1)))
                {
                    temp = jobList.get(j);
                    jobList.set(j, jobList.get(j-1));
                    jobList.set(j-1, temp);
                }
            }
        }
    }

    //Print out all the details for the chosen job listing, or to go back.
    public void viewJobListing(ArrayList<JobListing> jobList, int jobNo) throws IOException, ParseException {
        if (jobNo == 0) {
            try {
                Control.RecruiterCtrl.runRCHome();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("-----------------------------------------");
            jobList.get(jobNo - 1).displayJobDetails();
            System.out.println("This listing is currently set to: " + jobList.get(jobNo - 1).labelJobAd());
            System.out.println("-----------------------------------------");
        }
        manageJobListing(jobList.get(jobNo - 1));
    }

    public static void writeNewLineToFile(String infoToWrite, String fileName)
            throws IOException
    {
        FileIO fName = new FileIO(fileName);
        fName.appendFile(infoToWrite);
    }

}
