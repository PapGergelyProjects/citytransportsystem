package prv.pgergely.ctscountry.configurations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import prv.pgergely.ctscountry.domain.mobility.token.AuthToken;
import prv.pgergely.ctscountry.utils.TemplateQualifier;
import prv.pgergely.ctscountry.utils.UnauthorizedException;
import reactor.core.publisher.Mono;

@Configuration
public class MobilityApiTemplate {
	
	@Autowired
	private CtsConfig config;
	
	@Autowired
	private AtomicReference<AuthToken> tokenHolder;
	
	private Logger logger = LogManager.getLogger(MobilityApiTemplate.class);
	
	@Profile("main")
	@Bean(TemplateQualifier.MOBILITY_API)
	public WebClient getWebClientTemplate() {
		return WebClient.builder().filter(retryOn401Filter()).baseUrl(config.getMobilityApiUrl()).build();
	}
	
	@Profile("test")
	@Bean(TemplateQualifier.MOBILITY_API)
	public WebClient getWebClientTemplateTest() {
		return WebClient.builder().filter(debugFilter()).baseUrl(config.getMobilityApiUrl()).build();
	}
	
	private AuthToken getAuthToken() {
		Map<String, String> objMap = new HashMap<>();
		objMap.put("refresh_token", config.getMobilityApiRefreshToken());
		final AuthToken token = WebClient.builder().baseUrl(config.getMobilityApiTokenUrl()).defaultHeaders(header -> {
			header.add("Content-Type", "application/json");
		}).build().post().body(BodyInserters.fromValue(objMap)).retrieve().bodyToMono(AuthToken.class).block();
		tokenHolder.set(token);
		
		return token;
	}
	
	private ExchangeFilterFunction retryOn401Filter() {
		return (req, next) -> {
			boolean isMissingAuth = Optional.ofNullable(req.headers().get(HttpHeaders.AUTHORIZATION)).stream().flatMap(f -> f.stream()).filter(p -> "Bearer null".equals(p)).findFirst().isPresent();
			if(isMissingAuth) {
				return next.exchange(applyAuth(req));
			}
			return next.exchange(req).flatMap(resp -> {
				HttpStatus status = HttpStatus.valueOf(resp.statusCode().value());
				logger.info("status: "+status);
				switch (status) {
					case UNAUTHORIZED:{
						logger.info("unauth. retry...");
						return next.exchange(applyAuth(req));
					}
					default: {
						return Mono.just(resp);
					}
				}
			});
		};
	}
	
	private ExchangeFilterFunction debugFilter() {
		return (req, next) -> {
			return next.exchange(req).flatMap(resp -> {
				HttpStatus status = HttpStatus.valueOf(resp.statusCode().value());
				logger.info("status: "+status);
				switch (status) {
					case UNAUTHORIZED:{
						return Mono.error(new UnauthorizedException("Test access token expired, renew another one."));
					}
					default: {
						return Mono.just(resp);
					}
				}
			});
		};
	}
	
	private ClientRequest applyAuth(ClientRequest req) {
		final AuthToken token = getAuthToken();
		return ClientRequest.from(req).headers(headers -> headers.setBearerAuth(token.getAccessToken())).build();
	}
	
}
