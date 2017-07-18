package com.cjburkey.mineclone7d;

public class PerSecondCalculator {
	
	private int ps;
	private long lastCall = System.nanoTime();
	private Call onCall;
	
	public PerSecondCalculator(Call onCall) {
		this.onCall = onCall;
	}
	
	public void update(double delta) {
		ps ++;
		long now = System.nanoTime();
		if(now - lastCall >= Static.NANOS_PER_SECOND) {
			onCall.call(delta);
			ps = 0;
			lastCall = now;
		}
	}
	
	public int getValue() {
		return ps;
	}
	
	@FunctionalInterface
	public static interface Call {
		
		void call(double delta);
		
	}
	
}