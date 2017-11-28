package validators;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import model.ComputerDto;

public class ComputerValidator {

    private static final String NO_COMPANY       = "--";
    private static final String DATEFORMAT_ERROR = "Wrong format (leave empty or use " + ValidationUtils.DATE_FORMAT + ")";

    private Map<String, String> errors;
    private LocalDate           introducedDate;
    private LocalDate           discontinuedDate;
    private Long                companyId        = null;
    private Long                id               = null;

    /**
     * @param dto to check, check id if != null
     * @return the content given is valid, the different fields have been set.
     */
    public boolean validate(ComputerDto dto) {
        if (dto.getId() == null) {
            return validate(dto.getName(), dto.getIntroduced(), dto.getDiscontinued(), dto.getCompanyId());
        }
        return validate(dto.getId(), dto.getName(), dto.getIntroduced(), dto.getDiscontinued(), dto.getCompanyId());
    }

    /**
     * validate a computer (id too).
     *
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
                errors.put(ValidationUtils.ID, "id must be valid");
            }
        } else {
            errors.put(ValidationUtils.ID, "id has to be a number");
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
            errors.put(ValidationUtils.COMPUTER_NAME, "Computer name is mandatory");
        }

        if (name.length() < 3) {
            errors.put(ValidationUtils.COMPUTER_NAME, "Computer name has to be at least 3 char long");
        }

        if (companyIdStr != null && !companyIdStr.isEmpty() && !companyIdStr.equals(NO_COMPANY)) {
            if (ValidationUtils.isLong(companyIdStr)) {
                companyId = Long.parseLong(companyIdStr);
                if (companyId < 1) {
                    errors.put(ValidationUtils.COMPANY_ID, "company_id must be valid");
                }
            } else {
                errors.put(ValidationUtils.COMPANY_ID, "Company id has to be a number");
            }
        }

        if (!isEmptyOrNull(introduced)) {
            introducedDate = ValidationUtils.getDate(introduced);
            if (introducedDate == null) {
                errors.put(ValidationUtils.INTRODUCED, DATEFORMAT_ERROR);
            }
        }
        if (!isEmptyOrNull(discontinued)) {
            discontinuedDate = ValidationUtils.getDate(discontinued);
            if (discontinuedDate == null) {
                errors.put(ValidationUtils.DISCONTINUED, DATEFORMAT_ERROR);
            }
        }
        if (introducedDate != null && discontinuedDate != null && discontinuedDate.compareTo(introducedDate) < 0) {
            errors.put(ValidationUtils.DISCONTINUED, "Introduced Date should be before Discontinued Date");
        }
        return errors.isEmpty();
    }

    /**
     * @param s param to check
     * @return true if null or empty
     */
    private Boolean isEmptyOrNull(String s) {
        return s == null || s.isEmpty();
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

    public Map<String, String> getErrors() {
        return errors;
    }

    public Long getId() {
        return id;
    }


}
