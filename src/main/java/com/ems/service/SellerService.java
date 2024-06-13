package com.ems.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.Seller;
import com.ems.repository.SellerRepo;

@Service
public class SellerService {
    
    @Autowired
    private SellerRepo repo;
    
    public void addSeller(Seller seller) {
        repo.save(seller);
    }
    
    public List<Seller> getAllSellers() {
        return repo.findAll();
    }
    
    public Seller findById(int id) {
        Optional<Seller> seller = repo.findById(id);
        return seller.orElse(null);
    }
    
    public void deleteSeller(int id) {
        repo.deleteById(id);
    }
    
    public void updateSeller(Seller seller) {
        repo.save(seller);
    }
}
