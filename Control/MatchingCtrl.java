package Control;

import Model.JobListing;
import Model.JobSeeker;

import java.text.ParseException;
import java.util.ArrayList;
import java.io.*;
import Control.JSS;
import Control.FileIO;

public class MatchingCtrl
{
    public boolean isMatch(String str1, String str2)
    {
        if(str1 == null || str1.length() == 0)
            return false;
        if(str2 == null || str2.length() == 0)
            return false;
        return str1.toLowerCase().contains(str2.toLowerCase()) || str2.toLowerCase().contains(str1.toLowerCase());
    }

    public ArrayList<JobSeeker> matchJobSeekers(JobListing job)
            throws IOException, FileNotFoundException, ParseException
    {
        ArrayList<JobSeeker> js = parseJobSeekers();

        //match job to job seekers based on skills, location, work type
        for(int i = 0; i < js.size(); i++)
        {
            if(!js.get(i).publicProfile)
                continue;

            if(isMatch(js.get(i).getCity(), job.getJobLocation()))
                js.get(i).incrementMatchingScore(1);
            if(isMatch(js.get(i).getWorkType(), job.getJobHours()))
                js.get(i).incrementMatchingScore(1);

            for(int j = 0; j < js.get(i).getSkillListSize(); j++)
            {
                for(int k = 0; k < job.getJobSkills().size(); k++)
                {
                    if(isMatch(js.get(i).getSkillFromList(j), job.getJobSkills().get(k)))
                        js.get(i).incrementMatchingScore(1);
                }
            }
        }

        //remove job seekers with a matching score of 0
        for(int i = js.size() - 1; i >= 0; i--)
        {
            if(js.get(i).getMatchingScore() == 0)
            {
                js.remove(i);
            }
        }

        return sort(js);
    }

    public ArrayList<JobSeeker> parseJobSeekers()
            throws IOException, FileNotFoundException, ParseException
    {
        FileIO file = new FileIO(JSS.JSDETAILS);
        ArrayList<JobSeeker> users = new ArrayList<>();

        String[] lines = file.readFile("\n").split("\n");

        for (int i = 0; i < lines.length; i++)
        {
            String[] line = lines[i].split(";");
            if (line[7].equals("true"))
            {
                users.add(Control.JobSeekerCtrl.getJobSeeker(line[0]));
            }
        }

        /*try
        {
            String[] lines = file.readFile("\n").split("\n");

            for (int i = 0; i < lines.length; i++) {
                String[] line = lines[i].split(";");
                if (line[7].equals("true")) {
                    users.add(Control.JobSeekerCtrl.getJobSeeker(line[0]));
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("There was a problem reading the user details file.");
        }*/

        return users;
    }


    public ArrayList<JobSeeker> sort(ArrayList<JobSeeker> js)
    {
        JobSeeker temp;
        for(int i = 0; i < js.size() - 1; i++)
        {
            for(int j = js.size() - 1; j > i; j--)
            {
                if(js.get(j).getMatchingScore() > js.get(j - 1).getMatchingScore())
                {
                    temp = js.get(j);
                    js.set(j, js.get(j-1));
                    js.set(j-1, temp);
                }
            }
        }

        return js;
    }
}
