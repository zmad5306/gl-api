package com.example.gl.api.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
	
	@RequestMapping("/")
	public ResponseEntity<?> healthCheckRoot() {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping("/healthz")
	public void healthCheck() {
	}

}
