package pl.edu.wsisiz.darkavenger54;

import org.opencv.core.Core;
import pl.edu.wsisiz.darkavenger54.forms.MainForm;

/**
 * @author Yevhenii Manuilov
 */

public class Main
{
    public static void main(String[] args)
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println(Core.VERSION);
        MainForm mainForm = new MainForm();
    }
}