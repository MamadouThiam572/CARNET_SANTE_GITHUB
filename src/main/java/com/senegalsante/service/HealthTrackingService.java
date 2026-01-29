package com.senegalsante.service;

import com.senegalsante.model.HealthRecord;
import com.senegalsante.model.MedicationIntake;
import com.senegalsante.model.Profile;
import com.senegalsante.repository.HealthRecordRepository;
import com.senegalsante.repository.MedicationIntakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class HealthTrackingService {

    @Autowired
    private HealthRecordRepository healthRecordRepository;

    @Autowired
    private MedicationIntakeRepository intakeRepository;

    /**
     * Save a health record
     */
    public HealthRecord saveHealthRecord(HealthRecord record) {
        return healthRecordRepository.save(record);
    }

    /**
     * Get health records for a profile within a date range
     */
    public List<HealthRecord> getHealthRecords(Profile profile, LocalDate start, LocalDate end) {
        return healthRecordRepository.findByProfileAndRecordDateBetweenOrderByRecordDateAsc(
                profile, start, end);
    }

    /**
     * Get latest health records for a profile
     */
    public List<HealthRecord> getLatestHealthRecords(Profile profile, int limit) {
        List<HealthRecord> all = healthRecordRepository.findByProfileOrderByRecordDateDesc(profile);
        return all.size() > limit ? all.subList(0, limit) : all;
    }

    /**
     * Calculate medication observance rate for a profile
     * Returns percentage (0-100)
     */
    public double calculateObservanceRate(Profile profile, LocalDate start, LocalDate end) {
        List<MedicationIntake> allIntakes = intakeRepository
                .findByMedicationProfileAndScheduledDateTimeBetween(
                        profile,
                        start.atStartOfDay(),
                        end.atTime(23, 59));

        if (allIntakes.isEmpty()) {
            return 0.0;
        }

        long takenCount = allIntakes.stream()
                .filter(i -> i.getStatus() == MedicationIntake.Status.TAKEN)
                .count();

        return (takenCount * 100.0) / allIntakes.size();
    }

    /**
     * Calculate daily observance rates for charting
     */
    public Map<LocalDate, Double> calculateDailyObservance(Profile profile, LocalDate start, LocalDate end) {
        Map<LocalDate, Double> dailyRates = new LinkedHashMap<>();

        LocalDate current = start;
        while (!current.isAfter(end)) {
            double rate = calculateObservanceRate(profile, current, current);
            dailyRates.put(current, rate);
            current = current.plusDays(1);
        }

        return dailyRates;
    }

    /**
     * Analyze health trend based on wellness scores
     * Returns: "IMPROVING", "STABLE", or "DECLINING"
     */
    public String analyzeTrend(List<HealthRecord> records) {
        if (records.size() < 2) {
            return "INSUFFICIENT_DATA";
        }

        // Calculate average of first half vs second half
        int midPoint = records.size() / 2;

        double firstHalfAvg = records.subList(0, midPoint).stream()
                .filter(r -> r.getWellnessScore() != null)
                .mapToInt(HealthRecord::getWellnessScore)
                .average()
                .orElse(0.0);

        double secondHalfAvg = records.subList(midPoint, records.size()).stream()
                .filter(r -> r.getWellnessScore() != null)
                .mapToInt(HealthRecord::getWellnessScore)
                .average()
                .orElse(0.0);

        double difference = secondHalfAvg - firstHalfAvg;

        if (difference > 1.0) {
            return "IMPROVING";
        } else if (difference < -1.0) {
            return "DECLINING";
        } else {
            return "STABLE";
        }
    }

    /**
     * Identify critical periods (low wellness scores)
     */
    public List<String> identifyCriticalPeriods(List<HealthRecord> records) {
        List<String> criticalPeriods = new ArrayList<>();

        for (HealthRecord record : records) {
            if (record.getWellnessScore() != null && record.getWellnessScore() <= 3) {
                criticalPeriods.add(
                        record.getRecordDate().toString() +
                                " - Score très bas: " + record.getWellnessScore() + "/10");
            }
        }

        return criticalPeriods;
    }

    /**
     * Generate health summary text
     */
    public String generateHealthSummary(Profile profile, LocalDate start, LocalDate end) {
        List<HealthRecord> records = getHealthRecords(profile, start, end);

        if (records.isEmpty()) {
            return "Aucune donnée de santé disponible pour cette période.";
        }

        double avgWellness = records.stream()
                .filter(r -> r.getWellnessScore() != null)
                .mapToInt(HealthRecord::getWellnessScore)
                .average()
                .orElse(0.0);

        String trend = analyzeTrend(records);
        double observance = calculateObservanceRate(profile, start, end);

        StringBuilder summary = new StringBuilder();
        summary.append(String.format("Période du %s au %s:\n", start, end));
        summary.append(String.format("- Score de bien-être moyen: %.1f/10\n", avgWellness));
        summary.append(String.format("- Tendance: %s\n", getTrendLabel(trend)));
        summary.append(String.format("- Taux d'observance du traitement: %.1f%%\n", observance));

        List<String> criticalPeriods = identifyCriticalPeriods(records);
        if (!criticalPeriods.isEmpty()) {
            summary.append(String.format("- %d période(s) critique(s) identifiée(s)\n", criticalPeriods.size()));
        }

        return summary.toString();
    }

    /**
     * Get French label for trend
     */
    private String getTrendLabel(String trend) {
        switch (trend) {
            case "IMPROVING":
                return "En amélioration";
            case "DECLINING":
                return "En dégradation";
            case "STABLE":
                return "Stable";
            default:
                return "Données insuffisantes";
        }
    }

    /**
     * Calculate correlation score between observance and wellness improvement
     * Returns a score from -1 (negative correlation) to 1 (positive correlation)
     */
    public double calculateCorrelation(List<HealthRecord> records, Map<LocalDate, Double> observance) {
        if (records.size() < 2 || observance.isEmpty()) {
            return 0.0;
        }

        // Simple correlation: compare wellness improvement with observance
        List<Double> wellnessChanges = new ArrayList<>();
        List<Double> observanceValues = new ArrayList<>();

        for (int i = 1; i < records.size(); i++) {
            HealthRecord prev = records.get(i - 1);
            HealthRecord curr = records.get(i);

            if (prev.getWellnessScore() != null && curr.getWellnessScore() != null) {
                double change = curr.getWellnessScore() - prev.getWellnessScore();
                Double obs = observance.get(curr.getRecordDate());

                if (obs != null) {
                    wellnessChanges.add(change);
                    observanceValues.add(obs);
                }
            }
        }

        if (wellnessChanges.isEmpty()) {
            return 0.0;
        }

        // Calculate simple correlation
        double avgWellnessChange = wellnessChanges.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double avgObservance = observanceValues.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        double numerator = 0.0;
        double denomWellness = 0.0;
        double denomObservance = 0.0;

        for (int i = 0; i < wellnessChanges.size(); i++) {
            double wellnessDiff = wellnessChanges.get(i) - avgWellnessChange;
            double obsDiff = observanceValues.get(i) - avgObservance;

            numerator += wellnessDiff * obsDiff;
            denomWellness += wellnessDiff * wellnessDiff;
            denomObservance += obsDiff * obsDiff;
        }

        double denominator = Math.sqrt(denomWellness * denomObservance);

        return denominator == 0 ? 0.0 : numerator / denominator;
    }
}
