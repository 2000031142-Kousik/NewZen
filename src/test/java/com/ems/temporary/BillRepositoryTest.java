package com.ems.temporary;

import com.ems.entity.Bill;
import com.ems.repository.BillRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class BillRepositoryTest {

    @Autowired
    private BillRepository billRepository;

    @Test
    public void testCreateNewBill() {
        Bill bill = new Bill(
                "2024-05-12", // date
                "John Doe", // name
                "New York", // place
                "ABC123", // vehicleNumber
                1500.0, // fullVehicleWeight
                1000.0, // emptyVehicleWeight
                500.0, // weight
                10, // bags
                100.0, // reductionWeight
                400.0, // finalWeight
                50.0, // rate
                5000.0, // total
                "Cash", // modeofpayment
                "2024-05-12", // dateOfPayment
                "INV123", // invoiceNumber
                "example.jpg" // photos
        );
        Bill savedBill = billRepository.save(bill);
        assertThat(savedBill).isNotNull();
    }
}
