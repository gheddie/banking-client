package de.gravitex.banking.client.interaction;

import java.awt.Component;

import javax.swing.JOptionPane;

public class GuiInteractionHandler implements InteractionHandler {

	public String readStringValue(String aInputQuery, boolean aRetryOnError, int aMinLength, Component aParent) {
		return null;
	}

	public Integer readIntegerValue(String aInputQuery, boolean aRetryOnError, Component aParent) {
		return null;
	}

	public Long readLongValue(String aInputQuery, boolean aRetryOnError, Component aParent) {
		return null;
	}

	public void confirm(String aInputQuery, boolean aRetryOnError, Component aParent) {
		// TODO Auto-generated method stub
	}

	public boolean yesNo(String aInputQuery, boolean aRetryOnError, Component aParent) {
        int option = JOptionPane.showConfirmDialog(aParent, aInputQuery, "Bitte wählen", JOptionPane.YES_NO_OPTION);
        if (option == 0) {
            return true;
        } else {
            return false;
        }
	}

	public void showMessage(String aMessage, Component aParent) {
		JOptionPane.showMessageDialog(aParent, aMessage);
	}

	public void showError(String aMessage, Component aParent) {
        // TODO
        JOptionPane.showMessageDialog(aParent, aMessage);
	}
}