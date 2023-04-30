package com.nicele08.notifyme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class NotifymeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifymeApplication.class, args);
	}

}
