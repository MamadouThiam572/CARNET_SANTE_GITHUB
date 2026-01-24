
package com.senegalsante.controller;

import com.senegalsante.model.Medication;
import com.senegalsante.model.Profile;
import com.senegalsante.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private jakarta.servlet.http.HttpServletRequest request;

    private void setupTranslations(Model model, String lang) {
        model.addAttribute("lang", lang);
        model.addAttribute("t", translationService.getTranslations(lang));
        model.addAttribute("currentURI", request.getRequestURI());
    }

    @GetMapping("/")
    public String dashboard(Model model, @RequestParam(defaultValue = "FR") String lang) {
        setupTranslations(model, lang);
        model.addAttribute("title", "Accueil - Sénégal Santé");
        
        List<Medication> meds = new ArrayList<>();
        meds.add(new Medication(1L, "Paracétamol", "500mg", "3x/jour", "5 jours", false, null));
        meds.add(new Medication(2L, "Amoxicilline", "1g", "2x/jour", "7 jours", true, null));
        
        model.addAttribute("medications", meds);
        return "dashboard";
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam(defaultValue = "FR") String lang) {
        setupTranslations(model, lang);
        model.addAttribute("title", "Connexion");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model, @RequestParam(defaultValue = "FR") String lang) {
        setupTranslations(model, lang);
        model.addAttribute("title", "Inscription");
        return "register";
    }

    @GetMapping("/meds")
    public String medications(Model model, @RequestParam(defaultValue = "FR") String lang) {
        setupTranslations(model, lang);
        model.addAttribute("title", "Mes Médicaments");
        List<Medication> meds = new ArrayList<>();
        meds.add(new Medication(1L, "Paracétamol", "500mg", "3x/jour", "5 jours", false, null));
        model.addAttribute("medications", meds);
        return "meds";
    }

    @GetMapping("/profiles")
    public String profiles(Model model, @RequestParam(defaultValue = "FR") String lang) {
        setupTranslations(model, lang);
        model.addAttribute("title", "Ma Famille");
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile(1L, "Moussa", "Diop", "1985-06-15", "M", "O+", "Pénicilline", true, null));
        profiles.add(new Profile(2L, "Awa", "Diop", "2012-03-22", "F", "A+", "", false, null));
        model.addAttribute("profiles", profiles);
        return "profiles";
    }

    @GetMapping("/calendar")
    public String calendar(Model model, @RequestParam(defaultValue = "FR") String lang) {
        setupTranslations(model, lang);
        model.addAttribute("title", "Rendez-vous");
        return "calendar";
    }
}
