package com.senegalsante.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prescriptions")
public class Prescription extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    private String doctorName;
    private String hospitalName;
    private LocalDate prescriptionDate;
    private String imagePath; // Path to scanned image

    @Column(columnDefinition = "TEXT")
    private String notes;

    public Prescription() {
    }

    public Prescription(Profile profile, String doctorName, String hospitalName, LocalDate prescriptionDate,
            String imagePath, String notes) {
        this.profile = profile;
        this.doctorName = doctorName;
        this.hospitalName = hospitalName;
        this.prescriptionDate = prescriptionDate;
        this.imagePath = imagePath;
        this.notes = notes;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public LocalDate getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDate prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
