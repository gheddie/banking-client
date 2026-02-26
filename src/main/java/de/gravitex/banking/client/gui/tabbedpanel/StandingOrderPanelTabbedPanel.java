package de.gravitex.banking.client.gui.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.util.List;

import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.StandingOrder;
import de.gravitex.banking_core.entity.base.IdEntity;

public class StandingOrderPanelTabbedPanel extends TabbedPanel implements EntityTablePanelListener {

	private static final long serialVersionUID = 797161186805941583L;
	
	private EntityTablePanel standingOrderTable;

	private StandingOrder selectedOrder;

	@Override
	protected void putListeners() {
		// TODO Auto-generated method stub
	}

	@Override
	protected LayoutManager getPanelLayout() {
		return new BorderLayout();
	}

	@Override
	protected void init() {
		standingOrderTable = new EntityTablePanel("Daueraufträge", this, true);
		add(standingOrderTable, BorderLayout.CENTER);
	}

	@Override
	public void onPanelActivated(Object aContextEntity) {
		List<StandingOrder> standingOrders = ApplicationRegistry.getInstance().getBankingAccessor().readStandingOrders();
		standingOrderTable.displayEntities(standingOrders);
	}

	public void onEntitySelected(Object aEntity) {
		selectedOrder = (StandingOrder) aEntity;		
	}

	public void onEntityDoubeClicked(Object aEntity) {

	}
	
	@Override
	public void reload() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Object getSelectedObject() {
		return selectedOrder;
	}
	
	@Override
	public void acceptEditedEntity(IdEntity aEntity) {
		System.out.println("DO SAVE SO --> " + aEntity);		
	}
}