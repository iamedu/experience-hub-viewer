package com.iamedu.experience;

import static android.opengl.GLES20.*;
import static android.opengl.Matrix.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.iamedu.experience.geometry.ColorQuad;
import com.iamedu.experience.program.ColorShaderProgram;
import com.iamedu.experience.program.TextureShaderProgram;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class ExperienceRenderer implements Renderer {
	private Context context;
	
	private final float[] projectionMatrix = new float[16];
	private final float[] modelMatrix = new float[16];
	
	private ColorShaderProgram colorProgram;
	private TextureShaderProgram shaderProgram;
	
	private float movement = 0.0f;
	
	private ColorQuad colorQuad;

	public ExperienceRenderer(Context context) {
		this.context = context;
	}
	
	@Override
	public void onSurfaceChanged(GL10 glUnused, int width, int height) {
		glViewport(0, 0, width, height);
		
		final float aspectRatio = width > height ?
				(float) width / (float) height :
			    (float) height / (float) width;
				
		if (width > height) {
			orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
		} else {
			orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
		}
		
		setIdentityM(modelMatrix, 0);
		translateM(modelMatrix, 0, movement, 0.0f, 0.0f);
		
	}

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		colorProgram = new ColorShaderProgram(context);
		shaderProgram = new TextureShaderProgram(context);
		
		colorQuad = new ColorQuad();
	}

	@Override
	public void onDrawFrame(GL10 glUnused) {
		glClear(GL_COLOR_BUFFER_BIT);
		
		
		colorProgram.useProgram();
		colorProgram.setUniforms(projectionMatrix, modelMatrix);
		
		colorQuad.bindData(colorProgram);
		colorQuad.draw();
		
	}


}
