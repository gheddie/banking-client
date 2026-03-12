package de.gravitex.banking.client.tester.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.gravitex.banking.client.accessor.response.util.HttpResultListener;
import de.gravitex.banking.client.tester.exception.ManualWebTesterException;
import de.gravitex.banking.entity.base.IdEntity;

public class WebTesterObjectCache implements HttpResultListener {

	private Map<String, Object> objectMap = new HashMap<>();

	@Override
	public void acceptListResult(List<?> aEntityList, String aVariableName) {
		evualuateVariableName(aVariableName);
		objectMap.put(aVariableName, aEntityList);
	}

	@Override
	public void acceptObjectResult(Object antity, String aVariableName) {
		evualuateVariableName(aVariableName);
		objectMap.put(aVariableName, antity);
	}

	private void evualuateVariableName(String aVariableName) {
		if (objectMap.get(aVariableName) != null) {
			throw new ManualWebTesterException("key {" + aVariableName + "} already present in object cache!!!");
		}
	}

	public IdEntity getEntity(String aVariableName) {
		Object entity = objectMap.get(aVariableName);
		if (entity == null) {
			throw new ManualWebTesterException("entity for variable {" + aVariableName + "} name was not cached!!!");
		}
		if (!(entity instanceof IdEntity)) {
			throw new ManualWebTesterException("entity for variable {" + aVariableName + "} is not of type {"
					+ IdEntity.class.getSimpleName() + "}, but {" + entity.getClass().getSimpleName() + "}!!!");
		}
		return (IdEntity) entity;
	}

	public Object getObject(String aVariableName) {
		Object object = objectMap.get(aVariableName);
		if (object == null) {
			throw new ManualWebTesterException("object for variable {" + aVariableName + "} name was not cached!!!");
		}
		return object;
	}
}