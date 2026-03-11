package de.gravitex.banking.client.accessor;

import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.client.accessor.response.HttpGetResult;
import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPostResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking_core.entity.base.IdEntity;

public interface IHttpRemoteHandler {

	HttpPatchResult patchEntity(String aUrl, IdEntity aEntity);

	HttpDeleteResult deleteEntity(String aUrl, IdEntity aEntity);

	HttpGetResult readEntity(HttpRequestBuilder requestBuilder, Class<?> aEntityClass);

	HttpGetResult readEntityList(HttpRequestBuilder aRequestBuilder);

	HttpPostResult post(HttpRequestBuilder forEntity, Object aRequestBody,
			Class<?> aResultEntityClass);

	HttpPutResult putEntity(String url, IdEntity aEntity);

	HttpGetResult readById(HttpRequestBuilder aRequestBuilder);
}