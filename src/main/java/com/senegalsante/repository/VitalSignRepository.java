package com.senegalsante.repository;

import com.senegalsante.model.Profile;
import com.senegalsante.model.VitalSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VitalSignRepository extends JpaRepository<VitalSign, Long> {

        @Query("SELECT v FROM VitalSign v WHERE v.profile.user.id = :userId ORDER BY v.measurementDate DESC")
        List<VitalSign> findByUserIdOrderByDateDesc(@Param("userId") Long userId);

        List<VitalSign> findByProfileAndSignTypeAndMeasurementDateBetweenOrderByMeasurementDateAsc(
                        Profile profile, String signType, LocalDateTime start, LocalDateTime end);

        List<VitalSign> findByProfileAndSignTypeOrderByMeasurementDateDesc(
                        Profile profile, String signType);

        List<VitalSign> findByProfileOrderByMeasurementDateDesc(Profile profile);

        List<VitalSign> findTop30ByProfileAndSignTypeOrderByMeasurementDateDesc(
                        Profile profile, String signType);
}
