package com.itstartup.controllers;

import com.itstartup.controllers.Main.Main;
import com.itstartup.models.Startups;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class StatsCont extends Main {

    @GetMapping("/stats")
    public String sales(Model model) {
        List<Startups> startups = getUser().getStartups();

        int investment = 0;
        for (Startups startup : startups) {
            investment += startup.getInvestment().getCount();
        }

        model.addAttribute("investment", investment);
        model.addAttribute("startups", startups);
        model.addAttribute("role", getRole());

        startups.sort(Comparator.comparing(Startups::getCount));
        Collections.reverse(startups);

        String[] topName = new String[5];
        int[] topNum = new int[5];

        for (int i = 0; i < startups.size(); i++) {
            if (i == 5) break;
            topName[i] = startups.get(i).getName();
            topNum[i] = startups.get(i).getInvestment().getCount();
        }
        model.addAttribute("topName", topName);
        model.addAttribute("topNum", topNum);

        return "stats";
    }
}
