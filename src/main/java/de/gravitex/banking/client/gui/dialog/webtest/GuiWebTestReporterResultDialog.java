package de.gravitex.banking.client.gui.dialog.webtest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.reporterstub.util.HttpResultWrapper;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking.entity.base.NoIdEntity;

public class GuiWebTestReporterResultDialog extends JDialog implements EntityTablePanelListener, ActionListener {
	
	private static final long serialVersionUID = 7576595080732837229L;
	
	private List<HttpResultWrapper> httpResultWrappers;

	private EntityTablePanel resultWrapperTable;

	private HttpResultWrapper selectedWrapper;

	private JButton ok;

	public GuiWebTestReporterResultDialog(List<HttpResultWrapper> aHttpResultWrappers, ManualWebTester aManualWebTester) {
		super();
		this.httpResultWrappers = aHttpResultWrappers;
		setModal(true);
		setTitle(makeTitle(aManualWebTester));
		setSize(900, 600);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		resultWrapperTable = new EntityTablePanel("Resultate", this, false, HttpResultWrapper.class);
		add(resultWrapperTable, BorderLayout.CENTER);
		resultWrapperTable.displayEntities(httpResultWrappers);
		ok = new JButton("OK");
		ok.addActionListener(this);
		add(ok, BorderLayout.SOUTH);
	}

	private String makeTitle(ManualWebTester aManualWebTester) {
		return "Tests für Instanz " + aManualWebTester.getClass().getSimpleName();
	}

	@Override
	public void onEntitySelected(Object aEntity) {
		this.selectedWrapper = (HttpResultWrapper) aEntity;
	}

	@Override
	public void onEntityDoubleClicked(Object aEntity) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object getSelectedObject() {
		return selectedWrapper;
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