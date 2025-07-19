package com.fastturtle.hibernateallmappingsspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HibernateAllMappingsSpringBootApplication {

    //	@PostConstruct
//	public void init() {
    // Setting Spring Boot SetTimeZone to Indian Standard Time
//    TimeZone.setDefault(TimeZone.getTimeZone("IST"));
//	}

    public static void main(String[] args) {
        SpringApplication.run(HibernateAllMappingsSpringBootApplication.class, args);
    }

}
