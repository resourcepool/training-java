import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import model.Computer;
import persistence.impl.ComputerDao;
import service.impl.ComputerService;

@RunWith(MockitoJUnitRunner.class)
public class ComputerServiceImplTest {

    @Mock
    private static ComputerDao computerDao;

    @InjectMocks
    private ComputerService service;

    /**
     * Init mock, mostly Dao.
     * @throws SQLException never thrown
     */
    @Before
    public void setUp() throws SQLException {
        Mockito.when(computerDao.getComputerDetail(1L)).thenReturn(Mockito.mock(Computer.class));
    }

    /**
     * @throws SQLException never thrown
     */
    @Test
    public void testGetComputerList() throws SQLException {
        assertNotNull(service.getDetail(1L));
        verify(computerDao, times(1)).getComputerDetail(1L);
    }
}
