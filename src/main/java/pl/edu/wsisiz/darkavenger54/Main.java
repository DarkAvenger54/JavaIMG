package pl.edu.wsisiz.darkavenger54;

import org.opencv.core.Core;
import pl.edu.wsisiz.darkavenger54.forms.MainForm;

import java.io.*;
import java.nio.file.Paths;

/**
 * @author Yevhenii Manuilov
 */

public class Main
{
    public static void main(String[] args)
    {
        loadOpenCV();
        System.out.println("OpenCV Version: " + Core.VERSION);
        MainForm mainForm = new MainForm();
    }
    private static void loadOpenCV() {
        try {
            String arch = System.getProperty("os.arch");
            String libName = Core.NATIVE_LIBRARY_NAME; // opencv_java4100
            String fileName = System.mapLibraryName(libName); // .dll/.so/.dylib

            String resourcePath = arch.contains("64") ? "/native/x64/" + fileName : "/native/x86/" + fileName;
            InputStream in = Main.class.getResourceAsStream(resourcePath);
            if (in == null) {
                throw new RuntimeException("Resource Not Found " + resourcePath);
            }

            File temp = File.createTempFile(libName, fileName.substring(fileName.lastIndexOf('.')));
            temp.deleteOnExit();

            try (OutputStream out = new FileOutputStream(temp)) {
                in.transferTo(out);
            }

            System.load(temp.getAbsolutePath());
            System.out.println("OpenCV loaded from resource: " + resourcePath);
        } catch (IOException | UnsatisfiedLinkError e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading OpenCV", e);
        }
    }
}