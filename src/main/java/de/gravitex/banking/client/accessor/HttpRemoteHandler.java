package de.gravitex.banking.client.accessor;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.gravitex.banking.client.accessor.exception.ResponseMappingException;
import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPostResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.accessor.response.util.RequestDuration;
import de.gravitex.banking.client.accessor.response.util.StreamTraffic;
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

	public HttpDeleteResult deleteEntity(HttpRequestBuilder aRequestBuilder, IdEntity aEntity) {
		RequestDuration duration = new RequestDuration();
		HttpResponse<String> response = null;
		String aRequestUrl = aRequestBuilder.buildRequestUrl();
		try {
			String payload = String.valueOf(aEntity.getId());
			HttpRequest request = HttpRequest.newBuilder().header(CONTENT_TYPE_ATTRIBUTE, JSON_CONTEXT_TYPE)
					.method(HttpMethod.DELETE.name(), BodyPublishers.ofString(payload)).uri(URI.create(aRequestUrl))
					.build();
			response = client.send(request, BodyHandlers.ofString());
			String body = response.body();
			Object responseObject = mapResponseEntity(aEntity.getClass(), body);
			return new HttpDeleteResult(response.statusCode(), null, aRequestUrl, responseObject, null,
					new StreamTraffic(payload), new StreamTraffic(response.body()));
		} catch (Exception e) {
			return new HttpDeleteResult(response.statusCode(), response.body(), aRequestUrl, null, null,
					new StreamTraffic(), new StreamTraffic());
		}
	}

	public HttpPatchResult patchEntity(HttpRequestBuilder aRequestBuilder, IdEntity aEntity) {
		RequestDuration duration = new RequestDuration();
		HttpResponse<String> response = null;
		String aRequestUrl = aRequestBuilder.buildRequestUrl();
		try {
			HttpRequest request = HttpRequest.newBuilder().header(CONTENT_TYPE_ATTRIBUTE, JSON_CONTEXT_TYPE)
					.method(HttpMethod.PATCH.name(), BodyPublishers.ofString(new JSONObject(aEntity).toString()))
					.uri(URI.create(aRequestUrl)).build();
			response = client.send(request, BodyHandlers.ofString());
			Object responseObject = mapResponseEntity(aEntity.getClass(), response.body());
			return new HttpPatchResult(response.statusCode(), null, aRequestUrl, responseObject, duration.finish(),
					new StreamTraffic(), new StreamTraffic());
		} catch (Exception e) {
			return new HttpPatchResult(response.statusCode(), response.body(), aRequestUrl, null, duration.finish(),
					new StreamTraffic(), new StreamTraffic());
		}
	}

	public HttpPutResult putEntity(HttpRequestBuilder aRequestBuilder, IdEntity aEntity) {
		RequestDuration duration = new RequestDuration();
		HttpResponse<String> response = null;
		String aRequestUrl = aRequestBuilder.buildRequestUrl();
		try {
			String payload = new JSONObject(aEntity).toString();
			HttpRequest request = HttpRequest.newBuilder().header(CONTENT_TYPE_ATTRIBUTE, JSON_CONTEXT_TYPE)
					.method(HttpMethod.PUT.name(), BodyPublishers.ofString(payload)).uri(URI.create(aRequestUrl))
					.build();
			response = client.send(request, BodyHandlers.ofString());
			String body = response.body();
			Object responseObject = mapResponseEntity(aEntity.getClass(), body);
			return new HttpPutResult(response.statusCode(), null, aRequestUrl, responseObject, duration.finish(),
					new StreamTraffic(), new StreamTraffic());
		} catch (Exception e) {
			return new HttpPutResult(response.statusCode(), response.body(), aRequestUrl, null, duration.finish(),
					new StreamTraffic(), new StreamTraffic());
		}
	}

	@Override
	public HttpGetResult readEntityList(HttpRequestBuilder aRequestBuilder) {
		RequestDuration duration = new RequestDuration();
		HttpResponse<String> response = null;
		String requestUrl = aRequestBuilder.buildRequestUrl();
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl)).build();
			response = client.send(request, BodyHandlers.ofString());
			String body = response.body();
			return new HttpGetResult(response.statusCode(), null,
					mapResponseEntityList(body, aRequestBuilder.getEntityClass()), aRequestBuilder.buildRequestUrl(),
					duration.finish(), new StreamTraffic(), new StreamTraffic());
		} catch (Exception e) {
			return new HttpGetResult(0, response.body(), null, requestUrl, duration.finish(), new StreamTraffic(),
					new StreamTraffic());
		}
	}

	@Override
	public HttpGetResult readEntity(HttpRequestBuilder aRequestBuilder, Class<?> aEntityClass) {
		RequestDuration duration = new RequestDuration();
		HttpResponse<String> response = null;
		String requestUrl = aRequestBuilder.buildRequestUrl();
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl)).build();
			response = client.send(request, BodyHandlers.ofString());
			return new HttpGetResult(response.statusCode(), null, mapResponseEntity(aEntityClass, response.body()),
					aRequestBuilder.buildRequestUrl(), duration.finish(), new StreamTraffic(), new StreamTraffic());
		} catch (Exception e) {
			return new HttpGetResult(response.statusCode(), response.body(), null, requestUrl, duration.finish(),
					new StreamTraffic(), new StreamTraffic());
		}
	}

	@Override
	public HttpGetResult readById(HttpRequestBuilder aRequestBuilder) {
		RequestDuration duration = new RequestDuration();
		HttpResponse<String> response = null;
		String requestUrl = aRequestBuilder.buildRequestUrl();
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestUrl)).build();
			response = client.send(request, BodyHandlers.ofString());
			return new HttpGetResult(response.statusCode(), null,
					mapResponseEntity(aRequestBuilder.getEntityClass(), response.body()),
					aRequestBuilder.buildRequestUrl(), duration.finish(), new StreamTraffic(), new StreamTraffic(response.body()));
		} catch (Exception e) {
			return new HttpGetResult(response.statusCode(), response.body(), null, requestUrl, duration.finish(),
					new StreamTraffic(), new StreamTraffic(response.body()));
		}
	}

	@Override
	public HttpPostResult post(HttpRequestBuilder aRequestBuilder, Object aRequestBody, Class<?> aResultEntityClass) {
		RequestDuration duration = new RequestDuration();
		HttpResponse<String> response = null;
		String requestUrl = aRequestBuilder.buildRequestUrl();
		String requestPayload = new JSONObject(aRequestBody).toString();
		try {
			HttpRequest request = HttpRequest.newBuilder().header(CONTENT_TYPE_ATTRIBUTE, JSON_CONTEXT_TYPE)
					.method(HttpMethod.POST.name(), BodyPublishers.ofString(requestPayload))
					.uri(URI.create(requestUrl)).build();
			response = client.send(request, BodyHandlers.ofString());
			return new HttpPostResult(response.statusCode(), null,
					mapResponseEntity(aResultEntityClass, response.body()), aRequestBuilder.buildRequestUrl(),
					duration.finish(), new StreamTraffic(requestPayload), new StreamTraffic(response.body()));
		} catch (Exception e) {
			return new HttpPostResult(response.statusCode(), response.body(), null, requestUrl, duration.finish(),
					new StreamTraffic(requestPayload), new StreamTraffic(response.body()));
		}
	}

	private Object mapResponseEntity(Class<?> entityClass, String body) throws ResponseMappingException {
		try {
			return objectMapper.readValue(body, entityClass);
		} catch (JacksonException e) {
			throw new ResponseMappingException(
					"unable to map an entity of type {" + entityClass.getSimpleName() + "}!!!", e);
		}
	}

	private List<?> mapResponseEntityList(String body, Class<?> aEntityClass) throws ResponseMappingException {
		try {
			return objectMapper.readValue(body,
					objectMapper.getTypeFactory().constructParametricType(List.class, aEntityClass));
		} catch (JacksonException e) {
			throw new ResponseMappingException(
					"unable to map an entity of type {" + aEntityClass.getSimpleName() + "}!!!", e);
		}
	}
}