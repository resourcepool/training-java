import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import persistence.CompanyDaoImpl;
import persistence.exceptions.DaoException;
import service.CompanyServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceImplTest {

    @Mock
    private static CompanyDaoImpl companyDao;

    @InjectMocks
    private CompanyServiceImpl service;

    /**
     * Init mock, mostly Dao.
     * @throws DaoException never thrown
     */
    @Before
    public void setUp() throws DaoException {
        Mockito.when(companyDao.companyExists(10L)).thenReturn(true);
    }

    /**
     * @throws DaoException never thrown
     */
    @Test
    public void testCompanyExists() throws DaoException {
        assertTrue(service.companyExists(10L));
        verify(companyDao, times(1)).companyExists(10L);
    }

    /**
     * @throws DaoException never thrown
     */
    @Test(expected = NullPointerException.class)
    public void testCompanyExistsWithNullObject() throws DaoException {
        service.companyExists(null);
    }

}
