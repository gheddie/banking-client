package de.gravitex.banking.client.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import de.gravitex.banking.client.gui.GuiUtil;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.entity.TradingPartner;

public class ShowTradingPartnersHistoryDialog extends JDialog {

	private static final long serialVersionUID = -4116549030974726077L;

	private static final int OFFSET = 20;

	private JButton ok;

	private JTree tradingPartnersHierarchyTree;

	public ShowTradingPartnersHistoryDialog() {

		super();

		setModal(true);
		setSize(900, 600);
		setTitle("Trading-Partner (Historie)");
		Window parentView = ApplicationRegistry.getInstance().getParentView();
		setLocation(parentView.getX() + OFFSET, parentView.getY() + OFFSET);

		setLayout(new BorderLayout());
		ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		});
		tradingPartnersHierarchyTree = new JTree();
		add(GuiUtil.nestComponent(tradingPartnersHierarchyTree, "Hierarchie"), BorderLayout.CENTER);
		add(ok, BorderLayout.SOUTH);

		fillData();
	}

	private void closeDialog() {
		dispose();
	}

	private void fillData() {
		tradingPartnersHierarchyTree.setModel(buildTreeModel());
		buildTreeModel();
	}

	@SuppressWarnings("unchecked")
	private TreeModel buildTreeModel() {
		List<TradingPartner> tradingPartners = (List<TradingPartner>) ApplicationRegistry.getInstance().getBankingAccessor().readTradingPartners().getEntityList();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		for (TradingPartner aTradingPartner : tradingPartners) {
			if (aTradingPartner.getParentTradingPartner() == null) {
				root.add(new DefaultMutableTreeNode(aTradingPartner));
			}
		}
		return new DefaultTreeModel(root);
	}
}