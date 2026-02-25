package de.gravitex.banking.client.gui.dialog.selectentity;

import java.awt.BorderLayout;
import java.awt.Window;
import java.util.List;

import de.gravitex.banking.client.exception.BankingException;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.dialog.BankingDialog;
import de.gravitex.banking_core.entity.base.NoIdEntity;

public abstract class BrowseEntitiesDialog<T extends NoIdEntity, E extends NoIdEntity> extends BankingDialog implements EntityTablePanelListener {

	private static final long serialVersionUID = 9002122497080582561L;
	
	private EntityTablePanel entityTable;

	private T referenceObject;

	private E selectedEntity;
	
	public BrowseEntitiesDialog(T aReferenceObject, Window owner) {
		super(owner);
		this.referenceObject = aReferenceObject;
		// setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setSize(900, 600);
		setLayout(new BorderLayout());
		entityTable = new EntityTablePanel(getTitleDialog(), this, true);
		add(entityTable, BorderLayout.CENTER);
		fillEntities();
		setVisible(true);
	}

	protected abstract String getTitleDialog();

	private void fillEntities() {
		try {
			List<E> entties = readSelectableEntities();
			entityTable.displayEntities(entties);
		} catch (BankingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected abstract List<E> readSelectableEntities() throws BankingException;

	public E getSelectedEntity() {
		return selectedEntity;
	}
	
	public T getReferenceObject() {
		return referenceObject;
	}
	
	protected void setSelectedEntity(E selectedEntity) {
		this.selectedEntity = selectedEntity;
	}
}