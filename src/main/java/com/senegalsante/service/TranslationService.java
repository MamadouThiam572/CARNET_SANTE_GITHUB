package com.senegalsante.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class TranslationService {
    private final Map<String, Map<String, String>> translations = new HashMap<>();

    public TranslationService() {
        // --- FRANÇAIS ---
        Map<String, String> fr = new HashMap<>();
        // General
        fr.put("app_name", "Sénégal Santé");
        fr.put("app_subtitle", "Carnet Numérique");
        fr.put("lang_switch", "Wolof");
        fr.put("footer_secure", "Données Sécurisées • Vie Privée Respectée");
        fr.put("footer_rights", "© 2026 Sénégal Santé. Tous droits réservés.");

        // Auth / Home
        fr.put("home_title", "Votre carnet de santé numérique, simple et sécurisé.");
        fr.put("home_subtitle",
                "Gérez vos médicaments, rendez-vous et le suivi de toute votre famille en un seul endroit.");
        fr.put("login_btn", "Se connecter");
        fr.put("register_btn", "Créer un compte");
        fr.put("welcome_login", "Bienvenue");
        fr.put("access_carnet", "Accédez à votre carnet");
        fr.put("phone_placeholder", "Téléphone (+221)");
        fr.put("password_placeholder", "Mot de passe");
        fr.put("no_account", "Nouveau ?");
        fr.put("create_account", "Créer un compte");
        fr.put("register_title", "Inscription");
        fr.put("register_subtitle", "Créez votre dossier médical");
        fr.put("firstname", "Prénom");
        fr.put("lastname", "Nom");
        fr.put("vital_info", "Informations Vitales");
        fr.put("gender", "Genre");
        fr.put("male", "Homme");
        fr.put("female", "Femme");
        fr.put("start_carnet", "Commencer mon carnet");
        fr.put("already_account", "Déjà inscrit ?");

        // Dashboard
        fr.put("dashboard", "Tableau de Bord");
        fr.put("family_tracking", "Suivi famille");
        fr.put("medications", "Médicaments");
        fr.put("next_rdv", "Prochain RDV");
        fr.put("to_take", "à prendre");
        fr.put("none", "Aucun");
        fr.put("today_schedule", "À faire aujourd'hui");
        fr.put("calm_day", "Tout est calme aujourd'hui ! 🌴");
        fr.put("taken", "Pris");
        fr.put("emergency_call", "Urgence 1515");

        // Internal Pages Titles
        fr.put("meds", "Mes Mes Médocs");
        fr.put("calendar", "Rendez-vous");
        fr.put("profiles", "Ma Famille");
        fr.put("prescriptions", "Ordonnances");

        // Profiles
        fr.put("add_btn", "Ajouter");
        fr.put("gender_male", "Homme");
        fr.put("gender_female", "Femme");
        fr.put("profile_main", "Principal");

        // Meds Status
        fr.put("status_done", "TERMINÉ");
        fr.put("status_progress", "EN COURS");

        // Calendar / Empty
        fr.put("no_rdv_title", "Aucun rendez-vous");
        fr.put("no_rdv_desc", "Vos futurs rendez-vous médicaux s'afficheront ici.");
        fr.put("take_rdv_btn", "Prendre rendez-vous");
        fr.put("new_rdv_btn", "Nouveau RDV");

        // Features Home
        fr.put("feat_key", "Fonctionnalités Clés");
        fr.put("feat_sub", "Tout pour votre santé");
        fr.put("feat_meds", "Médicaments");
        fr.put("feat_meds_desc", "Rappels et suivi quotidien.");
        fr.put("feat_rdv", "Rendez-vous");
        fr.put("feat_rdv_desc", "Agenda médical centralisé.");
        fr.put("feat_fam", "Famille");
        fr.put("feat_fam_desc", "Gérez les profils de vos proches.");
        fr.put("feat_doc", "Documents");
        fr.put("feat_doc_desc", "Export PDF de vos dossiers.");
        fr.put("why_app", "Pourquoi cette application ?");
        fr.put("simplicity", "Simplicité d'utilisation");
        fr.put("adapted_senegal", "Adapté au Sénégal");

        translations.put("FR", fr);

        // --- WOLOF ---
        Map<String, String> wo = new HashMap<>();
        // General
        wo.put("app_name", "Sénégal Santé");
        wo.put("app_subtitle", "Kayeet Numérique");
        wo.put("lang_switch", "Français");
        wo.put("footer_secure", "Mbaat bu wóor • Sutura");
        wo.put("footer_rights", "© 2026 Sénégal Santé. Ño mom lépp.");

        // Auth / Home
        wo.put("home_title", "Sa kayeetu wér-gi-yaram, bu yomb té wóor.");
        wo.put("home_subtitle", "Saytu sa garab, say rendez-vous, ak wér-gi-yaramu sa njaboot gi yépp ci bénn barab.");
        wo.put("login_btn", "Dugg ci biir");
        wo.put("register_btn", "Bindu");
        wo.put("welcome_login", "Dalal ak Jamm");
        wo.put("access_carnet", "Duggallal sa numéro");
        wo.put("phone_placeholder", "Numéro Téléphone (+221)");
        wo.put("password_placeholder", "Sa Code (Mot de passe)");
        wo.put("no_account", "Amo compte ?");
        wo.put("create_account", "Bindu fi");
        wo.put("register_title", "Bindu");
        wo.put("register_subtitle", "Sos sa dossier wér-gi-yaram");
        wo.put("firstname", "Tur");
        wo.put("lastname", "Sant");
        wo.put("vital_info", "Xibaar yu am solo");
        wo.put("gender", "Goor/Jigéen");
        wo.put("male", "Goor");
        wo.put("female", "Jigéen");
        wo.put("start_carnet", "Tambali");
        wo.put("already_account", "Bindu nga ba paré ?");

        // Dashboard
        wo.put("dashboard", "Kër Gi");
        wo.put("family_tracking", "Saytu Njaboot gi");
        wo.put("medications", "Garab yi");
        wo.put("next_rdv", "Rendez-vous bi di ñëw");
        wo.put("to_take", "warul");
        wo.put("none", "Tuss");
        wo.put("today_schedule", "Li ngay def tay");
        wo.put("calm_day", "Lépp dal na tay ! 🌴");
        wo.put("taken", "Jël nga ko");
        wo.put("emergency_call", "Wo 1515");

        // Internal Pages Titles
        wo.put("meds", "Garab yi");
        wo.put("calendar", "Daje yi");
        wo.put("profiles", "Njaboot gi");
        wo.put("prescriptions", "Kayit yi");

        // Profiles
        wo.put("add_btn", "Yokku");
        wo.put("gender_male", "Goor");
        wo.put("gender_female", "Jigéen");
        wo.put("profile_main", "Borom Kër");

        // Meds Status
        wo.put("status_done", "PARE NA");
        wo.put("status_progress", "MI NGI DOKH");

        // Calendar / Empty
        wo.put("no_rdv_title", "Amulo bénn rendez-vous");
        wo.put("no_rdv_desc", "Fi la say rendez-vous di fëgn.");
        wo.put("take_rdv_btn", "Jël rendez-vous");
        wo.put("new_rdv_btn", "Rendez-vous bu bess");

        // Navigation
        wo.put("nav_home", "Kër gi");
        wo.put("nav_meds", "Garab");
        wo.put("nav_calendar", "Daje");
        wo.put("nav_family", "Njaboot");

        // Health / Pain
        wo.put("health_title", "Wér-gi-yaram & Metit");
        wo.put("health_desc", "Waxal li lay metti, nu jox la xel ci sasë.");
        wo.put("pain_type", "Lu lay metti ?");
        wo.put("headache", "Bopp");
        wo.put("belly", "Biir");
        wo.put("muscle", "Siddit");
        wo.put("joint", "Càq");
        wo.put("teeth", "Bëñ");
        wo.put("fever", "Tàngaay");
        wo.put("intensity", "Metit bi (1-10)");
        wo.put("duration", "Dir bi");
        wo.put("less_24h", "< 24h");
        wo.put("24_48h", "24-48h");
        wo.put("more_48h", "> 48h");
        wo.put("description_details", "Leral (ex: ciammoñ...)");
        wo.put("get_advice", "Jox ma xel");
        wo.put("health_advice_title", "Diggal");
        wo.put("assistant_diag", "Ndimbal");
        wo.put("here_is_advice", "Li la assistant bi diggal :");
        wo.put("recap", "Teunku");
        wo.put("back_home", "Dellu Kër gi");

        // Features Home
        wo.put("feat_key", "Li am solo");
        wo.put("feat_sub", "Lépp ngir sa wér-gi-yaram");
        wo.put("feat_meds", "Garab yi");
        wo.put("feat_meds_desc", "Fatali ak saytu.");
        wo.put("feat_rdv", "Rendez-vous");
        wo.put("feat_rdv_desc", "Kayeetu daje yi.");
        wo.put("feat_fam", "Njaboot gi");
        wo.put("feat_fam_desc", "Saytu sa profilu mbokk yi.");
        wo.put("feat_doc", "Kayit yi");
        wo.put("feat_doc_desc", "Soti PDF.");
        wo.put("why_app", "Lutax application bi ?");
        wo.put("simplicity", "Yomb na lool");
        wo.put("adapted_senegal", "Mëngó ak Sénégal");

        translations.put("WO", wo);
    }

    public Map<String, String> getTranslations(String lang) {
        return translations.getOrDefault(lang.toUpperCase(), translations.get("FR"));
    }
}
