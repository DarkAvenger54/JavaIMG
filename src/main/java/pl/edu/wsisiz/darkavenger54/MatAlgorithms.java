package pl.edu.wsisiz.darkavenger54;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import pl.edu.wsisiz.darkavenger54.enums.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa zawiera zestaw metod do przetwarzania obrazów Mat przy użyciu OpenCV.
 * Obejmuje operacje filtrowania, progowania, morfologii, konwersji kolorów
 * i inne operacje typowe dla cyfrowego przetwarzania obrazów.
 * <p>
 * @author Yevhenii Manuilov
 */

public class MatAlgorithms
{

    /**
     * Konwertuje obraz Mat (OpenCV) na obiekt BufferedImage.
     * Obsługuje zarówno obrazy 1-kanałowe (skala szarości), jak i 3-kanałowe (RGB).
     *
     * @param mat obraz Mat
     * @return obraz w formacie BufferedImage
     */
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
    /**
     * Konwertuje obraz RGB na obraz w skali szarości.
     *
     * @param currentImage wejściowy obraz Mat
     * @return obraz w skali szarości
     */
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
    /**
     * Konwertuje obraz w skali szarości na RGB.
     *
     * @param currentImage wejściowy obraz Mat
     * @return obraz RGB
     */
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
    /**
     * Oblicza histogram intensywności pikseli dla obrazu w skali szarości.
     *
     * @param grayImage obraz wejściowy
     * @return tablica rozmiaru 256 zliczająca liczbę pikseli
     */
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
    /**
     * Rozdziela obraz RGB na poszczególne kanały (R, G, B).
     *
     * @param rgbImage wejściowy obraz RGB
     * @return lista kanałów
     */
    public static List<Mat> convertRGBtoGrayChannels(Mat rgbImage)
    {
        List<Mat> channels = new ArrayList<>();
        Core.split(rgbImage, channels);
        return channels;
    }
    /**
     * Konwertuje obraz RGB na przestrzeń HSV i rozdziela na kanały.
     *
     * @param rgbImage wejściowy obraz RGB
     * @return lista kanałów HSV
     */
    public static List<Mat> convertRGBtoHSVChannels(Mat rgbImage)
    {
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(rgbImage, hsvImage, Imgproc.COLOR_RGB2HSV);
        List<Mat> hsvChannels = new ArrayList<Mat>();
        Core.split(hsvImage, hsvChannels);
        return hsvChannels;
    }
    /**
     * Konwertuje obraz RGB na przestrzeń Lab i rozdziela na kanały.
     *
     * @param rgbImage wejściowy obraz RGB
     * @return lista kanałów Lab
     */
    public static List<Mat> convertRGBtoLabChannels(Mat rgbImage)
    {
        Mat labImage = new Mat();
        Imgproc.cvtColor(rgbImage, labImage, Imgproc.COLOR_RGB2Lab);
        List<Mat> labChannels = new ArrayList<Mat>();
        Core.split(labImage, labChannels);
        return labChannels;
    }
    /**
     * Wykonuje rozciąganie histogramu (normalizację) obrazu.
     *
     * @param input obraz wejściowy
     * @return obraz po rozciągnięciu histogramu
     */
    public static Mat histogramStretch(Mat input) {
        Mat result = input.clone();

        for (int channel = 0; channel < input.channels(); channel++) {
            int min = 255;
            int max = 0;

            // Szukanie minimalnej i maksymalnej wartości
            for (int y = 0; y < input.rows(); y++) {
                for (int x = 0; x < input.cols(); x++) {
                    double[] pixel = input.get(y, x);
                    int val = (int) pixel[channel];
                    if (val < min) min = val;
                    if (val > max) max = val;
                }
            }

            // Przekształcenie piksela do nowego przedziału
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
    /**
     * Wykonuje wyrównanie histogramu na obrazie.
     *
     * @param input obraz wejściowy
     * @return obraz po wyrównaniu histogramu
     */
    public static Mat histogramEqualization(Mat input) {
        Mat result = input.clone();

        for (int channel = 0; channel < input.channels(); channel++) {
            int[] hist = new int[256];
            int totalPixels = input.rows() * input.cols();

            // Tworzenie histogramu
            for (int y = 0; y < input.rows(); y++) {
                for (int x = 0; x < input.cols(); x++) {
                    double[] pixel = input.get(y, x);
                    hist[(int) pixel[channel]]++;
                }
            }

            // Tworzenie dystrybuanty (CDF) i normalizacja
            int[] cdf = new int[256];
            cdf[0] = hist[0];
            for (int i = 1; i < 256; i++) {
                cdf[i] = cdf[i - 1] + hist[i];
            }

            double[] cdfNorm = new double[256];
            for (int i = 0; i < 256; i++) {
                cdfNorm[i] = (cdf[i] - cdf[0]) * 255.0 / (totalPixels - cdf[0]);
                if (cdfNorm[i] < 0) cdfNorm[i] = 0;
            }

            // Przekształcenie pikseli
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
    /**
     * Wykonuje negację obrazu (odwrócenie wartości intensywności).
     *
     * @param input obraz wejściowy
     * @return obraz po negacji
     */
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
    /**
     * Wykonuje rozciąganie intensywności pikseli poprzez ustawienie nowego
     * przedziału wartości (p1, p2) na (q3, q4).
     *
     * @param input obraz wejściowy
     * @param p1 minimalny próg wejściowy
     * @param p2 maksymalny próg wejściowy
     * @param q3 minimalny próg wyjściowy
     * @param q4 maksymalny próg wyjściowy
     * @return obraz po rozciągnięciu intensywności
     */
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
    /**
     * Redukuje liczbę intensywności (poziomów) poprzez posterizację obrazu.
     *
     * @param src wejściowy obraz
     * @param levels liczba poziomów intensywności
     * @return obraz po posterizacji
     */
    public static Mat posterize(Mat src, int levels) {
        Mat result = new Mat(src.rows(), src.cols(), src.type());

        int levelSize = 256 / levels;

        for (int row = 0; row < src.rows(); row++) {
            for (int col = 0; col < src.cols(); col++) {
                double[] pixel = src.get(row, col);
                int gray = (int) pixel[0];

                int posterizedGray = (gray / levelSize) * levelSize;

                if (posterizedGray > 255) posterizedGray = 255;

                result.put(row, col, posterizedGray);
            }
        }

        return result;
    }

    /**
     * Wykonuje rozmycie obrazu poprzez filtrację uśredniającą.
     *
     * @param src obraz wejściowy
     * @param ksize rozmiar jądra rozmywania
     * @return rozmyty obraz
     */
    public static Mat blur(Mat src, int ksize) {
        Mat dst = new Mat();
        Size kernelSize = new Size(ksize, ksize);
        Imgproc.blur(src, dst, kernelSize);
        return dst;
    }
    /**
     * Wykonuje rozmycie gaussowskie obrazu.
     *
     * @param input obraz wejściowy
     * @param ksize rozmiar jądra
     * @param sigmaX odchylenie standardowe w kierunku X
     * @param sigmaY odchylenie standardowe w kierunku Y
     * @param paddingType typ wypełniania krawędzi
     * @return rozmyty obraz
     */
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

    /**
     * Wykonuje filtr Sobela, obliczając gradient obrazu.
     *
     * @param input obraz wejściowy
     * @param ksize rozmiar jądra
     * @param paddingType typ wypełniania krawędzi
     * @param depthType typ głębi obrazu
     * @return obraz po filtracji Sobela
     */
    public static Mat sobel(Mat input, int ksize, EnPaddingType paddingType, EnDepthType depthType) {
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
        Mat gradX = new Mat();
        Mat gradY = new Mat();
        Imgproc.Sobel(input, gradX, depth, 1, 0, ksize, 1, 0, borderType);
        Imgproc.Sobel(input, gradY, depth, 0, 1, ksize, 1, 0, borderType);
        Mat absGradX = new Mat();
        Mat absGradY = new Mat();
        Core.convertScaleAbs(gradX, absGradX);
        Core.convertScaleAbs(gradY, absGradY);
        Mat sobelResult = new Mat();
        Core.addWeighted(absGradX, 0.5, absGradY, 0.5, 0, sobelResult);
        return sobelResult;
    }
    /**
     * Wykonuje filtrację Laplace'a na obrazie.
     *
     * @param input obraz wejściowy
     * @param ksize rozmiar jądra
     * @param paddingType typ wypełniania krawędzi
     * @param depthType typ głębi obrazu
     * @return obraz po filtracji Laplace'a
     */
    public static Mat laplacian(Mat input,  int ksize, EnPaddingType paddingType, EnDepthType depthType) {
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
        Mat laplacian = new Mat();
        Imgproc.Laplacian(input, laplacian, depth, ksize, 1, 0, borderType);
        Mat absLaplacian = new Mat();
        Core.convertScaleAbs(laplacian, absLaplacian);
        return absLaplacian;
    }
    /**
     * Wykonuje algorytm detekcji krawędzi Canny'ego.
     *
     * @param input obraz wejściowy
     * @param threshold1 dolny próg
     * @param threshold2 górny próg
     * @return obraz z wykrytymi krawędziami
     */
    public static Mat canny(Mat input, double threshold1, double threshold2) {
        Mat edges = new Mat();
        Imgproc.Canny(input, edges, threshold1, threshold2);
        return edges;
    }

    /**
     * Wykonuje filtrację Prewitt'a, aby wykryć krawędzie w wybranym kierunku.
     *
     * @param input obraz wejściowy
     * @param direction kierunek detekcji
     * @param depthType typ głębi
     * @param paddingType typ wypełniania
     * @return obraz z wynikiem filtracji
     */
    public static Mat prewitt(Mat input, EnDirectionType direction, EnDepthType depthType, EnPaddingType paddingType) {
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
        Mat kernel = getPrewittKernel(direction);
        Mat result = new Mat();
        Imgproc.filter2D(input, result, depth, kernel, new Point(-1, -1), 0, borderType);
        if (depth != CvType.CV_8U) {
            Mat absResult = new Mat();
            Core.convertScaleAbs(result, absResult);
            return absResult;
        } else {
            return result;
        }
    }
    /**
     * Tworzy jądro Prewitt'a dla wybranego kierunku.
     *
     * @param direction kierunek
     * @return macierz jądra Prewitt'a
     */
    private static Mat getPrewittKernel(EnDirectionType direction) {
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
    /**
     * Wykonuje wyostrzanie obrazu poprzez filtrację Laplace'a.
     *
     * @param input obraz wejściowy
     * @param sharpenType typ wyostrzania (maska)
     * @param depthType typ głębi obrazu
     * @param paddingType typ wypełniania krawędzi
     * @return wyostrzony obraz
     */

    public static Mat laplacianSharpening(Mat input, EnSharpenType sharpenType, EnDepthType depthType, EnPaddingType paddingType) {
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
        Mat kernel = getSharpenKernel(sharpenType);
        Mat filtered = new Mat();
        Imgproc.filter2D(input, filtered, depth, kernel, new Point(-1, -1), 0, borderType);
        if (depth != CvType.CV_8U) {
            Mat absFiltered = new Mat();
            Core.convertScaleAbs(filtered, absFiltered);
            return absFiltered;
        } else {
            return filtered;
        }
    }
    /**
     * Tworzy jądro wyostrzające według wybranego typu.
     *
     * @param sharpenType typ wyostrzania
     * @return jądro wyostrzające
     */

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
    /**
     * Wykonuje splot obrazu z wybraną maską 3x3.
     *
     * @param input obraz wejściowy
     * @param maskValues lista wartości maski (9 elementów)
     * @param depthType typ głębi obrazu
     * @param paddingType typ wypełniania
     * @return obraz po splocie
     */
    public static Mat convolve(Mat input, List<Integer> maskValues, EnDepthType depthType, EnPaddingType paddingType) {
        if (maskValues == null || maskValues.size() != 9) {
            throw new IllegalArgumentException("Mask must contain exactly 9 values (3x3)");
        }
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

        Mat kernel = new Mat(3, 3, CvType.CV_32F);
        for (int i = 0; i < 9; i++) {
            kernel.put(i / 3, i % 3, maskValues.get(i));
        }

        Mat filtered = new Mat();
        Imgproc.filter2D(input, filtered, depth, kernel, new Point(-1, -1), 0, borderType);

        if (depth != CvType.CV_8U) {
            Mat absFiltered = new Mat();
            Core.convertScaleAbs(filtered, absFiltered);
            return absFiltered;
        } else {
            return filtered;
        }
    }
    /**
     * Wykonuje filtrowanie medianowe obrazu.
     *
     * @param input obraz wejściowy
     * @param ksize rozmiar jądra
     * @param paddingType typ wypełniania
     * @return obraz po filtracji medianowej
     */
    public static Mat medianFilter(Mat input, int ksize, EnPaddingType paddingType) {
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
        int pad = ksize / 2;
        Mat padded = new Mat();
        Core.copyMakeBorder(input, padded, pad, pad, pad, pad, borderType);
        Mat blurred = new Mat();
        Imgproc.medianBlur(padded, blurred, ksize);
        Rect roi = new Rect(pad, pad, input.cols(), input.rows());
        return new Mat(blurred, roi).clone();
    }
    /**
     * Wykonuje dodawanie dwóch obrazów o tych samych rozmiarach i typie.
     *
     * @param a pierwszy obraz
     * @param b drugi obraz
     * @return obraz powstały po dodaniu
     */
    public static Mat add(Mat a, Mat b) {
        if (!a.size().equals(b.size()) || a.type() != b.type()) {
            throw new IllegalArgumentException("Images must be the same size and type for addition");
        }

        Mat result = new Mat();
        Core.add(a, b, result);
        return result;
    }
    /**
     * Wykonuje odejmowanie dwóch obrazów o tych samych rozmiarach i typie.
     *
     * @param a pierwszy obraz
     * @param b drugi obraz
     * @return obraz powstały po odjęciu
     */
    public static Mat subtract(Mat a, Mat b) {
        if (!a.size().equals(b.size()) || a.type() != b.type()) {
            throw new IllegalArgumentException("Images must be the same size and type for subtraction");
        }

        Mat result = new Mat();
        Core.subtract(a, b, result);
        return result;
    }
    /**
     * Wykonuje mieszanie dwóch obrazów poprzez ważoną sumę.
     *
     * @param a pierwszy obraz
     * @param b drugi obraz
     * @param alpha waga pierwszego obrazu (0-1), (1-alpha) daje wagę drugiego obrazu
     * @return obraz po mieszaniu
     */
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
    /**
     * Wykonuje operację AND na dwóch obrazach o tych samych rozmiarach i typie.
     *
     * @param a pierwszy obraz
     * @param b drugi obraz
     * @return obraz powstały po operacji AND
     */
    public static Mat bitwiseAnd(Mat a, Mat b) {
        if (!a.size().equals(b.size()) || a.type() != b.type()) {
            throw new IllegalArgumentException("Images must be the same size and type for bitwise AND");
        }

        Mat result = new Mat();
        Core.bitwise_and(a, b, result);
        return result;
    }
    /**
     * Wykonuje operację OR na dwóch obrazach o tych samych rozmiarach i typie.
     *
     * @param a pierwszy obraz
     * @param b drugi obraz
     * @return obraz powstały po operacji OR
     */
    public static Mat bitwiseOr(Mat a, Mat b) {
        if (!a.size().equals(b.size()) || a.type() != b.type()) {
            throw new IllegalArgumentException("Images must be the same size and type for bitwise OR");
        }

        Mat result = new Mat();
        Core.bitwise_or(a, b, result);
        return result;
    }

    /**
     * Wykonuje operację XOR na dwóch obrazach o tych samych rozmiarach i typie.
     *
     * @param a pierwszy obraz
     * @param b drugi obraz
     * @return obraz powstały po operacji XOR
     */
    public static Mat bitwiseXor(Mat a, Mat b) {
        if (!a.size().equals(b.size()) || a.type() != b.type()) {
            throw new IllegalArgumentException("Images must be the same size and type for bitwise XOR");
        }

        Mat result = new Mat();
        Core.bitwise_xor(a, b, result);
        return result;
    }
    /**
     * Wykonuje operację NOT na obrazie.
     *
     * @param a obraz wejściowy
     * @return obraz po operacji NOT
     */
    public static Mat bitwiseNot(Mat a) {
        Mat result = new Mat();
        Core.bitwise_not(a, result);
        return result;
    }
    /**
     * Tworzy element strukturalny dla operacji morfologicznych.
     *
     * @param type typ elementu (np. kwadratowy, romb)
     * @param size rozmiar elementu (musi być liczbą nieparzystą ≥ 1)
     * @return element strukturalny
     */
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
    /**
     * Pomocnicza metoda do zamiany typu wypełniania na typ OpenCV.
     *
     * @param paddingType typ wypełniania
     * @return typ wypełniania rozpoznawany przez OpenCV
     */
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
    /**
     * Wykonuje erozję obrazu.
     *
     * @param input obraz wejściowy
     * @param type typ elementu strukturalnego
     * @param size rozmiar elementu
     * @param paddingType typ wypełniania
     * @return obraz po erozji
     */
    public static Mat erode(Mat input, EnStructureType type, int size, EnPaddingType paddingType) {
        Mat kernel = createStructuringElement(type, size);
        Mat output = new Mat();
        Imgproc.erode(input, output, kernel, new Point(-1, -1), 1, toBorderType(paddingType), new Scalar(0));
        return output;
    }
    /**
     * Wykonuje dylatację obrazu.
     *
     * @param input obraz wejściowy
     * @param type typ elementu strukturalnego
     * @param size rozmiar elementu
     * @param paddingType typ wypełniania
     * @return obraz po dylatacji
     */
    public static Mat dilate(Mat input, EnStructureType type, int size, EnPaddingType paddingType) {
        Mat kernel = createStructuringElement(type, size);
        Mat output = new Mat();
        Imgproc.dilate(input, output, kernel, new Point(-1, -1), 1, toBorderType(paddingType), new Scalar(0));
        return output;
    }
    /**
     * Wykonuje operację otwarcia (erozja, a następnie dylatacja).
     *
     * @param input obraz wejściowy
     * @param type typ elementu strukturalnego
     * @param size rozmiar elementu
     * @param paddingType typ wypełniania
     * @return obraz po operacji otwarcia
     */
    public static Mat open(Mat input, EnStructureType type, int size, EnPaddingType paddingType) {
        Mat kernel = createStructuringElement(type, size);
        Mat output = new Mat();
        Imgproc.morphologyEx(input, output, Imgproc.MORPH_OPEN, kernel, new Point(-1, -1), 1, toBorderType(paddingType), new Scalar(0));
        return output;
    }
    /**
     * Wykonuje operację zamknięcia (dylatacja, a następnie erozja).
     *
     * @param input obraz wejściowy
     * @param type typ elementu strukturalnego
     * @param size rozmiar elementu
     * @param paddingType typ wypełniania
     * @return obraz po operacji zamknięcia
     */
    public static Mat close(Mat input, EnStructureType type, int size, EnPaddingType paddingType) {
        Mat kernel = createStructuringElement(type, size);
        Mat output = new Mat();
        Imgproc.morphologyEx(input, output, Imgproc.MORPH_CLOSE, kernel, new Point(-1, -1), 1, toBorderType(paddingType), new Scalar(0));
        return output;
    }

    /**
     * Wykonuje szkieletyzację obrazu binarnego.
     *
     * @param input obraz wejściowy (binarne CV_8UC1)
     * @param structureType typ elementu strukturalnego
     * @param size rozmiar elementu
     * @param paddingType typ wypełniania
     * @return obraz po szkieletyzacji
     */
    public static Mat skeletonize(Mat input, EnStructureType structureType, int size, EnPaddingType paddingType) {
        if (input.type() != CvType.CV_8UC1) {
            throw new IllegalArgumentException("Input must be a binary image (CV_8UC1)");
        }
        Mat kernel = createStructuringElement(structureType, size);
        int borderType = toBorderType(paddingType);
        Mat img = input.clone();
        Mat skel = Mat.zeros(img.size(), CvType.CV_8UC1);
        Mat temp = new Mat();
        Mat eroded = new Mat();
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

    /**
     * Wykonuje progowanie manualne obrazu.
     *
     * @param input obraz wejściowy
     * @param thresholdValue próg
     * @return obraz po progowaniu
     */
    public static Mat manualThreshold(Mat input, int thresholdValue) {
        Mat result = new Mat();
        Imgproc.threshold(input, result, thresholdValue, 255, Imgproc.THRESH_BINARY);
        return result;
    }

    /**
     * Wykonuje adaptacyjne progowanie obrazu.
     *
     * @param input obraz wejściowy
     * @return obraz po adaptacyjnym progowaniu
     */
    public static Mat adaptiveThreshold(Mat input) {
        Mat result = new Mat();
        Imgproc.adaptiveThreshold(
                input,
                result,
                255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                Imgproc.THRESH_BINARY,
                11,
                2
        );
        return result;
    }
    /**
     * Wykonuje progowanie Otsu.
     *
     * @param input obraz wejściowy
     * @return obraz po progowaniu Otsu
     */
    public static Mat otsuThreshold(Mat input) {
        Mat result = new Mat();
        Imgproc.threshold(input, result, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
        return result;
    }
    /**
     * Wykonuje transformację Hougha do detekcji linii.
     *
     * @param input obraz wejściowy
     * @return obraz z wykrytymi liniami
     */
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
    /**
     * Tworzy obraz z wyznaczoną otoczką wypukłą.
     *
     * @param binary obraz wejściowy (binarne CV_8UC1)
     * @return obraz z wypełnioną otoczką wypukłą
     */
    
    public static Mat convexHull(Mat binary) {
        // Lista do przechowywania znalezionych konturów
        List<MatOfPoint> contours = new ArrayList<>();
        // Macierz hierarchii konturów
        Mat hierarchy = new Mat();
        // Znajdujemy zewnętrzne kontury na obrazie binarnym
        Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        // Tworzymy nowe czarne zdjęcie o takich samych wymiarach
        Mat result = Mat.zeros(binary.size(), CvType.CV_8UC1);
        // Dla każdego znalezionego konturu
        for (MatOfPoint contour : contours) {
            MatOfInt hull = new MatOfInt();
            // Wyznaczamy otoczkę wypukłą dla danego konturu
            Imgproc.convexHull(contour, hull);
            // Zamieniamy kontur na tablicę punktów
            Point[] contourArray = contour.toArray();
            // Lista do przechowywania punktów należących do otoczki wypukłej
            List<Point> hullPoints = new ArrayList<>();
            for (int index : hull.toArray()) {
                // Dodajemy do listy punkt odpowiadający danemu indeksowi z hull
                hullPoints.add(contourArray[index]);
            }
            // Tworzymy obiekt MatOfPoint z punktów otoczki wypukłej
            MatOfPoint hullMat = new MatOfPoint();
            hullMat.fromList(hullPoints);
            // Tworzymy listę konturów do narysowania 
            List<MatOfPoint> hullList = new ArrayList<>();
            hullList.add(hullMat);
            // Rysujemy otoczkę wypukłą na obrazie wynikowym, białym kolorem 
            Imgproc.drawContours(result, hullList, -1, new Scalar(255), -1);
        }
        return result;
    }
    /**
     * Pobiera intensywność pikseli wzdłuż linii pomiędzy dwoma punktami.
     *
     * @param mat obraz wejściowy (skala szarości)
     * @param p1 pierwszy punkt
     * @param p2 drugi punkt
     * @return lista tablic [x, y, intensywność]
     */
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
                double[] pixel = mat.get(y0, x0);
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