package com.example.gl.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.example.gl.api.web.filter.HeaderFilter;

@SpringBootApplication
@EnableZuulProxy
public class ApiApplication {
	
	@Bean
	HeaderFilter headerFilter() {
		return new HeaderFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}
