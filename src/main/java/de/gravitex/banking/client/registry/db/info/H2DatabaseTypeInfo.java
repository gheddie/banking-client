package de.gravitex.banking.client.registry.db.info;

import de.gravitex.banking.client.registry.db.info.base.DatabaseTypeInfo;

public class H2DatabaseTypeInfo extends DatabaseTypeInfo {

	@Override
	public boolean shouldRunTests() {
		return true;
	}

	@Override
	public String getTypeDescription() {
		return "H2";
	}
}