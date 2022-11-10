package com.folhasdagua.folhasdagua.service;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.folhasdagua.folhasdagua.model.Flow;
import com.folhasdagua.folhasdagua.model.Sensor;
import com.folhasdagua.folhasdagua.repository.FlowRepository;
import jssc.SerialPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FlowService {
    @Autowired
    SensorService sensorService;
    @Autowired
    FlowRepository flowRepository;
    SensorConnectService sensorConnectService;
    public FlowService() {
        sensorConnectService = new SensorConnectService();
    }

    public Flow getRealTimeFlow(Sensor sensor) {
        Flow flow = null;
        try {
            String value = sensorConnectService.read();
            flow = makeFlowObject(value, sensor);
            System.out.println(flow);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return flow;
    }

    public void saveRealTimeFlow(Sensor sensor) {
        try {
            Flow flow = getRealTimeFlow(sensor);
            save(flow);
            System.out.println("------------------ SALVO NO BANCO ------------------");
        } catch (Exception exception) {
            saveRealTimeFlow(sensor);
        }
    }

    public double getAmountAverage(Sensor sensor) {
        double average = flowRepository.sumFlowsAmountBySensor(sensor);
        return average / getFlowListBySensor(sensor).size();
    }

    public List<Flow> getFlowListBySensor(Sensor sensor) {
        return flowRepository.findAllBySensor(sensor);
    }

    private Flow makeFlowObject(String value, Sensor sensor) {
        Flow flow = new Flow();
        double amount = Double.parseDouble(value);
        flow.setAmount(makeAmount(amount));
        flow.setDate(getNowDate());
        flow.setHour(getNowHours());
        flow.setDuration(10.0);
        flow.setStatus(getFlowStatus(amount));
        flow.setSensor(sensor);

        return flow;
    }

    private boolean getFlowStatus(double value) {
        return value >= 86;
    }

    private double makeAmount(double value) {
        return Math.min(value, 100.0);
    }

    private String getNowHours() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    private String getNowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    private Flow getLastFlow(Sensor sensor) {
        Stream<Flow> flowStream = getFlowListBySensor(sensor).stream();
        Optional<Flow> flow = flowStream.reduce((first, last) -> last);
        return (Flow) flow.orElse(null);
    }

    public Flow save(Flow flow) {
        return flowRepository.save(flow);
    }
}
