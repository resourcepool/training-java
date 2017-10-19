package validators;

public class ValidationUtils {

    /**
     * @param s String to check
     * @return true is containing only digit and could call Long.parseLong without throw
     */
    public static boolean isLong(String s) {

        if (s == null) {
            return false;
        }

        int size = s.length();
        for (int i = 0; i < size; i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
