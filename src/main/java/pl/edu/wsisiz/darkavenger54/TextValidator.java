package pl.edu.wsisiz.darkavenger54;

/**
 * @author Yevhenii Manuilov
 */
public class TextValidator
{
    public static boolean tryParseIntAndValidateClosed(String text, int min, int max)
    {
        try
        {
            int value = Integer.parseInt(text);
            if (value >= min && value <= max)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
    public static boolean tryParseIntAndValidateOpen(String text, int min, int max)
    {
        try
        {
            int value = Integer.parseInt(text);
            if (value > min && value < max)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
    public static boolean tryParseDoubleAndValidateClosed(String text, double min, double max)
    {
        try
        {
            double value = Double.parseDouble(text);
            if (value >= min && value <= max)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
    public static boolean tryParseDoubleAndValidateOpen(String text, double min, double max)
    {
        try
        {
            double value = Double.parseDouble(text);
            if (value > min && value < max)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
    public static boolean kernelSizeValidator(String text)
    {
        try
        {
            int value = Integer.parseInt(text);
            if (value >= 0 && value <= 99 && value % 2 != 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
}
