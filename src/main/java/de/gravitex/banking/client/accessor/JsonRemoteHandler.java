package de.gravitex.banking.client.accessor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.gravitex.banking.client.exception.BankingRequestException;
import de.gravitex.banking_core.entity.base.IdEntity;

public class JsonRemoteHandler {

	private HttpClient client;
	
	private ObjectMapper mapper;

	public JsonRemoteHandler() {
		super();
		client = HttpClient.newHttpClient();
		mapper = new ObjectMapper();
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> readEntityList(HttpRequestBuilder aRequestBuilder) {
		try {
			JavaType type = mapper.getTypeFactory().constructParametricType(List.class, aRequestBuilder.getEntityClass());
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(aRequestBuilder.buildRequestUrl()))
					.build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			List<T> result = (List<T>) mapper.readValue(response.body(), type);
			return (List<T>) result;			
		} catch (Exception e) {
			throw new BankingRequestException(aRequestBuilder);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T readEntity(HttpRequestBuilder aRequestBuilder, Class<?> entityClass) {
		try {
			String requestUrl = aRequestBuilder.buildRequestUrl();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl))
					.build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			return (T) mapper.readValue(response.body(), entityClass);
		} catch (Exception e) {
			throw new BankingRequestException(aRequestBuilder);
		}
	}

	public void patch(String aPatchCommand) throws IOException, InterruptedException {
		System.out.println("Patch --> " + aPatchCommand);
		HttpRequest request = HttpRequest.newBuilder().method(HttpMethod.PATCH.name(), BodyPublishers.noBody())
				.uri(URI.create(aPatchCommand)).build();
		client.send(request, BodyHandlers.ofString());
	}

	public void patchEntity(String aUrl, IdEntity aEntity) {
		
		JSONObject json = new JSONObject(aEntity);
		System.out.println(json.toString());
		
		/*
		try {
			StringEntity moo = new StringEntity(json.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		/*
		try {
			HttpPost post = new HttpPost(aUrl);
			post.setEntity(moo);
			// client.send(post, null);						
		} catch (Exception e) {
			// TODO: handle exception
		}
		*/		
		
		/*
		try {
			String payload = mapper.writeValueAsString(aEntity);
			// patch.setEntity(new StringEntity(payload, ContentType.APPLICATION_JSON));
			HttpRequest request = HttpRequest.newBuilder().method(HttpMethod.PATCH.name(), BodyPublishers.noBody())
					.uri(URI.create(aUrl)).build();
			// request.
			HttpResponse<String> result = client.send(request, BodyHandlers.ofString());
			int werner = 5;
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/		
		
		/*
		HttpPost httppost = new HttpPost(aUrl);
		httppost.setHeader("X-GWT-Permutation", "3DE824138FE65400740EC1816A73CACC");
		httppost.setHeader("Content-Type", "text/x-gwt-rpc; charset=UTF-8");
		// StringEntity se = new StringEntity(payLoadLogin );      
		httppost.setEntity(new BasicHttpEntity());
		client.send(httppost, BodyHandlers.ofString());
		*/
		
		int werner = 5;
	}
}