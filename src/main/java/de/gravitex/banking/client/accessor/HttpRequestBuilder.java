package de.gravitex.banking.client.accessor;

import de.gravitex.banking_core.entity.base.NoIdEntity;

public class HttpRequestBuilder {

	private String server = "localhost";

	private int port = 8080;

	private boolean listRequest = false;

	private Class<?> entityClass;

	private String attribute;

	private Long identifier;

	private HttpRequestBuilder(Class<?> aEntityClass, boolean aListRequest) {
		super();
		this.entityClass = aEntityClass;
		this.listRequest = aListRequest;
	}

	public static HttpRequestBuilder forEntityList(Class<? extends NoIdEntity> aEntityClass) {
		return new HttpRequestBuilder(aEntityClass, true);
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
			buffer.append("?id=" + identifier);
		}
		return buffer.toString();
	}

	private String baseAdress() {
		return "http://" + server + ":" + port;
	}

	public HttpRequestBuilder byAttribute(String attribute) {
		this.attribute = attribute;
		return this;
	}

	public HttpRequestBuilder identified(Long aIdentifier) {
		this.identifier = aIdentifier;
		return this;
	}
	
	public Class<?> getEntityClass() {
		return entityClass;
	}

	public static HttpRequestBuilder forEntity(Class<?> aEntityClass) {
		return new HttpRequestBuilder(aEntityClass, false);		
	}
}