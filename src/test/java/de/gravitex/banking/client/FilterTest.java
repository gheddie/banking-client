package de.gravitex.banking.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import de.gravitex.banking.client.gui.filter.util.BigDecimalRange;
import de.gravitex.banking.client.gui.filter.util.StringLikeMatcher;

public class FilterTest {

	@Test
	public void testBigDecimalRangeBothEndsDefined() {

		// true
		assertTrue(new BigDecimalRange(getBigDecimal("0"), getBigDecimal("100.0")).contains(getBigDecimal("50")));		

		// false
		assertFalse(new BigDecimalRange(getBigDecimal("0"), getBigDecimal("100")).contains(getBigDecimal("120")));
	}
	
	@Test
	public void testBigDecimalRangeFromDefined() {

		// true
		assertTrue(new BigDecimalRange(getBigDecimal("0"), null).contains(getBigDecimal("50")));
		
		// false
		assertFalse(new BigDecimalRange(getBigDecimal("0"), null).contains(getBigDecimal("-120.07")));
	}
	
	@Test
	public void testBigDecimalRangeToDefined() {
		
		// true
		assertTrue(new BigDecimalRange(null, getBigDecimal("-123.4")).contains(getBigDecimal("-200")));
		
		// false
		assertFalse(new BigDecimalRange(null, getBigDecimal("-123.4")).contains(getBigDecimal("833")));
	}
	
	@Test
	public void testBigDecimalRangeNoEndDefined() {
		
		BigDecimalRange openRange = new BigDecimalRange(null, null);
		
		assertTrue(openRange.contains(getBigDecimal("50")));
		assertTrue(openRange.contains(getBigDecimal("-12.7")));
		assertTrue(openRange.contains(getBigDecimal("-3383")));
		assertTrue(openRange.contains(null));
	}
	
	@Test
	public void testStringLikeMatcher() {
		
		StringLikeMatcher matcher = new StringLikeMatcher("Wurst");

		// true
		assertTrue(matcher.matches("urs"));
		
		// false
		assertTrue(matcher.matches("ars"));
	}

	private BigDecimal getBigDecimal(String aValue) {
		return new BigDecimal(aValue);
	}
}