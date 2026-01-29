package com.senegalsante.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordonnances")
public class Ordonnance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomMedecin;
    private String specialite;
    private LocalDate datePrescription;
    private String dureeTraitement;

    @Column(columnDefinition = "TEXT")
    private String commentaires;

    private String cheminPhoto; // Chemin vers la photo de l'ordonnance

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "lignes_ordonnance", joinColumns = @JoinColumn(name = "ordonnance_id"))
    private List<LigneOrdonnance> medicaments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Ordonnance() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomMedecin() {
        return nomMedecin;
    }

    public void setNomMedecin(String nomMedecin) {
        this.nomMedecin = nomMedecin;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public LocalDate getDatePrescription() {
        return datePrescription;
    }

    public void setDatePrescription(LocalDate datePrescription) {
        this.datePrescription = datePrescription;
    }

    public String getDureeTraitement() {
        return dureeTraitement;
    }

    public void setDureeTraitement(String dureeTraitement) {
        this.dureeTraitement = dureeTraitement;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public List<LigneOrdonnance> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(List<LigneOrdonnance> medicaments) {
        this.medicaments = medicaments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCheminPhoto() {
        return cheminPhoto;
    }

    public void setCheminPhoto(String cheminPhoto) {
        this.cheminPhoto = cheminPhoto;
    }

    @Embeddable
    public static class LigneOrdonnance {
        private String nomMedicament;
        private String dosage;
        private String frequence;

        public LigneOrdonnance() {
        }

        public LigneOrdonnance(String nomMedicament, String dosage, String frequence) {
            this.nomMedicament = nomMedicament;
            this.dosage = dosage;
            this.frequence = frequence;
        }

        public String getNomMedicament() {
            return nomMedicament;
        }

        public void setNomMedicament(String nomMedicament) {
            this.nomMedicament = nomMedicament;
        }

        public String getDosage() {
            return dosage;
        }

        public void setDosage(String dosage) {
            this.dosage = dosage;
        }

        public String getFrequence() {
            return frequence;
        }

        public void setFrequence(String frequence) {
            this.frequence = frequence;
        }

        @Override
        public String toString() {
            return nomMedicament + " (" + dosage + ") - " + frequence;
        }
    }
}
