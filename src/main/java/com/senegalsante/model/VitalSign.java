package com.senegalsante.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vital_signs")
public class VitalSign extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(nullable = false)
    private LocalDateTime measurementDate;

    @Column(nullable = false)
    private String signType; // TEMPERATURE, BLOOD_PRESSURE, WEIGHT, GLUCOSE, HEART_RATE, etc.

    private Double measurementValue;
    private String unit;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public VitalSign() {
    }

    public VitalSign(Profile profile, LocalDateTime measurementDate, String signType,
            Double measurementValue, String unit, String notes) {
        this.profile = profile;
        this.measurementDate = measurementDate;
        this.signType = signType;
        this.measurementValue = measurementValue;
        this.unit = unit;
        this.notes = notes;
    }

    // Getters and Setters
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public LocalDateTime getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(LocalDateTime measurementDate) {
        this.measurementDate = measurementDate;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public Double getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(Double measurementValue) {
        this.measurementValue = measurementValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Enum for common vital sign types
    public enum SignType {
        TEMPERATURE,
        BLOOD_PRESSURE,
        WEIGHT,
        GLUCOSE,
        HEART_RATE,
        OXYGEN_SATURATION,
        RESPIRATORY_RATE
    }
}
