package de.gravitex.banking.client.accessor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.gravitex.banking.client.exception.BankingRequestException;
import de.gravitex.banking_core.entity.Booking;
import de.gravitex.banking_core.entity.base.IdEntity;

public class JsonRemoteHandler {

	private HttpClient client;

	private ObjectMapper objectMapper;

	public JsonRemoteHandler() {
		super();
		client = HttpClient.newHttpClient();
		objectMapper = initObjectMapper();
	}

	private ObjectMapper initObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> readEntityList(HttpRequestBuilder aRequestBuilder) {
		try {
			JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class,
					aRequestBuilder.getEntityClass());
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(aRequestBuilder.buildRequestUrl())).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			List<T> result = (List<T>) objectMapper.readValue(response.body(), type);
			return (List<T>) result;
		} catch (Exception e) {
			throw new BankingRequestException(aRequestBuilder, e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T readEntity(HttpRequestBuilder aRequestBuilder, Class<?> entityClass) {
		try {
			String requestUrl = aRequestBuilder.buildRequestUrl();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl)).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			return (T) objectMapper.readValue(response.body(), entityClass);
		} catch (Exception e) {
			throw new BankingRequestException(aRequestBuilder, e);
		}
	}

	public void patch(String aPatchCommand) throws IOException, InterruptedException {
		System.out.println("Patch --> " + aPatchCommand);
		HttpRequest request = HttpRequest.newBuilder().method(HttpMethod.PATCH.name(), BodyPublishers.noBody())
				.uri(URI.create(aPatchCommand)).build();
		client.send(request, BodyHandlers.ofString());
	}

	public void patchEntity(String aUrl, IdEntity aEntity) {
		try {
			JSONObject json = new JSONObject(aEntity);
			System.out.println(json.toString());
			HttpRequest request = HttpRequest.newBuilder().header("Content-type", "application/json")
					.method(HttpMethod.PATCH.name(), BodyPublishers.ofString(json.toString())).uri(URI.create(aUrl))
					.build();
			client.send(request, BodyHandlers.ofString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}