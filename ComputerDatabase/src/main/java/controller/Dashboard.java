package controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Computer;
import model.pages.Page;
import service.ICompanyService;
import service.IComputerService;
import service.PageRequest;

@Controller
@RequestMapping("/dashboard")
public class Dashboard {

    private static final String DEFAULT_PAGESIZE      = "20";
    private static final String DEFAULT_STARTING_PAGE = "1";

    @Autowired
    private IComputerService    computerService;
    @Autowired
    private ICompanyService     companyService;

    /**
     * @param model model
     * @param pageSize pageSize
     * @param pageNumber pageNumber
     * @param search search
     * @param sort sort
     * @param order order
     * @return view name to load
     */
    @GetMapping
    public String doGet(ModelMap model,
            @RequestParam(value = "pagination", required = false, defaultValue = DEFAULT_PAGESIZE) Long pageSize,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_STARTING_PAGE) Long pageNumber,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "order", required = false) String order) {

        PageRequest<Computer> builder = new PageRequest<Computer>();
        builder.atPage(pageNumber).withPageSize(pageSize).withSearch(search).withSort(sort).withOrder(order);

        loadDashboard(model, builder);
        return "dashboard";
    }

    /**
     * @param model model
     * @param pageSize pageSize
     * @param pageNumber pageNumber
     * @param search search
     * @param sort sort
     * @param order order
     * @param id id to delete Company
     * @return view name to load
     */
    @PostMapping("/delete-company")
    public String deleteCompany(
            ModelMap model,
            @RequestParam(value = "pagination", required = false, defaultValue = DEFAULT_PAGESIZE) Long pageSize,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_STARTING_PAGE) Long pageNumber,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "company_id_delete", required = true) Long id) {
        //
        //         handleComputerDeletion(req);
        PageRequest<Computer> request = new PageRequest<Computer>();
        request.atPage(pageNumber).withPageSize(pageSize).withSearch(search).withSort(sort).withOrder(order);

        handleCompanyDeletion(model, id);
        loadDashboard(model, request);

        return "dashboard";
    }

    /**
     * @param model model
     * @param pageSize pageSize
     * @param pageNumber pageNumber
     * @param search search
     * @param sort sort
     * @param order order
     * @param ids ids of computers to delete
     * @return view name to load
     */
    @PostMapping("/delete-computer")
    public String deleteComputer(
            ModelMap model,
            @RequestParam(value = "pagination", required = false, defaultValue = DEFAULT_PAGESIZE) Long pageSize,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_STARTING_PAGE) Long pageNumber,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "computer_selection_delete", required = true) Long[] ids) {

        PageRequest<Computer> request = new PageRequest<Computer>();
        request.atPage(pageNumber).withPageSize(pageSize).withSearch(search).withSort(sort).withOrder(order);

        handleComputerDeletion(model, Arrays.asList(ids));
        loadDashboard(model, request);

        return "dashboard";
    }

    /**
     * @param model model
     * @param id id to delete
     */
    private void handleCompanyDeletion(ModelMap model, Long id) {
        companyService.delete(id);
        String msg = "Sucessfully deleted company : n°" + id;
        RequestUtils.showMsg(model, true, msg);
    }

    /**
     * @param model model
     * @param ids ids
     */
    private void handleComputerDeletion(ModelMap model, List<Long> ids) {

        computerService.delete(ids);
        RequestUtils.showMsg(model, true, "Success, " + ids.size() + " computer ids deleted");
        model.addAttribute("page", 1);
    }

    /**
     * @param model model
     * @param builder loaded request
     */
    private void loadDashboard(ModelMap model, PageRequest<Computer> builder) {

        Page<Computer> page = computerService.loadPage(builder);
        buildParams(model, page);
    }

    /**
     * @param model model
     * @param page page
     */
    public static void buildParams(ModelMap model, Page<Computer> page) {
        RequestUtils.buildPageParams(model, page);
        List<Computer> content = page.getContent();
        model.addAttribute("computers", content);
        model.addAttribute("page", page);
    }

}
