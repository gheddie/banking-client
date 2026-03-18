package de.gravitex.banking.client.accessor;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import de.gravitex.banking.entity.base.NoIdEntity;
import de.gravitex.banking_core.controller.bookingimport.ImportFileBookings;
import de.gravitex.banking_core.dto.base.BankingDto;
import de.gravitex.banking_core.util.StringHelper;

public class HttpRequestBuilder {

	private static final String CUSTOM_IDENTIFIER = "id";

	private String server = "localhost";

	private int port = 4712;

	private boolean listRequest = false;

	private Class<?> entityClass;

	private String attribute;

	private Long identifier;

	private String identifierName;

	private static final String PATH_VARIABLE_TEMPLATE = "&{0}={1}";

	private Map<String, String> pathVariables = new HashMap<>();

	private HttpRequestBuilder(Class<?> aEntityClass, boolean aListRequest) {
		super();
		this.entityClass = aEntityClass;
		this.listRequest = aListRequest;
	}

	public String buildRequestUrl() {
		StringBuffer buffer = new StringBuffer(baseAdress());
		buffer.append("/" + entityClass.getSimpleName().toLowerCase());
		if (listRequest) {
			buffer.append("s");
		}
		if (attribute != null) {
			buffer.append("/" + attribute);
		}
		if (identifier != null) {
			buffer.append("?" + identifierName() + "=" + identifier);
		}
		appendPathVariables(buffer);
		return buffer.toString();
	}

	private void appendPathVariables(StringBuffer aBuffer) {
		if (pathVariables.isEmpty()) {
			return;
		}
		for (String variableName : pathVariables.keySet()) {
			aBuffer.append(MessageFormat.format(PATH_VARIABLE_TEMPLATE, variableName, pathVariables.get(variableName)));
		}
	}

	private String identifierName() {
		if (StringHelper.isBlank(identifierName)) {
			return CUSTOM_IDENTIFIER;
		}
		return identifierName;
	}

	private String baseAdress() {
		return "http://" + server + ":" + port;
	}

	public HttpRequestBuilder byAttribute(String attribute) {
		this.attribute = attribute;
		return this;
	}

	public HttpRequestBuilder identified(Long aIdentifier) {
		return this.identified(aIdentifier, null);
	}

	public HttpRequestBuilder identified(Long aIdentifier, String aIdentifierName) {
		this.identifier = aIdentifier;
		this.identifierName = aIdentifierName;
		return this;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public static HttpRequestBuilder forEntity(Class<?> aEntityClass) {
		return new HttpRequestBuilder(aEntityClass, false);
	}

	public static HttpRequestBuilder forEntityList(Class<? extends NoIdEntity> aEntityClass) {
		return new HttpRequestBuilder(aEntityClass, true);
	}

	public static HttpRequestBuilder forDtoList(Class<? extends BankingDto> aDtoClass) {
		return new HttpRequestBuilder(aDtoClass, true);
	}

	public static HttpRequestBuilder forDto(Class<ImportFileBookings> aDtoClass) {
		return new HttpRequestBuilder(aDtoClass, false);
	}

	public HttpRequestBuilder withPathVariable(String aVariableName, String aVariableValue) {
		pathVariables.put(aVariableName, aVariableValue);
		return this;
	}
}