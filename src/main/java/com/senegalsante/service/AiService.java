package com.senegalsante.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class AiService {
    private final String API_KEY = System.getenv("API_KEY");
    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    public String getHealthAdvice(String symptoms) {
        if (API_KEY == null) return "Conseil indisponible (Clé API manquante).";

        RestTemplate restTemplate = new RestTemplate();
        String url = GEMINI_URL + API_KEY;

        Map<String, Object> requestBody = Map.of(
            "contents", List.of(Map.of(
                "parts", List.of(Map.of("text", "Analyse ces symptômes pour un patient au Sénégal et donne 2 conseils simples : " + symptoms))
            ))
        );

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestBody, Map.class);
            // Extraction simplifiée du texte de la réponse Gemini
            List candidates = (List) response.getBody().get("candidates");
            Map content = (Map) ((Map) candidates.get(0)).get("content");
            List parts = (List) content.get("parts");
            return (String) ((Map) parts.get(0)).get("text");
        } catch (Exception e) {
            return "Consultez un médecin. Erreur IA : " + e.getMessage();
        }
    }
}