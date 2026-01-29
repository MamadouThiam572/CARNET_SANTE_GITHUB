package com.senegalsante.repository;

import com.senegalsante.model.Symptom;
import com.senegalsante.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    List<Symptom> findByProfileOrderByOnsetDateDesc(Profile profile);

    List<Symptom> findByProfileAndOnsetDateBetweenOrderByOnsetDateAsc(
            Profile profile, LocalDateTime start, LocalDateTime end);
}
