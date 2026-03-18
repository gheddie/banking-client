package de.gravitex.banking.client.accessor.response.util;

public class StreamTraffic {

	private String payload;
	
	public StreamTraffic(String aPayload) {
		super();
		this.payload = aPayload;
	}

	@Override
	public String toString() {
		return payload.getBytes().length + " kb";
	}
	
	public String getPayload() {
		return payload;
	}
}