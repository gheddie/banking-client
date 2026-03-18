package de.gravitex.banking.client.gui.action;

import de.gravitex.banking.client.gui.action.base.TableContextAction;
import de.gravitex.banking.client.gui.action.exception.ActionException;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.tester.reporterstub.util.HttpResultWrapper;

public class CopyPayloadUpstreamAction extends TableContextAction<HttpResultWrapper> {

	public CopyPayloadUpstreamAction(ActionProvider tActionProvider) {
		super("Payload kopieren (Up-Stream)", tActionProvider);
	}

	@Override
	protected void checkContextObject(Object aContextObject) throws ActionException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void executeAction(HttpResultWrapper aHttpResultWrapper) {
		System.out.println("up [" + aHttpResultWrapper.getHttpResult().getRequestUrl() + "] --> "
				+ aHttpResultWrapper.getUpstreamBytes().getPayload());
	}
}