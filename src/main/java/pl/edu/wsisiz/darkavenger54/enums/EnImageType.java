package pl.edu.wsisiz.darkavenger54.enums;

/**
 * Typ wyliczeniowy określający rodzaj obrazu:
 * skala szarości (Grayscale) lub RGB.
 *
 * @author Yevhenii Manuilov
 */
public enum EnImageType
{
    GRAYSCALE("Grayscale"), RGB("RGB");
    private String imageType;
    private EnImageType(String imageType)
    {
        this.imageType = imageType;
    }

    public String getImageType()
    {
        return imageType;
    }
}
