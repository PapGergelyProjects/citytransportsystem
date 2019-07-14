package prv.pgergely.ctscountry.utils;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class TransitFeedZipFileInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		request.getHeaders().set("X-Alternate-FileName", "");
		List<String> contents = request.getHeaders().get("Content-Disposition");
    	if(contents != null && !contents.isEmpty()){
    		String dispos = contents.get(0);
    		Matcher match = Pattern.compile("(?<filename>filename\\=\\\".*\\\")").matcher(dispos);
    		while(match.find()){
    			String[] fileWithExtension = match.group("filename").split("\\=");
    			String fileName = fileWithExtension[1].replaceAll("\"", "");
    			request.getHeaders().set("X-Alternate-FileName", fileName);
    			break;
    		}
    	}
		
		return execution.execute(request, body);
	}
}
