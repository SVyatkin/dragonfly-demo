package com.vyatkin.dragonfly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.vyatkin.dragonfly.service.WebSocketServerEndPoint;

@Configuration
@EnableWebSocket
@ComponentScan(basePackageClasses = {WebSocketServerEndPoint.class})
public class ServicesConfiguration {

	@Bean
	public WebSocketServerEndPoint cloudWebSocketServerEndPoint() {
		return new WebSocketServerEndPoint();
	}
	
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
