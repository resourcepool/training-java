package mapper;

import validators.ValidationUtils;

public enum ComputerMapping {
    COMPANY_NAME(ValidationUtils.COMPANY_NAME, ComputerMapper.COMPANY_NAME),
    COMPANY_ID(ValidationUtils.COMPANY_ID, ComputerMapper.COMPANY_ID),
    DISCONTINUED(ValidationUtils.DISCONTINUED, ComputerMapper.DISCONTINUED),
    INTRODUCED(ValidationUtils.INTRODUCED, ComputerMapper.INTRODUCED),
    COMPUTER_NAME(ValidationUtils.COMPUTER_NAME, ComputerMapper.NAME),
    ID(ValidationUtils.ID, ComputerMapper.ID);

    private String formName;
    private String dbName;

    public String getFormName() {
        return formName;
    }

    public String getDbName() {
        return dbName;
    }

    /**
     * ctor.
     * @param formName name used in different form in ui
     * @param dbName name used in db
     */
    ComputerMapping(String formName, String dbName) {
        this.formName = formName;
        this.dbName = dbName;
    }

    /**
     * @param sort formName to search
     * @return mapping found of null
     */
    public static ComputerMapping get(String sort) {
        ComputerMapping[] values = ComputerMapping.values();
        for (ComputerMapping computerMapping : values) {
            if (computerMapping.formName.equals(sort)) {
                return computerMapping;
            }
        }
        return null;
    }
}
