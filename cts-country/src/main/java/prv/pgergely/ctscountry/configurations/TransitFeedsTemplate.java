package prv.pgergely.ctscountry.configurations;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import prv.pgergely.ctscountry.utils.TemplateQualifier;
import prv.pgergely.ctscountry.utils.TransitFeedTemplateInterceptor;

@Configuration
public class TransitFeedsTemplate {
	
	@Bean(TemplateQualifier.TRANSITFEED_TEMPLATE)
	public RestTemplate transitTemplate() {
		RestTemplate template = new RestTemplate();
		template.setInterceptors(Arrays.asList(new TransitFeedTemplateInterceptor()));
		
		return template;
	}
	
//	@Bean(TemplateQualifier.ZIPFILE_TEMPLATE)
//	public RestTemplate getZipFileTemplate() {
//		RestTemplate template = new RestTemplate();
//		template.getMessageConverters().add(new ByteArrayHttpMessageConverter());
//		
//		return template;
//	}
	
	
	@Bean(TemplateQualifier.ZIPFILE_TEMPLATE)
	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
	    SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .build();

		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("https", csf).register("http", new PlainConnectionSocketFactory()).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		
		CloseableHttpClient httpClient = HttpClients.custom()
		                .setConnectionManager(cm)
		                .build();
		
		HttpComponentsClientHttpRequestFactory requestFactory =
		                new HttpComponentsClientHttpRequestFactory();
		
		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		
		return restTemplate;
	 }
	
}
