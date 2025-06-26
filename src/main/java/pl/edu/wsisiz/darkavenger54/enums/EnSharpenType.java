package pl.edu.wsisiz.darkavenger54.enums;

/**
 * Typ wyliczeniowy określający rodzaj maski
 * wyostrzającej obraz poprzez filtrację.
 *
 * <ul>
 * <li>CROSS_5 – Maska w kształcie krzyża 3x3</li>
 * <li>SQUARE_5 – Maska kwadratowa 3x3 ze wzmocnionym środkiem</li>
 * <li>SQUARE_9 – Maska kwadratowa 3x3 o większym wzmocnieniu środka</li>
 * </ul>
 *
 * @author Yevhenii Manuilov
 */
public enum EnSharpenType
{
    CROSS_5, SQUARE_5,SQUARE_9
}
