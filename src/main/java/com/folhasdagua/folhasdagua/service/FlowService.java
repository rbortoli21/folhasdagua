
package com.folhasdagua.folhasdagua.service;

import com.folhasdagua.folhasdagua.model.Flow;
import com.folhasdagua.folhasdagua.model.Sensor;
import com.folhasdagua.folhasdagua.repository.FlowRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.folhasdagua.folhasdagua.model.Flow;
import com.folhasdagua.folhasdagua.model.Sensor;
import com.folhasdagua.folhasdagua.repository.FlowRepository;
import jssc.SerialPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlowService {
    @Autowired
    SensorService sensorService;
    @Autowired
    FlowRepository flowRepository;
    SensorConnectService sensorConnectService = new SensorConnectService("COM3");

    public FlowService() {
    }

    public void refreshValues() {
        Timer timer = new Timer();
        this.sensorConnectService.connect();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Flow flow = null;

                try {
                    flow = FlowService.this.makeFlowObject(FlowService.this.sensorConnectService.read(3));
                } catch (SerialPortException var3) {
                    throw new RuntimeException(var3);
                }

                FlowService.this.flowRepository.save(flow);
                System.out.println("salvo");
            }
        }, 100L, 10000L);
        this.sensorConnectService.disconnect();
    }

    public List<Flow> getFlowListBySensor(Sensor sensor) {
        return this.flowRepository.findAllBySensor(sensor);
    }

    private Flow makeFlowObject(String value) {
        Flow flow = new Flow();
        flow.setAmount(this.makeAmount(value));
        flow.setDate(this.getNowDate());
        flow.setHour(this.getNowHours());
        flow.setDuration(10.0);
        flow.setStatus(this.getFlowStatus(value));
        flow.setSensor(this.sensorService.getFirstSensor());
        System.out.println(flow.getAmount());
        return flow;
    }

    private String getValue(String st) {
        this.sensorConnectService.connect();

        try {
            st = this.sensorConnectService.read(3);
            this.sensorConnectService.disconnect();
        } catch (SerialPortException var3) {
            System.out.println("erro: " + var3.getMessage());
        }

        return st;
    }

    private boolean getFlowStatus(String value) {
        return Integer.parseInt(value) >= 50;
    }

    private double makeAmount(String value) {
        double flowValue = (double)Integer.parseInt(value);
        if (flowValue >= 100.0) {
            return 100.0;
        } else {
            return flowValue < 0.0 ? 0.0 : flowValue;
        }
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
}
