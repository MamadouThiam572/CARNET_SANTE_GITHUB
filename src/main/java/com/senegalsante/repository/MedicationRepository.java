
package com.senegalsante.repository;

import com.senegalsante.model.Medication;
import com.senegalsante.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByUser(User user);
}
