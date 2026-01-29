package com.senegalsante.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "symptoms")
public class Symptom extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(nullable = false)
    private String painType; // Headache, Stomach, etc.

    private String duration; // e.g. < 24h, > 48h

    @Column(columnDefinition = "TEXT")
    private String associatedSymptoms; // Nausea, Fatigue...

    @Column(nullable = false)
    private String description;

    private int severity; // 1-10
    private LocalDateTime onsetDate;

    private Boolean resolved = false;
    private LocalDateTime resolutionDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public Symptom() {
    }

    public Symptom(Profile profile, String painType, String description, int severity, LocalDateTime onsetDate,
            String duration, String associatedSymptoms, String notes) {
        this.profile = profile;
        this.painType = painType;
        this.description = description;
        this.severity = severity;
        this.onsetDate = onsetDate;
        this.duration = duration;
        this.associatedSymptoms = associatedSymptoms;
        this.notes = notes;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getPainType() {
        return painType;
    }

    public void setPainType(String painType) {
        this.painType = painType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public LocalDateTime getOnsetDate() {
        return onsetDate;
    }

    public void setOnsetDate(LocalDateTime onsetDate) {
        this.onsetDate = onsetDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAssociatedSymptoms() {
        return associatedSymptoms;
    }

    public void setAssociatedSymptoms(String associatedSymptoms) {
        this.associatedSymptoms = associatedSymptoms;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }

    public LocalDateTime getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(LocalDateTime resolutionDate) {
        this.resolutionDate = resolutionDate;
    }
}