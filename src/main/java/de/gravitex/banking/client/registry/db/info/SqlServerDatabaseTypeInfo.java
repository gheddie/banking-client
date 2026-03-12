package de.gravitex.banking.client.registry.db.info;

import de.gravitex.banking.client.registry.db.info.base.DatabaseTypeInfo;

public class SqlServerDatabaseTypeInfo extends DatabaseTypeInfo {

	@Override
	public boolean shouldRunTests() {
		return false;
	}

	@Override
	public String getTypeDescription() {
		return "SQL-Server";
	}
}