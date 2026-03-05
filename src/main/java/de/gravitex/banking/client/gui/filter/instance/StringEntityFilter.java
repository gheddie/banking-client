package de.gravitex.banking.client.gui.filter.instance;

import java.awt.BorderLayout;
import java.lang.reflect.Field;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import de.gravitex.banking.client.gui.filter.instance.base.EntityFilter;
import de.gravitex.banking.client.gui.filter.util.StringLikeMatcher;
import de.gravitex.banking_core.util.StringHelper;

public class StringEntityFilter extends EntityFilter implements CaretListener {

	private JTextField textField;
	
	private String filterText;

	public StringEntityFilter(Field aField) {
		super(aField);
	}
	
	@Override
	public JPanel makeComponent() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		textField = new JTextField();
		panel.add(textField, BorderLayout.CENTER);
		textField.addCaretListener(this);
		return panel;
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		filterText = textField.getText();
	}

	@Override
	public boolean doAcceptValue(Object aValue) {
		
		new StringLikeMatcher(String.valueOf(aValue));
		
		String tmp = String.valueOf(aValue);
		if (tmp == null) {
			return true;
		}
		if (filterText == null) {
			return true;
		}
		return tmp.contains(filterText);
	}
	
	@Override
	protected boolean hasFilterInfo() {
		return !StringHelper.isBlank(filterText);
		// return false;
	}

	@Override
	public String formatFilterInfo() {
		return filterText;
	}
}