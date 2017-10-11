package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import mapper.ComputerMapper;
import mapper.pages.Page;
import model.Computer;
import model.ComputerPreview;
import persistence.querycommands.PageQueryCommand;

public class ComputerDao {

    private static final String SELECT_COUNT_FROM_COMPUTER   = "select count(*) from computer";
    private static final String SELECT_ID_NAME_FROM_COMPUTER = "select id, name from computer";
    private static final String SELECT_COMPUTER_WHERE        = "select * from computer where ";
    private static final String INSERT_INTO_COMPUTER_VALUES  = "insert into computer values (null, ?, ?, ?, ?)";

    private static final String ID_FILTER   = "id = ?";
    private static final String NAME_FILTER = "name = \"?\"";

    private static ComputerDao instance;

    /**
     * private ctor.
     */
    private ComputerDao() {
    }

    /**
     * @return unique instance of this dao
     */
    public static ComputerDao getInstance() {
        if (instance == null) {
            instance = new ComputerDao();
        }
        return instance;
    }

    /**
     * @return the full list of computer, only name and id preview
     * @throws SQLException content couldn't be loaded
     */
    public List<ComputerPreview> getComputersList() throws SQLException {
        return DaoConnection.executeSelectQuery(SELECT_ID_NAME_FROM_COMPUTER, new ComputerMapper());
    }

    /**
     * @return the first page of the full computer preview list from DB
     * @throws SQLException content couldn't be loaded
     */
    public Page<ComputerPreview> getComputerPage() throws SQLException {
        Long size = DaoConnection.executeSelectQuery(SELECT_COUNT_FROM_COMPUTER, (ResultSet r) -> {
            return (r.next() ? r.getLong(1) : null);
        });

        PageQueryCommand<ComputerPreview> command = (Long start, Long splitSize) -> {
            return getComputerPageContent(start, splitSize);
        };

        return new Page<ComputerPreview>(command, size);
    }

    /**
     * @param start start index to query
     * @param split number of elements to return
     * @return the content resulting of the query with @split elements
     * @throws SQLException content couldn't be loaded
     */
    private List<ComputerPreview> getComputerPageContent(Long start, Long split) throws SQLException {
        String filter = String.format(" ORDER BY id LIMIT %d,%d", start, split);
        return DaoConnection.executeSelectQuery(SELECT_ID_NAME_FROM_COMPUTER + filter, new ComputerMapper());
    }

    /**
     * @param newComputer complete computer to create, without id
     * @return the id of the created computer
     * @throws SQLException content couldn't be loaded
     */
    public Long createComputer(Computer newComputer) throws SQLException {
        return DaoConnection.executeQuery((Connection c) -> {
            PreparedStatement s = c.prepareStatement(INSERT_INTO_COMPUTER_VALUES, Statement.RETURN_GENERATED_KEYS);

            s.setString(1, newComputer.getName());
            s.setDate(2, Date.valueOf(newComputer.getIntroduced()));
            s.setDate(3, newComputer.getDiscontinued() == null ? null : Date.valueOf(newComputer.getDiscontinued()));
            s.setLong(4, newComputer.getCompanyId());

            s.executeUpdate();
            ResultSet r = s.getGeneratedKeys();
            Long id = r.next() ? r.getLong(1) : null;

            r.close();
            s.close();
            return id;
        });
    }

    /**
     * @param name the name to search
     * @return the first computer corresponding exactly to @name
     * @throws SQLException content couldn't be loaded
     */
    public Computer getComputerDetail(String name) throws SQLException {
        return getComputerDetail(NAME_FILTER, name);
    }

    /**
     * @param id the computer id to search
     * @return the first computer corresponding exactly to @id
     * @throws SQLException content couldn't be loaded
     */
    public Computer getComputerDetail(Long id) throws SQLException {
        return getComputerDetail(ID_FILTER, id.toString());
    }

    /**
     * @param queryFilter the String containing the filter
     * @param param the string connection the param to fill the @queryfilter with, escaping the content in a
     *        preparedStatement
     * @return the first computer corresponding to filter
     * @throws SQLException content couldn't be loaded
     */
    private Computer getComputerDetail(String queryFilter, String param) throws SQLException {
        return DaoConnection.executeQuery((Connection conn) -> {
            PreparedStatement s = conn.prepareStatement(SELECT_COMPUTER_WHERE + queryFilter);
            s.setString(1, param);
            ResultSet r = s.executeQuery();

            Computer c = new ComputerMapper().mapComputer(r);

            r.close();
            s.close();
            return c;
        });
    }

    /**
     * @param c full computer to update with id != null
     * @throws SQLException content couldn't be loaded
     */
    public void updateComputer(Computer c) throws SQLException {
        DaoConnection.executeQuery((Connection conn) -> {
            PreparedStatement s = conn
                    .prepareStatement(
                            "update computer set name = ?, introduced = ? discontinued = ?, company_id = ? where id = ?");
            s.setString(1, c.getName());
            s.setDate(2, Date.valueOf(c.getIntroduced()));
            s.setDate(3, c.getDiscontinued() == null ? null : Date.valueOf(c.getDiscontinued()));
            s.setLong(4, c.getCompanyId());
            s.setLong(5, c.getId());
            s.executeUpdate();

            s.close();
            return true;
        });
    }

    /**
     * @param id id of the computer to delete
     * @throws SQLException content couldn't be loaded
     */
    public void deleteComputer(Long id) throws SQLException {
        DaoConnection.executeQuery((Connection conn) -> {
            try (Statement s = conn.createStatement()) {
                s.execute("delete from computer where id = " + id);
            }
            return true;
        });
    }
}
