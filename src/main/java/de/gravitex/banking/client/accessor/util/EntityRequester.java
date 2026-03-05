package de.gravitex.banking.client.accessor.util;

import de.gravitex.banking.client.exception.EntityRequestException;

public interface EntityRequester {

	void handleRequestException(EntityRequestException aEntityRequestException);
}