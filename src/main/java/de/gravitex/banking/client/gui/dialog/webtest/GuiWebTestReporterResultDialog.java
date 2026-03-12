package de.gravitex.banking.client.gui.dialog.webtest;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JDialog;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.tester.reporterstub.util.HttpResultWrapper;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking.entity.base.NoIdEntity;

public class GuiWebTestReporterResultDialog extends JDialog implements EntityTablePanelListener {
	
	private static final long serialVersionUID = 7576595080732837229L;
	
	private List<HttpResultWrapper> httpResultWrappers;

	private EntityTablePanel resultWrapperTable;

	public GuiWebTestReporterResultDialog(List<HttpResultWrapper> aHttpResultWrappers) {
		super();
		this.httpResultWrappers = aHttpResultWrappers;
		setModal(true);
		setSize(900, 600);
		setLayout(new BorderLayout());
		resultWrapperTable = new EntityTablePanel("Resultate", this, false, HttpResultWrapper.class);
		add(resultWrapperTable, BorderLayout.CENTER);
		resultWrapperTable.displayEntities(httpResultWrappers);
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