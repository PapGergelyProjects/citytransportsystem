package cts.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import cts.app.service.MessageHandler;

//@Configuration
//@EnableWebSocket
//@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Autowired
	private MessageHandler handler;
	
    @Override
    public void configureMessageBroker(final MessageBrokerRegistry config) {
    	config.enableSimpleBroker("/cts-channel");
        config.setApplicationDestinationPrefixes("/app");
        
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
    	RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
        registry.addEndpoint("/channel").setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy)).setAllowedOrigins("*");
        registry.addEndpoint("/channel").setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy)).setAllowedOrigins("*").withSockJS();
    }

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(handler);
	}
    
    
}
