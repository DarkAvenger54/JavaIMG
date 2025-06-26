package pl.edu.wsisiz.darkavenger54;

/**
 * Klasa pomocnicza do sprawdzania, czy podany łańcuch znaków
 * można skonwertować na typ liczbowy (int lub double).
 *
 * @author Yevhenii Manuilov
 */
public class TextParser
{
    public static boolean tryParseToInt(String input)
    {
        try
        {
            Integer.parseInt(input);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }
    public static boolean tryParseToDouble(String input)
    {
        try
        {
            Double.parseDouble(input);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }
}
