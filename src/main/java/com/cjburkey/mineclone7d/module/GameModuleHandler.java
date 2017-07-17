package com.cjburkey.mineclone7d.module;

import java.util.ArrayList;
import java.util.List;
import com.cjburkey.mineclone7d.Mineclone7D;

public final class GameModuleHandler {
	
	private final List<IGameModule> modules = new ArrayList<>();
	
	public void addModule(IGameModule module) {
		modules.add(module);
	}
	
	public void initModules(Mineclone7D instance) {
		foreach((e) -> e.onInit(instance));
	}
	
	public void updateModules(Mineclone7D instance, double delta) {
		foreach((e) -> e.onUpdate(instance, delta));
	}
	
	public void renderModules(Mineclone7D instance) {
		foreach((e) -> e.onRender(instance));
	}
	
	public void cleanupModules(Mineclone7D instance) {
		foreach((e) -> e.onCleanup(instance));
	}
	
	private void foreach(ForEachable toDo) {
		for(int i = 0; i < modules.size(); i ++) {
			if(i < modules.size()) {
				toDo.call(modules.get(i));
			}
		}
	}
	
	@FunctionalInterface
	private static interface ForEachable {
		
		void call(IGameModule module);
		
	}
	
}