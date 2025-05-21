package pl.edu.wsisiz.darkavenger54;

import org.opencv.core.Core;
import pl.edu.wsisiz.darkavenger54.forms.MainForm;

import java.nio.file.Paths;

/**
 * @author Yevhenii Manuilov
 */

public class Main
{
    public static void main(String[] args)
    {
        String arch = System.getProperty("os.arch");
        String libPath;
        if (arch.contains("64")) {
            libPath = "lib/native/x64/opencv_java4100.dll";
        } else {
            libPath = "lib/native/x86/opencv_java4100.dll";
        }
        try {
            System.load(Paths.get(libPath).toAbsolutePath().toString());
            System.out.println("Loaded OpenCV native library: " + libPath);
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Failed to load native OpenCV library for arch: " + arch);
        }
        System.out.println("OpenCV Version: " + Core.VERSION);
        MainForm mainForm = new MainForm();
    }
}