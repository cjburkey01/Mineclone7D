package com.cjburkey.mineclone7d.render;

import static org.lwjgl.opengl.GL20.*;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;
import com.cjburkey.mineclone7d.Logger;

public class ShaderProgram {
	
	private final Map<String, Integer> uniforms = new HashMap<>();
	private final int programId;
	private int vertexId;
	private int fragmentId;
	
	public ShaderProgram() {
		programId = glCreateProgram();
		if (programId == 0) {
			throw new RuntimeException("Shader program could not be created.");
		}
	}
	
	public void createVertexShader(String code) {
		vertexId = createShader(GL_VERTEX_SHADER, code);
	}
	
	public void createFragmentShader(String code) {
		fragmentId = createShader(GL_FRAGMENT_SHADER, code);
	}
	
	public int createShader(int type, String code) {
		int shaderId = glCreateShader(type);
		if (shaderId == 0) {
			throw new RuntimeException("Shader could not be created.");
		}
		
		glShaderSource(shaderId, code);
		glCompileShader(shaderId);
		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
			throw new RuntimeException("Could not compile shader: " + glGetShaderInfoLog(shaderId));
		}
		
		glAttachShader(programId, shaderId);
		
		return shaderId;
	}
	
	public void link() {
		glLinkProgram(programId);
		if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
			throw new RuntimeException("Shader program could not be linked: " + glGetProgramInfoLog(programId));
		}
		
		if (vertexId != 0) {
			glDetachShader(programId, vertexId);
		}

		if (fragmentId != 0) {
			glDetachShader(programId, fragmentId);
		}
		
		glValidateProgram(programId);
		if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
			Logger.error("Shader validation failed: " + glGetProgramInfoLog(programId));
		}
	}
	
	public void createUniform(String name) {
		int loc = glGetUniformLocation(programId, name);
		if(loc < 0) {
			throw new RuntimeException("Uniform could not be found: " + name);
		}
		uniforms.put(name, loc);
	}
	
	public void setUniform(String name, int value) {
		glUniform1i(uniforms.get(name), value);
	}
	
	public void setUniform(String name, float value) {
		glUniform1f(uniforms.get(name), value);
	}
	
	public void setUniform(String name, Matrix4f value) {
		FloatBuffer buff = MemoryUtil.memAllocFloat(16);
		value.get(buff);
		glUniformMatrix4fv(uniforms.get(name), false, buff);
		MemoryUtil.memFree(buff);
	}
	
	public void bind() {
		glUseProgram(programId);
	}
	
	public void cleanup() {
		unbind();
		if (programId != 0) {
			glDeleteProgram(programId);
		}
	}
	
	public static void unbind() {
		glUseProgram(0);
	}
	
}