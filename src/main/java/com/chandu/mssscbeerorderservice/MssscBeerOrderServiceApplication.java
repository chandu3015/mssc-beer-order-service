package com.chandu.mssscbeerorderservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MssscBeerOrderServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(MssscBeerOrderServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MssscBeerOrderServiceApplication.class, args);
		logger.info("Application started...");
	}

}
