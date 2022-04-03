package prv.pgergely.ctsdata.config;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import prv.pgergely.ctsdata.utility.Schema;
import prv.pgergely.ctsdata.utility.WebSocketSessionHandler;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketClientConfig implements WebSocketMessageBrokerConfigurer  {
	
	@Autowired
	private Schema schema;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/channel");
	}

	@Bean
	public StandardWebSocketClient getWsClient() {
		StandardWebSocketClient client = new StandardWebSocketClient();
		try {
			HttpHeaders header = new HttpHeaders();
			header.set("X-Schema", schema.getSchemaName());
			WebSocketStompClient stompClient = new WebSocketStompClient(initSSL(client));
			stompClient.setMessageConverter(new MappingJackson2MessageConverter());
			stompClient.connect(URI.create("ws://localhost:8080/cts/channel"), new WebSocketHttpHeaders(header), new StompHeaders(), new WebSocketSessionHandler());
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return client;
	}
	
	private SockJsClient initSSL(StandardWebSocketClient client) throws NoSuchAlgorithmException, KeyManagementException {

        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        Map<String, Object> properties = new HashMap<>();
        properties.put("org.apache.tomcat.websocket.SSL_CONTEXT", sc);
        client.setUserProperties(properties);

        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(client));

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());
        
        return sockJsClient;
	}
	
}
