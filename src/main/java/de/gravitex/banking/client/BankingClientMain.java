package de.gravitex.banking.client;

import de.gravitex.banking.client.registry.ApplicationRegistry;

public class BankingClientMain {
	
	public static void main(String[] args) {	
		BankingClient bankingClient = new BankingClient();
		ApplicationRegistry.getInstance().setParentView(bankingClient);
		bankingClient.onStartUp().setVisible(true);
	}
}