package com.folhasdagua.folhasdagua.controller;

import com.folhasdagua.folhasdagua.model.Flow;
import com.folhasdagua.folhasdagua.service.FlowService;
import com.folhasdagua.folhasdagua.service.SensorService;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class FlowController {
    @Autowired
    FlowService flowService;
    @Autowired
    SensorService sensorService;
    public FlowController() {}
    @RequestMapping(value = {"/getFlowList/{id}"}, method = {RequestMethod.GET})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Flow>> getFlowList(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(flowService.getFlowListBySensor(sensorService.getSensorById(id)));
    }
    @MessageMapping({"/getRealTimeFlow"})
    @SendTo({"/topic/realTimeFlow"})
    public Object getRealTimeFlow(@Payload final Integer sensorId) throws Exception {
        System.out.println("passou");
        return flowService.getRealTimeFlow(sensorService.getSensorById(sensorId));
    }
    @MessageMapping({"/getAmountAverage"})
    @SendTo({"/topic/amountAverage"})
    public Object getAmountAverage(@Payload final Integer sensorId) throws Exception {
        return flowService.getAmountAverage(sensorService.getSensorById(sensorId));
    }
    @MessageMapping({"/saveRealTimeFlow"})
    public void saveRealTimeFlow(@Payload final Integer sensorId) throws Exception {
        flowService.saveRealTimeFlow(sensorService.getSensorById(sensorId));
    }
}
