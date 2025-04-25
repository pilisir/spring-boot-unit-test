package com.mywebsb.controller;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.mywebsb.service.WelcomeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WelcomeController {
	
	private static final Logger logger 
	  = LoggerFactory.getLogger(WelcomeController.class);
	
	private final WelcomeService welcomeService;

	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@GetMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message + welcomeService.getCurrentPersonFirstName(2));
		logger.debug(message);
		return "welcome";
	}

}
