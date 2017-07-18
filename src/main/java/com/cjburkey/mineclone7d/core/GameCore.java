package com.cjburkey.mineclone7d.core;

import com.cjburkey.mineclone7d.Logger;
import com.cjburkey.mineclone7d.Mineclone7D;
import com.cjburkey.mineclone7d.PerSecondCalculator;
import com.cjburkey.mineclone7d.module.IGameModule;
import com.cjburkey.mineclone7d.render.MainRender;

public final class GameCore implements IGameModule {
	
	private PerSecondCalculator psc;
	private final MainRender renderer;
	
	public GameCore() {
		psc = new PerSecondCalculator((d) -> Logger.info("FPS: " + psc.getValue() + ". Delta: " + d));
		renderer = new MainRender();
	}
	
	public void onInit(Mineclone7D instance) {
		try {
			renderer.init(instance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onUpdate(Mineclone7D instance, double delta) {
		psc.update(delta);
	}

	public void onRender(Mineclone7D instance) {
		renderer.render(instance);
	}

	public void onCleanup(Mineclone7D instance) {
		renderer.cleanup(instance);
	}
	
}