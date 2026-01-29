package com.senegalsante.repository;

import com.senegalsante.model.MedicationIntake;
import com.senegalsante.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicationIntakeRepository extends JpaRepository<MedicationIntake, Long> {

    // Find intakes for a specific user within a date range (e.g., today)
    @Query("SELECT i FROM MedicationIntake i WHERE i.medication.user = :user AND i.scheduledDateTime BETWEEN :start AND :end ORDER BY i.scheduledDateTime ASC")
    List<MedicationIntake> findByUserAndDateRange(@Param("user") User user, @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    // Find overdue intakes
    @Query("SELECT i FROM MedicationIntake i WHERE i.medication.user = :user AND i.scheduledDateTime < :now AND i.status = 'PENDING'")
    List<MedicationIntake> findOverdue(@Param("user") User user, @Param("now") LocalDateTime now);

    List<MedicationIntake> findByMedicationProfileAndScheduledDateTimeBetween(
            com.senegalsante.model.Profile profile,
            LocalDateTime start,
            LocalDateTime end);
}
