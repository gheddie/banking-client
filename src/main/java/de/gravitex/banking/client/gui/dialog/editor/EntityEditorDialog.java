package de.gravitex.banking.client.gui.dialog.editor;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.action.util.ActionProvider;
import de.gravitex.banking_core.entity.base.IdEntity;

public class EntityEditorDialog extends JDialog {

	private static final long serialVersionUID = -5975553157168276421L;
	
	private static final int OFFSET = 25;

	private IdEntity entity;

	private JButton save;

	private ActionProvider actionProvider;
	
	public EntityEditorDialog(IdEntity aEntity, ActionProvider actionProvider) {
		super(actionProvider.getWindow());
		this.entity = aEntity;
		this.actionProvider = actionProvider;
		setModal(true);
		setSize(900, 600);
		setLayout(new BorderLayout());
		save = new JButton("Speichern");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveEntity();
			}
		});
		add(save, BorderLayout.CENTER);
		if (actionProvider != null) {
			setLocation(actionProvider.getWindow().getX() + OFFSET, actionProvider.getWindow().getY() + OFFSET);
		}
	}
	
	private void saveEntity() {
		Object tmp = actionProvider.getInvoker();
		// TODO
		if (tmp instanceof EntityTablePanelListener) {
			EntityTablePanelListener invoker = (EntityTablePanelListener) tmp;
			System.out.println("saveEntity --> " + entity + " of class {" + entity.getClass().getSimpleName()
					+ "} for invoker [" + invoker + "]");
			invoker.acceptEditedEntity(entity);
			dispose();
		}
	}
}