package com.soa.carmanagement.controller;


import com.soa.carmanagement.intercomm.UserClient;
import com.soa.carmanagement.model.Transaction;
import com.soa.carmanagement.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CarController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private CarService carService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Environment env;

    @Value("${spring.application.name}")
    private String serviceId;

    @GetMapping("/service/port")
    public String getPort(){
        return "Service is working at port : " + env.getProperty("local.server.port");
    }

    @GetMapping("/service/instances")
    public ResponseEntity<?> getInstances() {
        return ResponseEntity.ok(discoveryClient.getInstances(serviceId));
    }

    @GetMapping("/service/user/{userId}")
    public ResponseEntity<?> findTransactionsOfUser(@PathVariable Long userId){
        return ResponseEntity.ok(carService.findTransactionsOfUser(userId));
    }

    @GetMapping("/service/all")
    public ResponseEntity<?> findAllCourses(){
        return ResponseEntity.ok(carService.allCars());
    }

    @PostMapping("/service/rent")
    public ResponseEntity<?> saveTransaction(@RequestBody Transaction transaction) {
        transaction.setDateOfIssue(LocalDateTime.now());
        transaction.setCar(carService.findCarById(transaction.getCar().getId()));
        return new ResponseEntity<>(carService.saveTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping("/service/car/{carId}")
    public ResponseEntity<?> findStudentsOfCourse(@PathVariable Long carId){
        List<Transaction> transactions = carService.findTransactionsOfCar(carId);
        if(CollectionUtils.isEmpty(transactions)){
           return ResponseEntity.notFound().build();
        }
        List<Long> userIdList = transactions.parallelStream().map(t -> t.getUserId()).collect(Collectors.toList());
        List<String> students = userClient.getUserNames(userIdList);
        return ResponseEntity.ok(students);
    }
}
