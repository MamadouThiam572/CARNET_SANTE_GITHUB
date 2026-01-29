package com.senegalsante.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "profiles")
public class Profile extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String gender;

    private String bloodGroup;
    private String address;
    private String city;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    private boolean isMainProfile = false;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Medication> medications = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Appointment> appointments = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Prescription> prescriptions = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<HealthRecord> healthRecords = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Symptom> symptoms = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<VitalSign> vitalSigns = new java.util.ArrayList<>();

    public Profile() {
    }

    private String relation; // e.g. Enfant, Conjoint

    public Profile(Long id, User user, String firstName, String lastName, LocalDate birthDate, String gender,
            String bloodGroup, String address, String city, String allergies, String relation, boolean isMainProfile) {
        this.id = id;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.city = city;
        this.allergies = allergies;
        this.relation = relation;
        this.isMainProfile = isMainProfile;
    }

    // Getters and Setters for the new collections
    public java.util.List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(java.util.List<Medication> medications) {
        this.medications = medications;
    }

    public java.util.List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(java.util.List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public java.util.List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(java.util.List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public java.util.List<HealthRecord> getHealthRecords() {
        return healthRecords;
    }

    public void setHealthRecords(java.util.List<HealthRecord> healthRecords) {
        this.healthRecords = healthRecords;
    }

    public java.util.List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(java.util.List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public java.util.List<VitalSign> getVitalSigns() {
        return vitalSigns;
    }

    public void setVitalSigns(java.util.List<VitalSign> vitalSigns) {
        this.vitalSigns = vitalSigns;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public boolean isMainProfile() {
        return isMainProfile;
    }

    public void setMainProfile(boolean mainProfile) {
        isMainProfile = mainProfile;
    }

    // Méthode de convenance pour JavaFX
    public String getNom() {
        return firstName + " " + lastName;
    }
}
