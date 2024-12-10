package prv.pgergely.ctscountry.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.JsonObject;

import prv.pgergely.ctscountry.domain.mobility.token.AuthToken;
import prv.pgergely.ctscountry.utils.TemplateQualifier;
import prv.pgergely.ctscountry.utils.UnauthorizedException;
import reactor.core.publisher.Mono;

@Configuration
public class MobilityApiTemplate {
	
	@Autowired
	private CtsConfig config;
	
	@Bean(TemplateQualifier.MOBILITY_API)
	public WebClient getWebClientTemplate() {
		return WebClient.builder().filter(errorHandler()).baseUrl(config.getMobilityApiUrl()).build();
	}
	
	@Bean(TemplateQualifier.MOBILITY_API_TOKEN)
	public WebClient refreshAccessToken() {
		return WebClient.builder().baseUrl(config.getMobilityApiTokenUrl()).defaultHeaders(header -> {
			header.add("Content-Type", "application/json");
		}).build();
	}
	
	private ExchangeFilterFunction errorHandler(){
		return ExchangeFilterFunction.ofResponseProcessor(clientResp -> {
			HttpStatus status = HttpStatus.valueOf(clientResp.statusCode().value());
			switch (status) {
				case UNAUTHORIZED: {
					return clientResp.bodyToMono(String.class).flatMap(eb -> Mono.error(new UnauthorizedException(eb)));
				}
				case OK: {
					return Mono.just(clientResp);
				}
				default:{
					return Mono.just(clientResp);
				}
			}
		});
		
	}
	
}
