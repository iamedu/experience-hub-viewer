package com.iamedu.experience.data;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glVertexAttribPointer;
import static com.iamedu.experience.Constants.BYTES_PER_FLOAT;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class VertexData {

	private final FloatBuffer floatBuffer;

	public VertexData(float[] vertexArray) {
		floatBuffer = ByteBuffer
				.allocateDirect(vertexArray.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer()
				.put(vertexArray);
	}

	public void setVertexAttribPointer(int dataOffset, int attributeLocation,
			int componentCount, int stride) {
		floatBuffer.position(dataOffset);
		glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT,
				false, stride, floatBuffer);
		glEnableVertexAttribArray(attributeLocation);

		floatBuffer.position(0);
	}

}
