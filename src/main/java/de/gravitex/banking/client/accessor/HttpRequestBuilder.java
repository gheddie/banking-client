package de.gravitex.banking.client.accessor;

import de.gravitex.banking.entity.base.NoIdEntity;
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
			buffer.append("?"+identifierName()+"=" + identifier);
		}
		return buffer.toString();
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
}