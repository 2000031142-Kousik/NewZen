package com.ems.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.OtherExpenditure;
import com.ems.repository.OtherExpenditureRepository;

@Service
public class OtherExpenditureService {

    private final OtherExpenditureRepository otherExpenditureRepository;

    @Autowired
    public OtherExpenditureService(OtherExpenditureRepository otherExpenditureRepository) {
        this.otherExpenditureRepository = otherExpenditureRepository;
    }

    public void saveExpenditure(OtherExpenditure expenditure) {
        otherExpenditureRepository.save(expenditure);
    }

    public List<OtherExpenditure> getAllOtherExpenditures() {
        return otherExpenditureRepository.findAll();
    }

    public OtherExpenditure findById(int id) {
        Optional<OtherExpenditure> expenditure = otherExpenditureRepository.findById(id);
        return expenditure.orElse(null);
    }
}
