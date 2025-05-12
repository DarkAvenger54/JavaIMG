package pl.edu.wsisiz.darkavenger54;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;

/**
 * @author Yevhenii Manuilov
 */

public class MatAlgorithms
{
    public static BufferedImage matToBufferedImage(Mat mat) {
        Mat converted = new Mat();
        if (mat.channels() == 3) {
            Imgproc.cvtColor(mat, converted, Imgproc.COLOR_BGR2RGB);
        } else {
            converted = mat;
        }
        int type = (converted.channels() > 1) ? BufferedImage.TYPE_3BYTE_BGR : BufferedImage.TYPE_BYTE_GRAY;
        int bufferSize = converted.channels() * converted.cols() * converted.rows();
        byte[] b = new byte[bufferSize];
        converted.get(0, 0, b);
        BufferedImage image = new BufferedImage(converted.cols(), converted.rows(), type);
        image.getRaster().setDataElements(0, 0, converted.cols(), converted.rows(), b);
        return image;
    }
    public static Mat rgbToGray(Mat currentImage)
    {
        if (currentImage.channels() == 3 || currentImage.channels() == 4) {
            Mat gray = new Mat();
            Imgproc.cvtColor(currentImage, gray, Imgproc.COLOR_BGR2GRAY);
            return gray;
        } else {
            return currentImage;
        }
    }
    public static Mat grayToRgb(Mat currentImage)
    {
        if (currentImage.channels() == 1)
        {
            Mat rgb = new Mat();
            Imgproc.cvtColor(currentImage, rgb, Imgproc.COLOR_BGR2RGB);
            return rgb;
        }
        else
        {
            return currentImage;
        }
    }

    public static void test(Mat currentImage)
    {

    }
}