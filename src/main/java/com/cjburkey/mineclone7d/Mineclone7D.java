package com.cjburkey.mineclone7d;

import com.cjburkey.mineclone7d.core.GameCore;
import com.cjburkey.mineclone7d.module.GameModuleHandler;
import com.cjburkey.mineclone7d.window.GLFWWindow;

public final class Mineclone7D {
	
	private static Mineclone7D instance;
	
	private boolean started = false;
	
	private GameModuleHandler moduleHandler;
	private GameLoopHandler gameLoop;
	private GLFWWindow window;
	
	public static void main(String[] args) {
		instance = new Mineclone7D();
		instance.start();
	}
	
	private Mineclone7D() {
	}
	
	private void start() {
		if (!started) {
			started = true;
			
			moduleHandler = new GameModuleHandler();
			gameLoop = new GameLoopHandler();
			window = new GLFWWindow(300, 300, "Mineclone7D", true);
			
			moduleHandler.addModule(new GameCore());
			window.initWindow();
			gameLoop.start(this, moduleHandler);
		}
	}
	
	public GLFWWindow getWindow() {
		return window;
	}
	
}