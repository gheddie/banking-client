package de.gravitex.banking.client.gui.filter.instance;

import java.awt.Color;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import de.gravitex.banking.client.gui.filter.instance.base.EntityFilter;
import de.gravitex.banking.client.gui.filter.util.BigDecimalRange;

public class BigDecimalRangeEntityFilter extends EntityFilter {

	private JTextField fromField;
	
	private JTextField toField;

	private BigDecimal fromValue;

	private BigDecimal toValue;

	public BigDecimalRangeEntityFilter(Field aField) {
		super(aField);
	}

	@Override
	public boolean doAcceptValue(Object aValue) {
		BigDecimal tmp = new BigDecimal(String.valueOf(aValue));
		boolean accepted = new BigDecimalRange(fromValue, toValue).contains(tmp);
		return accepted;
	}
	
	@Override
	public JPanel makeComponent() {
		JPanel panel = new JPanel();		
		panel.setLayout(new GridLayout(2, 2));
		panel.add(new JLabel("von"));
		fromField = new JTextField();
		fromField.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				testFromValue(((JTextField) e.getSource()).getText());
			}
		});
		panel.add(fromField);
		panel.add(new JLabel("bis"));
		toField = new JTextField();
		toField.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				testToValue(((JTextField) e.getSource()).getText());
			}
		});
		panel.add(toField);
		return panel;
	}
	
	private void testFromValue(String aValue) {
		try {
			fromValue = new BigDecimal(aValue);
			fromField.setBackground(Color.WHITE);
		} catch (NumberFormatException e) {
			fromField.setBackground(Color.RED);
		}		
	}
	
	private void testToValue(String aValue) {
		try {
			toValue = new BigDecimal(aValue);
			toField.setBackground(Color.WHITE);
		} catch (NumberFormatException e) {
			toField.setBackground(Color.RED);
		}				
	}

	@Override
	protected boolean hasFilterInfo() {
		return false;
	}

	@Override
	public String formatFilterInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}