package de.gravitex.banking.client.gui.tabbedpanel;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.dialog.selectentity.ListBookingsByTradingsPartnerDialog;
import de.gravitex.banking.client.gui.tabbedpanel.base.TabbedPanel;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.PurposeCategory;
import de.gravitex.banking_core.entity.TradingPartner;

public class PartnerTabbedPanel extends TabbedPanel implements EntityTablePanelListener, ActionListener {
	
	private static final long serialVersionUID = 8715991386775560682L;
	
	private EntityTablePanel partnerTable;

	private List<PurposeCategory> purposeCategorys;

	private JComboBox<PurposeCategory> purposeCategoryChooser;

	private PurposeCategory selectedPurposeCategory;

	private TradingPartner selectedTradingPartner;

	private JButton updateCategory;
	
	@Override
	public void onPanelActivated(Object aContextEntity) {		
		fillData();
	}

	private void fillData() {
		purposeCategorys = ApplicationRegistry.getInstance().getBankingAccessor().readPurposeCategorys();			
		fillPurposeCategorys();
		List<TradingPartner> tradingPartners = ApplicationRegistry.getInstance().getBankingAccessor().readTradingPartners();
		System.out.println("read "+tradingPartners.size()+" trading partners...");
		partnerTable.displayEntities(tradingPartners);
	}

	private void fillPurposeCategorys() {
		DefaultComboBoxModel<PurposeCategory> model = new DefaultComboBoxModel<PurposeCategory>();
		for (PurposeCategory aPurposeCategory : purposeCategorys) {
			model.addElement(aPurposeCategory);
		}
		purposeCategoryChooser.setModel(model);
	}

	@Override
	protected void init() {
		
		partnerTable = new EntityTablePanel("Partner", this, true);
		add(partnerTable, BorderLayout.CENTER);
		
		purposeCategoryChooser = new JComboBox<PurposeCategory>();
		add(purposeCategoryChooser, BorderLayout.NORTH);
		
		updateCategory = new JButton("Ändern");
		add(updateCategory, BorderLayout.SOUTH);
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
		purposeCategoryChooser.addActionListener(this);
		updateCategory.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				if (selectedPurposeCategory != null && selectedTradingPartner != null) {
					updateTradingPartner();
				}				
			}
		});
	}
	
	private void updateTradingPartner() {
		String message = "Partner "+selectedTradingPartner+" zu Kategorie "+selectedPurposeCategory+" ändern?";
		if (ApplicationRegistry.getInstance().getInteractionHandler().yesNo(message, true, ApplicationRegistry.getInstance()
				.getParentView())) {
			selectedTradingPartner.setPurposeCategory(selectedPurposeCategory);
			ApplicationRegistry.getInstance().getBankingAccessor().updateTradingPartner(selectedTradingPartner.getId(),
					selectedPurposeCategory.getId());	
			reload();
		}
	}

	public void actionPerformed(ActionEvent e) {
		selectedPurposeCategory = (PurposeCategory) purposeCategoryChooser.getSelectedItem();
		System.out.println(selectedPurposeCategory);
	}
	
	public void onEntityDoubeClicked(Object aEntity) {
		new ListBookingsByTradingsPartnerDialog(selectedTradingPartner,
				ApplicationRegistry.getInstance().getParentView());
	}
	
	@Override
	public void reload() {
		fillData();
	}
}