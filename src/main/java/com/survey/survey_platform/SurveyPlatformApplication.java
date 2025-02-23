package com.survey.survey_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 생성일시(createdAt)와 수정일시(updatedAt) 같은 감사(audit) 필드를 자동으로 관리하기 위함
public class SurveyPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveyPlatformApplication.class, args);
	}

}
