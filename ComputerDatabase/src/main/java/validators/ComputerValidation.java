package validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ComputerValidation {
    public static final String NO_COMPANY    = "--";
    public static final String COMPANY_ID    = "companyId";
    public static final String DISCONTINUED  = "discontinued";
    public static final String INTRODUCED    = "introduced";
    public static final String COMPUTER_NAME = "computerName";

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String DATEFORMAT_ERROR = "Wrong format (leave empty or use " + DATE_FORMAT + ")";

    private Map<String, String> errors;
    private LocalDate           introducedDate;
    private LocalDate           discontinuedDate;
    private Long                companyId = null;

    /**
     * @param name name entry from Dashboad servlet
     * @param introduced introduced
     * @param discontinued discontinued
     * @param companyIdStr companyId
     * @return Values received can be mapper to a Computer model
     */
    public Boolean validate(String name, String introduced, String discontinued, String companyIdStr) {
        errors = new HashMap<String, String>();

        if (name == null || name.isEmpty()) {
            errors.put(COMPUTER_NAME, "Computer name is mandatory");
        }

        if (name.length() < 3) {
            errors.put(COMPUTER_NAME, "Computer name has to be at least 3 char long");
        }

        if (companyIdStr != null && !companyIdStr.isEmpty() && !companyIdStr.equals(NO_COMPANY)) {
            if (ValidationUtils.isLong(companyIdStr)) {
                companyId = Long.parseLong(companyIdStr);
            } else {
                errors.put(COMPANY_ID, "Company id has to be a number");
            }
        }

        if (!isEmptyOrNull(introduced)) {
            introducedDate = checkDate(introduced);
            if (introducedDate == null) {
                errors.put(INTRODUCED, DATEFORMAT_ERROR);
            }
        }
        if (!isEmptyOrNull(discontinued)) {
            discontinuedDate = checkDate(discontinued);
            if (discontinuedDate == null) {
                errors.put(DISCONTINUED, DATEFORMAT_ERROR);
            }
        }
        if (introducedDate != null && discontinuedDate != null && discontinuedDate.compareTo(introducedDate) < 0) {
            errors.put(DISCONTINUED, "Introduced Date should be before Discontinued Date");
        }
        return errors.isEmpty();
    }

    public LocalDate getIntroduced() {
        return introducedDate;
    }

    public LocalDate getDiscontinued() {
        return discontinuedDate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    /**
     * @param s param to check
     * @return true if null or empty
     */
    private Boolean isEmptyOrNull(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * @param dateStr @NotNull dateToCheck, empty or null is automatically VALID (true)
     * @return true if valid
     */
    private LocalDate checkDate(String dateStr) {

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

    public Map<String, String> getErrors() {
        return errors;
    }
}

