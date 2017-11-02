import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import model.Computer;
import persistence.ComputerDaoImpl;
import persistence.exceptions.DaoException;
import service.ComputerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ComputerServiceImplTest {

    @Mock
    private static ComputerDaoImpl computerDao;

    @InjectMocks
    private ComputerServiceImpl service;

    /**
     * Init mock, mostly Dao.
     * @throws DaoException never thrown
     */
    @Before
    public void setUp() throws DaoException {
        Mockito.when(computerDao.getComputerDetail(1L)).thenReturn(Mockito.mock(Computer.class));
    }

    /**
     * @throws DaoException never thrown
     */
    @Test
    public void testGetComputerList() throws DaoException {
        assertNotNull(service.getComputerDetail(1L));
        verify(computerDao, times(1)).getComputerDetail(1L);
    }
}
