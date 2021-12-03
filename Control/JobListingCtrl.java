package Control;

import Model.JobSeeker;
import Model.JobListing;
import View.Input;
import View.JobListingUI;
import View.JobSeekerUI;
import Control.MatchingCtrl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JobListingCtrl {

    private ArrayList<Model.JobListing> jobList;
    private Model.JobListing req;
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
        req = new Model.JobListing();
    }

    //Method to bring together job listing details to write to txt file
    public static void addNewJob(String jobRC, String jobId, String jobTitle, String jobCategory, String jobLocation, String jobHours, String jobPay, ArrayList<String> jobSkills, String jobDescription, Date appDeadline, boolean jobAd)
            throws IOException, FileNotFoundException
    {
        String jobDetails = Control.LogInCtrl.getRcUsername() + "," + jobId + "," + jobTitle + "," + jobCategory + "," + jobLocation + "," + jobHours + "," + jobPay + "," + jobSkills + "," + jobDescription + "," + appDeadline + "," + jobAd;
        writeNewLineToFile(jobDetails, Control.JSS.JSSJOBLIST);

    }

    //Options when editing job listing

    public void editJobListing(Model.JobListing jl) throws IOException, ParseException {
        View.JobListingUI jlu = new View.JobListingUI();
        View.Input input = new View.Input();
        FileIO file = new FileIO(Control.JSS.JSSJOBLIST);
        FileIO file2 = new FileIO(Control.JSS.JSSJOBCATEGORY);
        String[] list = file2.readFile("\n").split("\n");

        System.out.println("            EDIT JOB LISTING\n"
            + "--------------------------------------------\n");
        jl.displayJobDetails();
        System.out.println("--------------------------------------------\n");
        String newString = "";
        Date newDate;
        int detailNo = JobListingUI.editJobOptions();
        switch (detailNo)
        {
            case 1:
                //edit job title
                System.out.println("\nCurrent job title: " + jl.getJobTitle());
                newString = input.acceptString("Please enter new job title: ");
                jl.setJobTitle(newString);
                break;
            case 2:
                //edit job category
                System.out.println("\nCurrent job category: " + jl.getJobCategory());
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
                System.out.println("\nCurrent job location: " + jl.getJobLocation());
                newString = input.acceptString("Please enter new job location: ");
                jl.setJobLocation(newString);
                break;
            case 4:
                //edit job hours
                System.out.println("\nCurrent job hours: " + jl.getJobHours());
                newString = input.acceptString("Please enter new job hours: ");
                jl.setJobHours(newString);
                break;
            case 5:
                //edit job pay
                System.out.println("\nCurrent job compensation: " + jl.getJobPay());
                newString = input.acceptString("Please enter new job compensation: ");
                jl.setJobPay(newString);
                break;
            case 6:
                //edit job skills
                ArrayList<String> newSkills;
                ArrayList<String> origSkills = jl.getJobSkills();
                System.out.println("\nCurrent saved job skills are: ");
                //jl.displayJobSkills();

                //the following switch case is to allow RC to add or modify a skill
                int proceedNo = jlu.openSkillMenu();
                switch (proceedNo)
                {
                    case 1:
                        //add new skill to jl
                        newSkills = jlu.inputJobListingSkill();
                        for (int j = 0; j < newSkills.size(); j++)
                        {
                            origSkills.add(newSkills.get(j));
                        }
                        break;
                    case 2:
                        //modify skill
                        origSkills = editJobSkill(jl, origSkills, origSkills.get(jlu.getSkillNo(origSkills.size()) - 1));
                        break;
                    case 0:
                        //go back
                        editJobListing(jl);
                }
                jl.setJobSkills(origSkills);
                break;
            case 7:
                //edit job description
                System.out.println("\nCurrent job description: " + jl.getJobDescription());
                newString = input.acceptString("Please enter new job description: ");
                jl.setJobDescription(newString);
                break;
            case 8:
                //edit application deadline
                System.out.println("\nCurrent job application deadline: " + jl.getAppDeadline());
                newDate = input.acceptDate("Please enter new application deadline: ");
                jl.setAppDeadline(newDate);
                break;
            case 9:
                //edit advertisement status
                //System.out.println("\nCurrent job advertisement status: " + jl.labelJobAd(jl.getJobAd()));
                boolean isAdvertised = View.JobListingUI.advertiseJob();
                jl.setJobAd(isAdvertised);
                break;
            case 0:
                //Go back
                manageJobListing(jl);
        }
        removeOldJob(jl);
        addNewJob(Control.LogInCtrl.getRcUsername(), jl.getJobId(), jl.getJobTitle(), jl.getJobCategory(), jl.getJobLocation(), jl.getJobHours(), jl.getJobPay(), jl.getJobSkills(), jl.getJobDescription(), jl.getAppDeadline(), jl.getJobAd());
        editJobListing(jl);
    }

    //Method to allow RC to change and replace an existing skill for the job listing, or to delete an existing skill.
    public ArrayList<String> editJobSkill(Model.JobListing jl, ArrayList<String> jobSkills, String skill) throws IOException, ParseException {
        Input input = new Input();
        View.JobListingUI jlu = new JobListingUI();
        int optNo = JobListingUI.modifySkillOptions();
        switch (optNo)
        {
            case 1:
                //edit the skill
                System.out.println("\nThe skill provided previously was: " + skill.replace('[', ' ').replace(']', ' ').trim() + ".");
                String newSkill = input.acceptString("Please input the new skill that you want to replace this skill with:");
                jobSkills.remove(skill);
                jobSkills.add(newSkill);
                System.out.println("\nCurrent saved job skills are: ");
                //jl.displayJobSkills();
                break;
            case 2:
                //delete the skill
                System.out.println("\nThe skill provided previously was: " + skill.replace('[', ' ').replace(']', ' ').trim() + ".");
                boolean deleteSkill = true;
                boolean charInputCheck = false;
                char userRepsonse = input.acceptChar("Please enter y to delete skill; or n to go back");
                do {
                    if (userRepsonse == 'y') {
                        deleteSkill = true;
                    } else if (userRepsonse == 'n') {
                        deleteSkill = false;
                    } else {
                        System.out.println("Please enter y or n!");
                        charInputCheck = true;
                    }
                } while (charInputCheck);
                if (deleteSkill)
                {
                    jobSkills.remove(skill);
                    System.out.println("\nCurrent job skills are: ");
                    //jl.displayJobSkills();
                    break;
                } else {
                    editJobSkill(jl, jobSkills, skill);
                }
                break;
            case 0:
                //go back
                editJobListing(jl);
        }

        return jobSkills;
    }

    public static Model.JobListing filterSpecificJobListingByID(String jobID)
    {
        Control.JobListingCtrl jlc = new Control.JobListingCtrl();
        int numJobListings = jlc.jobList.size();
        Model.JobListing jl = new Model.JobListing();
        for (int i = 0; i < numJobListings; i++)
        {
            Model.JobListing jlCheck = jlc.jobList.get(i);
            String jobListingId = jlCheck.getJobId();
            if(jobListingId.equals(jobID))
                jl = jlCheck;
        }
        return jl;
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

    //Filters out the job search results based on matching scores
    public void filterJobSearch(ArrayList<Model.JobListing> jobList)
    {
        for (int i = 0; i < jobList.size(); i++)
        {
            //System.out.println("THIS JOB MATCHING SCORE IS " + jobList.get(i).getMatchingScore());
            if (jobList.get(i).getMatchingScore() < 1)
            {
                jobList.remove(jobList.get(i));
            }
        }

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

    public void generateSearchResults(ArrayList<Model.JobListing> jobList) throws IOException, ParseException {

        if (jobList.size() > 0) {
            int num = viewJobListingJS(jobList, View.JobListingUI.chooseJobListing(jobList.size()));
            openJobListing(jobList.get(num - 1));
        } else {
            Input input = new Input();
            System.out.println("Your search returned no result.");
            int proceed = input.acceptInt("Please press 0 to return to homepage.", 0, 0);
            if (proceed == 0)
            {
                JobSeekerCtrl.runJSHome();
            }
        }
    }

    //Method to link jl to job app
    public void linkJobApp(ArrayList<Model.JobListing> jobList) throws IOException, ParseException {
        JobApplicationCtrl jac = new JobApplicationCtrl();
        printListJobRC(jobList);
        JobApplicationCtrl.viewRCSpecificApplication(jobList, JobApplicationCtrl.parseJobApplicationTextFile("RC"));
    }

    //Direct to each functionality related to job listing management
    public void manageJobListing(Model.JobListing jl) throws IOException, ParseException {
        JobListingUI jlu = new JobListingUI();
        System.out.println("\n             MANAGE JOB LISTING\n"
            + "--------------------------------------------");
        jl.displayJobDetails();
        int choice = JobListingUI.manageJobOptions();
        switch (choice)
        {
            case 1:
                //edit listing
                editJobListing(jl);
                break;
            case 2:
                //invite candidates
                MatchingCtrl mc = new MatchingCtrl();
                ArrayList<Model.JobSeeker> js = mc.matchJobSeekers(jl);
                int usrIn = jlu.selectJobSeeker(js);
                if(usrIn == 0)
                    manageJobListing(jl);
                else
                    System.out.println(usrIn);
                break;
            case 3:
                //delete job listing
                removeOldJob(jl);
                viewJLFromRC();
                break;
            case 0:
                //go back
                viewJLFromRC();
                break;
        }
    }

    //Generate matching scores for all job listings, filters out private jobs and
    //jobs that do not match job title search criteria, and call method to sort them
    public ArrayList<Model.JobListing> matchJobs(ArrayList<Model.JobListing> jobList, JobListing req) throws IOException, ParseException {
        MatchingCtrl mc = new MatchingCtrl();
        Model.JobSeeker js = Control.JobSeekerCtrl.getCurrentJobSeeker();

        //Remove all private jobs from joblist first.
        ArrayList<Model.JobListing> publicJobList = removePrivateJobs(jobList);
        ArrayList<Model.JobListing> updatedJobList = removeDiffJobTitle(publicJobList, req);

        //Hard filter: Remove all jobs that do not contain words from job title search input
        for(int i = 0; i < jobList.size(); i++) {
            updatedJobList.get(i).incrementMatchingScore(1);
        }

        //Matching job category, location, hours, and pay with user search criteria
        for (int i = 0; i < updatedJobList.size(); i++) {
            if(updatedJobList.get(i).getJobCategory().equals(req.getJobCategory())) {
                updatedJobList.get(i).incrementMatchingScore(1);
            }
            if(mc.isMatch(updatedJobList.get(i).getJobLocation(), req.getJobLocation()) && (!req.getJobLocation().isBlank())) {
                updatedJobList.get(i).incrementMatchingScore(1);
            }
            if(mc.isMatch(updatedJobList.get(i).getJobHours(), req.getJobHours()) && (!req.getJobLocation().isBlank())) {
                updatedJobList.get(i).incrementMatchingScore(1);
            }
            if(mc.isMatch(updatedJobList.get(i).getJobPay(), req.getJobPay()) && (!req.getJobLocation().isBlank())) {
                updatedJobList.get(i).incrementMatchingScore(1);
            }

            //Matching user skills and job listing
            ArrayList<String> list1 = updatedJobList.get(i).getJobSkills();
            ArrayList<String> list2 = js.getSkillList();

            for (int j = 0; j < list2.size(); j++)
                {
                    for (int k = 0; k < list1.size(); k++)
                    {
                        String skill1 = list2.get(j).replace('[', ' ').replace(']', ' ').trim();
                        String skill2 = list1.get(k).replace('[', ' ').replace(']', ' ').trim();

                        //System.out.println("Comparing " + skill1 + " and " + skill2);

                        if(mc.isMatch(skill1, skill2)) {
                            //System.out.println("Updating score!");
                            updatedJobList.get(i).incrementMatchingScore(1);
                        }
                    }
                }



        }

        filterJobSearch(updatedJobList);

        if (updatedJobList.size() > 1) {
            sortJobs();
        }
        return updatedJobList;
    }

    /*BAILEY'S MATCHING CODES. MAGGIE: I have made quite a few changes to yours so leaving yours here just in case we change our minds!
    public void matchJobs(JobListing reqs)
    {
        for(int i = 0; i < jobList.size(); i++)
        {
            if(jobList.get(i).getJobAd() == false)
                continue;
            if(jobList.get(i).getJobTitle().equals(reqs.getJobTitle()))
                jobList.get(i).incrementMatchingScore(1);
            if(jobList.get(i).getJobCategory().equals(reqs.getJobCategory()))
                jobList.get(i).incrementMatchingScore(1);
            if(jobList.get(i).getJobLocation().equals(reqs.getJobLocation()))
                jobList.get(i).incrementMatchingScore(1);
            if(jobList.get(i).getJobHours().equals(reqs.getJobHours()))
                jobList.get(i).incrementMatchingScore(1);
            if(jobList.get(i).getJobPay().equals(reqs.getJobPay()))
                jobList.get(i).incrementMatchingScore(1);
            if(jobList.get(i).getJobDescription().equals(reqs.getJobDescription()))
                jobList.get(i).incrementMatchingScore(1);

            ArrayList<String> jSkills = jobList.get(i).getJobSkills();
            for(int j = 0; j < jSkills.size(); j++)
            {
                ArrayList<String> reqSkills = reqs.getJobSkills();
                for(int k = 0; k < reqSkills.size(); k++)
                {
                    if(jSkills.get(j).equals(reqSkills.get(k)))
                        jobList.get(i).incrementMatchingScore(1);
                }
            }
        }

        sortJobs();
    }*/

    //Method for JS to interact with job listing
    public void openJobListing(Model.JobListing jl) throws IOException, ParseException {
        JobListingUI jlu = new JobListingUI();
        int choice = JobListingUI.openJobOptions();
        switch (choice)
        {
            case 1:
                //apply for job
                Control.JobApplicationCtrl.applyForJob(jl);
            case 0:
                //go back
                JobSeekerCtrl.runJSHome();
        }
    }

    //Convert CSV to Array List of JL objects and return this Array List.
    public ArrayList<JobListing> parseFromCSV()
            throws IOException, FileNotFoundException, ParseException
    {
        FileIO file = new FileIO(Control.JSS.JSSJOBLIST);
        Model.JobListing jl = new Model.JobListing();
        jobList = new ArrayList<Model.JobListing>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH);
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
    public void printJobList(ArrayList<Model.JobListing> jobList)
            throws IOException, FileNotFoundException, ParseException
    {

        System.out.println("                 JOB LIST\n"
                + "--------------------------------------------");
        for (int i = 0; i < jobList.size(); i++)
        {
            System.out.println("Job " + (i+1) + ":          ");
            System.out.println("Job ID:                  " + jobList.get(i).getJobId());
            //System.out.println("Matching Score:          " + jobList.get(i).getMatchingScore());
            System.out.println("Job Title:               " + jobList.get(i).getJobTitle().toUpperCase());
            System.out.println("Application deadline:    " + dateShortFormat.format(jobList.get(i).getAppDeadline()));
            System.out.println("Advertise:               " + jobList.get(i).labelJobAd(jobList.get(i).getJobAd()));

            System.out.println("--------------------------------------------");
        }

    }

    //Print abbreviated job list for JS
    public void printJobListJS(ArrayList<Model.JobListing> jobList)
            throws IOException, FileNotFoundException, ParseException
    {

        System.out.println("\n               SEARCH RESULTS\n"
                + "--------------------------------------------");
        for (int i = 0; i < jobList.size(); i++)
        {
            System.out.println("Job " + (i+1) + ": " + jobList.get(i).getJobTitle().toUpperCase());
            System.out.println("\nMatching Score: " + jobList.get(i).getMatchingScore());
            System.out.println("(Matching score indicates how relevant the job \nis to your search criteria and skill sets.)\n");
            System.out.println("Application deadline: " + dateShortFormat.format(jobList.get(i).getAppDeadline()) + "\n");
            System.out.println("Required skills are:       ");
            jobList.get(i).displayJobSkills();

            System.out.println("--------------------------------------------");
        }

    }

    //Method that removes the old job listing from the JSSJOBLIST text file
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

    //For filtering out job search results in matchJob()
    public ArrayList<Model.JobListing> removeDiffJobTitle(ArrayList<Model.JobListing> jobList, JobListing req)
    {
        MatchingCtrl mc = new MatchingCtrl();
        for (int i = 0; i < jobList.size(); i++)
        {
            if (!mc.isMatch(jobList.get(i).getJobTitle(), req.getJobTitle()))
            {
                jobList.remove(jobList.get(i));
                removeDiffJobTitle(jobList, req);
            }
        }
        return jobList;
    }

    //For filtering out private jobs in matchJob()
    public ArrayList<Model.JobListing> removePrivateJobs(ArrayList<Model.JobListing> jobList)
    {
        for (int i = 0; i < jobList.size(); i++)
        {
            if (jobList.get(i).getJobAd() == false)
            {
                jobList.remove(jobList.get(i));
            }
        }
        return jobList;
    }

    public void printListJobRC(ArrayList<Model.JobListing> currentList) throws IOException, ParseException {
        JobApplicationCtrl jac = new JobApplicationCtrl();
        ArrayList<Model.JobApplication> listApp = JobApplicationCtrl.parseJobApplicationTextFile("RC");

        for (int i = 0; i < currentList.size(); i++)
        {
            int counter = 0;
            for (int j = 0; j < listApp.size(); j++)
            {
                if (listApp.get(j).getJobApplicationJobId().equals(currentList.get(i).getJobId()))
                {
                    counter++;
                }
            }
            System.out.println("Job " + (i+1) + ": " + currentList.get(i).getJobTitle());
            System.out.println("Number of applications: " + counter);
        }
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

    public static boolean verifyJobIDInFile(String jobID)
    {
        Control.JobListingCtrl jlc = new Control.JobListingCtrl();
        int numJobListings = jlc.jobList.size();
        boolean jobFound = false;
        for (int i = 0; i < numJobListings; i++)
        {
            Model.JobListing jlCheck = jlc.jobList.get(i);
            String jobListingId = jlCheck.getJobId();
            if(jobListingId.equals(jobID))
            {
                jobFound = true;
                break;
            }
        }
        return jobFound;
    }

    //JS-centered method to call all the relevant methods for JS to search and view job listings.
    public ArrayList<Model.JobListing> viewJLFromJS()
            throws IOException, ParseException
    {
        parseFromCSV();
        View.JobSeekerUI jsu = new JobSeekerUI();
        req = jsu.inputSearchKeywords();
        ArrayList<Model.JobListing> searchResults = matchJobs(jobList, req);

        printJobListJS(searchResults);
        generateSearchResults(searchResults);

        return searchResults;
    }

    //RC-centered method to call all the relevant methods for RC to work on Job Listings.
    public void viewJLFromRC()
            throws IOException, ParseException
    {
        printJobList(filterRCJob(parseFromCSV(), LogInCtrl.getRcUsername()));
        int num = viewJobListing(jobList, View.JobListingUI.chooseJobListing(jobList.size()));
        manageJobListing(jobList.get(num - 1));
    }

    //For RC to print out all the details for the chosen job listing, or to go back.
    public int viewJobListing(ArrayList<JobListing> jobList, int jobNo) throws IOException, ParseException {
        if (jobNo == 0) {
            try {
                Control.RecruiterCtrl.runRCHome();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("--------------------------------------------\n"
                + "             VIEW JOB DETAILS            ");
            jobList.get(jobNo - 1).displayJobDetails();
            System.out.println("This listing is currently set to: " + jobList.get(jobNo - 1).labelJobAd(jobList.get(jobNo - 1).getJobAd()));
            System.out.println("--------------------------------------------");
        }

        return (jobNo);
    }

    //For JS to print out all the details for the chosen job listing, or to go back.
    public int viewJobListingJS(ArrayList<JobListing> jobList, int jobNo) throws IOException, ParseException {
        if (jobNo == 0) {
            try {
                JobSeekerCtrl.runJSHome();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\n             VIEW JOB DETAILS            \n"
                    + "--------------------------------------------");
            jobList.get(jobNo - 1).displayJobDetails();
            System.out.println("--------------------------------------------");
        }

        return (jobNo);
    }

    public static void writeNewLineToFile(String infoToWrite, String fileName)
            throws IOException
    {
        FileIO fName = new FileIO(fileName);
        fName.appendFile(infoToWrite);
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
}
