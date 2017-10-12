import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import client.commandHandlers.CompanyListHandler;
import mapper.exceptions.PageException;
import mapper.pages.Page;
import model.Company;
import persistence.exceptions.DaoException;
import persistence.querycommands.PageQueryCommand;
import service.CompanyServiceImpl;
import ui.UiConsole;

@RunWith(MockitoJUnitRunner.class)
public class ClientHandlerTest {

    @Mock
    private UiConsole ui;

    @Mock
    private CompanyServiceImpl service;

    @InjectMocks
    private CompanyListHandler companyListHandler;

    private PageQueryCommand<Company> query = (Long start, Long size) -> {
        List<Company> list = new ArrayList<Company>();
        list.add(new Company("name"));
        return list;
    };

    @Spy
    private Page<Company> page = new Page<Company>(query, 10L);;

    /**
     * Init Mocks.
     * @throws DaoException never thrown
     */
    @Before
    public void setUp() throws DaoException {
        ui = Mockito.mock(UiConsole.class);

        Mockito.when(ui.getInput()).thenReturn("n");
        Mockito.when(service.getCompanyPage()).thenReturn(page);
    }

    /**
     * Test Run CompanyList.
     * @throws DaoException never thrown
     * @throws PageException never thrown
     */
    @Test
    public void runCompanyList() throws DaoException, PageException {
        String[] args = new String[] {"cmd"};
        Boolean result = companyListHandler.runCommand(ui, args);

        verify(service, times(1)).getCompanyPage();
        verify(page, times(1)).next();
        verify(ui, atLeast(1)).write(any(String.class));
        assertTrue(result);
    }

}
