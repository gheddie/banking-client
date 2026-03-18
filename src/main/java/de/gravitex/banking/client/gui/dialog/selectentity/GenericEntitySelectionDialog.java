package de.gravitex.banking.client.gui.dialog.selectentity;

import java.awt.BorderLayout;
import java.awt.Window;
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
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking.entity.base.NoIdEntity;

public class GenericEntitySelectionDialog<T> extends JDialog implements EntityTablePanelListener, ActionListener {
	
	private static final long serialVersionUID = 2813451699009359858L;

	private static final int OFFSET = 20;
	
	private boolean singleSelection;

	private EntityTablePanel table;

	private Class<?> entityClass;

	private List<T> entities;

	private Object selectedEntity;

	private JButton ok;

	public GenericEntitySelectionDialog(List<T> aEntities, Window parent,
			boolean aSingleSelection, String aTitle) {
		
		super(parent);
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		this.singleSelection = aSingleSelection;
		this.entities = aEntities;
		this.entityClass = entities.get(0).getClass();
		
		setTitle(aTitle);
		
		setLocation(parent.getX() + OFFSET, parent.getY() + OFFSET);
		setSize(900, 600);
		setModal(true);
		setLayout(new BorderLayout());			
		
		table = new EntityTablePanel("Auswahl", this, aSingleSelection, entityClass);		
		add(table, BorderLayout.CENTER);
		
		ok = new JButton("OK");
		ok.addActionListener(this);
		add(ok, BorderLayout.SOUTH);
		
		table.displayEntities(entities);
	}

	@Override
	public void onEntitySelected(Object aEntity) {
		selectedEntity = aEntity;
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
	
	public Object getSelectedEntity() {
		return selectedEntity;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}
}