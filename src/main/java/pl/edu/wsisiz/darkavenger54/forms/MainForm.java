package pl.edu.wsisiz.darkavenger54.forms;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yevhenii Manuilov
 */

public class MainForm extends JFrame
{
    private List<ImageForm> imageForms;
    public MainForm()
    {
        imageForms = new ArrayList<>();
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
                imageForms.add(new ImageForm(imagePath, image, this));
            }
        }
    }

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
                imageForms.add(new ImageForm(imagePath, image, this));
            }
        }
    }
    private void showInformation()
    {
        JOptionPane.showMessageDialog(this, "Lab Exercises Summary Application\nMade by Yevhenii Manuilov\nPresenter: Dr. Eng. Łukasz Roszkowiak\n" +
                "Image Processing Algorithms 2025\nWIT ID: 21679");
    }
    public void duplicateImageForm(ImageForm imageForm)
    {
        imageForms.add(new ImageForm(imageForm.getImagePath()+"(1)", imageForm.getImageMat(), this));
    }
}
