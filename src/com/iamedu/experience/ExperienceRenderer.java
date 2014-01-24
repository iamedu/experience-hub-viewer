package com.iamedu.experience;

import static android.opengl.GLES20.*;
import static android.opengl.Matrix.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.iamedu.experience.geometry.ColorQuad;
import com.iamedu.experience.geometry.TextureQuad;
import com.iamedu.experience.program.ColorShaderProgram;
import com.iamedu.experience.program.TextureShaderProgram;
import com.iamedu.experience.util.TextureHelper;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class ExperienceRenderer implements Renderer {
	private Context context;
	
	private final float[] projectionMatrix = new float[16];
	
	private ColorShaderProgram colorProgram;
	private TextureShaderProgram textureProgram;
	
	private ColorQuad colorQuad;
	private TextureQuad textureQuad;
	
	private int texture;

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
				
	}

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		colorProgram = new ColorShaderProgram(context);
		textureProgram = new TextureShaderProgram(context);
		
		colorQuad = new ColorQuad();
		textureQuad = new TextureQuad();
		
		texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface);
	}

	@Override
	public void onDrawFrame(GL10 glUnused) {
		glClear(GL_COLOR_BUFFER_BIT);
		
		
		colorProgram.useProgram();
		colorProgram.setUniforms(projectionMatrix);
		
		colorQuad.bindData(colorProgram);
		colorQuad.draw();
		
		
		textureProgram.useProgram();
		textureProgram.setUniforms(projectionMatrix, texture);
		
		textureQuad.bindData(textureProgram);
		textureQuad.draw();
		
	}


}
