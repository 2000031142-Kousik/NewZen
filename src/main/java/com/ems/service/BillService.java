package com.ems.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ems.entity.Bill;
import com.ems.repository.BillRepository;
import org.springframework.data.repository.CrudRepository;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public List<Bill> getAllBills() {
        return (List<Bill>) billRepository.findAll();
    }

    public void addBill(Bill bill) {
        billRepository.save(bill);
    }

    public Bill getBillById(int id) {
        return billRepository.findById(id).orElse(null);
    }

    public void updateBill(Bill bill) {
        billRepository.save(bill);
    }

    public void deleteBill(int id) {
        billRepository.deleteById(id);
    }
    
    public Bill save(Bill billObj)
    {
    	return billRepository.save(billObj);
    }
}
