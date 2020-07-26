package com.microservices.discoverynamingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryNamingServerApplication.class, args);
	}

}
