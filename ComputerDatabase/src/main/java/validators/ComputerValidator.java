package validators;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ComputerValidator {
    private static final String ID = "id";
    public static final String NO_COMPANY    = "--";
    public static final String COMPANY_ID    = "companyId";
    public static final String DISCONTINUED  = "discontinued";
    public static final String INTRODUCED    = "introduced";
    public static final String COMPUTER_NAME = "computerName";

    private static final String DATEFORMAT_ERROR = "Wrong format (leave empty or use " + ValidationUtils.DATE_FORMAT + ")";

    private Map<String, String> errors;
    private LocalDate           introducedDate;
    private LocalDate           discontinuedDate;
    private Long                companyId = null;
    private Long                id = null;


    /**
     * validate a computer (id too).
     * @param idStr id as string from jsp page
     * @param name name
     * @param introduced introduced
     * @param discontinued discontinued
     * @param companyIdStr companyId
     * @return errorList
     */
    public boolean validate(String idStr, String name, String introduced, String discontinued, String companyIdStr) {
        prepare();

        if (ValidationUtils.isLong(idStr)) {
            id = Long.parseLong(idStr);
            if (id < 1) {
                errors.put(ID, "id must be valid");
            }
        } else {
            errors.put(ID, "id has to be a number");
        }

        return validateCore(name, introduced, discontinued, companyIdStr);
    }

    /**
     * @param name name entry from Dashboad servlet
     * @param introduced introduced
     * @param discontinued discontinued
     * @param companyIdStr companyId
     * @return Values received can be mapper to a Computer model
     */
    public Boolean validate(String name, String introduced, String discontinued, String companyIdStr) {
        prepare();
        return validateCore(name, introduced, discontinued, companyIdStr);
    }

    /**
     * clean previous error, init list and old values.
     */
    private void prepare() {
        errors = new HashMap<String, String>();
    }

    /**
     * @param name name
     * @param introduced introduced
     * @param discontinued discontinued
     * @param companyIdStr companyIdStr
     * @return true if valid
     */
    private Boolean validateCore(String name, String introduced, String discontinued, String companyIdStr) {
        if (name == null || name.isEmpty()) {
            errors.put(COMPUTER_NAME, "Computer name is mandatory");
        }

        if (name.length() < 3) {
            errors.put(COMPUTER_NAME, "Computer name has to be at least 3 char long");
        }

        if (companyIdStr != null && !companyIdStr.isEmpty() && !companyIdStr.equals(NO_COMPANY)) {
            if (ValidationUtils.isLong(companyIdStr)) {
                companyId = Long.parseLong(companyIdStr);
                if (companyId < 1) {
                    errors.put(COMPANY_ID, "company_id must be valid");
                }
            } else {
                errors.put(COMPANY_ID, "Company id has to be a number");
            }
        }

        if (!isEmptyOrNull(introduced)) {
            introducedDate = ValidationUtils.checkDate(introduced);
            if (introducedDate == null) {
                errors.put(INTRODUCED, DATEFORMAT_ERROR);
            }
        }
        if (!isEmptyOrNull(discontinued)) {
            discontinuedDate = ValidationUtils.checkDate(discontinued);
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

    public Map<String, String> getErrors() {
        return errors;
    }

    public Long getId() {
        return id;
    }

}

