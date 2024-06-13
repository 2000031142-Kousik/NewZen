package com.ems.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.SellerExpenditure;
import com.ems.repository.SellerExpenditureRepository;

@Service
public class SellerExpenditureService {

    private final SellerExpenditureRepository sellerExpenditureRepository;

    @Autowired
    public SellerExpenditureService(SellerExpenditureRepository sellerExpenditureRepository) {
        this.sellerExpenditureRepository = sellerExpenditureRepository;
    }

    public void saveExpenditure(SellerExpenditure expenditure) {
        sellerExpenditureRepository.save(expenditure);
    }
    
    public List<SellerExpenditure> getAllSellerExpenditures() {
        return sellerExpenditureRepository.findAll();
    }

    public SellerExpenditure findById(int id) {
        Optional<SellerExpenditure> expenditure = sellerExpenditureRepository.findById(id);
        return expenditure.orElse(null);
    }
}
