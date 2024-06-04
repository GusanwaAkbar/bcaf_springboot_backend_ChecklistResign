package com.hrs.checklist_resign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.hrs.checklist_resign.repository")
public class ChecklistResignApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChecklistResignApplication.class, args);
	}

}
