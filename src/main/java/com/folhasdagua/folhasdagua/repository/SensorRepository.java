package com.folhasdagua.folhasdagua.repository;

import com.folhasdagua.folhasdagua.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
}
