import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import mapper.exceptions.PageException;
import model.Company;
import model.pages.Page;
import persistence.CompanyDaoImpl;
import persistence.exceptions.DaoException;
import persistence.querycommands.PageQuery;
import service.CompanyServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class PageLoadingTest {

    @Mock
    private static CompanyDaoImpl companyDao;

    @Mock
    private PageQuery<Company> query;

    @InjectMocks
    private CompanyServiceImpl service;

    private Company content;

    /**
     * Init Mocks.
     * @throws DaoException never thrown
     * @throws PageException never thrown
     */
    @Before
    public void setUp() throws DaoException, PageException {
        content = new Company("name");
        List<Company> list = new ArrayList<Company>();
        list.add(content);
        Mockito.when(query.getContent(0L, 10L)).thenReturn(list);

        Page<Company> page = new Page<Company>(query, 10L);
        Mockito.when(companyDao.getCompanyPage()).thenReturn(page);
    }

    /**
     * @throws SQLException never thrown
     * @throws DaoException never thrown
     * @throws PageException never thrown
     */
    @Test
    public void processTest() throws SQLException, DaoException, PageException {
        Page<Company> page = service.getCompanyPage();
        verify(companyDao, times(1)).getCompanyPage();

        List<Company> l = page.next().getContent();
        assertSame(l.get(0), content);
    }
}
