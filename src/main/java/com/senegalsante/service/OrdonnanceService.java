package com.senegalsante.service;

import com.senegalsante.model.Ordonnance;
import com.senegalsante.model.User;
import com.senegalsante.repository.OrdonnanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdonnanceService {

    @Autowired
    private OrdonnanceRepository ordonnanceRepository;

    public List<Ordonnance> getOrdonnancesByUser(User user) {
        return ordonnanceRepository.findByUserOrderByDatePrescriptionDesc(user);
    }

    public Ordonnance saveOrdonnance(Ordonnance ordonnance) {
        return ordonnanceRepository.save(ordonnance);
    }

    public void deleteOrdonnance(Ordonnance ordonnance) {
        ordonnanceRepository.delete(ordonnance);
    }
}
