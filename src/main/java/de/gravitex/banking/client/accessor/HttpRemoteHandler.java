package de.gravitex.banking.client.accessor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.exception.EntityRequestException;
import de.gravitex.banking_core.entity.base.IdEntity;

public class HttpRemoteHandler {
	
	private Logger logger = LoggerFactory.getLogger(HttpRemoteHandler.class);

	private static final String JSON_CONTEXT_TYPE = "application/json";

	private HttpClient client;

	private ObjectMapper objectMapper;

	public HttpRemoteHandler() {
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
	public <T> T readById(HttpRequestBuilder aRequestBuilder) throws EntityRequestException {
		HttpResponse<String> response = null;
		try {
			String requestUrl = aRequestBuilder.buildRequestUrl();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl)).build();
			response = client.send(request, BodyHandlers.ofString());
			return (T) objectMapper.readValue(response.body(), aRequestBuilder.getEntityClass());
		} catch (Exception e) {
			throw new EntityRequestException(response.body(), e, aRequestBuilder);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> readEntityList(HttpRequestBuilder aRequestBuilder) throws EntityRequestException {
		HttpResponse<String> response = null;
		try {
			JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class,
					aRequestBuilder.getEntityClass());
			String requestUrl = aRequestBuilder.buildRequestUrl();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl)).build();
			response = client.send(request, BodyHandlers.ofString());
			String body = response.body();
			List<T> result = (List<T>) objectMapper.readValue(body, type);
			return (List<T>) result;			
		} catch (JacksonException e) {
			throw new EntityRequestException(response.body(), null, aRequestBuilder);
		} catch (IOException e) {
			throw new EntityRequestException(response.body(), null, aRequestBuilder);
		} catch (InterruptedException e) {
			throw new EntityRequestException(response.body(), null, aRequestBuilder);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T readEntity(HttpRequestBuilder aRequestBuilder, Class<?> entityClass) throws EntityRequestException {
		HttpResponse<String> response = null;
		try {
			String requestUrl = aRequestBuilder.buildRequestUrl();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl)).build();
			response = client.send(request, BodyHandlers.ofString());
			return (T) objectMapper.readValue(response.body(), entityClass);
		} catch (Exception e) {
			throw new EntityRequestException(response.body(), e, aRequestBuilder);
		}
	}

	public HttpDeleteResult deleteEntity(String aUrl, IdEntity aEntity) {
		try {
			HttpRequest request = HttpRequest.newBuilder().header("Content-type", JSON_CONTEXT_TYPE)
					.method(HttpMethod.DELETE.name(), BodyPublishers.ofString(String.valueOf(aEntity.getId())))
					.uri(URI.create(aUrl)).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			logger.info("Löschen ---> " + aEntity + " [" + aUrl + "]");
			String body = response.body();
			return new HttpDeleteResult(response.statusCode(), body);
		} catch (Exception e) {
			e.printStackTrace();
			return new HttpDeleteResult();
		}
	}
	
	public HttpPatchResult patchEntity(String aUrl, IdEntity aEntity) {
		try {
			HttpRequest request = HttpRequest.newBuilder().header("Content-type", JSON_CONTEXT_TYPE)
					.method(HttpMethod.PATCH.name(), BodyPublishers.ofString(new JSONObject(aEntity).toString()))
					.uri(URI.create(aUrl)).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			return new HttpPatchResult(response.statusCode(), response.body());
		} catch (Exception e) {
			e.printStackTrace();
			return new HttpPatchResult();
		}
	}

	public HttpPutResult putEntity(String aUrl, IdEntity aEntity) {
		try {
			HttpRequest request = HttpRequest.newBuilder().header("Content-type", JSON_CONTEXT_TYPE)
					.method(HttpMethod.PUT.name(), BodyPublishers.ofString(new JSONObject(aEntity).toString()))
					.uri(URI.create(aUrl)).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			return new HttpPutResult(response.statusCode(), response.body());
		} catch (Exception e) {
			e.printStackTrace();
			return new HttpPutResult();
		}
	}
}