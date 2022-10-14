package com.folhasdagua.folhasdagua.controller;

import com.folhasdagua.folhasdagua.model.Flow;
import com.folhasdagua.folhasdagua.service.FlowService;
import com.folhasdagua.folhasdagua.service.SensorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public FlowController() {
    }

    @RequestMapping(
            value = {"/refreshValues"},
            method = {RequestMethod.GET}
    )
    @ResponseStatus(HttpStatus.OK)
    public void refreshValues() {
        this.flowService.refreshValues();
    }

    @RequestMapping(
            value = {"/getFlowList/{id}"},
            method = {RequestMethod.GET}
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Flow>> getFlowList(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.flowService.getFlowListBySensor(this.sensorService.getSensorById(id)));
    }
}
