//package View;
import java.util.Scanner;

public class Input
{
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
