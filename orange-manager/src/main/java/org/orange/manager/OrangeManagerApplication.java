package org.orange.manager;

import org.orange.manager.websocket.OrangeWebsocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class OrangeManagerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(OrangeManagerApplication.class, args);
		OrangeWebsocket.setApplicationContext(applicationContext);
	}

}
