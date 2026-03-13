package de.gravitex.banking.client.accessor.response.util;

public class StreamTraffic {

	private int bytes;
	
	public StreamTraffic(int aBytes) {
		super();
		this.bytes = aBytes;
	}

	public StreamTraffic(String aPayload) {
		this(aPayload.getBytes().length);
	}

	public StreamTraffic() {
		this(0);
	}
	
	@Override
	public String toString() {
		return bytes + " bytes";
	}
}