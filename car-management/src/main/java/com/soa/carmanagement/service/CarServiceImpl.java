package com.soa.carmanagement.service;


import com.soa.carmanagement.model.Car;
import com.soa.carmanagement.model.Transaction;
import com.soa.carmanagement.repository.CarRepository;
import com.soa.carmanagement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Car> allCars() {
        return carRepository.findAll();
    }

    @Override
    public Car findCarById(Long carId) {
        return carRepository.findById(carId).orElse(null);
    }

    @Override
    public List<Transaction> findTransactionsOfUser(Long userId) {
        return transactionRepository.findAllByUserId(userId);
    }

    @Override
    public List<Transaction> findTransactionsOfCar(Long carId) {
        return transactionRepository.findAllByCarId(carId);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
