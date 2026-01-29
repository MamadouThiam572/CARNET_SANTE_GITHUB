package com.senegalsante.service;

import com.senegalsante.model.Medication;
import com.senegalsante.model.MedicationIntake;
import com.senegalsante.model.User;
import com.senegalsante.repository.MedicationIntakeRepository;
import com.senegalsante.repository.MedicationRepository;
import com.senegalsante.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private MedicationIntakeRepository intakeRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveMedicationWithSchedule(Medication med) {
        // Save the medication definition first
        medicationRepository.save(med);

        // Generate Intakes
        generateIntakes(med);
    }

    private void generateIntakes(Medication med) {
        List<MedicationIntake> intakes = new ArrayList<>();
        LocalDate current = med.getStartDate();
        LocalDate end = med.getEndDate();

        if (end == null) {
            // If no end date (shouldn't happen with our wizard, but safety fall back to 30
            // days)
            end = current.plusDays(30);
        }

        while (!current.isAfter(end)) {
            // Check frequency logic
            boolean isDay = false;

            if ("EVERY_DAYS".equals(med.getFrequencyType())) {
                // Logic for every X days
                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(med.getStartDate(), current);
                if (daysBetween % med.getFrequencyDaysVal() == 0) {
                    isDay = true;
                }
            } else {
                // DAILY or default
                isDay = true;
            }

            if (isDay) {
                for (LocalTime time : med.getIntakeTimes()) {
                    LocalDateTime scheduled = LocalDateTime.of(current, time);
                    // Only add if in the future or today? Allow today even if time passed for
                    // recording purposes? Yes.
                    intakes.add(new MedicationIntake(med, scheduled));
                }
            }
            current = current.plusDays(1);
        }

        intakeRepository.saveAll(intakes);
    }

    public List<MedicationIntake> getIntakesForToday(User user) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);
        List<MedicationIntake> results = intakeRepository.findByUserAndDateRange(user, start, end);
        return results != null ? results : new ArrayList<>();
    }

    public void takeIntake(Long intakeId) {
        MedicationIntake intake = intakeRepository.findById(intakeId).orElse(null);
        if (intake != null) {
            intake.setStatus(MedicationIntake.Status.TAKEN);
            intake.setTakenDateTime(LocalDateTime.now());
            intakeRepository.save(intake);
        }
    }

    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }

    public Medication getMedicationById(Long id) {
        return medicationRepository.findById(id).orElse(null);
    }
}
