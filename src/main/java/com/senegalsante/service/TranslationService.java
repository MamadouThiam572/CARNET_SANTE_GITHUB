package com.senegalsante.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class TranslationService {
    private final Map<String, Map<String, String>> translations = new HashMap<>();

    public TranslationService() {
        Map<String, String> fr = new HashMap<>();
        fr.put("dashboard", "Accueil");
        fr.put("profiles", "Ma Famille");
        fr.put("meds", "Mes Médocs");
        fr.put("calendar", "Rendez-vous");
        fr.put("symptoms", "Comment ça va ?");
        fr.put("prescriptions", "Papiers");
        fr.put("emergency", "AU SECOURS (1515)");
        fr.put("quick_add", "Ma Santé Numérique");
        fr.put("scan_prescription", "Scanner mon ordonnance");
        fr.put("scan_instruction", "Prenez en photo votre papier");
        fr.put("welcom_msg", "Dalal ak Jamm !");
        fr.put("priority_msg", "Votre santé est notre priorité au Sénégal.");
        translations.put("FR", fr);

        Map<String, String> wo = new HashMap<>();
        wo.put("dashboard", "Kër gi");
        wo.put("profiles", "Njaboot gi");
        wo.put("meds", "Garab yi");
        wo.put("calendar", "Daje yi");
        wo.put("symptoms", "Nan ngay dunde ?");
        wo.put("prescriptions", "Kayit yi");
        wo.put("emergency", "DIMBALI (1515)");
        wo.put("quick_add", "Sama Wer gi yaram");
        wo.put("scan_prescription", "Nattil kayitu doktoor bi");
        wo.put("scan_instruction", "Nattil kayit bi ci sa téléphone");
        wo.put("welcom_msg", "Dalal ak Jamm !");
        wo.put("priority_msg", "Sa wér gi yaram moy sunu yité fi Sénégal.");
        translations.put("WO", wo);
    }

    public Map<String, String> getTranslations(String lang) {
        return translations.getOrDefault(lang.toUpperCase(), translations.get("FR"));
    }
}
