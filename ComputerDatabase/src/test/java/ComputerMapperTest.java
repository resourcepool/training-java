import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import mapper.ComputerMapper;
import model.ComputerPreview;
import persistence.exceptions.DaoException;

@RunWith(MockitoJUnitRunner.class)
public class ComputerMapperTest {

    @Mock
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
        resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getString("id")).thenReturn("myId");
        Mockito.when(resultSet.getString("name")).thenReturn("myHost");
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);

        List<ComputerPreview> result = mapper.process(resultSet);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getName(), "myHost");
        assertEquals(result.get(0).getId(), "myId");
    }

}
