package com.anderl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dasanderl on 07.12.14.
 */
@Controller
public class SpringMvcController {

    /**
     * This will redirect to thymeleaf template /src/main/resources/templates/thymeleaf.html
     *
     * @return
     */
    @RequestMapping("/thymeleaf")
    String index() {
        return "thymeleaf";
    }
}

