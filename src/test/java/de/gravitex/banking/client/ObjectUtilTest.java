package de.gravitex.banking.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.gravitex.banking.client.util.ObjectUtil;
import de.gravitex.banking.entity.Booking;
import de.gravitex.banking.entity.TradingPartner;

public class ObjectUtilTest {

	@Test
	public void testObjectEquality() {
		
		assertTrue(ObjectUtil.areValuesEqual("", ""));
		assertTrue(ObjectUtil.areValuesEqual("123", "123"));
		assertTrue(ObjectUtil.areValuesEqual(null, null));
		
		assertFalse(ObjectUtil.areValuesEqual("", "123"));
		assertFalse(ObjectUtil.areValuesEqual(null, "123"));
		assertFalse(ObjectUtil.areValuesEqual("123", null));
		assertFalse(ObjectUtil.areValuesEqual("12", "123"));
		
		Booking booking = new Booking();
		TradingPartner tradingPartner = new TradingPartner();
		
		assertFalse(ObjectUtil.areValuesEqual(booking, tradingPartner));
		assertTrue(ObjectUtil.areValuesEqual(booking, booking));
		assertTrue(ObjectUtil.areValuesEqual(tradingPartner, tradingPartner));
		
		assertFalse(ObjectUtil.areValuesEqual(booking, null));
		assertFalse(ObjectUtil.areValuesEqual(tradingPartner, null));
	}
	
	@Test
	public void testObjectEqualityWithClasses() {
		
		assertTrue(ObjectUtil.areValuesEqual(String.class, String.class));
		assertFalse(ObjectUtil.areValuesEqual(String.class, Long.class));
	}
}