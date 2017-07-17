package com.cjburkey.mineclone7d;

import com.cjburkey.mineclone7d.module.GameModuleHandler;

public final class GameLoopHandler {
	
	private static final long NANOS_PER_SECOND = 1000000000L;
	private static final int TARGET_FPS = 60;
	private static final long OPTIMAL_TIME = NANOS_PER_SECOND / TARGET_FPS;
	
	private boolean running = false;
	private long lastLoopTime = System.nanoTime();
	
	public void start(Mineclone7D instance, GameModuleHandler handler) {
		running = true;
		
		handler.initModules(instance);
		while (running) {
			if (instance.getWindow().shouldClose()) {
				stop();
				continue;
			}
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double) OPTIMAL_TIME);
			instance.getWindow().clear();
			handler.updateModules(instance, delta);
			handler.renderModules(instance);
			instance.getWindow().pollEvents();
			instance.getWindow().swapBuffers();
			long timeout = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / NANOS_PER_SECOND;
			if (timeout > 0) {
				try {
					Thread.sleep(timeout);
				} catch(Exception e) {  }
			}
		}
		handler.cleanupModules(instance);
	}
	
	public void stop() {
		running = false;
	}
	
}