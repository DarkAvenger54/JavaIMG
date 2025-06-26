package pl.edu.wsisiz.darkavenger54.enums;
/**
 * Typ wyliczeniowy określający, czy obraz jest binarny,
 * czy też niebinarny.
 *
 * @author Yevhenii Manuilov
 */
public enum EnBinaryType
{
    BINARY("Binary"), NON_BINARY("");
    private String binaryType;
    private EnBinaryType(String binaryType)
    {
        this.binaryType = binaryType;
    }
    public String getBinaryType()
    {
        return binaryType;
    }
}
