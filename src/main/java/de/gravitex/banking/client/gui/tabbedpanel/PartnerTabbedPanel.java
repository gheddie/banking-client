package de.gravitex.banking.client.gui.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.dialog.selectentity.ListBookingsByTradingsPartnerDialog;
import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.TradingPartner;
import de.gravitex.banking_core.entity.base.IdEntity;

public class PartnerTabbedPanel extends TabbedPanel implements EntityTablePanelListener {

	private static final long serialVersionUID = 8715991386775560682L;

	private EntityTablePanel partnerTable;

	private TradingPartner selectedTradingPartner;

	@Override
	public void onPanelActivated(Object aContextEntity) {
		fillData();
	}

	private void fillData() {
		List<TradingPartner> tradingPartners = ApplicationRegistry.getInstance().getBankingAccessor()
				.readTradingPartners();
		System.out.println("read " + tradingPartners.size() + " trading partners...");
		partnerTable.displayEntities(tradingPartners);
	}

	@Override
	protected void init() {
		partnerTable = new EntityTablePanel("Partner", this, true);
		add(partnerTable, BorderLayout.CENTER);
	}

	public void onEntitySelected(Object aEntity) {
		selectedTradingPartner = (TradingPartner) aEntity;
		System.out.println("selectedTradingPartner --> " + selectedTradingPartner);
	}

	@Override
	protected LayoutManager getPanelLayout() {
		return new BorderLayout();
	}

	@Override
	protected void putListeners() {

	}

	public void onEntityDoubeClicked(Object aEntity) {
		new ListBookingsByTradingsPartnerDialog(selectedTradingPartner,
				ApplicationRegistry.getInstance().getParentView());
	}

	@Override
	public void reload() {
		fillData();
	}

	@Override
	public Object getSelectedObject() {
		return selectedTradingPartner;
	}

	@Override
	public HttpPatchResult acceptEditedEntity(IdEntity aEntity) {
		HttpPatchResult patchResult = ApplicationRegistry.getInstance().getBankingAccessor().saveTradingPartner((TradingPartner) aEntity);
		reload();
		return patchResult;
	}
}