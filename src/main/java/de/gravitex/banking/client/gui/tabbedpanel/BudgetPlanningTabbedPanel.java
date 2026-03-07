package de.gravitex.banking.client.gui.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.util.List;

import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.BudgetPlanning;

public class BudgetPlanningTabbedPanel  extends TabbedPanel {

	private static final long serialVersionUID = 5309769179995700875L;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPanelActivated(Object aContextEntity) {
		fillData();
	}

	private void fillData() {
		List<BudgetPlanning> budgetPlannings = ApplicationRegistry.getInstance().getBankingAccessor()
				.readBudgetPlannings(null);
		int werner = 5;
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
	}
}