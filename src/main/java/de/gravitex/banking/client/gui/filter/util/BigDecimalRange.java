package de.gravitex.banking.client.gui.filter.util;

import java.math.BigDecimal;

public class BigDecimalRange {

	private BigDecimal fromValue;
	
	private BigDecimal toValue;

	public BigDecimalRange(BigDecimal aFromValue, BigDecimal aToValue) {
		super();
		this.fromValue = aFromValue;
		this.toValue = aToValue;
	}

	public boolean contains(BigDecimal aValue) {
		if (fromValue != null && toValue != null) {
			return (aValue.compareTo(fromValue) >= 0 && aValue.compareTo(toValue) <= 0);	
		}
		if (fromValue != null && toValue == null) {
			return (aValue.compareTo(fromValue) >= 0);	
		}
		if (fromValue == null && toValue != null) {
			return (aValue.compareTo(toValue) <= 0);	
		}
		// both ends open...
		return true;		
	}
}