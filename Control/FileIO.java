package Control;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
* Class which controls reading and writing to a file
*
* @author Taken from ITO4131 07/06/2021. Modified by Bailey Edwards
* @version ver1.0.1
*/
public class FileIO
{
    private static String fileName;

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
    public static String readFile(String delim)
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

    //Remove target line from txt file (DOESN'T APPEND NEW LINE)
    public static void removeLine(String target)
            throws IOException
    {
        File tempFile = new File("Files/TempFile.txt");
        File inputFile = new File(fileName);

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String[] list = readFile("\n").split("\n");

        for (int i = 0; i < list.length; i++)
        {
            if (!list[i].equals(target)) {
                if (i == 0) {
                    writer.write(list[i]);
                } else {
                    writer.write("\n" + list[i]);
                }
            } else {
                continue;
            }

        }

        writer.close();
        reader.close();

        FileChannel src = new FileInputStream(tempFile).getChannel();
        FileChannel dest = new FileOutputStream(inputFile).getChannel();
        dest.transferFrom(src, 0, src.size());
    }
}
