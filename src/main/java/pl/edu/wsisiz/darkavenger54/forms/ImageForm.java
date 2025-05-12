package pl.edu.wsisiz.darkavenger54.forms;

import java.awt.event.*;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import pl.edu.wsisiz.darkavenger54.MatAlgorithms;
import pl.edu.wsisiz.darkavenger54.enums.EnImageType;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author Yevhenii Manuilov
 */

public class ImageForm extends JFrame
{
    private String imagePath;
    private Mat imageCurrentVersion;
    private int scalePercent = 100;
    private final int SCALE_STEP = 25;
    private final int SCALE_MIN = 25;
    private final int SCALE_MAX = 400;
    private EnImageType imageType;
    private HistogramForm histogramForm;
    private final MainForm mainForm;

    public ImageForm(String imagePath, Mat mat, MainForm mainForm)
    {
        //Initializing form fields
        this.imagePath = imagePath;
        this.imageCurrentVersion = mat.clone();
        this.mainForm = mainForm;
        this.imageType = getMatType(imageCurrentVersion);
        histogramForm = new HistogramForm();
        initComponents();
        //----
        //updating designer fields

        imageLabel.setIcon(new ImageIcon(MatAlgorithms.matToBufferedImage(imageCurrentVersion)));
        typeLabel.setText(imageType.getImageType());
        //--

        // Form Settings
        setLocationRelativeTo(null);
        setTitle("JavaIMG " + imagePath);
        sizeLabel.setText(scalePercent + "%");

        //-----
        this.setVisible(true);
    }
    private EnImageType getMatType(Mat mat)
    {
        if (mat.channels() == 1)
        {
            return EnImageType.GRAYSCALE;
        }
        else
        {
            return EnImageType.RGB;
        }
    }
    private void updateImage(Mat mat)
    {
        imageType = getMatType(mat);
        typeLabel.setText(imageType.getImageType());
        mat = resizeFromOriginal(mat);
        BufferedImage updatedImage = MatAlgorithms.matToBufferedImage(mat);
        imageLabel.setIcon(new ImageIcon(updatedImage));
        this.repaint();
    }
    private Mat resizeFromOriginal(Mat updatedImage) {
        double scale = scalePercent / 100.0;
        Mat resized = new Mat();
        Size newSize = new Size(
                Math.round(updatedImage.width() * scale),
                Math.round(updatedImage.height() * scale)
        );
        Imgproc.resize(updatedImage, resized, newSize, 0, 0, Imgproc.INTER_AREA);
        sizeLabel.setText(scalePercent + "%");
        return resized;
    }

    private void increaseImageSize(ActionEvent e) {
        if (scalePercent + SCALE_STEP <= SCALE_MAX) {
            scalePercent += SCALE_STEP;
            updateImage(imageCurrentVersion);
        }
    }

    private void decreaseImageSize(ActionEvent e) {
        if (scalePercent - SCALE_STEP >= SCALE_MIN) {
            scalePercent -= SCALE_STEP;
            updateImage(imageCurrentVersion);
        }
    }

    private void rgbToGray(ActionEvent e) {
        if (imageType == EnImageType.GRAYSCALE)
        {
            JOptionPane.showMessageDialog(this, "Image is already grayscale");
        }
        else
        {
            imageCurrentVersion = MatAlgorithms.rgbToGray(imageCurrentVersion);
            updateImage(imageCurrentVersion);
        }
    }

    private void grayToRgb(ActionEvent e) {
        if (imageType == EnImageType.RGB)
        {
            JOptionPane.showMessageDialog(this, "Image is already RGB");
        }
        else
        {
            imageCurrentVersion = MatAlgorithms.grayToRgb(imageCurrentVersion);
            updateImage(imageCurrentVersion);
        }
    }

    private void testMenuItem(ActionEvent e) {
        MatAlgorithms.test(imageCurrentVersion);
    }
    //occurs errors if disk d is chose, answear???
    private void saveAs(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("choose");
        fileChooser.setSelectedFile(new File(imagePath));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            Imgcodecs.imwrite(file.getAbsolutePath(), imageCurrentVersion);
        }
    }

    private void duplicate(ActionEvent e) {
       mainForm.duplicateImageForm(this);
    }

    private void histogram(ActionEvent e) {
        this.histogramForm.setVisible(true);
    }

    public String getImagePath()
    {
        return imagePath;
    }
    public Mat getImageMat()
    {
        return imageCurrentVersion;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        saveAsMenuItem = new JMenuItem();
        duplicateMenuItem = new JMenuItem();
        menuItem2 = new JMenuItem();
        menu2 = new JMenu();
        rgbToGrayMenuItem = new JMenuItem();
        grayToRgbMenuItem = new JMenuItem();
        histogramMenuItem = new JMenuItem();
        testMenuItem = new JMenuItem();
        menu3 = new JMenu();
        menu4 = new JMenu();
        menu5 = new JMenu();
        scrollPane1 = new JScrollPane();
        imageLabel = new JLabel();
        typeLabel = new JLabel();
        decreaseButton = new JButton();
        increaseButton = new JButton();
        sizeLabel = new JLabel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("File");

                //---- saveAsMenuItem ----
                saveAsMenuItem.setText("Save as");
                saveAsMenuItem.addActionListener(e -> saveAs(e));
                menu1.add(saveAsMenuItem);

                //---- duplicateMenuItem ----
                duplicateMenuItem.setText("Duplicate");
                duplicateMenuItem.addActionListener(e -> duplicate(e));
                menu1.add(duplicateMenuItem);
                menu1.addSeparator();

                //---- menuItem2 ----
                menuItem2.setText("Exit");
                menu1.add(menuItem2);
            }
            menuBar1.add(menu1);

            //======== menu2 ========
            {
                menu2.setText("Lab1");

                //---- rgbToGrayMenuItem ----
                rgbToGrayMenuItem.setText("rgbToGray");
                rgbToGrayMenuItem.addActionListener(e -> rgbToGray(e));
                menu2.add(rgbToGrayMenuItem);

                //---- grayToRgbMenuItem ----
                grayToRgbMenuItem.setText("grayToRgb");
                grayToRgbMenuItem.addActionListener(e -> grayToRgb(e));
                menu2.add(grayToRgbMenuItem);

                //---- histogramMenuItem ----
                histogramMenuItem.setText("histogram");
                histogramMenuItem.addActionListener(e -> histogram(e));
                menu2.add(histogramMenuItem);

                //---- testMenuItem ----
                testMenuItem.setText("test");
                testMenuItem.addActionListener(e -> testMenuItem(e));
                menu2.add(testMenuItem);
            }
            menuBar1.add(menu2);

            //======== menu3 ========
            {
                menu3.setText("Lab2");
            }
            menuBar1.add(menu3);

            //======== menu4 ========
            {
                menu4.setText("Lab3");
            }
            menuBar1.add(menu4);

            //======== menu5 ========
            {
                menu5.setText("Lab4");
            }
            menuBar1.add(menu5);
        }
        setJMenuBar(menuBar1);

        //======== scrollPane1 ========
        {

            //---- imageLabel ----
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            scrollPane1.setViewportView(imageLabel);
        }

        //---- typeLabel ----
        typeLabel.setText("type");

        //---- decreaseButton ----
        decreaseButton.setText("-");
        decreaseButton.addActionListener(e -> decreaseImageSize(e));

        //---- increaseButton ----
        increaseButton.setText("+");
        increaseButton.addActionListener(e -> increaseImageSize(e));

        //---- sizeLabel ----
        sizeLabel.setText("100%");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addContainerGap())
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(typeLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 224, Short.MAX_VALUE)
                            .addComponent(sizeLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(decreaseButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(increaseButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                            .addGap(9, 9, 9))))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(typeLabel)
                        .addComponent(increaseButton)
                        .addComponent(decreaseButton)
                        .addComponent(sizeLabel))
                    .addGap(4, 4, 4))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem saveAsMenuItem;
    private JMenuItem duplicateMenuItem;
    private JMenuItem menuItem2;
    private JMenu menu2;
    private JMenuItem rgbToGrayMenuItem;
    private JMenuItem grayToRgbMenuItem;
    private JMenuItem histogramMenuItem;
    private JMenuItem testMenuItem;
    private JMenu menu3;
    private JMenu menu4;
    private JMenu menu5;
    private JScrollPane scrollPane1;
    private JLabel imageLabel;
    private JLabel typeLabel;
    private JButton decreaseButton;
    private JButton increaseButton;
    private JLabel sizeLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
