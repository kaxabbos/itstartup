package com.itstartup.controllers;

import com.itstartup.controllers.Main.Main;
import com.itstartup.models.enums.Field;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CatalogCont extends Main {
    @GetMapping("/catalog")
    public String catalog(Model model) {
        model.addAttribute("startups", repoStartups.findAll());
        model.addAttribute("fields", Field.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }

    @GetMapping("/catalog/all")
    public String catalog_main(Model model) {
        model.addAttribute("startups", repoStartups.findAll());
        model.addAttribute("fields", Field.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }

    @PostMapping("/catalog/search")
    public String search(@RequestParam Field field, Model model) {
        model.addAttribute("startups", repoStartups.findAllByField(field));
        model.addAttribute("fields", Field.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }

    @GetMapping("/catalog/field/{field}")
    public String catalog_field_search(@PathVariable(value = "field") Field field, Model model) {
        model.addAttribute("startups", repoStartups.findAllByField(field));
        model.addAttribute("fields", Field.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }


    @PostMapping("/catalog/search/name")
    public String catalogSearch(@RequestParam String search, Model model) {
        model.addAttribute("startups", repoStartups.findAllByNameContaining(search));
        model.addAttribute("fields", Field.values());
        model.addAttribute("role", getRole());
        return "catalog";
    }
}
