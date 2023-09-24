package prv.pgergely.ctscountry.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class TransitFeedTemplateInterceptor implements ClientHttpRequestInterceptor {
	
	private Logger logger = LogManager.getLogger(TransitFeedTemplateInterceptor.class);
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		
		ClientHttpResponse response = execution.execute(request, body);
		StringBuilder join = new StringBuilder();
		try(BufferedReader bfr = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"))){
			while(bfr.ready()){
				join.append(bfr.readLine());
			}
		}catch(IOException e){
			logger.error(e);
		}
		
		String json = removeArrayNoti(join.toString());
		
		ClientHttpResponse alteredResponse = new ClientHttpResponse() {

			@Override
			public InputStream getBody() throws IOException {
				return new ByteArrayInputStream(json.getBytes());
			}

			@Override
			public HttpHeaders getHeaders() {
				return response.getHeaders();
			}

			@Override
			public HttpStatusCode getStatusCode() throws IOException {
				return response.getStatusCode();
			}

			@Override
			public int getRawStatusCode() throws IOException {
				return response.getStatusCode().value();
			}

			@Override
			public String getStatusText() throws IOException {
				return response.getStatusText();
			}

			@Override
			public void close() {
				response.close();
			}};
		
		return alteredResponse;
	}
	
	private String removeArrayNoti(String jsonFeed){
		return jsonFeed.replaceAll("\"u\":\\[\\]", "\"u\":\\{\\}");// This is a jury-rigged solution, because transit feeds api sometimes(especially when no download link) change the {} brackets to [].
	}
	
}
