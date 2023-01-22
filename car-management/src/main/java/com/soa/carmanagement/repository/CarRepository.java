package com.soa.carmanagement.repository;

import com.soa.carmanagement.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
