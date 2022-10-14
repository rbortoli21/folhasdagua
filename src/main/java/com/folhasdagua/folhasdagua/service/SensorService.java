package com.folhasdagua.folhasdagua.service;

import com.folhasdagua.folhasdagua.model.Flow;
import com.folhasdagua.folhasdagua.model.Sensor;
import com.folhasdagua.folhasdagua.repository.FlowRepository;
import com.folhasdagua.folhasdagua.repository.SensorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorService {
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    FlowRepository flowRepository;
    public Integer idActive;

    public SensorService() {
    }

    public Sensor getSensorById(Integer sensorId) {
        Optional<Sensor> sensorOptional = this.sensorRepository.findById(sensorId);
        return (Sensor)sensorOptional.orElse(null);
    }

    public List<Flow> getSensorFlows(Integer sensorId) {
        Optional<Sensor> sensorOptional = this.sensorRepository.findById(sensorId);
        List<Flow> flows = this.flowRepository.findAll();
        if (sensorOptional.isPresent()) {
            Sensor var4 = (Sensor)sensorOptional.get();
        }

        return new ArrayList();
    }

    public Sensor getFirstSensor() {
        Optional<Sensor> sensorOptional = this.sensorRepository.findAll().stream().findFirst();
        return (Sensor)sensorOptional.orElse(null);
    }

    public List<Sensor> getAll() {
        return this.sensorRepository.findAll();
    }

    public void setSensorIdActive(Integer id) {
        this.idActive = id;
    }

    public Sensor getSensorActive() {
        Optional<Sensor> sensorOptional = this.sensorRepository.findById(1);
        return sensorOptional.isPresent() ? (Sensor)sensorOptional.get() : null;
    }
}
