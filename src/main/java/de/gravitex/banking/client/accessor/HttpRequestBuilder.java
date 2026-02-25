package de.gravitex.banking.client.accessor;

import de.gravitex.banking_core.entity.base.NoIdEntity;

public class HttpRequestBuilder {

	private String server = "localhost";

	private int port = 8080;

	private boolean listRequest = false;

	private Class<? extends NoIdEntity> entityClass;

	private String attribute;

	private Long identifier;

	private HttpRequestBuilder(Class<? extends NoIdEntity> aEntityClass) {
		super();
		this.entityClass = aEntityClass;
		this.listRequest = true;
	}

	public static HttpRequestBuilder forEntityList(Class<? extends NoIdEntity> aEntityClass) {
		return new HttpRequestBuilder(aEntityClass);
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
	
	public Class<? extends NoIdEntity> getEntityClass() {
		return entityClass;
	}
}