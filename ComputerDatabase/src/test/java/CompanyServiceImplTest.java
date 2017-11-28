import static org.junit.Assert.assertTrue;
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

import persistence.impl.CompanyDao;
import service.impl.CompanyService;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceImplTest {

    @Mock
    private static CompanyDao companyDao;

    @InjectMocks
    private CompanyService service;

    /**
     * Init mock, mostly Dao.
     * @throws SQLException never thrown
     */
    @Before
    public void setUp() throws SQLException {
        Mockito.when(companyDao.companyExists(10L)).thenReturn(true);
    }

    /**
     * @throws SQLException never thrown
     */
    @Test
    public void testCompanyExists() throws SQLException {
        assertTrue(service.exists(10L));
        verify(companyDao, times(1)).companyExists(10L);
    }

    /**
     * @throws SQLException never thrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCompanyExistsWithNullObject() throws SQLException {
        service.exists(null);
    }
}
