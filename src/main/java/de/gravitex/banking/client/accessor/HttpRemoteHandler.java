package de.gravitex.banking.client.accessor;

import java.io.IOException;
import java.net.ConnectException;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPostResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.exception.BackEndNotAvailableException;
import de.gravitex.banking.entity.base.IdEntity;

public class HttpRemoteHandler implements IHttpRemoteHandler {

	private Logger logger = LoggerFactory.getLogger(HttpRemoteHandler.class);

	private static final String JSON_CONTEXT_TYPE = "application/json";

	private static final String CONTENT_TYPE_ATTRIBUTE = "Content-type";

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

	public HttpDeleteResult deleteEntity(String aUrl, IdEntity aEntity) {
		try {
			String payload = String.valueOf(aEntity.getId());
			HttpRequest request = HttpRequest.newBuilder().header(CONTENT_TYPE_ATTRIBUTE, JSON_CONTEXT_TYPE)
					.method(HttpMethod.DELETE.name(), BodyPublishers.ofString(payload))
					.uri(URI.create(aUrl)).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			String body = response.body();
			return new HttpDeleteResult(response.statusCode(), body, aUrl);
		} catch (Exception e) {
			e.printStackTrace();
			return new HttpDeleteResult(0, e.getMessage(), aUrl);
		}
	}

	public HttpPatchResult patchEntity(String aUrl, IdEntity aEntity) {
		try {
			HttpRequest request = HttpRequest.newBuilder().header(CONTENT_TYPE_ATTRIBUTE, JSON_CONTEXT_TYPE)
					.method(HttpMethod.PATCH.name(), BodyPublishers.ofString(new JSONObject(aEntity).toString()))
					.uri(URI.create(aUrl)).build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			IdEntity responseObject = objectMapper.readValue(response.body(), aEntity.getClass());
			return new HttpPatchResult(response.statusCode(), null, aUrl, responseObject);
		} catch (Exception e) {
			return new HttpPatchResult(0, e.getMessage(), aUrl, null);
		}
	}

	public HttpPutResult putEntity(String aUrl, IdEntity aEntity) {
		HttpResponse<String> response = null;
		try {

			String payload = new JSONObject(aEntity).toString();
			HttpRequest request = HttpRequest.newBuilder().header(CONTENT_TYPE_ATTRIBUTE, JSON_CONTEXT_TYPE)
					.method(HttpMethod.PUT.name(), BodyPublishers.ofString(payload))
					.uri(URI.create(aUrl)).build();
			response = client.send(request, BodyHandlers.ofString());
			String body = response.body();
			IdEntity responseObject = objectMapper.readValue(body, aEntity.getClass());
			return new HttpPutResult(response.statusCode(), null, aUrl, responseObject);
		} catch (Exception e) {
			return new HttpPutResult(response.statusCode(), response.body(), aUrl, null);
		}
	}

	@Override
	public HttpGetResult readEntityList(HttpRequestBuilder aRequestBuilder) {
		HttpResponse<String> response = null;
		try {
			JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class,
					aRequestBuilder.getEntityClass());
			String requestUrl = aRequestBuilder.buildRequestUrl();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl)).build();
			response = client.send(request, BodyHandlers.ofString());
			String body = response.body();
			return new HttpGetResult(response.statusCode(), null, objectMapper.readValue(body, type),
					aRequestBuilder.buildRequestUrl());
		} catch (JacksonException e) {
			return new HttpGetResult(response.statusCode(), response.body(), null, aRequestBuilder.buildRequestUrl());
		} catch (IOException e) {
			return new HttpGetResult(response.statusCode(), response.body(), null, aRequestBuilder.buildRequestUrl());
		} catch (InterruptedException e) {
			return new HttpGetResult(response.statusCode(), response.body(), null, aRequestBuilder.buildRequestUrl());
		}
	}

	@Override
	public HttpGetResult readEntity(HttpRequestBuilder aRequestBuilder, Class<?> aEntityClass) {
		HttpResponse<String> response = null;
		try {
			String requestUrl = aRequestBuilder.buildRequestUrl();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl)).build();
			response = client.send(request, BodyHandlers.ofString());
			return new HttpGetResult(response.statusCode(), null, objectMapper.readValue(response.body(), aEntityClass),
					aRequestBuilder.buildRequestUrl());
		} catch (JsonMappingException e) {
			return new HttpGetResult(response.statusCode(), response.body(), null, aRequestBuilder.buildRequestUrl());
		} catch (JsonProcessingException e) {
			return new HttpGetResult(response.statusCode(), response.body(), null, aRequestBuilder.buildRequestUrl());
		} catch (InterruptedException e) {
			return new HttpGetResult(response.statusCode(), response.body(), null, aRequestBuilder.buildRequestUrl());
		} catch (ConnectException ce) {
			handleInvalidBackendConnection(ce, aRequestBuilder);
			return null;
		} catch (IOException e) {
			return new HttpGetResult(response.statusCode(), response.body(), null, aRequestBuilder.buildRequestUrl());
		}
	}

	@Override
	public HttpGetResult readById(HttpRequestBuilder aRequestBuilder) {
		HttpResponse<String> response = null;
		try {
			String requestUrl = aRequestBuilder.buildRequestUrl();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl)).build();
			response = client.send(request, BodyHandlers.ofString());
			return new HttpGetResult(response.statusCode(), null,
					objectMapper.readValue(response.body(), aRequestBuilder.getEntityClass()),
					aRequestBuilder.buildRequestUrl());
		} catch (Exception e) {
			return new HttpGetResult(response.statusCode(), response.body(), null, aRequestBuilder.buildRequestUrl());
		}
	}

	@Override
	public HttpPostResult post(HttpRequestBuilder aRequestBuilder, Object aRequestBody, Class<?> aResultEntityClass) {
		HttpResponse<String> response = null;
		try {
			String requstUrl = aRequestBuilder.buildRequestUrl();
			HttpRequest request = HttpRequest.newBuilder().header(CONTENT_TYPE_ATTRIBUTE, JSON_CONTEXT_TYPE)
					.method(HttpMethod.POST.name(), BodyPublishers.ofString(new JSONObject(aRequestBody).toString()))
					.uri(URI.create(requstUrl)).build();
			response = client.send(request, BodyHandlers.ofString());
			return new HttpPostResult(response.statusCode(), null,
					objectMapper.readValue(response.body(), aResultEntityClass), aRequestBuilder.buildRequestUrl());
		} catch (JsonMappingException e) {
			return new HttpPostResult(response.statusCode(), response.body(), null, aRequestBuilder.buildRequestUrl());
		} catch (JsonProcessingException e) {
			return new HttpPostResult(response.statusCode(), response.body(), null, aRequestBuilder.buildRequestUrl());
		} catch (IOException e) {
			return new HttpPostResult(response.statusCode(), response.body(), null, aRequestBuilder.buildRequestUrl());
		} catch (InterruptedException e) {
			return new HttpPostResult(response.statusCode(), response.body(), null, aRequestBuilder.buildRequestUrl());
		}
	}

	private void handleInvalidBackendConnection(ConnectException aConnectException,
			HttpRequestBuilder aRequestBuilder) {
		throw new BackEndNotAvailableException(aConnectException, aRequestBuilder);
	}
}