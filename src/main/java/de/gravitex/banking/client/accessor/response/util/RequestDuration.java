package de.gravitex.banking.client.accessor.response.util;

public class RequestDuration {

	private long startMillis;
	
	private long duration;
	
	public RequestDuration() {
		super();
		this.startMillis = System.currentTimeMillis();
	}

	public RequestDuration finish() {
		this.duration = System.currentTimeMillis() - startMillis;
		return this;
	}
	
	@Override
	public String toString() {
		return duration + " ms";
	}
}