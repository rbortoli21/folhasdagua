package com.folhasdagua.folhasdagua.service;

import com.folhasdagua.folhasdagua.model.Sensor;
import com.folhasdagua.folhasdagua.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {
    @Autowired
    SensorRepository sensorRepository;

    public SensorService() {
    }

    public Sensor getSensorById(Integer sensorId) {
        Optional<Sensor> sensorOptional = this.sensorRepository.findById(sensorId);
        return (Sensor)sensorOptional.orElse(null);
    }

    public Sensor getFirstSensor() {
        Optional<Sensor> sensorOptional = this.sensorRepository.findAll().stream().findFirst();
        return (Sensor)sensorOptional.orElse(null);
    }

    public List<Sensor> getAll() {
        return this.sensorRepository.findAll();
    }

    public Sensor getSensorActive() {
        Optional<Sensor> sensorOptional = this.sensorRepository.findById(1);
        return (Sensor) sensorOptional.orElse(null);
    }

}
