import static org.junit.Assert.assertEquals;
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

import model.Company;
import model.pages.Page;
import persistence.CompanyDaoImpl;
import persistence.exceptions.DaoException;
import persistence.querycommands.PageQuery;
import service.impl.CompanyServiceImpl;

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
     */
    @Before
    public void setUp() throws DaoException {
        content = new Company("name");
        List<Company> list = new ArrayList<Company>();
        list.add(content);

        Page<Company> page = new Page<Company>(query, 100L, 10L, 1L);

        Mockito.when(query.getContent(page)).thenReturn(list);
        Mockito.when(companyDao.getCompanyPage()).thenReturn(page);
    }

    /**
     * verify content is retrieved from page > from service > from dao.
     * @throws SQLException never thrown
     * @throws DaoException never thrown
     */
    @Test
    public void processTest() throws SQLException, DaoException {
        Page<Company> page = service.getPage();
        verify(companyDao, times(1)).getCompanyPage();

        List<Company> l = page.next().getContent();
        assertSame(l.get(0), content);
    }

    /**
     * test if page number is accurate.
     */
    @Test
    public void testTotalPage() {
        Page<Company> page;
        page = new Page<Company>(query, 50L,  1L, 0L);
        assertEquals(50L, page.getTotalPages().longValue());

        page = new Page<Company>(query, 50L, 1L, 50L);
        assertEquals(50L, page.getTotalPages().longValue()); //start elem don't impact pages

        page = new Page<Company>(query, 150L, 50L, 0L);
        assertEquals(3L, page.getTotalPages().longValue());

        page = new Page<Company>(query, 151L, 50L, 0L);
        assertEquals(4L, page.getTotalPages().longValue());

        page = new Page<Company>(query, 150L, 30L, 0L);
        assertEquals(5L, page.getTotalPages().longValue());

        page = new Page<Company>(query, 151L, 30L, 0L);
        assertEquals(6L, page.getTotalPages().longValue());

        page = new Page<Company>(query, 150L, 150L, 0L);
        assertEquals(1L, page.getTotalPages().longValue());

        page = new Page<Company>(query, 150L, 160L, 0L);
        assertEquals(1L, page.getTotalPages().longValue());
    }
}
