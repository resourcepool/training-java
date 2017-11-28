import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import model.Computer;
import model.pages.Page;
import persistence.querycommands.PageQuery;
import service.PageRequest;

@RunWith(MockitoJUnitRunner.class)
public class PageResquestTest {

    /**
     * Page size cannot be > 100.
     */
    @Test
    public void pageAndpageSizeDefault() {
        PageRequest<Computer> r = new PageRequest<Computer>();

        assertNotNull(r.getPageSize());
        assertNotSame(0L, r.getPageSize());

        assertNotNull(r.getNbPage());
        assertNotSame(0L, r.getNbPage());
    }

    /**
     * Page size cannot be > 100.
     */
    @Test
    public void pageSizeLimit() {
        PageRequest<Computer> r = new PageRequest<Computer>();

        r.withPageSize(10L);
        assertSame(10L, r.getPageSize());

        r.withPageSize(100L);
        assertSame(100L, r.getPageSize());

        r.withPageSize(300L);
        assertSame(100L, r.getPageSize());
    }

    /**
     * build page using pageRequest.
     * @throws SQLException impossible
     */
    @Test
    public void buildNormal() throws SQLException {
        List<Computer> asList = Arrays.asList(Mockito.mock(Computer.class));
        PageRequest<Computer> r = new PageRequest<Computer>();
        PageQuery<Computer> q = (page) -> {
            return asList;
        };
        Page<Computer> pageResult = r.build(q, 10L);
        assertSame(10L, pageResult.getTotalCount());

        assertSame(null, pageResult.getContent());
        pageResult.load();
        assertSame(asList, pageResult.getContent());
    }

    /**
     * resize page if trying to load to high.
     * @throws SQLException impossible
     */
    @Test
    public void resizePage() throws SQLException {
        List<Computer> asList = Arrays.asList(Mockito.mock(Computer.class));
        PageQuery<Computer> q = (page) -> {
            return asList;
        };

        PageRequest<Computer> r = new PageRequest<Computer>().withPageSize(10L).atPage(3L);
        Page<Computer> pageResult = r.build(q, 20L);
        assertSame(20L, pageResult.getTotalCount());
        assertSame(2L, pageResult.getCurrentPage());
    }
}
