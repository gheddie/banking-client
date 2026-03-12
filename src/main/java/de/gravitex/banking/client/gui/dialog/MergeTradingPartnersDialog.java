package de.gravitex.banking.client.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPostResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking.entity.base.NoIdEntity;
import de.gravitex.banking_core.dto.MergeTradingPartners;
import de.gravitex.banking_core.dto.TradingPartnersMergeResult;

public class MergeTradingPartnersDialog extends JDialog implements EntityTablePanelListener {

	private static final long serialVersionUID = -4116549030974726077L;
	
	private static final int OFFSET = 20;

	private EntityTablePanel tradingPartnerTable;

	private JButton ok;

	private JTextField tfTradingKey;
	
	public MergeTradingPartnersDialog() {
		
		super();
		
		setModal(true);
		setSize(900, 600);		
		setTitle("Trading-Partner zusammenführen");
		Window parentView = ApplicationRegistry.getInstance().getParentView();
		setLocation(parentView.getX() + OFFSET, parentView.getY() + OFFSET);
		
		setLayout(new BorderLayout());
		tradingPartnerTable = new EntityTablePanel("Vorhandene Partner", this, false, TradingPartner.class);
		add(tradingPartnerTable, BorderLayout.CENTER);
		ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		});
		add(ok, BorderLayout.SOUTH);
		
		add(getNewTradingKeyPanel(), BorderLayout.NORTH);
		
		fillData();
	}
	
	private Component getNewTradingKeyPanel() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("Trading-Key"), BorderLayout.WEST);
		tfTradingKey = new JTextField();
		panel.add(tfTradingKey, BorderLayout.CENTER);
		return panel;
	}

	private void closeDialog() {
		
		MergeTradingPartners merge = new MergeTradingPartners();
		merge.setNewTradingKey(tfTradingKey.getText());
		List<TradingPartner> partnersToMerge = new ArrayList<>();
		for (Object tmp : tradingPartnerTable.getSelectedObjects()) {
			partnersToMerge.add((TradingPartner) tmp);
		}
		merge.setPartnersToMerge(partnersToMerge);
		HttpPostResult result = ApplicationRegistry.getInstance().getBankingAccessor().mergeTradingPartners(merge);
		if (result.hasValidStatusCode()) {
			TradingPartnersMergeResult mergeResult = (TradingPartnersMergeResult) result.getResponseObject();
			ApplicationRegistry.getInstance().getInteractionHandler()
					.showMessage("Zusammenfühtung erfolgreich (" + mergeResult.summarize() + ")", this);
			dispose();
		} else {
			ApplicationRegistry.getInstance().getInteractionHandler().showError(result.getErrorMessage(), this);
			dispose();
		}
	}

	private void fillData() {
		tradingPartnerTable.displayEntities(
				ApplicationRegistry.getInstance().getBankingAccessor().readTradingPartners().getEntityList());
	}

	@Override
	public void onEntitySelected(Object aEntity) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onEntityDoubeClicked(Object aEntity) {
		// TODO Auto-generated method stub
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
	public HttpPutResult acceptCreatedEntity(IdEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFilter getActionFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends NoIdEntity> reloadEntities(Class<?> aEntityClass) {
		// TODO Auto-generated method stub
		return null;
	}
}