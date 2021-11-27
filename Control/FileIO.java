package Control;
import java.io.*;
import java.util.Scanner;

/**
* Class which controls reading and writing to a file
*
* @author Taken from ITO4131 07/06/2021. Modified by Bailey Edwards
* @version ver1.0.1
*/
public class FileIO
{
    private String fileName;

    /**
    * Default constructor to create an object of the class FileIO
    *
    */
    public FileIO()
    {
        fileName = "default.txt";
    }

    /**
    * Non-Default constructor to create an object of the class FileIO
    *
    * @param fileName   The name of the file to be accessed as a String
    */
    public FileIO(String fileName)
    {
        this.fileName = fileName;
    }

    public void appendFile(String output)
            throws IOException
    {
        //System.out.println("y");
        FileWriter writer = new FileWriter(fileName, true);
        writer.append("\n" + output);
        writer.close();
    }

    /**
    * Accessor method to get the file name
    *
    * @return           The name of the file being accessed as a String
    */
    public String getFileName()
    {
        return fileName;
    }

    /**
    * Reads the file and returns its contents as a string.
    * If the file has multiple lines they are separated by a delimiter which is specified as a parameter.
    *
    * @param delim      A String which will be inserted between the lines of the file in the output String
    * @return           The contents of the file as a String
    * @throws IOException
    * @throws FileNotFoundException
    */
    public String readFile(String delim)
        throws IOException, FileNotFoundException
    {
        FileReader reader = new FileReader(fileName);
        Scanner fileInput = new Scanner(reader);

        String output = "";
        while(fileInput.hasNext())
        {
            if(output != "")
                output += delim;
            output += fileInput.nextLine();
        }

        reader.close();
        return output;
    }

    /**
    * Mutator method to set the file to be accessed
    *
    * @param fileName   The name of the file to be accessed as a String
    */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    /**
    * Writes to the file
    *
    * @param output     The String which will be written to the file
    * @throws IOException
    */
    public void writeFile(String output)
        throws IOException
    {
        FileWriter writer = new FileWriter(fileName);
        writer.write(output);
        writer.close();
    }

    public void replaceTextFile(String target)
            throws IOException, FileNotFoundException
    {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        BufferedWriter bw = new BufferedWriter(new FileWriter("Temp.txt"));

        String currentLine;
        while((currentLine = br.readLine()) != null)
        {
            System.out.println("Are you doing anything?");
            String trimmedLine = currentLine.trim();
            if (trimmedLine.equals(target))
            {
                continue;
            }
            bw.write(currentLine + "\n");

        }
        bw.close();
        br.close();
    }
}
