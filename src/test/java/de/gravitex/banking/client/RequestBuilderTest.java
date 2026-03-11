package de.gravitex.banking.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.gravitex.banking.client.accessor.HttpRequestBuilder;
import de.gravitex.banking.entity.Booking;

public class RequestBuilderTest {

	@Test
	public void testRequestBuilder() {
		
		HttpRequestBuilder builder = HttpRequestBuilder.forEntityList(Booking.class);
		
		assertEquals("http://localhost:4711/bookings",
				builder.buildRequestUrl());
		
		assertEquals("http://localhost:4711/bookings/account",
				builder.byAttribute("account").buildRequestUrl());
		
		assertEquals("http://localhost:4711/bookings/account?id=12",
				builder.identified(Long.valueOf(12)).buildRequestUrl());
	}
}