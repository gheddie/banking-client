package de.gravitex.banking.client.gui.dialog.selectentity;

import java.awt.Window;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.exception.BankingException;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.Account;
import de.gravitex.banking_core.entity.CreditInstitute;
import de.gravitex.banking_core.entity.base.IdEntity;

public class SelectAccountDialog extends BrowseEntitiesDialog<CreditInstitute, Account> {

	private static final long serialVersionUID = 2259759020222389093L;

	public SelectAccountDialog(CreditInstitute aReferenceObject, Window owner) {
		super(aReferenceObject, owner, Account.class);
	}

	@Override
	public void onEntitySelected(Object aEntity) {
		setSelectedEntity((Account) aEntity);
	}

	@Override
	public void onEntityDoubeClicked(Object aEntity) {
		dispose();
	}

	@Override
	public Object getSelectedObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpPatchResult acceptEditedEntity(IdEntity aEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTitleDialog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Account> readSelectableEntities() throws BankingException {
		return ApplicationRegistry.getInstance().getBankingAccessor().readAccounts();
	}
	
	@Override
	public ActionFilter getActionFilter() {
		// TODO Auto-generated method stub
		return null;
	}
}