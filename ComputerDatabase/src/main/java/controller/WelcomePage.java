package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class WelcomePage {
    /**
     * @return dashboard route
     */
    @GetMapping
    public String goToDashboard() {
        return "redirect:dashboard";
    }
}
