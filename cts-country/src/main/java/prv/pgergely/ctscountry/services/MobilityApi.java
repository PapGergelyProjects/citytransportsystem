package prv.pgergely.ctscountry.services;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
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
	
	private String expiredToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjYwOWY4ZTMzN2ZjNzg1NTE0ZTExMGM2ZDg0N2Y0M2M3NDM1M2U0YWYiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiR2VyZ2VseSBQYXAiLCJwaWN0dXJlIjoiaHR0cHM6Ly9hdmF0YXJzLmdpdGh1YnVzZXJjb250ZW50LmNvbS91LzM3NTc5OTQ4P3Y9NCIsImlzcyI6Imh0dHBzOi8vc2VjdXJldG9rZW4uZ29vZ2xlLmNvbS9tb2JpbGl0eS1mZWVkcy1wcm9kIiwiYXVkIjoibW9iaWxpdHktZmVlZHMtcHJvZCIsImF1dGhfdGltZSI6MTcxMDQ4NTczMywidXNlcl9pZCI6IjlpNkhta1MwWnBnRjR0QUlVNVJwaDVqajNONjMiLCJzdWIiOiI5aTZIbWtTMFpwZ0Y0dEFJVTVScGg1amozTjYzIiwiaWF0IjoxNzEwNDkwMTc3LCJleHAiOjE3MTA0OTM3NzcsImVtYWlsIjoicGFwZ2VyZ2VseTkxQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImdpdGh1Yi5jb20iOlsiMzc1Nzk5NDgiXSwiZW1haWwiOlsicGFwZ2VyZ2VseTkxQGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6ImdpdGh1Yi5jb20ifX0.gBCKXFz3BLKmrjH8URbx2JlBZ7SbaQ8BBChQi5n1rtyO8ZmuA9s8NU1XEeTPc2yxtqDrLAUBup4layiTSWmdAYM03km3u9xTAm-Y2Lg0qOd-kyKrXwFpKKpu01PLJ_-CijpB7glxCRdecS2v7hKweNmUG-4VsVB0Y0gfJnhw6w6o3zr94qIGj7VGJg8RSpyqPCJX6SNluAJ5OqSuUyow_MiRm5Jid73gm7RMNygczB1bGCXABQ-ovXI9psM2jDlxEEyQra6ayFIWpsqoXWpa-lQZpaxG6jLyYPkOIyhUCZQfAZENNMiPAwf9UyEk4rDjnKqmIdjXKbUVVNtSAddywQ";
	
	public MobilityGtfsFeed getGtfsFeed(String dbId) {
		final AuthToken token = tokenHolder.get();
		MobilityGtfsFeed result =  mobilityTempalte.get().uri("/gtfs_feeds/"+dbId).headers(header -> {
			header.add("Accept", "application/json");
			header.add("Authorization", "Bearer "+token.getAccessToken());
		}).retrieve().bodyToMono(MobilityGtfsFeed.class).block();
		
		return result;
	}
	
	public List<MobilityGtfsFeed> getAllGtfsFeeds() {
		return getAllGtfsFeeds(null);
	}
	
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
