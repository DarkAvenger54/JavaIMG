package pl.edu.wsisiz.darkavenger54.enums;

/**
 * Typ wyliczeniowy określający kierunek, używany m.in. przy
 * operatorach filtracji (np. Prewitt).
 *
 * <ul>
 * <li>NORTH – kierunek północny (góra)</li>
 * <li>EAST – kierunek wschodni (prawo)</li>
 * <li>SOUTH – kierunek południowy (dół)</li>
 * <li>WEST – kierunek zachodni (lewo)</li>
 * <li>NORTH_EAST – kierunek północno-wschodni</li>
 * <li>SOUTH_EAST – kierunek południowo-wschodni</li>
 * <li>NORTH_WEST – kierunek północno-zachodni</li>
 * <li>SOUTH_WEST – kierunek południowo-zachodni</li>
 * </ul>
 *
 * @author Yevhenii Manuilov
 */
public enum EnDirectionType
{
    NORTH, EAST, SOUTH, WEST, NORTH_EAST, SOUTH_EAST, NORTH_WEST, SOUTH_WEST;
}
