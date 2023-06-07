package com.itstartup.controllers;

import com.itstartup.controllers.Main.Main;
import com.itstartup.models.*;
import com.itstartup.models.enums.Field;
import com.itstartup.models.enums.Kinds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/startups")
public class StartupsCont extends Main {

    @GetMapping("/{id}")
    public String startup(@PathVariable Long id, Model model) {
        if (!repoStartups.existsById(id)) return "redirect:/catalog";
        model.addAttribute("s", repoStartups.getReferenceById(id));
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
        return "startup";
    }

    @PostMapping("/comment/add/{id}")
    public String comment_add(@PathVariable Long id, @RequestParam String date, @RequestParam String comment) {
        Startups startup = repoStartups.getReferenceById(id);
        startup.addComment(new Comments(getUser().getUsername(), date, comment));
        repoStartups.save(startup);
        return "redirect:/startups/{id}";
    }

    @PostMapping("/buy/{id}")
    public String buy(@PathVariable Long id, @RequestParam int count) {
        Startups startup = repoStartups.getReferenceById(id);

        Users user = getUser();
        user.addCart(new Carts(count, startup));
        repoUsers.save(user);

        startup.setCount(startup.getCount() + count);

        startup.getInvestment().setCount(startup.getInvestment().getCount() + count);

        repoStartups.save(startup);

        return "redirect:/startups/{id}";
    }

    @GetMapping("/add")
    public String startup_add(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("fields", Field.values());
        model.addAttribute("kinds", Kinds.values());
        return "startup_add";
    }

    @PostMapping("/add")
    public String startup_add(Model model, @RequestParam Kinds kind, @RequestParam String name, @RequestParam MultipartFile poster, @RequestParam MultipartFile[] screenshots, @RequestParam String pub, @RequestParam String date, @RequestParam int year, @RequestParam int price, @RequestParam int count, @RequestParam Field field, @RequestParam String description) {
        try {
            String uuidFile = UUID.randomUUID().toString();
            String result_poster = "";
            if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                result_poster = uuidFile + "_" + poster.getOriginalFilename();
                poster.transferTo(new File(uploadPath + "/" + result_poster));
            }

            String[] result_screenshots = new String[0];
            if (screenshots != null && !Objects.requireNonNull(screenshots[0].getOriginalFilename()).isEmpty()) {
                uuidFile = UUID.randomUUID().toString();
                String result_screenshot;
                result_screenshots = new String[screenshots.length];
                for (int i = 0; i < result_screenshots.length; i++) {
                    result_screenshot = uuidFile + "_" + screenshots[i].getOriginalFilename();
                    screenshots[i].transferTo(new File(uploadPath + "/" + result_screenshot));
                    result_screenshots[i] = result_screenshot;
                }
            }

            repoStartups.save(new Startups(kind, getUser(), name, pub, description, date, result_poster, result_screenshots, year, price, count, field));


        } catch (Exception e) {
            model.addAttribute("role", getRole());
            model.addAttribute("message", "Некорректные данные!");
            model.addAttribute("fields", Field.values());
            model.addAttribute("kinds", Kinds.values());
            return "startup_add";
        }
        return "redirect:/catalog/all";
    }

    @GetMapping("/edit/{id}")
    public String startup_edit(@PathVariable(value = "id") Long id, Model model) {
        if (!repoStartups.existsById(id)) return "redirect:/catalog";
        model.addAttribute("s", repoStartups.getReferenceById(id));
        model.addAttribute("role", getRole());
        model.addAttribute("fields", Field.values());
        model.addAttribute("kinds", Kinds.values());
        return "startup_edit";
    }

    @PostMapping("/edit/{id}")
    public String startup_edit(@PathVariable Long id, Model model, @RequestParam Kinds kind, @RequestParam String name, @RequestParam MultipartFile poster, @RequestParam MultipartFile[] screenshots, @RequestParam String pub, @RequestParam String date, @RequestParam int year, @RequestParam int price, @RequestParam int count, @RequestParam Field field, @RequestParam String description) {
        try {
            Startups startup = repoStartups.getReferenceById(id);

            startup.setDescription(description);
            startup.setName(name);
            startup.setPub(pub);
            startup.setCount(count);
            startup.setKind(kind);
            startup.setYear(year);
            startup.setDate(date);
            startup.getInvestment().setPrice(price);
            startup.setField(field);

            String uuidFile = UUID.randomUUID().toString();
            if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result_poster = uuidFile + "_" + poster.getOriginalFilename();
                poster.transferTo(new File(uploadPath + "/" + result_poster));
                startup.setPoster(result_poster);
            }

            if (screenshots != null && !Objects.requireNonNull(screenshots[0].getOriginalFilename()).isEmpty()) {
                uuidFile = UUID.randomUUID().toString();
                String result_screenshot;
                String[] result_screenshots = new String[screenshots.length];
                for (int i = 0; i < result_screenshots.length; i++) {
                    result_screenshot = uuidFile + "_" + screenshots[i].getOriginalFilename();
                    screenshots[i].transferTo(new File(uploadPath + "/" + result_screenshot));
                    result_screenshots[i] = result_screenshot;
                }
                startup.setScreenshots(result_screenshots);
            }
            repoStartups.save(startup);
        } catch (Exception e) {
            model.addAttribute("startup", repoStartups.getReferenceById(id));
            model.addAttribute("role", getRole());
            model.addAttribute("message", "Некорректные данные!");
            model.addAttribute("fields", Field.values());
            model.addAttribute("kinds", Kinds.values());
            return "startup_edit";
        }
        return "redirect:/startups/{id}/";
    }

    @GetMapping("/delete/{id}")
    public String startup_delete(@PathVariable Long id) {
        repoStartups.deleteById(id);
        return "redirect:/catalog/all";
    }
}
