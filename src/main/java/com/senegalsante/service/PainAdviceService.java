package com.senegalsante.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class PainAdviceService {

    private final Map<String, String> adviceMap;

    public PainAdviceService() {
        adviceMap = new HashMap<>();
        // Common symptoms mapping
        adviceMap.put("tête",
                "Le repos dans un endroit calme et sombre est recommandé. Hydratez-vous bien. Si la douleur persiste, le paracétamol peut aider.");
        adviceMap.put("ventre",
                "Évitez les repas copieux. Une infusion de gingembre ou de menthe peut soulager. Consultez si la douleur est aiguë.");
        adviceMap.put("dos",
                "Maintenez une bonne posture. Des étirements légers et l'application de chaleur peuvent soulager la tension.");
        adviceMap.put("dent",
                "Brossez-vous les dents doucement. Les clous de girofle peuvent agir comme anesthésiant naturel. Consultez un dentiste rapidement.");
        adviceMap.put("fièvre",
                "Buvez beaucoup d'eau, portez des vêtements légers et reposez-vous. Surveillez votre température régulièrement.");
        adviceMap.put("gorge",
                "Les gargarismes à l'eau salée et le miel peuvent apaiser l'irritation. Buvez des boissons chaudes.");
    }

    public String analyzeSymptom(com.senegalsante.model.Symptom symptom) {
        StringBuilder advice = new StringBuilder();
        int severity = symptom.getSeverity();
        String type = symptom.getPainType();
        String duration = symptom.getDuration();

        // 1. Severity Check High Priority
        if (severity >= 8) {
            return "⚠️ ALERTE : Douleur intense (" + severity
                    + "/10). Consultez un médecin ou allez aux urgences immédiatement.";
        }

        // 2. Specific Advice by Type
        if ("HEADACHE".equals(type)) {
            advice.append("Maux de tête : Reposez-vous dans le noir et au calme. Hydratez-vous. ");
            if (severity < 5)
                advice.append("Un simple antalgique peut suffire. ");
            else
                advice.append("Évitez les écrans. ");
        } else if ("ABDOMINAL".equals(type)) {
            advice.append("Douleurs abdominales : Évitez les repas lourds. Privilégiez riz, bouillon. ");
            advice.append("Une bouillotte chaude peut aider. ");
        } else if ("MUSCLE".equals(type)) {
            advice.append("Douleurs musculaires : Repos de la zone. Étirements doux. ");
            advice.append("Appliquez du chaud pour détendre ou du froid si inflammation récente. ");
        } else if ("JOINT".equals(type)) {
            advice.append("Douleurs articulaires : Évitez de forcer sur l'articulation. ");
            advice.append("Le froid peut soulager une crise inflammatoire. ");
        } else if ("DENTAL".equals(type)) {
            advice.append("Douleurs dentaires : Brossez doucement. Évitez le très chaud/froid. ");
            advice.append("Consultez un dentiste rapidement. ");
        } else if ("FEVER".equals(type)) {
            advice.append("Fièvre : Hydratation maximale. Découvrez-vous. ");
            advice.append("Surveillez la température. Si > 38.5°C persistante, consultez. ");
        } else {
            advice.append("Surveillez l'évolution des symptômes. Reposez-vous. ");
        }

        // 3. Duration Advice
        if ("LONG".equals(duration)) { // Assumed value from form for > 48h
            advice.append(
                    "\n\n🚨 Attention : Comme la douleur persiste depuis plus de 48h, une consultation médicale est recommandée.");
        }

        // 4. Age Context (Example logic)
        // if (symptom.getProfile().getBirthDate()...) { ... }

        return advice.toString();
    }
}
