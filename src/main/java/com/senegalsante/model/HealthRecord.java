package com.senegalsante.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "health_records")
public class HealthRecord extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = true)
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String type;

    @Column(nullable = false)
    private LocalDate recordDate;

    private Integer wellnessScore; // 1-100%

    // Paramètres Vitaux
    private Double temperature; // °C
    private Integer systolicPressure; // mmHg
    private Integer diastolicPressure; // mmHg
    private Double weight; // kg
    private Integer heartRate; // bpm

    // Douleur et Symptômes
    private String bodyZone; // Tête, Ventre, etc.
    private Integer intensity; // 0-10
    private String duration; // Durée de la douleur/symptôme
    private String evolution; // Stable, Amélioration, Aggravation

    @Column(columnDefinition = "TEXT")
    private String notes;

    public HealthRecord() {
    }

    public HealthRecord(Profile profile, LocalDate recordDate, Integer wellnessScore,
            Double temperature, Integer systolicPressure, Integer diastolicPressure,
            Double weight, String notes) {
        this.profile = profile;
        this.recordDate = recordDate;
        this.wellnessScore = wellnessScore;
        this.temperature = temperature;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.weight = weight;
        this.notes = notes;
    }

    // Getters and Setters
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getWellnessScore() {
        return wellnessScore;
    }

    public void setWellnessScore(Integer wellnessScore) {
        this.wellnessScore = wellnessScore;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(Integer systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public Integer getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(Integer diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public String getBodyZone() {
        return bodyZone;
    }

    public void setBodyZone(String bodyZone) {
        this.bodyZone = bodyZone;
    }

    public Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type != null ? type : "Bilan Quotidien";
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        StringBuilder desc = new StringBuilder();
        if ("Douleur".equals(type) || "Symptôme".equals(type)) {
            desc.append(type).append(": ").append(bodyZone != null ? bodyZone : "").append(" (Intensité: ")
                    .append(intensity).append("/10)");
        } else if ("Paramètre vital".equals(type)) {
            if (temperature != null)
                desc.append("Temp: ").append(temperature).append("°C ");
            if (systolicPressure != null)
                desc.append("Tension: ").append(systolicPressure).append("/").append(diastolicPressure).append(" ");
            if (weight != null)
                desc.append("Poids: ").append(weight).append("kg ");
        } else if ("Bien-être général".equals(type)) {
            desc.append("Bien-être: ").append(wellnessScore).append("%");
        }

        if (notes != null && !notes.isEmpty()) {
            if (desc.length() > 0)
                desc.append(" | ");
            desc.append(notes);
        }
        return desc.toString();
    }
}
