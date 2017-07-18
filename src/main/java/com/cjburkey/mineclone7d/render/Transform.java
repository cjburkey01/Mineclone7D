package com.cjburkey.mineclone7d.render;

import org.joml.Matrix4f;
import com.cjburkey.mineclone7d.window.GLFWWindow;

public class Transform {
	
	public static final float FOV = (float) Math.toRadians(90);
	public static final float NEAR = 0.01f;
	public static final float FAR = 1000.0f;
	
	private final Matrix4f projectionMatrix = new Matrix4f();
	
	public Matrix4f getProjectionMatrix(GLFWWindow window) {
		projectionMatrix.identity();
		projectionMatrix.perspective(FOV, (float) window.getWindowSize().x / (float) window.getWindowSize().y, NEAR, FAR);
		return projectionMatrix;
	}
	
}