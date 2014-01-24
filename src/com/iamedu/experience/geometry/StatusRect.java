package com.iamedu.experience.geometry;

import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glDrawArrays;
import static com.iamedu.experience.Constants.BYTES_PER_FLOAT;

import com.iamedu.experience.data.VertexData;
import com.iamedu.experience.program.ColorShaderProgram;

public class StatusRect {

	private static final int POSITION_COMPONENT_COUNT = 2;
	private static final int COLOR_COMPONENT_COUNT = 3;

	private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT)
			* BYTES_PER_FLOAT;

	private static final float[] VERTEX_DATA = {
			// Order of coordinates X, Y, R, G, B
			0.0f, -0.12f, 1.0f, 1.0f, 1.0f,
			0.0f,  0.12f, 1.0f, 1.0f, 1.0f,
			3.0f,  -0.6f,  1.0f, 1.0f, 1.0f,
			3.0f,  0.6f,  1.0f, 1.0f, 1.0f,};

	private VertexData vertexData;

	public StatusRect() {
		vertexData = new VertexData(VERTEX_DATA);
	}

	public void bindData(ColorShaderProgram colorProgram) {
		
		vertexData.setVertexAttribPointer(
				0,
				colorProgram.getPositionAttributeLocation(),
				POSITION_COMPONENT_COUNT,
				STRIDE);
		
		vertexData.setVertexAttribPointer(
				POSITION_COMPONENT_COUNT,
				colorProgram.getColorAttributeLocation(),
				COLOR_COMPONENT_COUNT,
				STRIDE);
		
	}

	public void draw() {
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
	}

	
}
