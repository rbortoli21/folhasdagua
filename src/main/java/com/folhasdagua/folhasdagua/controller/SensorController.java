package com.folhasdagua.folhasdagua.controller;

import com.folhasdagua.folhasdagua.model.Flow;
import com.folhasdagua.folhasdagua.model.Sensor;
import com.folhasdagua.folhasdagua.repository.FlowRepository;
import com.folhasdagua.folhasdagua.repository.SensorRepository;
import com.folhasdagua.folhasdagua.service.FlowService;
import com.folhasdagua.folhasdagua.service.SensorService;
import java.util.Collections;
import java.util.List;

import com.folhasdagua.folhasdagua.service.SmsService;
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
    SmsService smsService;

    public SensorController() {}

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    public ModelAndView getHomepage() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("sensorList", sensorService.getAll());
        return mv;
    }

    @MessageMapping({"/getSensorList"})
    @SendTo({"/topic/getSensorList"})
    public List<Sensor> getSensorList(){
        return sensorService.getAll();
    }
}
