package prv.pgergely.ctscountry.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import prv.pgergely.ctscountry.configurations.CtsConfig;
import prv.pgergely.ctscountry.domain.mobility.token.AuthToken;
import prv.pgergely.ctscountry.utils.TemplateQualifier;

@Service
public class AuthTokenService {
	
	@Autowired
	@Qualifier(TemplateQualifier.MOBILITY_API_TOKEN)
	private WebClient tokenClient;
	
	@Autowired
	private CtsConfig config;
	
	@Autowired
	private AtomicReference<AuthToken> tokenHolder;
	
	public void getAuthToken() {
		Map<String, String> objMap = new HashMap<>();
		objMap.put("refresh_token", config.getMobilityApiRefreshToken());
		AuthToken token = tokenClient.post().body(BodyInserters.fromValue(objMap)).retrieve().bodyToMono(AuthToken.class).block();
		tokenHolder.set(token);
	}
}
