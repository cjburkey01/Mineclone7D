package com.cjburkey.mineclone7d.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {
	
	private final int vaoId;
	private final int vboId;
	private final int triVboId;
	private final int vertexCount;
	
	public Mesh(float[] verts, int[] tris) {
		vertexCount = tris.length;
		FloatBuffer vertexBuffer = null;
		IntBuffer triangleBuffer = null;
		try {
			vertexBuffer = memAllocFloat(verts.length);
			triangleBuffer = memAllocInt(tris.length);

			vertexBuffer.put(verts).flip();
			triangleBuffer.put(tris).flip();
			
			vaoId = glGenVertexArrays();
			glBindVertexArray(vaoId);
			
			vboId = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vboId);
			glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
			
			triVboId = glGenBuffers();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, triVboId);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, triangleBuffer, GL_STATIC_DRAW);
			
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		} finally {
			if (vertexBuffer != null) {
				memFree(vertexBuffer);
			}
			
			if (triangleBuffer != null) {
				memFree(triangleBuffer);
			}
		}
	}
	
	public void render() {
		glBindVertexArray(vaoId);
		glEnableVertexAttribArray(0);
		
		//glDrawArrays(GL_TRIANGLES, 0, vertexCount);
		glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
	
	public void cleanup() {
		glDisableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vboId);
		glDeleteBuffers(triVboId);
		
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoId);
	}
	
}