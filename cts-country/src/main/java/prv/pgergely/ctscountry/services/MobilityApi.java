package prv.pgergely.ctscountry.services;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import prv.pgergely.ctscountry.domain.mobility.gtfs.MobilityGtfsFeed;
import prv.pgergely.ctscountry.domain.mobility.token.AuthToken;
import prv.pgergely.ctscountry.utils.TemplateQualifier;

@Service
@CacheConfig(cacheNames="static-feeds")
public class MobilityApi {
	
	@Autowired
	@Qualifier(TemplateQualifier.MOBILITY_API)
	private WebClient mobilityTempalte;
	
	@Autowired
	private AtomicReference<AuthToken> tokenHolder;
	
	@Cacheable(value="gtfs-feed", key="{#dbId}")
	public MobilityGtfsFeed getGtfsFeed(String dbId) {
		final AuthToken token = tokenHolder.get();
		MobilityGtfsFeed result =  mobilityTempalte.get().uri("/gtfs_feeds/"+dbId).headers(header -> {
			header.add("Accept", "application/json");
			header.add("Authorization", "Bearer "+token.getAccessToken());
		}).retrieve().bodyToMono(MobilityGtfsFeed.class).block();
		
		return result;
	}
	
	/*
	public List<MobilityGtfsFeed> getAllGtfsFeeds() {
		return getAllGtfsFeeds(null);
	}
	*/
	
	@Cacheable(value="gtfs-feeds", key="{#countryCode}")
	public List<MobilityGtfsFeed> getAllGtfsFeeds(String countryCode){
		final AuthToken token = tokenHolder.get();
		final String countryQueryParam = countryCode == null ? "" : "?country_code="+countryCode;
		List<MobilityGtfsFeed> feeds = mobilityTempalte.get().uri("/gtfs_feeds"+countryQueryParam).headers(header -> {
			header.add("Accept", "application/json");
			header.add("Authorization", "Bearer "+token.getAccessToken());
		}).retrieve().bodyToMono(new ParameterizedTypeReference<List<MobilityGtfsFeed>>() {}).block();
		
		return feeds;
	}
	
}
