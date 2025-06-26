package pl.edu.wsisiz.darkavenger54.forms;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Główna klasa aplikacji JavaIMG.
 * Odpowiada za główne okno oraz zarządzanie otwieraniem obrazów i formularzami obrazów.
 *
 * @author Yevhenii Manuilov
 */

public class MainForm extends JFrame
{
    /** Mapa przechowująca formularze obrazów z przypisanym identyfikatorem */
    private Map<Integer, ImageForm> imageForms;
    /** Licznik identyfikatorów obrazów */
    private int imageIdCounter = 0;
    /**
     * Konstruktor klasy MainForm.
     * Tworzy główne okno aplikacji i ustawia menu.
     */
    public MainForm()
    {
        imageForms = new HashMap<>();
        setTitle("JavaIMG");

        try {
            UIManager.put("FileView.useSystemIcons", Boolean.FALSE);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 150 );
        setResizable(false);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu openMenu = new JMenu("Open");

        // FileMenu
        JMenuItem openItemRGB = new JMenuItem("Open RGB");
        JMenuItem openItemGray = new JMenuItem("Open Gray");
        JMenuItem exitItem = new JMenuItem("Exit");
        openItemRGB.addActionListener(e -> chooseImageRGB());
        openItemGray.addActionListener(e-> chooseImageGray());
        exitItem.addActionListener(e -> System.exit(0));
        //About
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> showInformation());

        fileMenu.add(openMenu);
        openMenu.add(openItemRGB);
        openMenu.add(openItemGray);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        menuBar.add(about);
        setJMenuBar(menuBar);

        setVisible(true);
    }
    /**
     * Otwiera okno wyboru pliku i ładuje obraz w kolorze (RGB).
     */
    private void chooseImageRGB() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Image");

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Images (*.jpg, *.jpeg, *.png, *.bmp)", "jpg", "jpeg", "png", "bmp"
        ));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();

            Mat image = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_COLOR);

            if (image.empty()) {
                System.out.println("Error loading image");
            } else {
                System.out.println("Image loaded: " + image.size());
                createNewImageForm(imagePath, image);
            }
        }
    }

    /**
     * Otwiera okno wyboru pliku i ładuje obraz w odcieniach szarości.
     */
    private void chooseImageGray()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Image");

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Images (*.jpg, *.jpeg, *.png, *.bmp)", "jpg", "jpeg", "png", "bmp"
        ));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();

            Mat image = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_GRAYSCALE);

            if (image.empty()) {
                System.out.println("Error loading image");
            } else {
                System.out.println("Image loaded: " + image.size());
                createNewImageForm(imagePath, image);
            }
        }
    }
    /**
     * Wyświetla okno dialogowe z informacjami o aplikacji.
     */
    private void showInformation()
    {
        JOptionPane.showMessageDialog(this, "JavaIMG\nMade by Yevhenii Manuilov\nPresenter: Dr. Eng. Łukasz Roszkowiak\n" +
                "Image Processing Algorithms 2025\nWIT ID: 21679");
    }
    /**
     * Duplikuje formularz obrazu i dodaje go do mapy.
     *
     * @param imageForm formularz obrazu do duplikacji
     */
    public void duplicateImageForm(ImageForm imageForm)
    {
        ++imageIdCounter;
        imageForms.put(imageIdCounter, new ImageForm(imageForm.getImagePath()+"(1)", imageForm.getImageMat(), this, imageIdCounter));
    }
    /**
     * Dodaje nowy formularz obrazu do mapy.
     *
     * @param imageForm formularz obrazu do dodania
     */
    public void addImageForm(ImageForm imageForm)
    {
        ++imageIdCounter;
        imageForms.put(imageIdCounter, imageForm);
    }
    /**
     * Tworzy nowy formularz obrazu na podstawie ścieżki i macierzy obrazu.
     *
     * @param imagePath ścieżka do obrazu
     * @param mat macierz obrazu
     */
    public void createNewImageForm(String imagePath, Mat mat)
    {
        ++imageIdCounter;
        imageForms.put(imageIdCounter, new ImageForm(imagePath, mat, this, imageIdCounter));
    }
    /**
     * Zwraca mapę formularzy obrazów.
     *
     * @return mapa formularzy obrazów
     */
    public Map<Integer, ImageForm> getImageForms()
    {
        return imageForms;
    }
    /**
     * Zwraca formularz obrazu na podstawie klucza.
     *
     * @param key identyfikator formularza
     * @return formularz obrazu
     */
    public ImageForm getImageFormByKey(Integer key)
    {
        return imageForms.get(key);
    }
    /**
     * Usuwa formularz obrazu na podstawie klucza.
     *
     * @param key identyfikator formularza
     */
    public void removeImageFormByKey(Integer key)
    {
        imageForms.remove(key);
    }
    /**
     * Duplikuje formularz obrazu i zwraca nowy formularz.
     *
     * @param originalForm formularz do duplikacji
     * @return nowy duplikat formularza obrazu
     */
    public ImageForm duplicateImageFormAndReturn(ImageForm originalForm) {
        ++imageIdCounter;
        Mat clonedMat = originalForm.getImageMat().clone();
        ImageForm duplicated = new ImageForm(
                originalForm.getImagePath() + " (copy)",
                clonedMat,
                this,
                imageIdCounter
        );
        imageForms.put(imageIdCounter, duplicated);
        return duplicated;
    }
}
