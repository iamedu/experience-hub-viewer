package com.iamedu.experience;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class ExperienceActivity extends Activity {
	
	private GLSurfaceView glSurfaceView;
	private boolean rendererSet = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		glSurfaceView = new GLSurfaceView(this);
		
		final ActivityManager activityManager = (ActivityManager)getSystemService
                (Context.ACTIVITY_SERVICE);
    
        final ConfigurationInfo configurationInfo =
                activityManager.getDeviceConfigurationInfo();
        
        final boolean supportsEs2 =
                configurationInfo.reqGlEsVersion >= 0x20000 ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 &&
                (Build.FINGERPRINT.startsWith("generic") ||
                 Build.FINGERPRINT.startsWith("unknown") ||
                 Build.FINGERPRINT.startsWith("google_sdk") ||
                 Build.FINGERPRINT.startsWith("Emulator") ||
                 Build.FINGERPRINT.startsWith("Android SDK built for x86")));


        if(supportsEs2) {
        	glSurfaceView.setEGLContextClientVersion(2);
        	glSurfaceView.setEGLConfigChooser(new MultisampleConfigChooser());
    		glSurfaceView.setRenderer(new ExperienceRenderer(this));
    		rendererSet = true;
    		
    		setContentView(glSurfaceView);	
        } else {
        	Toast.makeText(this, "Device does not support GL", Toast.LENGTH_LONG).show();
        }
        

	}


	@Override
    protected void onPause() {
        super.onPause();

        if(rendererSet) {
            glSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(rendererSet) {
            glSurfaceView.onResume();
        }
    }
	
}
