package persistence;

import java.sql.SQLException;
import java.util.List;

import model.Company;
import model.pages.Page;

public interface ICompanyDao {

    /**
     * @return Full company list from DB
     * @throws SQLException content couldn't be loaded
     */
    List<Company> getCompanyList() throws SQLException;

    /**
     * @param idCompany the id to check
     * @return true is company id is present in DB
     * @throws SQLException content couldn't be loaded
     */
    boolean companyExists(Long idCompany) throws SQLException;

    /**
     * @return the first page of the full company list from DB
     * @throws SQLException content couldn't be loaded
     */
    Page<Company> getCompanyPage() throws SQLException;

    /**
     * @return the number of company in DB
     * @throws SQLException content couldn't be loaded
     */
    Long getCompanyCount() throws SQLException;

    /**
     * @param id id to delete
     * @throws SQLException failed to delete
     */
    void deleteCompany(Long id) throws SQLException;

}