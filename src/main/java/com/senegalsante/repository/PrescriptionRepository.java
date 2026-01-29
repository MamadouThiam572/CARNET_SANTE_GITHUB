package com.senegalsante.repository;

import com.senegalsante.model.Prescription;
import com.senegalsante.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByProfileOrderByPrescriptionDateDesc(Profile profile);
}
