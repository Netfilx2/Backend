package com.sparta.clonecoding_unite_00;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling // 스케줄러 시작
@EnableJpaAuditing // timestamped 적용
@SpringBootApplication
public class ClonecodingUnite00Application {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.properties,"
			+ "classpath:aws.yml";

//	public static void main(String[] args) {
//		SpringApplication.run(ClonecodingUnite00Application.class, args);
//	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(ClonecodingUnite00Application.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
