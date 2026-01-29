package com.senegalsante.repository;

import com.senegalsante.model.Appointment;
import com.senegalsante.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserOrderByDateTimeAsc(User user);

    // Find appointments in the next X hours (e.g. 24h)
    List<Appointment> findByUserAndDateTimeBetweenOrderByDateTimeAsc(User user, LocalDateTime start, LocalDateTime end);

    List<Appointment> findByProfileAndDateTimeAfterOrderByDateTimeAsc(com.senegalsante.model.Profile profile,
            LocalDateTime dateTime);
}
