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
        Mockito.when(resultSet.getLong(ComputerMapper.ID)).thenReturn(id);
        Mockito.when(resultSet.getString(ComputerMapper.NAME)).thenReturn(host);
        Mockito.when(resultSet.getDate(ComputerMapper.INTRODUCED)).thenReturn(null);
        Mockito.when(resultSet.getDate(ComputerMapper.DISCONTINUED)).thenReturn(Date.valueOf(date));
        Mockito.when(resultSet.getLong(ComputerMapper.COMPANY_ID)).thenReturn(companyId);
        Mockito.when(resultSet.getString(ComputerMapper.COMPANY_NAME)).thenReturn(companyName);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);

        List<Computer> result = mapper.process(resultSet);
        assertEquals(result.size(), 1);

        Computer first = result.get(0);
        assertEquals(id.longValue(), first.getId().longValue());
        assertEquals(host, first.getName());
        assertEquals(null, first.getIntroduced());
        assertEquals(date, first.getDiscontinued());
        assertEquals(companyId.longValue(), first.getCompany().getId().longValue());
        assertEquals(companyName, first.getCompany().getName());
    }

    /**
     * @return just a sql.Date mocked
     */
    private LocalDate getDate() {
        return LocalDate.parse("27-07-2017", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

}
