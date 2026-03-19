package de.gravitex.banking.client.gui.dialog.webtest;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.reporterstub.util.HttpResultWrapper;
import de.gravitex.banking.client.tester.util.ManualWebTestDefinition;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking.entity.base.NoIdEntity;

public class ManualWebTestListWrapperDialog extends JDialog implements EntityTablePanelListener, ActionListener {

	private static final long serialVersionUID = 2440545099409907985L;
	
	private List<ManualWebTestDefinition> testDefinitions;

	private JTabbedPane testPane;

	private JButton ok;
	
	public ManualWebTestListWrapperDialog(List<ManualWebTestDefinition> aTestDefinitions, Frame aParent) {
		
		super(aParent);
		this.testDefinitions = aTestDefinitions;
		
		setSize(900, 600);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		testPane = new JTabbedPane();
		add(testPane, BorderLayout.CENTER);
		ok = new JButton("OK");
		ok.addActionListener(this);
		add(ok, BorderLayout.SOUTH);
		
		setTitle(buildTitle());
	}

	private String buildTitle() {
		
		List<ManualWebTestDefinition> activeTestDefinitions = new ArrayList<>();
		List<ManualWebTestDefinition> inactiveTestDefinitions = new ArrayList<>();
		
		for (ManualWebTestDefinition aTestDefinition : testDefinitions) {
			if (aTestDefinition.isActive()) {				
				activeTestDefinitions.add(aTestDefinition);
			} else {
				inactiveTestDefinitions.add(aTestDefinition);
			}
		}
		
		String title = activeTestDefinitions.size() + " Tests";
		
		if (!inactiveTestDefinitions.isEmpty()) {
			title += " ("+inactiveTestDefinitions.size()+" inaktiv)";
		}
		
		return title;
	}

	public void publishResult(ManualWebTester aManualWebTester, List<HttpResultWrapper> aResultWrappers) {
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		
		EntityTablePanel resultWrapperTable = new EntityTablePanel("Resultate", this, false, HttpResultWrapper.class);
		panel.add(resultWrapperTable, BorderLayout.CENTER);
		if (aResultWrappers != null) {
			resultWrapperTable.displayEntities(aResultWrappers);	
		}		
		
		testPane.addTab(aManualWebTester.getClass().getSimpleName(), panel);
	}

	@Override
	public void onEntitySelected(Object aEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityDoubleClicked(Object aEntity) {
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

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}
}