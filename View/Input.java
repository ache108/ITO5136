package View;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Input
{
    public boolean acceptBoolean(String prompt)
            throws IndexOutOfBoundsException
    {
        boolean iptBoolean = false;
        boolean validIpt = true;
        do {
            printPrompt(prompt);
            char iptChar = keyboardInput().toLowerCase().charAt(0);
            // separate method here to reuse?
            if (iptChar == 'y')
            {
                iptBoolean = true;
                validIpt = false;
            } else if (iptChar == 'n')
            {
                iptBoolean = false;
                validIpt = false;
            }
            else
            {
                printPrompt("Please enter y or n!");
            }
        } while (validIpt);

        return iptBoolean;
    }

    public char acceptChar(String prompt)
        throws IndexOutOfBoundsException
    {
        printPrompt(prompt);
        return keyboardInput().charAt(0);
    }

    public char acceptChar(String prompt, int index)
        throws IndexOutOfBoundsException
    {
        printPrompt(prompt);
        return keyboardInput().charAt(index);
    }

    public Date acceptDate(String prompt)
            throws IllegalArgumentException
    {
        boolean validIpt = true;
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        do {
            try {
                printPrompt(prompt);
                String iptDate = keyboardInput();
                date = dateFormat.parse(iptDate);
                validIpt = false;

            } catch (Exception e) {
                printPrompt("Error! Please enter date in format dd-mm-yyyy");
            }
        } while (validIpt);
        return date;
    }

    public double acceptDouble(String prompt)
        throws IllegalArgumentException
    {
        printPrompt(prompt);
        return Double.parseDouble(keyboardInput());
    }

    public int acceptInt(String prompt)
        throws IllegalArgumentException
    {
        printPrompt(prompt);
        return Integer.parseInt(keyboardInput());
    }

    //accept int within a range, loop until valid
    public int acceptInt(String prompt, int min, int max)
    {
        boolean flag = true;
        int usrIn = -1;
        do
        {
            try
            {
                usrIn = acceptInt(prompt);
                if(usrIn >= min && usrIn <= max)
                    flag = false;
                else
                    System.out.println("Please enter a valid option");
            }
            catch(Exception e)
            {
                System.out.println("Please enter a valid option");
            }
        }while(flag);

        return usrIn;
    }

    public String acceptString(String prompt)
    {
        printPrompt(prompt);
        return keyboardInput();
    }

    private String keyboardInput()
    {
        Scanner console = new Scanner(System.in);
        return console.nextLine();
    }

    private void printPrompt(String prompt)
    {
        System.out.println(prompt);
    }
}
