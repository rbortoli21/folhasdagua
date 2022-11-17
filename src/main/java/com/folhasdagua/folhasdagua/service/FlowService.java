package com.folhasdagua.folhasdagua.service;

import com.folhasdagua.folhasdagua.model.Flow;
import com.folhasdagua.folhasdagua.model.Sensor;
import com.folhasdagua.folhasdagua.repository.FlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FlowService {
    @Autowired
    SmsService smsService;
    @Autowired
    FlowRepository flowRepository;
    SensorConnectService sensorConnectService;

    private final double MIN_STATUS_CHANGE = 86;

    public FlowService() {
        sensorConnectService = new SensorConnectService();
    }

    public Flow getRealTimeFlow(Sensor sensor) {
        Flow flow = null;
        try {
            String value = sensorConnectService.read();
            flow = makeFlowObject(Double.parseDouble(value), sensor);
            if (!flow.isStatus())
                smsService.send("Tem algo de errado com a irrigação, verifique com urgência!");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return flow;
    }

    public void saveRealTimeFlow(Sensor sensor) {
        try {
            Flow flow = getRealTimeFlow(sensor);
            save(flow);
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

    private Flow makeFlowObject(Double amount, Sensor sensor) {
        Flow flow = new Flow();

        flow.setAmount(makeAmount(amount));
        flow.setDate(getNowDate());
        flow.setHour(getNowHours());
        flow.setDuration(10.0);
        flow.setStatus(getFlowStatus(amount));
        flow.setSensor(sensor);

        return flow;
    }

    private boolean getFlowStatus(double value) {
        return value >= MIN_STATUS_CHANGE;
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
