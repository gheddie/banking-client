package de.gravitex.banking.client.registry.db;

import java.util.HashMap;
import java.util.Map;

import de.gravitex.banking.client.registry.db.info.H2DatabaseTypeInfo;
import de.gravitex.banking.client.registry.db.info.SqlServerDatabaseTypeInfo;
import de.gravitex.banking.client.registry.db.info.base.DatabaseTypeInfo;

public class DatabaseAdministrator {

	private static final Map<String, DatabaseTypeInfo> DB_INFOS = new HashMap<String, DatabaseTypeInfo>();
	static {
		DB_INFOS.put("com.microsoft.sqlserver.jdbc.SQLServerDriver", new SqlServerDatabaseTypeInfo());
		DB_INFOS.put("org.h2.Driver", new H2DatabaseTypeInfo());
	}

	public DatabaseTypeInfo getDatabaseInfoForDriverClass(String databaseDriverClass) {
		DatabaseTypeInfo typeInfo = DB_INFOS.get(databaseDriverClass);
		if (typeInfo == null) {
			throw new IllegalArgumentException(
					"no database info available for driver class {" + databaseDriverClass + "}!!!");
		}
		return typeInfo;
	}
}