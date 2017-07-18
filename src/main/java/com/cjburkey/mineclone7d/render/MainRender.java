package com.cjburkey.mineclone7d.render;

import java.io.IOException;
import org.joml.Matrix4f;
import com.cjburkey.mineclone7d.Mineclone7D;
import com.cjburkey.mineclone7d.Utils;

public final class MainRender {
	
	private ShaderProgram shaderBasic;
	private Transform transform;
	private Mesh mesh;
	
	private float[] verts = {
			0.5f, 0.5f, -1.0f,
			-0.5f, 0.5f, -1.0f,
			-0.5f, -0.5f, -1.0f,
			0.5f, -0.5f, -1.0f
	};
	
	private int[] tris = {
			0, 1, 2,
			0, 2, 3
	};
	
	public void init(Mineclone7D instance) throws IOException {
		shaderBasic = new ShaderProgram();
		shaderBasic.createVertexShader(Utils.readFileAsString("/assets/mc7d/shader/basic/basicShader.vs"));
		shaderBasic.createFragmentShader(Utils.readFileAsString("/assets/mc7d/shader/basic/basicShader.fs"));
		shaderBasic.link();
		shaderBasic.createUniform("projectionMatrix");
		
		transform = new Transform();
		
		mesh = new Mesh(verts, tris);
	}
	
	public void render(Mineclone7D instance) {
		shaderBasic.bind();
		
		Matrix4f projectionMatrix = transform.getProjectionMatrix(instance.getWindow());
		shaderBasic.setUniform("projectionMatrix", projectionMatrix);
		
		mesh.render();
		
		ShaderProgram.unbind();
	}
	
	public void cleanup(Mineclone7D instance) {
		mesh.cleanup();
		shaderBasic.cleanup();
	}
	
}