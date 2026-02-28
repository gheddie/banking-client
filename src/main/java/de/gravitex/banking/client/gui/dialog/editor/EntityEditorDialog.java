package de.gravitex.banking.client.gui.dialog.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking_core.entity.base.IdEntity;

public class EntityEditorDialog extends JDialog {

	private static final long serialVersionUID = -5975553157168276421L;
	
	private static final int OFFSET = 25;

	private IdEntity entity;

	private JButton save;
	
	private JButton abort;
	
	private boolean dirty = false;

	private ActionProvider actionProvider;
	
	public EntityEditorDialog(IdEntity aEntity, ActionProvider actionProvider) {
		super(actionProvider.getWindow());
		this.entity = aEntity;
		this.actionProvider = actionProvider;
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
		return ApplicationRegistry.getInstance().getStringTranslator().translate(entity.getClass().getSimpleName()+ ".Edit.Title");
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
			HttpPatchResult patchResult = invoker.acceptEditedEntity(entity);
			if (patchResult.hasValidStatusCode()) {
				dispose();	
			} else {
				ApplicationRegistry.getInstance().getInteractionHandler().showError(patchResult.getErrorMessage(),
						this);
			}			
		}
	}

	public void markDirty() {
		if (!dirty) {
			dirty = true;
			setTitle(getTitle() + " (*)");	
		}		
	}
}