package com.chandu.mssscbeerorderservice.bootstrap;

import com.chandu.mssscbeerorderservice.domain.Customer;
import com.chandu.mssscbeerorderservice.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BeerOrderBootStrap implements CommandLineRunner {
    public static final String TASTING_ROOM = "Tasting Room";
    public static final Long BEER_1_UPC =  631234200036l;
    public static final Long BEER_2_UPC = 631234300019l;
    public static final Long BEER_3_UPC = 83783375213l;

    private final CustomerRepository customerRepository;
    @Override
    public void run(String... args) throws Exception {
        loadCustomerData();
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer = customerRepository.save(Customer.builder()
                    .customerName(TASTING_ROOM)
                    .apiKey(UUID.randomUUID())
                    .build());
            System.out.println(" Customer Loaded "+customer);
        }


    }
}
