package de.gravitex.banking.client.gui.dialog.editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.crudhandler.CrudHandler;
import de.gravitex.banking.client.exception.CrudException;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.entity.base.IdEntity;

public class EntityCrudDialog extends JDialog {

	private static final long serialVersionUID = -5975553157168276421L;
	
	private static final int OFFSET = 25;

	private static final String EDIT_POSTFIX = ".Edit.Title";
	private static final String CREATE_POSTFIX = ".Create.Title";

	private IdEntity entity;

	private JButton save;
	
	private JButton abort;
	
	private boolean dirty = false;

	private ActionProvider actionProvider;
	
	private CrudHandler crudHandler;

	private boolean editing;
	
	public EntityCrudDialog(IdEntity aEntity, ActionProvider actionProvider, CrudHandler aCrudHandler, boolean aEditing) {
		super(actionProvider.getWindow());
		this.entity = aEntity;
		this.actionProvider = actionProvider;
		this.crudHandler = aCrudHandler;
		this.editing = aEditing;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setTitle(makeTitle());
		setSize(900, 600);
		setLayout(new BorderLayout());
		save = new JButton("Speichern");		
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveEntity();
			}
		});
		abort = new JButton("Abbrechen");
		abort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abortEditing();
			}
		});
		add(getEditorPanel(), BorderLayout.CENTER);
		add(getButtonPanel(), BorderLayout.SOUTH);
		if (actionProvider != null) {
			setLocation(actionProvider.getWindow().getX() + OFFSET, actionProvider.getWindow().getY() + OFFSET);
		}
	}
	
	private JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		buttonPanel.add(save);
		buttonPanel.add(abort);
		return buttonPanel;
	}

	private String makeTitle() {
		if (editing) {
			return ApplicationRegistry.getInstance().getStringTranslator().translate(entity.getClass().getSimpleName()+ EDIT_POSTFIX);	
		} else {
			return ApplicationRegistry.getInstance().getStringTranslator().translate(entity.getClass().getSimpleName()+ CREATE_POSTFIX);
		}
	}

	private EditorPanel getEditorPanel() {
		EditorPanel panel = new EditorPanel(entity, this);
		return panel;
	}
	
	private void abortEditing() {
		if (dirty) {
			if (ApplicationRegistry.getInstance().getInteractionHandler().yesNo("Änderungen verwerfen?", true, null)) {
				dispose();	
			}
		} else {
			dispose();	
		}		
	}

	private void saveEntity() {
		Object tmpInvoker = actionProvider.getInvoker();
		if (tmpInvoker instanceof EntityTablePanelListener) {
			EntityTablePanelListener invoker = (EntityTablePanelListener) tmpInvoker;
			if (editing) {
				tryClose(invoker.acceptEditedEntity(entity));	
			} else {
				tryClose(invoker.acceptCreatedEntity(entity));	
			}			
		}
	}

	private void tryClose(HttpPutResult aHttpPutResult) {
		try {
			crudHandler.evaluatePutResult(aHttpPutResult, actionProvider);
			crudHandler.onSuccessFullyPut(entity);
			dispose();
		} catch (CrudException aCrudException) {
			crudHandler.handleException(aCrudException);
		}		
	}

	private void tryClose(HttpPatchResult aHttpPatchResult) {
		try {
			crudHandler.evaluatePatchResult(aHttpPatchResult, actionProvider);
			crudHandler.onSuccessFullyPatched(entity);
			dispose();
		} catch (CrudException aCrudException) {
			crudHandler.handleException(aCrudException);
		}		
	}

	public void markDirty() {
		if (!dirty) {
			dirty = true;
			setTitle(getTitle() + " (*)");	
		}		
	}
}