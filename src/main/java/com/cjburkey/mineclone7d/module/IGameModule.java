package com.cjburkey.mineclone7d.module;

import com.cjburkey.mineclone7d.Mineclone7D;

public interface IGameModule {
	
	void onInit(Mineclone7D instance);
	void onUpdate(Mineclone7D instance, double delta);
	void onRender(Mineclone7D instance);
	void onCleanup(Mineclone7D instance);
	
}