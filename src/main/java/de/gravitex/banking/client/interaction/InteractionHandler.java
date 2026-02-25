package de.gravitex.banking.client.interaction;

import java.awt.Component;

public interface InteractionHandler {

    public static final String YES = "Y";
    public static final String NO = "N";

    public abstract String readStringValue(String aInputQuery, boolean aRetryOnError, int aMinLength,
            Component aParent);

    public abstract Integer readIntegerValue(String aInputQuery, boolean aRetryOnError, Component aParent);
    
    public abstract Long readLongValue(String aInputQuery, boolean aRetryOnError, Component aParent);

    public void confirm(String aInputQuery, boolean aRetryOnError, Component aParent);

    public boolean yesNo(String aInputQuery, boolean aRetryOnError, Component aParent);

    public abstract void showMessage(String aMessage, Component aParent);

    public abstract void showError(String aMessage, Component aParent);
}