package pl.edu.wsisiz.darkavenger54;

import org.opencv.core.Core;
import pl.edu.wsisiz.darkavenger54.forms.MainForm;

import java.io.*;

/**
 * Klasa główna uruchamiająca aplikację JavaIMG.
 * Inicjalizuje bibliotekę OpenCV i uruchamia główne
 * okno aplikacji (MainForm).
 *
 * @author Yevhenii Manuilov
 */

public class Main
{
    /**
     * Punkt wejścia do aplikacji.
     * Ładuje bibliotekę OpenCV i uruchamia główne
     * okno aplikacji.
     *
     * @param args Argumenty linii poleceń (niewykorzystywane).
     */
    public static void main(String[] args)
    {
        loadOpenCV();
        System.out.println("OpenCV Version: " + Core.VERSION);
        MainForm mainForm = new MainForm();
    }
    /**
     * Ładuje natywną bibliotekę OpenCV poprzez rozpakowanie
     * jej z zasobów aplikacji (w zależności od architektury
     * systemu) i zapisanie do pliku tymczasowego.
     *
     * Wybiera odpowiednią wersję pliku .dll/.so/.dylib
     * na podstawie architektury procesora.
     *
     * @throws RuntimeException w przypadku błędu
     *                           odczytu zasobów lub
     *                           błędu ładowania biblioteki.
     */
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