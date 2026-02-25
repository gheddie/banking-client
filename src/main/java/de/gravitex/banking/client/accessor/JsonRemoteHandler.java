package de.gravitex.banking.client.accessor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.gravitex.banking.client.exception.BankingRequestException;

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

	public void patch(String aPatchCommand) throws IOException, InterruptedException {
		System.out.println("Patch --> " + aPatchCommand);
		HttpRequest request = HttpRequest.newBuilder().method(HttpMethod.PATCH.name(), BodyPublishers.noBody())
				.uri(URI.create(aPatchCommand)).build();
		client.send(request, BodyHandlers.ofString());
	}
}