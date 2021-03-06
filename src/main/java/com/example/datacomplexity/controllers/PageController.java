package com.example.datacomplexity.controllers;

import com.example.datacomplexity.models.CodeModel;
import com.example.datacomplexity.models.VariableList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {
    private final CodeModel codeModel;

    PageController(CodeModel codeModel) {
        this.codeModel = codeModel;
    }

    @GetMapping
    public String pageOut(Model model) {
        model.addAttribute("inputCode", codeModel);
        return "PageLayout";
    }

    @PostMapping
    public String pageOut(@ModelAttribute("inputCode") CodeModel codeModel, Model model) {
        model.addAttribute("spenTable", codeModel.codeSpenAnalyzing());
        codeModel.codeChepinsAnalyzing();
        model.addAttribute("chepinsTable", codeModel.getVariableList().getVariables());
        model.addAttribute("chepinsCount", codeModel.getChepinsMetric(false));
        model.addAttribute("chepinsIOTable", codeModel.getVariableList().getIOVariables());
        model.addAttribute("chepinsIOCount", codeModel.getChepinsMetric(true));
        System.out.println(codeModel.getVariableList().contains("b"));
        codeModel.getVariableList().findVariableByName("begin1").ifPresent(System.out::println);
        return "PageLayout";
    }
}
