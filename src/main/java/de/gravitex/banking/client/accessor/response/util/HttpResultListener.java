package de.gravitex.banking.client.accessor.response.util;

import java.util.List;

public interface HttpResultListener {

	void acceptListResult(List<?> aEntityList, String aVariableName);

	void acceptObjectResult(Object antity, String aVariableName);
}