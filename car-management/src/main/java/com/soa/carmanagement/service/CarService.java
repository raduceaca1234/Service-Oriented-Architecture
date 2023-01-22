package com.soa.carmanagement.service;



import com.soa.carmanagement.model.Car;
import com.soa.carmanagement.model.Transaction;

import java.util.List;

public interface CarService {
    List<Car> allCars();

    Car findCarById(Long carId);

    List<Transaction> findTransactionsOfUser(Long userId);

    List<Transaction> findTransactionsOfCar(Long carId);

    Transaction saveTransaction(Transaction transaction);
}
