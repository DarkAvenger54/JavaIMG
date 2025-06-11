package pl.edu.wsisiz.darkavenger54;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import pl.edu.wsisiz.darkavenger54.enums.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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

    public static int[] calculateGrayscaleHistogram(Mat grayImage)
    {
        Mat hist = new Mat();
        MatOfInt histSize = new MatOfInt(256); // 256 wartości odcieni szarości
        MatOfFloat histRange = new MatOfFloat(0, 256); // Zakres od 0 do 255
        Imgproc.calcHist(java.util.Collections.singletonList(grayImage), // lista wejściowych obrazów
                new MatOfInt(0), // indeks kanału (0 - tylko jeden kanał dla skali szarości)
                new Mat(),       // maska – pusta (cały obraz)
                hist,            // wynik
                histSize,        // rozmiar histogramu
                histRange );
        int[] histogram = new int[256];
        for (int i = 0; i < 256; i++) {
            histogram[i] = (int) hist.get(i, 0)[0];
        }
        return histogram;
    }

    public static List<Mat> convertRGBtoGrayChannels(Mat rgbImage)
    {
        List<Mat> channels = new ArrayList<>();
        Core.split(rgbImage, channels);
        return channels;
    }

    public static List<Mat> convertRGBtoHSVChannels(Mat rgbImage)
    {
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(rgbImage, hsvImage, Imgproc.COLOR_RGB2HSV);
        List<Mat> hsvChannels = new ArrayList<Mat>();
        Core.split(hsvImage, hsvChannels);
        return hsvChannels;
    }
    public static List<Mat> convertRGBtoLabChannels(Mat rgbImage)
    {
        Mat labImage = new Mat();
        Imgproc.cvtColor(rgbImage, labImage, Imgproc.COLOR_RGB2Lab);
        List<Mat> labChannels = new ArrayList<Mat>();
        Core.split(labImage, labChannels);
        return labChannels;
    }

    public static Mat histogramStretch(Mat input) {
        Mat result = input.clone();

        for (int channel = 0; channel < input.channels(); channel++) {
            int min = 255;
            int max = 0;

            // Находим min и max для текущего канала
            for (int y = 0; y < input.rows(); y++) {
                for (int x = 0; x < input.cols(); x++) {
                    double[] pixel = input.get(y, x);
                    int val = (int) pixel[channel];
                    if (val < min) min = val;
                    if (val > max) max = val;
                }
            }

            // Применяем растяжение
            for (int y = 0; y < input.rows(); y++) {
                for (int x = 0; x < input.cols(); x++) {
                    double[] pixel = result.get(y, x);
                    pixel[channel] = 255.0 * (pixel[channel] - min) / (max - min);
                    result.put(y, x, pixel);
                }
            }
        }
        return result;
    }

    public static Mat histogramEqualization(Mat input) {
        Mat result = input.clone();

        for (int channel = 0; channel < input.channels(); channel++) {
            int[] hist = new int[256];
            int totalPixels = input.rows() * input.cols();

            // Вычисление гистограммы
            for (int y = 0; y < input.rows(); y++) {
                for (int x = 0; x < input.cols(); x++) {
                    double[] pixel = input.get(y, x);
                    hist[(int) pixel[channel]]++;
                }
            }

            // Вычисление CDF
            int[] cdf = new int[256];
            cdf[0] = hist[0];
            for (int i = 1; i < 256; i++) {
                cdf[i] = cdf[i - 1] + hist[i];
            }

            // Нормализация CDF
            double[] cdfNorm = new double[256];
            for (int i = 0; i < 256; i++) {
                cdfNorm[i] = (cdf[i] - cdf[0]) * 255.0 / (totalPixels - cdf[0]);
                if (cdfNorm[i] < 0) cdfNorm[i] = 0;
            }

            // Применяем equalizację
            for (int y = 0; y < input.rows(); y++) {
                for (int x = 0; x < input.cols(); x++) {
                    double[] pixel = result.get(y, x);
                    pixel[channel] = cdfNorm[(int) pixel[channel]];
                    result.put(y, x, pixel);
                }
            }
        }
        return result;
    }
    public static Mat negation(Mat input) {
        Mat result = new Mat(input.rows(), input.cols(), input.type());
        for (int y = 0; y < input.rows(); y++) {
            for (int x = 0; x < input.cols(); x++) {
                double[] pixel = input.get(y, x);
                for (int i = 0; i < pixel.length; i++) {
                    pixel[i] = 255 - pixel[i];
                }
                result.put(y, x, pixel);
            }
        }
        return result;
    }

    public static Mat stretch1(Mat input, int p1, int p2, int q3, int q4)
    {
        Mat result = new Mat(input.rows(), input.cols(), input.type());

        for (int y = 0; y < input.rows(); y++) {
            for (int x = 0; x < input.cols(); x++) {
                double[] pixel = input.get(y, x);
                for (int i = 0; i < pixel.length; i++) {
                    double value = pixel[i];
                    if (value < p1) {
                        value = q3;
                    } else if (value > p2) {
                        value = q4;
                    } else {
                        value = q3 + (value - p1) * (q4 - q3) / (double)(p2 - p1);
                    }
                    pixel[i] = Math.max(0, Math.min(255, value));
                }
                result.put(y, x, pixel);
            }
        }
        return result;
    }
    public static Mat posterize(Mat src, int levels) {
        Mat result = new Mat(src.rows(), src.cols(), src.type());

        // Расчёт размера одного уровня
        int levelSize = 256 / levels;

        for (int row = 0; row < src.rows(); row++) {
            for (int col = 0; col < src.cols(); col++) {
                double[] pixel = src.get(row, col);
                int gray = (int) pixel[0];

                // Определяем ближайший уровень
                int posterizedGray = (gray / levelSize) * levelSize;

                // Обеспечиваем границу не выше 255
                if (posterizedGray > 255) posterizedGray = 255;

                result.put(row, col, posterizedGray);
            }
        }

        return result;
    }
    public static Mat blur(Mat src, int ksize) {
        Mat dst = new Mat();
        Size kernelSize = new Size(ksize, ksize);
        Imgproc.blur(src, dst, kernelSize);
        return dst;
    }
    public static Mat gaussianBlur(Mat input, int ksize, double sigmaX, double sigmaY, EnPaddingType paddingType) {
        int borderType;
        Size kernelSize = new Size(ksize, ksize);
        switch (paddingType) {
            case REPLICATE:
                borderType = Core.BORDER_REPLICATE;
                break;
            case ISOLATED:
                borderType = Core.BORDER_ISOLATED;
                break;
            case REFLECT:
                borderType = Core.BORDER_REFLECT;
                break;
            default:
                borderType = Core.BORDER_DEFAULT;
                break;
        }
        Mat output = new Mat();
        Imgproc.GaussianBlur(input, output, kernelSize, sigmaX, sigmaY, borderType);
        return output;
    }
    public static Mat sobel(Mat input, int ksize, EnPaddingType paddingType, EnDepthType depthType) {
        // Определение глубины
        int depth;
        switch (depthType) {
            case U8:
                depth = CvType.CV_8U;
                break;
            case F64:
                depth = CvType.CV_64F;
                break;
            default:
                throw new IllegalArgumentException("Unsupported depth type");
        }
        // Определение типа границы
        int borderType;
        switch (paddingType) {
            case REPLICATE:
                borderType = Core.BORDER_REPLICATE;
                break;
            case ISOLATED:
                borderType = Core.BORDER_ISOLATED;
                break;
            case REFLECT:
                borderType = Core.BORDER_REFLECT;
                break;
            default:
                throw new IllegalArgumentException("Unsupported padding type");
        }
        // Вычисление градиентов
        Mat gradX = new Mat();
        Mat gradY = new Mat();
        Imgproc.Sobel(input, gradX, depth, 1, 0, ksize, 1, 0, borderType);
        Imgproc.Sobel(input, gradY, depth, 0, 1, ksize, 1, 0, borderType);
        // Модули градиентов
        Mat absGradX = new Mat();
        Mat absGradY = new Mat();
        Core.convertScaleAbs(gradX, absGradX);
        Core.convertScaleAbs(gradY, absGradY);
        // Комбинирование градиентов
        Mat sobelResult = new Mat();
        Core.addWeighted(absGradX, 0.5, absGradY, 0.5, 0, sobelResult);
        return sobelResult;
    }
    public static Mat laplacian(Mat input,  int ksize, EnPaddingType paddingType, EnDepthType depthType) {
        // Глубина
        int depth;
        switch (depthType) {
            case U8:
                depth = CvType.CV_8U;
                break;
            case F64:
                depth = CvType.CV_64F;
                break;
            default:
                throw new IllegalArgumentException("Unsupported depth type");
        }
        // Тип границы
        int borderType;
        switch (paddingType) {
            case REPLICATE:
                borderType = Core.BORDER_REPLICATE;
                break;
            case ISOLATED:
                borderType = Core.BORDER_ISOLATED;
                break;
            case REFLECT:
                borderType = Core.BORDER_REFLECT;
                break;
            default:
                throw new IllegalArgumentException("Unsupported padding type");
        }
        // Применяем Лаплас
        Mat laplacian = new Mat();
        Imgproc.Laplacian(input, laplacian, depth, ksize, 1, 0, borderType);
        // Приведение к 8-битному диапазону для отображения (если надо)
        Mat absLaplacian = new Mat();
        Core.convertScaleAbs(laplacian, absLaplacian);
        return absLaplacian;
    }
    public static Mat canny(Mat input, double threshold1, double threshold2) {
        // Результат
        Mat edges = new Mat();
        // Применение алгоритма Канни
        Imgproc.Canny(input, edges, threshold1, threshold2);
        return edges;
    }
    public static Mat prewitt(Mat input, EnDirectionType direction, EnDepthType depthType, EnPaddingType paddingType) {
        // Глубина
        int depth;
        switch (depthType) {
            case U8:
                depth = CvType.CV_8U;
                break;
            case F64:
                depth = CvType.CV_64F;
                break;
            default:
                throw new IllegalArgumentException("Unsupported depth type");
        }

        // Тип границ
        int borderType;
        switch (paddingType) {
            case REPLICATE:
                borderType = Core.BORDER_REPLICATE;
                break;
            case ISOLATED:
                borderType = Core.BORDER_ISOLATED;
                break;
            case REFLECT:
                borderType = Core.BORDER_REFLECT;
                break;
            default:
                throw new IllegalArgumentException("Unsupported padding type");
        }

        // Получение ядра Превитта по направлению
        Mat kernel = getPrewittKernel(direction);
        // Применение фильтра
        Mat result = new Mat();
        Imgproc.filter2D(input, result, depth, kernel, new Point(-1, -1), 0, borderType);
        // Приведение к 8-битам, если нужно
        if (depth != CvType.CV_8U) {
            Mat absResult = new Mat();
            Core.convertScaleAbs(result, absResult);
            return absResult;
        } else {
            return result;
        }
    }

    private static Mat getPrewittKernel(EnDirectionType direction) {
        // Стандартные 3x3 ядра Превитта по направлениям
        switch (direction) {
            case NORTH:
                return new MatOfFloat(
                        -1, -1, -1,
                        0,  0,  0,
                        1,  1,  1
                ).reshape(1, 3);
            case SOUTH:
                return new MatOfFloat(
                        1,  1,  1,
                        0,  0,  0,
                        -1, -1, -1
                ).reshape(1, 3);
            case EAST:
                return new MatOfFloat(
                        -1, 0, 1,
                        -1, 0, 1,
                        -1, 0, 1
                ).reshape(1, 3);
            case WEST:
                return new MatOfFloat(
                        1, 0, -1,
                        1, 0, -1,
                        1, 0, -1
                ).reshape(1, 3);
            case NORTH_EAST:
                return new MatOfFloat(
                        -1, -1,  0,
                        -1,  0,  1,
                        0,  1,  1
                ).reshape(1, 3);
            case NORTH_WEST:
                return new MatOfFloat(
                        0, -1, -1,
                        1,  0, -1,
                        1,  1,  0
                ).reshape(1, 3);
            case SOUTH_EAST:
                return new MatOfFloat(
                        0,  1,  1,
                        -1,  0,  1,
                        -1, -1,  0
                ).reshape(1, 3);
            case SOUTH_WEST:
                return new MatOfFloat(
                        1,  1,  0,
                        1,  0, -1,
                        0, -1, -1
                ).reshape(1, 3);
            default:
                throw new IllegalArgumentException("Unsupported direction");
        }
    }
    public static Mat laplacianSharpening(Mat input, EnSharpenType sharpenType, EnDepthType depthType, EnPaddingType paddingType) {
        // Глубина
        int depth;
        switch (depthType) {
            case U8:
                depth = CvType.CV_8U;
                break;
            case F64:
                depth = CvType.CV_64F;
                break;
            default:
                throw new IllegalArgumentException("Unsupported depth type");
        }
        // Тип границы
        int borderType;
        switch (paddingType) {
            case REPLICATE:
                borderType = Core.BORDER_REPLICATE;
                break;
            case ISOLATED:
                borderType = Core.BORDER_ISOLATED;
                break;
            case REFLECT:
                borderType = Core.BORDER_REFLECT;
                break;
            default:
                throw new IllegalArgumentException("Unsupported padding type");
        }
        // Выбор ядра маски
        Mat kernel = getSharpenKernel(sharpenType);
        // Применение фильтра
        Mat filtered = new Mat();
        Imgproc.filter2D(input, filtered, depth, kernel, new Point(-1, -1), 0, borderType);

        // Приведение к 8-битам при необходимости
        if (depth != CvType.CV_8U) {
            Mat absFiltered = new Mat();
            Core.convertScaleAbs(filtered, absFiltered);
            return absFiltered;
        } else {
            return filtered;
        }
    }

    private static Mat getSharpenKernel(EnSharpenType sharpenType) {
        switch (sharpenType) {
            case CROSS_5:
                return new MatOfFloat(
                        0, -1,  0,
                        -1,  5, -1,
                        0, -1,  0
                ).reshape(1, 3);
            case SQUARE_5:
                return new MatOfFloat(
                        1, -2,  1,
                        -2,  5, -2,
                        1, -2,  1
                ).reshape(1, 3);
            case SQUARE_9:
                return new MatOfFloat(
                        -1, -1, -1,
                        -1,  9, -1,
                        -1, -1, -1
                ).reshape(1, 3);
            default:
                throw new IllegalArgumentException("Unsupported sharpen type");
        }
    }
    public static Mat convolve(Mat input, List<Integer> maskValues, EnDepthType depthType, EnPaddingType paddingType) {
        // Проверка размера
        if (maskValues == null || maskValues.size() != 9) {
            throw new IllegalArgumentException("Mask must contain exactly 9 values (3x3)");
        }
        // Глубина
        int depth;
        switch (depthType) {
            case U8:
                depth = CvType.CV_8U;
                break;
            case F64:
                depth = CvType.CV_64F;
                break;
            default:
                throw new IllegalArgumentException("Unsupported depth type");
        }
        // Границы
        int borderType;
        switch (paddingType) {
            case REPLICATE:
                borderType = Core.BORDER_REPLICATE;
                break;
            case ISOLATED:
                borderType = Core.BORDER_ISOLATED;
                break;
            case REFLECT:
                borderType = Core.BORDER_REFLECT;
                break;
            default:
                throw new IllegalArgumentException("Unsupported padding type");
        }

        // Создание ядра 3x3
        Mat kernel = new Mat(3, 3, CvType.CV_32F);
        for (int i = 0; i < 9; i++) {
            kernel.put(i / 3, i % 3, maskValues.get(i));
        }

        // Применение фильтра
        Mat filtered = new Mat();
        Imgproc.filter2D(input, filtered, depth, kernel, new Point(-1, -1), 0, borderType);

        // Приведение к 8-битному диапазону при необходимости
        if (depth != CvType.CV_8U) {
            Mat absFiltered = new Mat();
            Core.convertScaleAbs(filtered, absFiltered);
            return absFiltered;
        } else {
            return filtered;
        }
    }
    public static Mat medianFilter(Mat input, int ksize, EnPaddingType paddingType) {
        // Выбор типа границы
        int borderType;
        switch (paddingType) {
            case REPLICATE:
                borderType = Core.BORDER_REPLICATE;
                break;
            case ISOLATED:
                borderType = Core.BORDER_ISOLATED;
                break;
            case REFLECT:
                borderType = Core.BORDER_REFLECT;
                break;
            default:
                throw new IllegalArgumentException("Unsupported padding type");
        }
        // Расширение изображения
        int pad = ksize / 2;
        Mat padded = new Mat();
        Core.copyMakeBorder(input, padded, pad, pad, pad, pad, borderType);
        // Применение медианного фильтра
        Mat blurred = new Mat();
        Imgproc.medianBlur(padded, blurred, ksize);
        // Обрезаем результат до оригинального размера
        Rect roi = new Rect(pad, pad, input.cols(), input.rows());
        return new Mat(blurred, roi).clone();
    }
    public static Mat add(Mat a, Mat b) {
        if (!a.size().equals(b.size()) || a.type() != b.type()) {
            throw new IllegalArgumentException("Images must be the same size and type for addition");
        }

        Mat result = new Mat();
        Core.add(a, b, result);
        return result;
    }
    public static Mat subtract(Mat a, Mat b) {
        if (!a.size().equals(b.size()) || a.type() != b.type()) {
            throw new IllegalArgumentException("Images must be the same size and type for subtraction");
        }

        Mat result = new Mat();
        Core.subtract(a, b, result);
        return result;
    }
    public static Mat blend(Mat a, Mat b, double alpha) {
        if (!a.size().equals(b.size()) || a.type() != b.type()) {
            throw new IllegalArgumentException("Images must be the same size and type for blending");
        }
        if (alpha < 0 || alpha > 1) {
            throw new IllegalArgumentException("Alpha must be between 0 and 1");
        }

        Mat result = new Mat();
        Core.addWeighted(a, alpha, b, 1.0 - alpha, 0.0, result);
        return result;
    }
    public static Mat bitwiseAnd(Mat a, Mat b) {
        if (!a.size().equals(b.size()) || a.type() != b.type()) {
            throw new IllegalArgumentException("Images must be the same size and type for bitwise AND");
        }

        Mat result = new Mat();
        Core.bitwise_and(a, b, result);
        return result;
    }
    public static Mat bitwiseOr(Mat a, Mat b) {
        if (!a.size().equals(b.size()) || a.type() != b.type()) {
            throw new IllegalArgumentException("Images must be the same size and type for bitwise OR");
        }

        Mat result = new Mat();
        Core.bitwise_or(a, b, result);
        return result;
    }
    public static Mat bitwiseXor(Mat a, Mat b) {
        if (!a.size().equals(b.size()) || a.type() != b.type()) {
            throw new IllegalArgumentException("Images must be the same size and type for bitwise XOR");
        }

        Mat result = new Mat();
        Core.bitwise_xor(a, b, result);
        return result;
    }
    public static Mat bitwiseNot(Mat a) {
        Mat result = new Mat();
        Core.bitwise_not(a, result);
        return result;
    }
    public static Mat createStructuringElement(EnStructureType type, int size) {
        if (size % 2 == 0 || size < 1)
            throw new IllegalArgumentException("Size must be an odd number ≥ 1");

        Mat kernel = Mat.zeros(size, size, CvType.CV_8U);
        int center = size / 2;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (type == EnStructureType.SQUARE) {
                    kernel.put(y, x, 1); // full square
                } else if (type == EnStructureType.RHOMBUS) {
                    if (Math.abs(x - center) + Math.abs(y - center) <= center) {
                        kernel.put(y, x, 1); // diamond shape
                    }
                }
            }
        }
        return kernel;
    }
    private static int toBorderType(EnPaddingType paddingType) {
        switch (paddingType) {
            case REPLICATE:
                return Core.BORDER_REPLICATE;
            case REFLECT:
                return Core.BORDER_REFLECT;
            case ISOLATED:
                return Core.BORDER_ISOLATED;
            default:
                throw new IllegalArgumentException("Unsupported padding type");
        }
    }
    public static Mat erode(Mat input, EnStructureType type, int size, EnPaddingType paddingType) {
        Mat kernel = createStructuringElement(type, size);
        Mat output = new Mat();
        Imgproc.erode(input, output, kernel, new Point(-1, -1), 1, toBorderType(paddingType), new Scalar(0));
        return output;
    }
    public static Mat dilate(Mat input, EnStructureType type, int size, EnPaddingType paddingType) {
        Mat kernel = createStructuringElement(type, size);
        Mat output = new Mat();
        Imgproc.dilate(input, output, kernel, new Point(-1, -1), 1, toBorderType(paddingType), new Scalar(0));
        return output;
    }
    public static Mat open(Mat input, EnStructureType type, int size, EnPaddingType paddingType) {
        Mat kernel = createStructuringElement(type, size);
        Mat output = new Mat();
        Imgproc.morphologyEx(input, output, Imgproc.MORPH_OPEN, kernel, new Point(-1, -1), 1, toBorderType(paddingType), new Scalar(0));
        return output;
    }
    public static Mat close(Mat input, EnStructureType type, int size, EnPaddingType paddingType) {
        Mat kernel = createStructuringElement(type, size);
        Mat output = new Mat();
        Imgproc.morphologyEx(input, output, Imgproc.MORPH_CLOSE, kernel, new Point(-1, -1), 1, toBorderType(paddingType), new Scalar(0));
        return output;
    }

    public static Mat skeletonize(Mat input, EnStructureType structureType, int size, EnPaddingType paddingType) {
        // Проверка входного изображения
        if (input.type() != CvType.CV_8UC1) {
            throw new IllegalArgumentException("Input must be a binary image (CV_8UC1)");
        }
        // Получаем ядро
        Mat kernel = createStructuringElement(structureType, size);
        int borderType = toBorderType(paddingType);
        // Подготовка
        Mat img = input.clone();
        Mat skel = Mat.zeros(img.size(), CvType.CV_8UC1);
        Mat temp = new Mat();
        Mat eroded = new Mat();
        // Итерационный процесс
        boolean done;
        do {
            Imgproc.erode(img, eroded, kernel, new Point(-1, -1), 1, borderType, new Scalar(0));
            Imgproc.dilate(eroded, temp, kernel, new Point(-1, -1), 1, borderType, new Scalar(0));
            Core.subtract(img, temp, temp);
            Core.bitwise_or(skel, temp, skel);
            eroded.copyTo(img);
            done = Core.countNonZero(img) == 0;
        } while (!done);
        return skel;
    }
    public static Mat manualThreshold(Mat input, int thresholdValue) {
        Mat result = new Mat();
        Imgproc.threshold(input, result, thresholdValue, 255, Imgproc.THRESH_BINARY);
        return result;
    }
    public static Mat adaptiveThreshold(Mat input) {
        Mat result = new Mat();
        Imgproc.adaptiveThreshold(
                input,
                result,
                255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, // или MEAN_C
                Imgproc.THRESH_BINARY,
                11,  // blockSize (должен быть нечетный)
                2    // значение C
        );
        return result;
    }
    public static Mat otsuThreshold(Mat input) {
        Mat result = new Mat();
        Imgproc.threshold(input, result, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
        return result;
    }
    public static Mat hough(Mat input) {
        Mat edges = new Mat();
        Imgproc.Canny(input, edges, 50, 150);

        Mat lines = new Mat();
        Imgproc.HoughLinesP(edges, lines,
                1,
                Math.PI / 180,
                80,
                30,
                10
        );

        Mat result = new Mat();
        Imgproc.cvtColor(input, result, Imgproc.COLOR_GRAY2BGR);

        for (int i = 0; i < lines.rows(); i++) {
            double[] line = lines.get(i, 0);
            Point pt1 = new Point(line[0], line[1]);
            Point pt2 = new Point(line[2], line[3]);
            Imgproc.line(result, pt1, pt2, new Scalar(0, 0, 255), 2);
        }

        return result;
    }
    public static Mat convexHull(Mat binary) {
        // Находим контуры
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Создаём пустое изображение (чёрный фон)
        Mat result = Mat.zeros(binary.size(), CvType.CV_8UC1);

        for (MatOfPoint contour : contours) {
            MatOfInt hull = new MatOfInt();
            Imgproc.convexHull(contour, hull);

            // Переводим индексы в реальные точки
            Point[] contourArray = contour.toArray();
            List<Point> hullPoints = new ArrayList<>();
            for (int index : hull.toArray()) {
                hullPoints.add(contourArray[index]);
            }

            // Рисуем выпуклую оболочку
            MatOfPoint hullMat = new MatOfPoint();
            hullMat.fromList(hullPoints);
            List<MatOfPoint> hullList = new ArrayList<>();
            hullList.add(hullMat);
            Imgproc.drawContours(result, hullList, -1, new Scalar(255), -1);  // заливка оболочки
        }
        return result;
    }
    public static List<int[]> getProfileLine(Mat mat, Point p1, Point p2) {
        if (mat.channels() != 1) {
            throw new IllegalArgumentException("Image must be grayscale (CV_8UC1)");
        }

        List<int[]> profile = new ArrayList<>();

        int x0 = (int) p1.x;
        int y0 = (int) p1.y;
        int x1 = (int) p2.x;
        int y1 = (int) p2.y;

        int dx = Math.abs(x1 - x0);
        int dy = -Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx + dy;

        while (true) {
            if (x0 >= 0 && y0 >= 0 && x0 < mat.cols() && y0 < mat.rows()) {
                double[] pixel = mat.get(y0, x0); // OpenCV uses row (y), then col (x)
                int luminocity = (int) pixel[0];
                profile.add(new int[] { x0, y0, luminocity });
            }

            if (x0 == x1 && y0 == y1) break;

            int e2 = 2 * err;
            if (e2 >= dy) {
                err += dy;
                x0 += sx;
            }
            if (e2 <= dx) {
                err += dx;
                y0 += sy;
            }
        }

        return profile;
    }

}