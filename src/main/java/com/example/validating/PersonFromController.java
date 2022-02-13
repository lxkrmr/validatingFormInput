package com.example.validating;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;


@Controller
public class PersonFromController implements WebMvcConfigurer {

    @GetMapping("/")
    public String showForm(PersonForm personForm) {
        return "form";
    }

    @PostMapping
    public String checkPersonInfo(@Valid PersonForm personForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "form";
        }

        return "redirect:/results";
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }
}
