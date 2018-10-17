package com.vyatkin.dragonfly.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Configuration
@Controller
@EnableAutoConfiguration
@ComponentScan
public class StaticController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getWss() throws Exception {
    	return "page.html";
    }
    
    @RequestMapping(value = "/simulator", method = RequestMethod.GET)
    public String start() throws Exception {
    	return "startSimulation2.html";
    }
    
    @RequestMapping(value = "/pull", method = RequestMethod.GET)
    public String pull() throws Exception {
    	return "sensorChart.htm";
    }
}