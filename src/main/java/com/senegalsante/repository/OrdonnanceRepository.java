package com.senegalsante.repository;

import com.senegalsante.model.Ordonnance;
import com.senegalsante.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdonnanceRepository extends JpaRepository<Ordonnance, Long> {
    List<Ordonnance> findByUserOrderByDatePrescriptionDesc(User user);
}
