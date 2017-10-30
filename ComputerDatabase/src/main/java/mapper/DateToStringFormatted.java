package mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import validators.ValidationUtils;

public class DateToStringFormatted {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(ValidationUtils.DATE_FORMAT);

    /**
     * @param date localDate
     * @return string formatted as ValidationUtils.DATE_FORMAT
     */
    public static String getFormattedString(LocalDate date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter formatter = DATE_FORMATTER;
        return date.format(formatter);
    }
}
