package com.cjburkey.mineclone7d.window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.joml.Vector2i;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

public final class GLFWWindow {
	
	private long window;
	private int width;
	private int height;
	private String title;
	private boolean vsync;
	private GLCapabilities caps;
	
	public GLFWWindow(int width, int height, String title, boolean vsync) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	public GLFWWindow(int width, int height, boolean vsync) {
		this(width, height, "", vsync);
	}
	
	public GLFWWindow(int width, int height) {
		this(width, height, true);
	}
	
	public void initWindow() {
		if (!glfwInit()) {
			throw new RuntimeException("GLFW Could not be initialized.");
		}
	
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("GLFW Window could not be initialized.");
		}
		
		glfwSetWindowSizeCallback(window, (id, width, height) -> onWindowSizeChanged(width, height));
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval((vsync) ? 1 : 0);
		glfwFocusWindow(window);
		
		caps = GL.createCapabilities();
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		sizeWindowToPartialMonitor(8, 9);
		centerOnScreen();
		
		glfwShowWindow(window);
	}
	
	private void onWindowSizeChanged(int newWidth, int newHeight) {
		this.width = newWidth;
		this.height = newHeight;
		glViewport(0, 0, width, height);
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT | GL_ACCUM_BUFFER_BIT);
	}
	
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}
	
	public void pollEvents() {
		glfwPollEvents();
	}
	
	public void destroyWindow() {
		glfwHideWindow(window);
		Callbacks.glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwSetErrorCallback(null).free();
		glfwTerminate();
	}
	
	public void setSize(int width, int height) {
		glfwSetWindowSize(window, width, height);
	}
	
	public boolean hasCapabilities() {
		return caps != null;
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public long getId() {
		return window;
	}
	
	public void centerOnScreen() {
		Vector2i monitor = getMonitorSize(glfwGetPrimaryMonitor());
		int x = (monitor.x - width) / 2;
		int y = (monitor.y - height) / 2;
		glfwSetWindowPos(window, x, y);
	}
	
	public void sizeWindowToPartialMonitor(int numerator, int denominator) {
		Vector2i monitor = getMonitorSize(glfwGetPrimaryMonitor());
		setSize(monitor.x * numerator / denominator, monitor.y * numerator / denominator);
	}
	
	private Vector2i getMonitorSize(long monitor) {
		GLFWVidMode vidmode = glfwGetVideoMode(window);
		return new Vector2i(vidmode.width(), vidmode.height());
	}
	
	public Vector2i getWindowSize() {
		return new Vector2i(width, height);
	}
	
}