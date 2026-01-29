package com.senegalsante.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String dosage;

    // Simplification fields
    private String frequencyType; // 'DAILY_x1', 'DAILY_x2', 'EVERY_x_DAYS'
    private int frequencyDaysVal; // For 'Every X days'

    @ElementCollection
    private List<LocalTime> intakeTimes = new ArrayList<>();

    private LocalDate startDate;
    private LocalDate endDate;

    // Legacy support / Derived Text
    private String frequency;
    private String duration;
    private String notes;

    private boolean isTaken = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicationIntake> intakes = new ArrayList<>();

    public Medication() {
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(String frequencyType) {
        this.frequencyType = frequencyType;
    }

    public int getFrequencyDaysVal() {
        return frequencyDaysVal;
    }

    public void setFrequencyDaysVal(int frequencyDaysVal) {
        this.frequencyDaysVal = frequencyDaysVal;
    }

    public List<LocalTime> getIntakeTimes() {
        return intakeTimes;
    }

    public void setIntakeTimes(List<LocalTime> intakeTimes) {
        this.intakeTimes = intakeTimes;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
