package com.example.restdemo.config;

import com.example.restdemo.model.Employee;
import com.example.restdemo.model.Order;
import com.example.restdemo.model.Status;
import com.example.restdemo.repo.EmployeeRepository;
import com.example.restdemo.repo.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        return args -> {
            employeeRepository.save(new Employee("Anton", "Kulyk", "Tricker"));
            employeeRepository.save(new Employee("Polina", "Kulyk", "Dwarf"));

            employeeRepository.findAll().forEach(employee -> {
                log.info("Preloaded " + employee);
            });

            orderRepository.save(new Order("MackBook pro", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
        };
    }
}
