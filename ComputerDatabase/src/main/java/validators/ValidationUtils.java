package validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ValidationUtils {

    public static final String COMPANY_NAME  = "company";
    public static final String COMPANY_ID    = "companyId";
    public static final String DISCONTINUED  = "discontinued";
    public static final String INTRODUCED    = "introduced";
    public static final String COMPUTER_NAME = "computerName";
    public static final String ID            = "id";
    public static final String DATE_FORMAT = "dd-MM-yyyy";

    /**
     * @param param parse to validate and parse
     * @param defaultValue value to return if @paramName doesn't exists
     * @return the selected parameter from the request params
     */
    public static Long retrieveLong(String param, Long defaultValue) {
        if (!ValidationUtils.isLong(param)) {
            return defaultValue;
        }
        return Long.parseLong(param);
    }

    /**
     * @param s String to check
     * @return true is containing only digit and could call Long.parseLong without
     *         throw
     */
    public static boolean isLong(String s) {

        if (s == null || s.isEmpty()) {
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

    /**
     * @param dateStr @NotNull dateToCheck, empty or null is automatically VALID
     *            (true)
     * @return true if valid
     */
    public static LocalDate checkDate(String dateStr) {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);

        try {

            Date date = sdf.parse(dateStr);
            LocalDate value = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return value;

        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @param selection selection ids (Long) splited by ","
     * @param ids list to fill
     * @return all entries are of type "Long"
     */
    public static Boolean isLongList(String[] selection, List<Long> ids) {
        for (String s : selection[0].split(",")) {
            if (!isLong(s)) {
                return false;
            } else {
                ids.add(Long.parseLong(s));
            }
        }
        return true;
    }
}
