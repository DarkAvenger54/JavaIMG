package pl.edu.wsisiz.darkavenger54.forms;

import java.awt.event.*;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import pl.edu.wsisiz.darkavenger54.MatAlgorithms;
import pl.edu.wsisiz.darkavenger54.dialogs.*;
import pl.edu.wsisiz.darkavenger54.dialogs.arithmetic.ArithmeticDialog;
import pl.edu.wsisiz.darkavenger54.dialogs.MorfologyDialog;
import pl.edu.wsisiz.darkavenger54.dialogs.arithmetic.BlendDialog;
import pl.edu.wsisiz.darkavenger54.dialogs.arithmetic.NotDialog;
import pl.edu.wsisiz.darkavenger54.enums.*;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private EnBinaryType binaryType;
    private HistogramForm histogramForm;
    private final MainForm mainForm;
    private final int id;


    public ImageForm(String imagePath, Mat mat, MainForm mainForm, int id)
    {
        //Initializing form fields
        this.imagePath = imagePath;
        this.imageCurrentVersion = mat.clone();
        this.mainForm = mainForm;
        this.id = id;
        this.imageType = getMatType(imageCurrentVersion);
        this.binaryType = getMatBinaryType(imageCurrentVersion);
        histogramForm = new HistogramForm(imagePath);
        initComponents();
        //----
        //updating designer fields

        imageLabel.setIcon(new ImageIcon(MatAlgorithms.matToBufferedImage(imageCurrentVersion)));
        typeLabel.setText(imageType.getImageType() + " " + binaryType.getBinaryType());
        //--

        // Form Settings
        setLocationRelativeTo(null);
        setTitle("JavaIMG " + imagePath);
        sizeLabel.setText(scalePercent + "%");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                histogramForm.dispose();
            }
        });

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
    private EnBinaryType getMatBinaryType(Mat mat)
    {
        if(mat.type() == CvType.CV_8UC1)
        {
            return  EnBinaryType.BINARY;
        }
        else
        {
            return EnBinaryType.NON_BINARY;
        }
    }
    private void updateImage(Mat mat)
    {
        imageType = getMatType(mat);
        binaryType = getMatBinaryType(mat);
        if(imageType == EnImageType.GRAYSCALE)
        {
            histogramForm.updateHistogram(mat);
        }
        else
        {
            histogramForm.setVisible(false);
        }
        typeLabel.setText(imageType.getImageType() + " " + binaryType.getBinaryType());
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
            JOptionPane.showMessageDialog(this, "Image is already grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Image is already RGB", "Error" , JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            imageCurrentVersion = MatAlgorithms.grayToRgb(imageCurrentVersion);
            updateImage(imageCurrentVersion);
        }
    }

    private void testMenuItem(ActionEvent e) {

    }
    //occurs errors when disk d is chosen, answer???
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
    @Deprecated
    private void addToMainForm()
    {
        mainForm.addImageForm(this);
    }

    private void histogram(ActionEvent e) {
        if (imageType == EnImageType.GRAYSCALE)
        {
            if (histogramForm.isVisible())
            {
                JOptionPane.showMessageDialog(this, "Histogram is already open", "Warning" , JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                histogramForm.updateHistogram(imageCurrentVersion);
                histogramForm.setVisible(true);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getImagePath()
    {
        return imagePath;
    }
    public Mat getImageMat()
    {
        return imageCurrentVersion;
    }

    private void stretch(ActionEvent e) {
        if (imageType == EnImageType.GRAYSCALE)
        {
            imageCurrentVersion = MatAlgorithms.histogramStretch(imageCurrentVersion);
            updateImage(imageCurrentVersion);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }

    }

    private void equalize(ActionEvent e) {
        if (imageType == EnImageType.GRAYSCALE)
        {
            imageCurrentVersion = MatAlgorithms.histogramEqualization(imageCurrentVersion);
            updateImage(imageCurrentVersion);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }

    }

    private void rgbToHsv(ActionEvent e) {
        if (imageType == EnImageType.RGB)
        {
            List<Mat> hsvChannels = MatAlgorithms.convertRGBtoHSVChannels(imageCurrentVersion);
            for (int i = 0; i < hsvChannels.size(); ++i)
            {
                mainForm.createNewImageForm(imagePath + i, hsvChannels.get(i));
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't RGB", "Error" , JOptionPane.ERROR_MESSAGE);
        }

    }

    private void rgbToLab(ActionEvent e) {
        if (imageType == EnImageType.RGB)
        {
            List<Mat> labChannels = MatAlgorithms.convertRGBtoLabChannels(imageCurrentVersion);
            for (int i = 0; i < labChannels.size(); ++i)
            {
                mainForm.createNewImageForm(imagePath + i, labChannels.get(i));
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't RGB", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rgbToRgb(ActionEvent e) {
        if (imageType == EnImageType.RGB)
        {
            List<Mat> rgbChannels = MatAlgorithms.convertRGBtoGrayChannels(imageCurrentVersion);
            for (int i = 0; i < rgbChannels.size(); ++i)
            {
                mainForm.createNewImageForm(imagePath + i, rgbChannels.get(i));
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't RGB", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void negation(ActionEvent e) {
        imageCurrentVersion = MatAlgorithms.negation(imageCurrentVersion);
        updateImage(imageCurrentVersion);
    }

    private void StertchInRange(ActionEvent e) {
        if (imageType == EnImageType.GRAYSCALE)
        {
            StretchRangeDialog stretchRangeDialog = new StretchRangeDialog(this);
            if(stretchRangeDialog.isConfirmed())
            {
                int p1 = stretchRangeDialog.getP1();
                int p2 = stretchRangeDialog.getP2();
                int q3 = stretchRangeDialog.getQ3();
                int q4 = stretchRangeDialog.getQ4();
                stretchRangeDialog.dispose();
                imageCurrentVersion = MatAlgorithms.stretch1(imageCurrentVersion, p1, p2, q3, q4);
                updateImage(imageCurrentVersion);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void posterize(ActionEvent e) {
        if (imageType == EnImageType.GRAYSCALE)
        {
            PosterizeDialog posterizeDialog = new PosterizeDialog(this);
            if (posterizeDialog.isConfirmed())
            {
                int posterizeValue = posterizeDialog.getPosterizeValue();
                posterizeDialog.dispose();
                imageCurrentVersion = MatAlgorithms.posterize(imageCurrentVersion, posterizeValue);
                updateImage(imageCurrentVersion);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void blur(ActionEvent e) {
        BlurDialog blurDialog = new BlurDialog(this);
        if (blurDialog.isConfirmed())
        {
            int blurValue = blurDialog.getBlurValue();
            blurDialog.dispose();
            imageCurrentVersion = MatAlgorithms.blur(imageCurrentVersion, blurValue);
            updateImage(imageCurrentVersion);
        }
    }

    private void sobel(ActionEvent e) {
        if (imageType == EnImageType.GRAYSCALE)
        {
            SobelDialog sobelDialog = new SobelDialog(this);
            if(sobelDialog.isConfirmed())
            {
                int ksize = sobelDialog.getKsize();
                EnPaddingType paddingType = sobelDialog.getPaddingType();
                EnDepthType depthType = sobelDialog.getDepthType();
                sobelDialog.dispose();
                imageCurrentVersion = MatAlgorithms.sobel(imageCurrentVersion, ksize, paddingType, depthType);
                updateImage(imageCurrentVersion);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void laplacian(ActionEvent e) {
        if (imageType == EnImageType.GRAYSCALE)
        {
            LaplacianDialog laplacianDialog = new LaplacianDialog(this);
            if(laplacianDialog.isConfirmed())
            {
                int ksize = laplacianDialog.getKsize();
                EnPaddingType paddingType = laplacianDialog.getPaddingType();
                EnDepthType depthType = laplacianDialog.getDepthType();
                laplacianDialog.dispose();
                imageCurrentVersion = MatAlgorithms.laplacian(imageCurrentVersion, ksize, paddingType, depthType);
                updateImage(imageCurrentVersion);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void canny(ActionEvent e) {
        if (imageType == EnImageType.GRAYSCALE)
        {
            CannyDialog cannyDialog = new CannyDialog(this);
            if(cannyDialog.isConfirmed())
            {
                int threshold1 = cannyDialog.getThreshold1();
                int threshold2 = cannyDialog.getThreshold2();
                cannyDialog.dispose();
                imageCurrentVersion = MatAlgorithms.canny(imageCurrentVersion, threshold1, threshold2);
                updateImage(imageCurrentVersion);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prewitt(ActionEvent e) {
        if (imageType == EnImageType.GRAYSCALE)
        {
            PrewittDialog prewittDialog = new PrewittDialog(this);
            if(prewittDialog.isConfirmed())
            {
                EnDirectionType direction = prewittDialog.getDirection();
                EnDepthType depthType = prewittDialog.getDepth();
                EnPaddingType paddingType = prewittDialog.getPadding();
                prewittDialog.dispose();
                imageCurrentVersion = MatAlgorithms.prewitt(imageCurrentVersion, direction, depthType, paddingType);
                updateImage(imageCurrentVersion);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void add(ActionEvent e) {
        ArithmeticDialog arithmeticDialog = new ArithmeticDialog(this, mainForm, "Add", "+");
        if(arithmeticDialog.isConfirmed())
        {
            Mat mat1 = arithmeticDialog.getImageForm1MatClone();
            Mat mat2 = arithmeticDialog.getImageForm2MatClone();
            if(mat1.size().equals(mat2.size()))
            {
                mainForm.createNewImageForm("added", MatAlgorithms.add(mat1, mat2));
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Image aren't same size", "Error" , JOptionPane.ERROR_MESSAGE);
            }
            arithmeticDialog.dispose();
        }
    }

    private void substract(ActionEvent e) {
        ArithmeticDialog arithmeticDialog = new ArithmeticDialog(this, mainForm, "Subtract", "-");
        if(arithmeticDialog.isConfirmed())
        {
            Mat mat1 = arithmeticDialog.getImageForm1MatClone();
            Mat mat2 = arithmeticDialog.getImageForm2MatClone();
            if(mat1.size().equals(mat2.size()))
            {
                mainForm.createNewImageForm("added", MatAlgorithms.subtract(mat1, mat2));
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Image aren't same size", "Error" , JOptionPane.ERROR_MESSAGE);
            }
            arithmeticDialog.dispose();
        }
    }

    private void blend(ActionEvent e) {
        BlendDialog blendDialog = new BlendDialog(this, mainForm, "Blend", "blend");
        if(blendDialog.isConfirmed())
        {
            Mat mat1 = blendDialog.getImageForm1MatClone();
            Mat mat2 = blendDialog.getImageForm2MatClone();
            double alpha = blendDialog.getAlpha();
            if(mat1.size().equals(mat2.size()))
            {
                mainForm.createNewImageForm("added", MatAlgorithms.blend(mat1, mat2, alpha));
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Image aren't same size", "Error" , JOptionPane.ERROR_MESSAGE);
            }
            blendDialog.dispose();
        }
    }

    private void and(ActionEvent e) {
        ArithmeticDialog arithmeticDialog = new ArithmeticDialog(this, mainForm, "And", "and");
        if(arithmeticDialog.isConfirmed())
        {
            Mat mat1 = arithmeticDialog.getImageForm1MatClone();
            Mat mat2 = arithmeticDialog.getImageForm2MatClone();
            if(mat1.size().equals(mat2.size()))
            {
                mainForm.createNewImageForm("added", MatAlgorithms.bitwiseAnd(mat1, mat2));
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Image aren't same size", "Error" , JOptionPane.ERROR_MESSAGE);
            }
            arithmeticDialog.dispose();
        }

    }

    private void or(ActionEvent e) {
        ArithmeticDialog arithmeticDialog = new ArithmeticDialog(this, mainForm, "Or", "or");
        if(arithmeticDialog.isConfirmed())
        {
            Mat mat1 = arithmeticDialog.getImageForm1MatClone();
            Mat mat2 = arithmeticDialog.getImageForm2MatClone();
            if(mat1.size().equals(mat2.size()))
            {
                mainForm.createNewImageForm("added", MatAlgorithms.bitwiseOr(mat1, mat2));
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Image aren't same size", "Error" , JOptionPane.ERROR_MESSAGE);
            }
            arithmeticDialog.dispose();
        }

    }

    private void xor(ActionEvent e) {
        ArithmeticDialog arithmeticDialog = new ArithmeticDialog(this, mainForm, "Xor", "xor");
        if(arithmeticDialog.isConfirmed())
        {
            Mat mat1 = arithmeticDialog.getImageForm1MatClone();
            Mat mat2 = arithmeticDialog.getImageForm2MatClone();
            if(mat1.size().equals(mat2.size()))
            {
                mainForm.createNewImageForm("added", MatAlgorithms.bitwiseXor(mat1, mat2));
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Image aren't same size", "Error" , JOptionPane.ERROR_MESSAGE);
            }
            arithmeticDialog.dispose();
        }

    }

    private void not(ActionEvent e) {
        NotDialog notDialog = new NotDialog(this, mainForm, "Not", "not");
        if(notDialog.isConfirmed())
        {
            Mat mat = notDialog.getImageFormMat2Clone();
            mainForm.createNewImageForm("added", MatAlgorithms.bitwiseNot(mat));
        }
    }


    private void gaussianBlur(ActionEvent e) {
        GaussianBlurDialog gaussianBlurDialog = new GaussianBlurDialog(this);
        if (gaussianBlurDialog.isConfirmed())
        {
            int ksize = gaussianBlurDialog.getKSize();
            double sigmaX = gaussianBlurDialog.getSigmaX();
            double sigmaY = gaussianBlurDialog.getSigmaY();
            EnPaddingType paddingType = gaussianBlurDialog.getPaddingType();
            gaussianBlurDialog.dispose();
            imageCurrentVersion = MatAlgorithms.gaussianBlur(imageCurrentVersion, ksize, sigmaX, sigmaY, paddingType);
            updateImage(imageCurrentVersion);
        }
    }

    private void laplacianSharpen(ActionEvent e) {
        if (imageType == EnImageType.GRAYSCALE)
        {
            LaplacianSharpenDialog laplacianSharpenDialog = new LaplacianSharpenDialog(this);
            if(laplacianSharpenDialog.isConfirmed())
            {
                EnSharpenType sharpenType = laplacianSharpenDialog.getSharpenType();
                EnDepthType depthType = laplacianSharpenDialog.getDepthType();
                EnPaddingType paddingType = laplacianSharpenDialog.getPaddingType();
                laplacianSharpenDialog.dispose();
                imageCurrentVersion = MatAlgorithms.laplacianSharpening(imageCurrentVersion, sharpenType, depthType, paddingType);
                updateImage(imageCurrentVersion);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void conlove(ActionEvent e) {
        if (imageType == EnImageType.GRAYSCALE)
        {
            ConvolveDialog convolveDialog = new ConvolveDialog(this);
            if(convolveDialog.isConfirmed())
            {
                List<Integer> values = new ArrayList<Integer>();
                values.add(convolveDialog.getC1());
                values.add(convolveDialog.getC2());
                values.add(convolveDialog.getC3());
                values.add(convolveDialog.getC4());
                values.add(convolveDialog.getC5());
                values.add(convolveDialog.getC6());
                values.add(convolveDialog.getC7());
                values.add(convolveDialog.getC8());
                values.add(convolveDialog.getC9());
                EnDepthType depthType = convolveDialog.getDepthType();
                EnPaddingType paddingType = convolveDialog.getPaddingType();
                convolveDialog.dispose();
                imageCurrentVersion = MatAlgorithms.convolve(imageCurrentVersion, values, depthType, paddingType);
                updateImage(imageCurrentVersion);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void median(ActionEvent e) {
        if(imageType == EnImageType.GRAYSCALE)
        {
            MedianDialog medianDialog = new MedianDialog(this);
            if(medianDialog.isConfirmed())
            {
                int ksize = medianDialog.getKsize();
                EnPaddingType paddingType = medianDialog.getPaddingType();
                imageCurrentVersion = MatAlgorithms.medianFilter(imageCurrentVersion, ksize, paddingType);
                updateImage(imageCurrentVersion);
            }
            medianDialog.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getId()
    {
        return id;
    }

    public Mat getImageCurrentVersion()
    {
        return imageCurrentVersion;
    }

    private void thisWindowClosed(WindowEvent e) {
        mainForm.removeImageFormByKey(id);
    }

    private void erosion(ActionEvent e) {
        MorfologyDialog morfologyDialog = new MorfologyDialog(this, "Erosion");
        if(binaryType == EnBinaryType.BINARY)
        {
            if(morfologyDialog.isConfirmed())
            {
                int ksize = morfologyDialog.getKsize();
                EnPaddingType paddingType = morfologyDialog.getPaddingType();
                EnStructureType structureType = morfologyDialog.getStructureType();
                imageCurrentVersion = MatAlgorithms.erode(imageCurrentVersion, structureType, ksize, paddingType);
                updateImage(imageCurrentVersion);
            }
            morfologyDialog.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Binary", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dilation(ActionEvent e) {
        MorfologyDialog morfologyDialog = new MorfologyDialog(this, "Erosion");
        if(binaryType == EnBinaryType.BINARY)
        {
            if(morfologyDialog.isConfirmed())
            {
                int ksize = morfologyDialog.getKsize();
                EnPaddingType paddingType = morfologyDialog.getPaddingType();
                EnStructureType structureType = morfologyDialog.getStructureType();
                imageCurrentVersion = MatAlgorithms.dilate(imageCurrentVersion, structureType, ksize, paddingType);
                updateImage(imageCurrentVersion);
            }
            morfologyDialog.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Binary", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void opening(ActionEvent e) {
        MorfologyDialog morfologyDialog = new MorfologyDialog(this, "Erosion");
        if(binaryType == EnBinaryType.BINARY)
        {
            if(morfologyDialog.isConfirmed())
            {
                int ksize = morfologyDialog.getKsize();
                EnPaddingType paddingType = morfologyDialog.getPaddingType();
                EnStructureType structureType = morfologyDialog.getStructureType();
                imageCurrentVersion = MatAlgorithms.open(imageCurrentVersion, structureType, ksize, paddingType);
                updateImage(imageCurrentVersion);
            }
            morfologyDialog.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Binary", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closing(ActionEvent e) {
        MorfologyDialog morfologyDialog = new MorfologyDialog(this, "Erosion");
        if(binaryType == EnBinaryType.BINARY)
        {
            if(morfologyDialog.isConfirmed())
            {
                int ksize = morfologyDialog.getKsize();
                EnPaddingType paddingType = morfologyDialog.getPaddingType();
                EnStructureType structureType = morfologyDialog.getStructureType();
                imageCurrentVersion = MatAlgorithms.close(imageCurrentVersion, structureType, ksize, paddingType);
                updateImage(imageCurrentVersion);
            }
            morfologyDialog.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Binary", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void skeletonization(ActionEvent e) {
        MorfologyDialog morfologyDialog = new MorfologyDialog(this, "Erosion");
        if(binaryType == EnBinaryType.BINARY)
        {
            if(morfologyDialog.isConfirmed())
            {
                int ksize = morfologyDialog.getKsize();
                EnPaddingType paddingType = morfologyDialog.getPaddingType();
                EnStructureType structureType = morfologyDialog.getStructureType();
                imageCurrentVersion = MatAlgorithms.skeletonize(imageCurrentVersion, structureType, ksize, paddingType);
                updateImage(imageCurrentVersion);
            }
            morfologyDialog.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Binary", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manualThreshold(ActionEvent e) {
        if (imageType == EnImageType.GRAYSCALE)
        {
            ManualThresholdDialog manualThresholdDialog = new ManualThresholdDialog(this);
            if(manualThresholdDialog.isConfirmed())
            {
                int threshold = manualThresholdDialog.getThreshold();
                imageCurrentVersion = MatAlgorithms.manualThreshold(imageCurrentVersion, threshold);
                updateImage(imageCurrentVersion);
            }
            manualThresholdDialog.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adaptiveThreshold(ActionEvent e) {
        if(imageType == EnImageType.GRAYSCALE)
        {
            imageCurrentVersion = MatAlgorithms.adaptiveThreshold(imageCurrentVersion);
            updateImage(imageCurrentVersion);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void otsuThreshold(ActionEvent e) {
        if(imageType == EnImageType.GRAYSCALE)
        {
            imageCurrentVersion = MatAlgorithms.otsuThreshold(imageCurrentVersion);
            updateImage(imageCurrentVersion);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void profileLine(ActionEvent e) {

    }

    private void hough(ActionEvent e) {
        if(imageType == EnImageType.GRAYSCALE)
        {
            imageCurrentVersion = MatAlgorithms.hough(imageCurrentVersion);
            updateImage(imageCurrentVersion);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Image isn't Grayscale", "Error" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void piramid(ActionEvent e) {
        ImageForm imageForm75 = mainForm.duplicateImageFormAndReturn(this);
        ImageForm imageForm50 = mainForm.duplicateImageFormAndReturn(this);
        ImageForm imageForm125 = mainForm.duplicateImageFormAndReturn(this);
        ImageForm imageForm150 = mainForm.duplicateImageFormAndReturn(this);
        imageForm75.setScalePercent(75);
        imageForm75.updateImage(imageForm75.getImageCurrentVersion());
        imageForm50.setScalePercent(50);
        imageForm50.updateImage(imageForm50.getImageCurrentVersion());
        imageForm125.setScalePercent(125);
        imageForm125.updateImage(imageForm125.getImageCurrentVersion());
        imageForm150.setScalePercent(150);
        imageForm150.updateImage(imageForm150.getImageCurrentVersion());
    }

    public void setScalePercent(int scalePercent)
    {
        this.scalePercent = scalePercent;
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
        rgbToHsvMenuItem = new JMenuItem();
        rgbToLabMenuItem = new JMenuItem();
        rgbToRgbMenuItem = new JMenuItem();
        StrechMenuItem = new JMenuItem();
        equalizeMenuItem = new JMenuItem();
        negationMenuItem = new JMenuItem();
        StertchInRangeMenuItem = new JMenuItem();
        testMenuItem = new JMenuItem();
        menu3 = new JMenu();
        arithmeticMenu = new JMenu();
        addMenuItem = new JMenuItem();
        substractMenuItem = new JMenuItem();
        blendMenuItem = new JMenuItem();
        andMenuItem = new JMenuItem();
        orMenuItem = new JMenuItem();
        xorMenuItem = new JMenuItem();
        notMenuItem = new JMenuItem();
        posterizeMenuItem = new JMenuItem();
        blurMenuItem = new JMenuItem();
        gaussianBlurMenuItem = new JMenuItem();
        sobelMenuItem = new JMenuItem();
        laplacianMenuItem = new JMenuItem();
        cannyMenuItem = new JMenuItem();
        prewittMenuItem = new JMenuItem();
        laplacianSharpenMenuItem = new JMenuItem();
        conloveMenuItem = new JMenuItem();
        medianMenuItem = new JMenuItem();
        menu4 = new JMenu();
        erosionMenuItem = new JMenuItem();
        dilationMenuItem = new JMenuItem();
        openingMenuItem = new JMenuItem();
        closingMenuItem = new JMenuItem();
        skeletonizationMenuItem = new JMenuItem();
        profileLineMenuItem = new JMenuItem();
        houghMenuItem = new JMenuItem();
        piramidMenuItem = new JMenuItem();
        menu5 = new JMenu();
        menu6 = new JMenu();
        manualThresholdMenuItem = new JMenuItem();
        adaptiveThresholdMenuItem = new JMenuItem();
        otsuThresholdMenuItem = new JMenuItem();
        scrollPane1 = new JScrollPane();
        imageLabel = new JLabel();
        typeLabel = new JLabel();
        decreaseButton = new JButton();
        increaseButton = new JButton();
        sizeLabel = new JLabel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
        });
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

                //---- rgbToHsvMenuItem ----
                rgbToHsvMenuItem.setText("rgbToHsv");
                rgbToHsvMenuItem.addActionListener(e -> rgbToHsv(e));
                menu2.add(rgbToHsvMenuItem);

                //---- rgbToLabMenuItem ----
                rgbToLabMenuItem.setText("rgbToLab");
                rgbToLabMenuItem.addActionListener(e -> rgbToLab(e));
                menu2.add(rgbToLabMenuItem);

                //---- rgbToRgbMenuItem ----
                rgbToRgbMenuItem.setText("rgbToRgb");
                rgbToRgbMenuItem.addActionListener(e -> rgbToRgb(e));
                menu2.add(rgbToRgbMenuItem);

                //---- StrechMenuItem ----
                StrechMenuItem.setText("strech");
                StrechMenuItem.addActionListener(e -> stretch(e));
                menu2.add(StrechMenuItem);

                //---- equalizeMenuItem ----
                equalizeMenuItem.setText("equalize");
                equalizeMenuItem.addActionListener(e -> equalize(e));
                menu2.add(equalizeMenuItem);

                //---- negationMenuItem ----
                negationMenuItem.setText("negation");
                negationMenuItem.addActionListener(e -> negation(e));
                menu2.add(negationMenuItem);

                //---- StertchInRangeMenuItem ----
                StertchInRangeMenuItem.setText("StretchInRange");
                StertchInRangeMenuItem.addActionListener(e -> StertchInRange(e));
                menu2.add(StertchInRangeMenuItem);

                //---- testMenuItem ----
                testMenuItem.setText("test");
                testMenuItem.addActionListener(e -> testMenuItem(e));
                menu2.add(testMenuItem);
            }
            menuBar1.add(menu2);

            //======== menu3 ========
            {
                menu3.setText("Lab2");

                //======== arithmeticMenu ========
                {
                    arithmeticMenu.setText("arithmetic");

                    //---- addMenuItem ----
                    addMenuItem.setText("add");
                    addMenuItem.addActionListener(e -> add(e));
                    arithmeticMenu.add(addMenuItem);

                    //---- substractMenuItem ----
                    substractMenuItem.setText("substract");
                    substractMenuItem.addActionListener(e -> substract(e));
                    arithmeticMenu.add(substractMenuItem);

                    //---- blendMenuItem ----
                    blendMenuItem.setText("blend");
                    blendMenuItem.addActionListener(e -> blend(e));
                    arithmeticMenu.add(blendMenuItem);

                    //---- andMenuItem ----
                    andMenuItem.setText("and");
                    andMenuItem.addActionListener(e -> and(e));
                    arithmeticMenu.add(andMenuItem);

                    //---- orMenuItem ----
                    orMenuItem.setText("or");
                    orMenuItem.addActionListener(e -> or(e));
                    arithmeticMenu.add(orMenuItem);

                    //---- xorMenuItem ----
                    xorMenuItem.setText("xor");
                    xorMenuItem.addActionListener(e -> xor(e));
                    arithmeticMenu.add(xorMenuItem);

                    //---- notMenuItem ----
                    notMenuItem.setText("not");
                    notMenuItem.addActionListener(e -> not(e));
                    arithmeticMenu.add(notMenuItem);
                }
                menu3.add(arithmeticMenu);

                //---- posterizeMenuItem ----
                posterizeMenuItem.setText("posterize");
                posterizeMenuItem.addActionListener(e -> posterize(e));
                menu3.add(posterizeMenuItem);

                //---- blurMenuItem ----
                blurMenuItem.setText("blur");
                blurMenuItem.addActionListener(e -> blur(e));
                menu3.add(blurMenuItem);

                //---- gaussianBlurMenuItem ----
                gaussianBlurMenuItem.setText("gaussianBlur");
                gaussianBlurMenuItem.addActionListener(e -> gaussianBlur(e));
                menu3.add(gaussianBlurMenuItem);

                //---- sobelMenuItem ----
                sobelMenuItem.setText("sobel");
                sobelMenuItem.addActionListener(e -> sobel(e));
                menu3.add(sobelMenuItem);

                //---- laplacianMenuItem ----
                laplacianMenuItem.setText("laplacian");
                laplacianMenuItem.addActionListener(e -> laplacian(e));
                menu3.add(laplacianMenuItem);

                //---- cannyMenuItem ----
                cannyMenuItem.setText("canny");
                cannyMenuItem.addActionListener(e -> canny(e));
                menu3.add(cannyMenuItem);

                //---- prewittMenuItem ----
                prewittMenuItem.setText("prewitt");
                prewittMenuItem.addActionListener(e -> prewitt(e));
                menu3.add(prewittMenuItem);

                //---- laplacianSharpenMenuItem ----
                laplacianSharpenMenuItem.setText("laplacianSharpen");
                laplacianSharpenMenuItem.addActionListener(e -> laplacianSharpen(e));
                menu3.add(laplacianSharpenMenuItem);

                //---- conloveMenuItem ----
                conloveMenuItem.setText("convolve");
                conloveMenuItem.addActionListener(e -> conlove(e));
                menu3.add(conloveMenuItem);

                //---- medianMenuItem ----
                medianMenuItem.setText("median");
                medianMenuItem.addActionListener(e -> median(e));
                menu3.add(medianMenuItem);
            }
            menuBar1.add(menu3);

            //======== menu4 ========
            {
                menu4.setText("Lab3");

                //---- erosionMenuItem ----
                erosionMenuItem.setText("Erosion");
                erosionMenuItem.addActionListener(e -> erosion(e));
                menu4.add(erosionMenuItem);

                //---- dilationMenuItem ----
                dilationMenuItem.setText("Dilation");
                dilationMenuItem.addActionListener(e -> dilation(e));
                menu4.add(dilationMenuItem);

                //---- openingMenuItem ----
                openingMenuItem.setText("Opening");
                openingMenuItem.addActionListener(e -> opening(e));
                menu4.add(openingMenuItem);

                //---- closingMenuItem ----
                closingMenuItem.setText("Closing");
                closingMenuItem.addActionListener(e -> closing(e));
                menu4.add(closingMenuItem);

                //---- skeletonizationMenuItem ----
                skeletonizationMenuItem.setText("Skeletonization");
                skeletonizationMenuItem.addActionListener(e -> skeletonization(e));
                menu4.add(skeletonizationMenuItem);

                //---- profileLineMenuItem ----
                profileLineMenuItem.setText("Profile Line");
                profileLineMenuItem.addActionListener(e -> profileLine(e));
                menu4.add(profileLineMenuItem);

                //---- houghMenuItem ----
                houghMenuItem.setText("Hough");
                houghMenuItem.addActionListener(e -> hough(e));
                menu4.add(houghMenuItem);

                //---- piramidMenuItem ----
                piramidMenuItem.setText("Image Piramid");
                piramidMenuItem.addActionListener(e -> piramid(e));
                menu4.add(piramidMenuItem);
            }
            menuBar1.add(menu4);

            //======== menu5 ========
            {
                menu5.setText("Lab4");

                //======== menu6 ========
                {
                    menu6.setText("Segmentation");

                    //---- manualThresholdMenuItem ----
                    manualThresholdMenuItem.setText("Manual Threshold");
                    manualThresholdMenuItem.addActionListener(e -> manualThreshold(e));
                    menu6.add(manualThresholdMenuItem);

                    //---- adaptiveThresholdMenuItem ----
                    adaptiveThresholdMenuItem.setText("Adaptive Threshold");
                    adaptiveThresholdMenuItem.addActionListener(e -> adaptiveThreshold(e));
                    menu6.add(adaptiveThresholdMenuItem);

                    //---- otsuThresholdMenuItem ----
                    otsuThresholdMenuItem.setText("Otsu Threshold");
                    otsuThresholdMenuItem.addActionListener(e -> otsuThreshold(e));
                    menu6.add(otsuThresholdMenuItem);
                }
                menu5.add(menu6);
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
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                            .addContainerGap())
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(typeLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 279, Short.MAX_VALUE)
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
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
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
    private JMenuItem rgbToHsvMenuItem;
    private JMenuItem rgbToLabMenuItem;
    private JMenuItem rgbToRgbMenuItem;
    private JMenuItem StrechMenuItem;
    private JMenuItem equalizeMenuItem;
    private JMenuItem negationMenuItem;
    private JMenuItem StertchInRangeMenuItem;
    private JMenuItem testMenuItem;
    private JMenu menu3;
    private JMenu arithmeticMenu;
    private JMenuItem addMenuItem;
    private JMenuItem substractMenuItem;
    private JMenuItem blendMenuItem;
    private JMenuItem andMenuItem;
    private JMenuItem orMenuItem;
    private JMenuItem xorMenuItem;
    private JMenuItem notMenuItem;
    private JMenuItem posterizeMenuItem;
    private JMenuItem blurMenuItem;
    private JMenuItem gaussianBlurMenuItem;
    private JMenuItem sobelMenuItem;
    private JMenuItem laplacianMenuItem;
    private JMenuItem cannyMenuItem;
    private JMenuItem prewittMenuItem;
    private JMenuItem laplacianSharpenMenuItem;
    private JMenuItem conloveMenuItem;
    private JMenuItem medianMenuItem;
    private JMenu menu4;
    private JMenuItem erosionMenuItem;
    private JMenuItem dilationMenuItem;
    private JMenuItem openingMenuItem;
    private JMenuItem closingMenuItem;
    private JMenuItem skeletonizationMenuItem;
    private JMenuItem profileLineMenuItem;
    private JMenuItem houghMenuItem;
    private JMenuItem piramidMenuItem;
    private JMenu menu5;
    private JMenu menu6;
    private JMenuItem manualThresholdMenuItem;
    private JMenuItem adaptiveThresholdMenuItem;
    private JMenuItem otsuThresholdMenuItem;
    private JScrollPane scrollPane1;
    private JLabel imageLabel;
    private JLabel typeLabel;
    private JButton decreaseButton;
    private JButton increaseButton;
    private JLabel sizeLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
