package com.iamedu.experience.geometry;

import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glDrawArrays;
import static com.iamedu.experience.Constants.BYTES_PER_FLOAT;

import com.iamedu.experience.data.VertexData;
import com.iamedu.experience.program.TextureShaderProgram;

public class TextureQuad {
	private static final int POSITION_COMPONENT_COUNT = 2;
	private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;

	private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT)
			* BYTES_PER_FLOAT;

	private static final float[] VERTEX_DATA = {
			// Order of coordinates X, Y, S, T
			-0.5f, -0.5f, 0.0f, 1.0f,
			-0.5f,  0.5f, 0.0f, 0.0f,
			0.5f,  -0.5f,  1.0f, 1.0f,
			0.5f,  0.5f,  1.0f, 0.0f};

	private VertexData vertexData;

	public TextureQuad() {
		vertexData = new VertexData(VERTEX_DATA);
	}

	public void bindData(TextureShaderProgram shaderProgram) {
		
		vertexData.setVertexAttribPointer(
				0,
				shaderProgram.getPositionAttributeLocation(),
				POSITION_COMPONENT_COUNT,
				STRIDE);
		
		vertexData.setVertexAttribPointer(
				POSITION_COMPONENT_COUNT,
				shaderProgram.getTextureCoordinatesAttributeLocation(),
				TEXTURE_COORDINATES_COMPONENT_COUNT,
				STRIDE);
		
	}

	public void draw() {
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
	}
}
