import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import mapper.ComputerMapper;
import model.Computer;
import persistence.exceptions.DaoException;

@RunWith(MockitoJUnitRunner.class)
public class ComputerMapperTest {
    private ResultSet resultSet;

    private ComputerMapper mapper;

    /**
     * Init Mocks.
     * @throws DaoException never thrown
     */
    @Before
    public void setUp() throws DaoException {
        mapper = new ComputerMapper();
    }

    /**
     * @throws SQLException never thrown
     */
    @Test
    public void processTest() throws SQLException {
        Long id = 10L;
        String host = "host";
        LocalDate date = getDate();
        Long companyId = 42L;
        String companyName = "cname";

        resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getLong("id")).thenReturn(id);
        Mockito.when(resultSet.getString("name")).thenReturn(host);
        Mockito.when(resultSet.getDate("introduced")).thenReturn(null);
        Mockito.when(resultSet.getDate("discontinued")).thenReturn(Date.valueOf(date));
        Mockito.when(resultSet.getLong("company_id")).thenReturn(companyId);
        Mockito.when(resultSet.getString("company_name")).thenReturn(companyName);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);

        List<Computer> result = mapper.process(resultSet);
        assertEquals(result.size(), 1);

        Computer first = result.get(0);
        assertEquals(first.getId().longValue(), id.longValue());
        assertEquals(first.getName(), host);
        assertEquals(first.getIntroduced(), null);
        assertEquals(first.getDiscontinued(), date);
        assertEquals(first.getCompany().getId().longValue(), companyId.longValue());
        assertEquals(first.getCompany().getName(), companyName);
    }

    /**
     * @return just a sql.Date mocked
     */
    private LocalDate getDate() {
        return LocalDate.parse("27-07-2017", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

}
