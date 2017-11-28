package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import model.Company;
import model.Computer;
import model.ComputerDto;
import service.ICompanyService;
import service.IComputerService;
import validators.ComputerValidator;

@Controller
@RequestMapping("/add-computer")
public class AddComputer {

    private static final String FORMPAGE = "computer_form";
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
        loadCompanies(req);
        return FORMPAGE;
    }

    /**
     * @param req request to fill
     */
    private void loadCompanies(HttpServletRequest req) {
        List<Company> companies = companyService.getList();
        req.setAttribute("companies", companies);
    }

    /**
     * @param req current request
     * @return pageView
     */
    @PostMapping
    protected String doPost(HttpServletRequest req) {

        ComputerValidator v = new ComputerValidator();
        ComputerDto dto = new ComputerDto(req);

        if (!v.validate(dto)) {

            RequestUtils.putBackAttributes(req, dto);
            RequestUtils.showMsg(req, false, "Computer cannot be added, ");
            req.setAttribute("errors", v.getErrors());

        } else {

            Computer c = new Computer(dto.getName(), v.getIntroduced(), v.getDiscontinued(), v.getCompanyId());
            computerService.create(c);
            RequestUtils.showMsg(req, true, "SUCCESS: Computer \"" + c.getName() + "\" successfully created (id=" + c.getId() + ")");

        }

        loadCompanies(req);
        return FORMPAGE;
    }
}
