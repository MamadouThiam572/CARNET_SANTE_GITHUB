package com.senegalsante.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private int intensity; // 1-5
    private String aiAdvice;
    private LocalDateTime createdAt = LocalDateTime.now();
}