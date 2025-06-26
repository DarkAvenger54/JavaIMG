package pl.edu.wsisiz.darkavenger54.enums;

/**
 * Typ wyliczeniowy określający rodzaj sposobu
 * wypełniania pikseli na krawędziach obrazu
 * podczas operacji filtrowania.
 *
 * <ul>
 * <li>REPLICATE – Powielanie pikseli krawędziowych</li>
 * <li>ISOLATED – Izolowany typ ramki</li>
 * <li>REFLECT – Odbicie pikseli wokół krawędzi</li>
 * </ul>
 *
 * @author Yevhenii Manuilov
 */

public enum EnPaddingType
{
    REPLICATE, ISOLATED, REFLECT
}
