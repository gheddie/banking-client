package de.gravitex.banking.client.accessor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.exception.BankingRequestException;
import de.gravitex.banking_core.entity.base.IdEntity;

public class JsonRemoteHandler {

	private static final String JSON_CONTEXT_TYPE = "application/json";

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

	public HttpPatchResult patchEntity(String aUrl, IdEntity aEntity) {
		try {
			HttpRequest request = HttpRequest.newBuilder().header("Content-type", JSON_CONTEXT_TYPE)
					.method(HttpMethod.PATCH.name(), BodyPublishers.ofString(new JSONObject(aEntity).toString()))
					.uri(URI.create(aUrl)).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			return new HttpPatchResult(response.statusCode(), null);
		} catch (Exception e) {
			e.printStackTrace();
			return new HttpPatchResult();
		}
	}
}