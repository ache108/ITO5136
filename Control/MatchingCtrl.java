package Control;

import Model.JobListing;
import Model.JobSeeker;
import java.util.ArrayList;

public class MatchingCtrl
{
    public boolean isMatch(String str1, String str2)
    {
        return str1.toLowerCase().contains(str2.toLowerCase()) || str2.toLowerCase().contains(str1.toLowerCase());
    }

    public ArrayList<JobSeeker> matchJobSeekers(JobListing job)
    {
        ArrayList<JobSeeker> js = new ArrayList<>();
        //initiate arraylist from file

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


        return sort(js);
    }

    /*public ArrayList<JobSeeker> parseJobSeekers()
    {
        //UNFINISHED
    }*/

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
