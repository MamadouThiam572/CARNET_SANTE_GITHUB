package com.senegalsante.repository;

import com.senegalsante.model.HealthRecord;
import com.senegalsante.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {

    @Query("SELECT h FROM HealthRecord h WHERE h.profile.user.id = :userId")
    List<HealthRecord> findByUserId(@Param("userId") Long userId);

    List<HealthRecord> findByProfileAndRecordDateBetweenOrderByRecordDateAsc(
            Profile profile, LocalDate start, LocalDate end);

    List<HealthRecord> findByProfileOrderByRecordDateDesc(Profile profile);

    Optional<HealthRecord> findByProfileAndRecordDate(Profile profile, LocalDate date);

    List<HealthRecord> findTop30ByProfileOrderByRecordDateDesc(Profile profile);
}
