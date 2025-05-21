package pl.edu.wsisiz.darkavenger54;

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
