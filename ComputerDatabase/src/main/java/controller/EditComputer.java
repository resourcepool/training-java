package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import model.Computer;
import model.ComputerDto;
import service.ICompanyService;
import service.IComputerService;
import validators.ComputerValidator;
import validators.ValidationUtils;

@Controller
@RequestMapping("/edit-computer")
public class EditComputer {

    private static final String FORMPAGE = "computer_form";
    private static final String ID_IS_NOT_VALID = "id is not valid";

    @Autowired
    private IComputerService computerService;
    @Autowired
    private ICompanyService companyService;

    /**
     * @param req current request
     * @return view name
     */
    @GetMapping
    protected String doGet(HttpServletRequest req) {

        if (!loadComputer(req)) {

            return "redirect:/ComputerDatabase/add-computer";
        }

        loadPage(req);
        return FORMPAGE;
    }

    /**
     * @param req current request
     * @return view name
     */
    @PostMapping
    protected String doPost(HttpServletRequest req) {

        ComputerValidator v = new ComputerValidator();
        ComputerDto dto = new ComputerDto(req);
        if (!v.validate(dto)) {

            RequestUtils.showMsg(req, false, "Computer cannot be edited, ");
            req.setAttribute("errors", v.getErrors());

        } else {

            Computer c = new Computer(v.getId(), dto.getName(), v.getIntroduced(), v.getDiscontinued(), v.getCompanyId());
            computerService.update(c);
            RequestUtils.showMsg(req, true, "SUCCESS: Computer \"" + c.getName() + "\" successfully edited (id=" + v.getId() + ")");

        }

        RequestUtils.putBackAttributes(req, dto);
        loadPage(req);
        return FORMPAGE;
    }

    /**
     * Calls the matching jsp.
     * @param req req
     */
    private void loadPage(HttpServletRequest req) {

        loadCompanies(req);
        req.setAttribute("edit", true);
    }

    /**
     * @param req servletRequest
     * @return successfully loaded
     */
    private Boolean loadComputer(HttpServletRequest req) {
        String idStr = req.getParameter("id");

        if (!ValidationUtils.isLong(idStr)) {
            RequestUtils.showMsg(req, false, ID_IS_NOT_VALID);
            return false;
        }

        Long id = Long.parseLong(idStr);
        Computer c = computerService.getDetail(id);
        RequestUtils.putAttributes(req, id, c.getName(), c.getIntroduced(), c.getDiscontinued(), c.getCompany().getId());
        return true;
    }

    /**
     * @param req servletRequest
     */
    private void loadCompanies(HttpServletRequest req) {
        req.setAttribute("companies", companyService.getList());
    }

}
