package com.folhasdagua.folhasdagua.controller;

import com.folhasdagua.folhasdagua.model.Flow;
import com.folhasdagua.folhasdagua.model.Sensor;
import com.folhasdagua.folhasdagua.repository.FlowRepository;
import com.folhasdagua.folhasdagua.repository.SensorRepository;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SensorController {
    @Autowired
    SensorService sensorService;
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    FlowService flowService;
    @Autowired
    FlowRepository flowRepository;

    public SensorController() {}

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    public ModelAndView getHomepage() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Sensor> sensorList = this.sensorService.getAll();
        List<Flow> flows = this.flowRepository.findAllBySensor(this.sensorService.getSensorActive());
        Flow lastFlow = null;
        if (flows.size() > 0) {
            lastFlow = (Flow)flows.get(flows.size() - 1);
        }

        modelAndView.addObject("flows", flows);
        modelAndView.addObject("sensorList", sensorList);
        modelAndView.addObject("lastFlow", lastFlow);
        return modelAndView;
    }

    @MessageMapping({"/getFlowListBySensor"})
    @SendTo({"/topic/flowList"})
    public List<Object> refreshEntity(@Payload final Integer sensorId) throws Exception {
        return Collections.singletonList(this.flowService.getFlowListBySensor(this.sensorService.getSensorById(sensorId)));
    }
}
