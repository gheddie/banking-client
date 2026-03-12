package de.gravitex.banking.client.registry.db.info.base;

public abstract class DatabaseTypeInfo {

	public abstract boolean shouldRunTests();

	public abstract String getTypeDescription();
}