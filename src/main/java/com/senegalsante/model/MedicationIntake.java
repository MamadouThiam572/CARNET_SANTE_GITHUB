package com.senegalsante.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medication_intakes")
public class MedicationIntake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    private LocalDateTime scheduledDateTime;

    private LocalDateTime takenDateTime;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public MedicationIntake() {
    }

    public MedicationIntake(Medication medication, LocalDateTime scheduledDateTime) {
        this.medication = medication;
        this.scheduledDateTime = scheduledDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public LocalDateTime getScheduledDateTime() {
        return scheduledDateTime;
    }

    public void setScheduledDateTime(LocalDateTime scheduledDateTime) {
        this.scheduledDateTime = scheduledDateTime;
    }

    public LocalDateTime getTakenDateTime() {
        return takenDateTime;
    }

    public void setTakenDateTime(LocalDateTime takenDateTime) {
        this.takenDateTime = takenDateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        PENDING, TAKEN, MISSED, SKIPPED
    }
}
