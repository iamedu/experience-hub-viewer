package com.iamedu.experience;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_ALPHA;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;

import com.iamedu.experience.geometry.StatusRect;
import com.iamedu.experience.geometry.TextureQuad;
import com.iamedu.experience.program.ColorShaderProgram;
import com.iamedu.experience.program.TextureShaderProgram;
import com.iamedu.experience.util.TextureHelper;

public class ExperienceRenderer implements Renderer {
	private Context context;
	
	private final float[] projectionMatrix = new float[16];
	private final float[] modelMatrix = new float[16];
	
	private TextureShaderProgram textureProgram;
	private ColorShaderProgram colorProgram;
	
	private StatusRect statusRect;
	private TextureQuad textureQuad;
	private TextureQuad largeTextureQuad;
	
	private float rotationAngle = 0;
	private float moveLeft = -1.7f;
	
	private int texture;
	private int logoTexture;
	
	private int userPicTexture;
	private int usernameTexture;
	private int nameTexture;
	private int lineTexture;

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
		glClearColor(24.0f / 255.0f, 20.0f / 255.0f, 69.0f / 255.0f, 0.0f);
		
		textureProgram = new TextureShaderProgram(context);
		colorProgram = new ColorShaderProgram(context);
		
		textureQuad = new TextureQuad();
		largeTextureQuad = new TextureQuad(true);
		statusRect = new StatusRect();
		
		texture = TextureHelper.loadTexture(context, R.drawable.tuerca);
		logoTexture = TextureHelper.loadTexture(context, R.drawable.logo);
		userPicTexture = TextureHelper.loadTexture(context, R.drawable.userpic);
		
		
		usernameTexture = TextureHelper.createText(context, "@reginadavila34");
		nameTexture = TextureHelper.createText(context, "Regina Dávila");
		lineTexture = TextureHelper.createText(context, "Texto Texto Texto Texto Texto Texto");
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		
	}
	
	public void twitter() {
		
		float[] translate = new float[16];
		float[] rotate = new float[16];
		float[] result = new float[16];
		
		setIdentityM(modelMatrix, 0);
		translateM(modelMatrix, 0, moveLeft, 0, 0);
		scaleM(modelMatrix, 0, 2.0f, 2.0f, 2.0f);
		
		setRotateM(rotate, 0, rotationAngle, 0, 0, 1);
		multiplyMM(result, 0, modelMatrix, 0, rotate, 0);
		
		textureProgram.useProgram();
		textureProgram.setUniforms(projectionMatrix, result, texture);
		textureQuad.bindData(textureProgram);
		textureQuad.draw();
		
		setIdentityM(modelMatrix, 0);
		translateM(modelMatrix, 0, moveLeft, 0, 0);
		scaleM(modelMatrix, 0, 0.7f, 0.7f, 0.7f);
		translateM(modelMatrix, 0, 0.4f, 0, 0);
		textureProgram.setUniforms(projectionMatrix, modelMatrix, logoTexture);
		textureQuad.bindData(textureProgram);
		textureQuad.draw();
		
		float angle = 10.5f;
		
		for(int i = 0; i < 9; i++, angle += 40.0f) {
			setIdentityM(translate, 0);
			translateM(translate, 0, moveLeft, 0, 0);
			
			setRotateM(modelMatrix, 0, rotationAngle, 0.0f, 0.0f, 1.0f);
			translateM(modelMatrix, 0, 0.93f, 0, 0);
			
			setRotateM(rotate, 0, angle, 0, 0, 1);
			multiplyMM(result, 0, rotate, 0, modelMatrix, 0);
			multiplyMM(modelMatrix, 0, translate, 0, result, 0);
			
			colorProgram.useProgram();
			colorProgram.setUniforms(projectionMatrix, modelMatrix);
			statusRect.bindData(colorProgram);
			statusRect.draw();
			
			setRotateM(modelMatrix, 0, rotationAngle, 0.0f, 0.0f, 1.0f);
			translateM(modelMatrix, 0, 1.52f, 0, 0);
			scaleM(modelMatrix, 0, 0.4f, 0.4f, 0.4f);
			
			setRotateM(rotate, 0, angle, 0, 0, 1);
			multiplyMM(result, 0, rotate, 0, modelMatrix, 0);
			multiplyMM(modelMatrix, 0, translate, 0, result, 0);
			
			textureProgram.useProgram();
			textureProgram.setUniforms(projectionMatrix, modelMatrix, nameTexture);
			textureQuad.bindData(textureProgram);
			textureQuad.draw();
			
			setRotateM(modelMatrix, 0, rotationAngle, 0.0f, 0.0f, 1.0f);
			translateM(modelMatrix, 0, 1.62f, -0.1f, 0f);
			scaleM(modelMatrix, 0, 0.6f, 0.6f, 0.6f);
			
			setRotateM(rotate, 0, angle, 0, 0, 1);
			multiplyMM(result, 0, rotate, 0, modelMatrix, 0);
			multiplyMM(modelMatrix, 0, translate, 0, result, 0);
			textureProgram.setUniforms(projectionMatrix, modelMatrix, usernameTexture);
			textureQuad.bindData(textureProgram);
			textureQuad.draw();
			
			setRotateM(modelMatrix, 0, rotationAngle, 0.0f, 0.0f, 1.0f);
			translateM(modelMatrix, 0, 1.2f, 0f, 0f);
			scaleM(modelMatrix, 0, 0.2f, 0.2f, 0.2f);
			
			setRotateM(rotate, 0, angle, 0, 0, 1);
			multiplyMM(result, 0, rotate, 0, modelMatrix, 0);
			multiplyMM(modelMatrix, 0, translate, 0, result, 0);
			textureProgram.setUniforms(projectionMatrix, modelMatrix, userPicTexture);
			textureQuad.bindData(textureProgram);
			textureQuad.draw();
			
			setRotateM(modelMatrix, 0, rotationAngle, 0.0f, 0.0f, 1.0f);
			translateM(modelMatrix, 0, 2.2f, 0f, 0f);
			setRotateM(rotate, 0, angle, 0, 0, 1);
			multiplyMM(result, 0, rotate, 0, modelMatrix, 0);
			multiplyMM(modelMatrix, 0, translate, 0, result, 0);
			textureProgram.setUniforms(projectionMatrix, modelMatrix, lineTexture);
			largeTextureQuad.bindData(textureProgram);
			largeTextureQuad.draw();
			
			setRotateM(modelMatrix, 0, rotationAngle, 0.0f, 0.0f, 1.0f);
			translateM(modelMatrix, 0, 2.2f, -0.1f, 0f);
			setRotateM(rotate, 0, angle, 0, 0, 1);
			multiplyMM(result, 0, rotate, 0, modelMatrix, 0);
			multiplyMM(modelMatrix, 0, translate, 0, result, 0);
			textureProgram.setUniforms(projectionMatrix, modelMatrix, lineTexture);
			largeTextureQuad.bindData(textureProgram);
			largeTextureQuad.draw();
			
			setRotateM(modelMatrix, 0, rotationAngle, 0.0f, 0.0f, 1.0f);
			translateM(modelMatrix, 0, 2.2f, -0.2f, 0f);
			setRotateM(rotate, 0, angle, 0, 0, 1);
			multiplyMM(result, 0, rotate, 0, modelMatrix, 0);
			multiplyMM(modelMatrix, 0, translate, 0, result, 0);
			textureProgram.setUniforms(projectionMatrix, modelMatrix, lineTexture);
			largeTextureQuad.bindData(textureProgram);
			largeTextureQuad.draw();
		}
		
				
		rotationAngle += 0.1f;
		
	}
	
	public void instagram() {
		
	}

	@Override
	public void onDrawFrame(GL10 glUnused) {
		glClear(GL_COLOR_BUFFER_BIT);
		
		instagram();
	}


}
