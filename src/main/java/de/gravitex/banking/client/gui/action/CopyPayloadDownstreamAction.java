package de.gravitex.banking.client.gui.action;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.exception.ActionException;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.tester.reporterstub.util.HttpResultWrapper;

public class CopyPayloadDownstreamAction extends TableContextAction<HttpResultWrapper> {

	public CopyPayloadDownstreamAction(ActionProvider tActionProvider) {
		super("Payload kopieren (Down-Stream)", tActionProvider);
	}

	@Override
	protected void checkContextObject(Object aContextObject) throws ActionException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void executeAction(HttpResultWrapper aHttpResultWrapper) {
		System.out.println("down [" + aHttpResultWrapper.getHttpResult().getRequestUrl() + "] --> "
				+ aHttpResultWrapper.getDownstreamBytes().getPayload());
	}
}