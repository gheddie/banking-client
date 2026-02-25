package de.gravitex.banking.client.registry;

import java.awt.Window;

import de.gravitex.banking.client.accessor.BankingAccessor;
import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.formatter.ValueFormatterFactory;
import de.gravitex.banking.client.interaction.GuiInteractionHandler;
import de.gravitex.banking.client.interaction.InteractionHandler;
import de.gravitex.banking.client.sorter.DefaultEntitySorter;
import de.gravitex.banking.client.sorter.base.EntitySorter;
import de.gravitex.banking_core.controller.admin.BookingAdminData;
import de.gravitex.banking_core.formatter.base.ValueFormatter;

public class ApplicationRegistry {

    private static ApplicationRegistry instance;
    
	private ValueFormatterFactory formatter = new ValueFormatterFactory();
	
	private InteractionHandler interactionHandler = new GuiInteractionHandler();
	
	private IBankingAccessor bankingAccessor = new BankingAccessor();

	private Window parentView;

	private StringTranslator stringTranslator;

	private BookingAdminData adminData;
    
    private ApplicationRegistry() {
    	adminData = bankingAccessor.readAdminData();
    }
    
    public static ApplicationRegistry getInstance() {
        if(instance == null) {
            instance = new ApplicationRegistry();
        }
        
        return instance;
    }

	public String formatValue(Object aValue, Class<? extends ValueFormatter> formatterClass) {
		return formatter.format(aValue, formatterClass);
	}
	
	public InteractionHandler getInteractionHandler() {
		return interactionHandler;
	}
	
	public IBankingAccessor getBankingAccessor() {
		return bankingAccessor;
	}

	public void setParentView(Window aWindow) {
		this.parentView = aWindow;
	}
	
	public Window getParentView() {
		return parentView;
	}

	public EntitySorter getEntitySorter(Class<? extends Object> objectClass) {
		return new DefaultEntitySorter();
	}

	public StringTranslator getStringTranslator() {
		if (stringTranslator == null) {
			stringTranslator = new StringTranslator();
			stringTranslator.init();
		}
		return stringTranslator;
	}

	public BookingAdminData getAdminData() {
		return adminData;
	}
}