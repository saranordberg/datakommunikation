package DataTypeHelpers;

/**
 * Created by Bente on 28/04/2016.
 */
public final class ConverterHelper {
    public static int toInt(String source) {
        if(source != null) {
            try {
                return Integer.valueOf(source);
            } catch(NumberFormatException e) {
                return 0;
            }
        }

        return 0;
    }
}
