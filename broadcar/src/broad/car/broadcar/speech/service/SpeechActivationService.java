/*
 * Copyright 2011 Greg Milette and Adam Stroud
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package broad.car.broadcar.speech.service;



import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import broad.car.broadcar.R;
import broad.car.broadcar.speech.VoiceRecognition;

/**
 * Persistently run a speech activator in the background.
 * Use {@link Intent}s to start and stop it
 * @author Greg Milette &#60;<a
 *         href="mailto:gregorym@gmail.com">gregorym@gmail.com</a>&#62;
 * @Modified by: Ibon ortega & Iratxe Trevejo
 */
public class SpeechActivationService extends Service implements
        SpeechActivationListener
{
	
	/*********************************************************************
	 ** 																**
	 ** GLOBAL VARIABLES 												**
	 ** 																**
	 **********************************************************************/
	
    private static final String TAG = "SpeechActivationService";
    public static final String NOTIFICATION_ICON_RESOURCE_INTENT_KEY =
        "NOTIFICATION_ICON_RESOURCE_INTENT_KEY";
    public static final String ACTIVATION_TYPE_INTENT_KEY =
            "ACTIVATION_TYPE_INTENT_KEY";
    public static final String ACTIVATION_RESULT_INTENT_KEY =
            "ACTIVATION_RESULT_INTENT_KEY";
    public static final String ACTIVATION_RESULT_BROADCAST_NAME =
            "broad.car.broadcar.speech.service.ACTIVATION";
    /**
     * send this when external code wants the Service to stop
     */
    public static final String ACTIVATION_STOP_INTENT_KEY =
            "ACTIVATION_STOP_INTENT_KEY";
    public static final int NOTIFICATION_ID = 10298;
    private boolean isStarted;
    private SpeechActivator activator;
    private int m_interval = 10000; // 10 seconds
    private Handler m_handler;
    Intent dialogIntent;
    VoiceRecognition recog;
    Context ctx;
    SharedPreferences pref;
 	private String KEY_PREF_SPEECH_RECOG;
 	
	/*********************************************************************
	 ** 																**
	 ** LOCAL FUNCTIONS 												**
	 ** 																**
	 **********************************************************************/
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        ctx = getApplicationContext(); 
    	pref = PreferenceManager.getDefaultSharedPreferences(ctx);
    	KEY_PREF_SPEECH_RECOG=getResources().getText(R.string.KEY_PREF_SPEECH_RECOG).toString();
    	
        isStarted = false;
        m_handler = new Handler();
        activator = SpeechActivatorFactory.createSpeechActivator(this, this, "hola");
        recog = new VoiceRecognition();
        dialogIntent = new Intent(getBaseContext(), recog.getClass());
    }
    
    public Runnable m_statusChecker = new Runnable()
    {
    	
         @Override 
         public void run() {
        	 if(pref.getBoolean(KEY_PREF_SPEECH_RECOG, false)){
        		 if(!recog.getActive()){
     				activator.detectActivation();   
     				Toast.makeText(getApplicationContext(),"Say 'Hola'", Toast.LENGTH_LONG).show();
     				m_handler.postDelayed(m_statusChecker, m_interval);
     			}	
        	 }
         }
   /*      public void stop(){
        	 activo=false;
         }
         public void start(){
        	 activo=true;
         }*/
    };
    public void startRepeatingTask()
    {
        m_statusChecker.run(); 
    }

    public void stopRepeatingTask()
    {
        m_handler.removeCallbacks(m_statusChecker);
    }

    
	/**********************************************************************
	 * @brief  onStartCommand se lanza al crearse el servicio. 
	 * @par	   Logica 
	 * 		    -	La fabrica de activadores {@link SpeechActivatorFactory} crea 
	 * 				un activador {@link WordActivator} con la palabra "Hola"
	 * @param   Intent intent, int flags, int startId
	 * @return
	 * @TODO 
	 * @Description  Stop or start an activator based on the activator type and if an
     * 				activator is currently running
	 **********************************************************************/
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
    	 if (intent != null)
         {
    		 m_statusChecker.run();       
         }
    	 //You can use START_STICKY to restart the Service if your Serice is killed
         // restart in case the Service gets canceled, use START_REDELIVER_INTENT
         return Service.START_STICKY;
    }

	/**********************************************************************
	 * @brief  activated se ejecuta cuando se recibe que la palabra asignada 
	 * 			al activador se recibe. 
	 * @par	   Logica 
	 * 		    -	Lanza el intent de reconocimiento de voz. 
	 * 			-	Detiene la ejecucion del servicio.
	 * @param   Intent intent, int flags, int startId
	 * @return
	 **********************************************************************/
    @Override
    public void activated(boolean success)
    {
    	
    	dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	getApplication().startActivity(dialogIntent);
        stopActivator();
        // always stop after receive an activation
        stopSelf();

    }

    @Override
    public void onDestroy()
    {
        Log.d(TAG, "On destroy");
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"Listen!", Toast.LENGTH_LONG).show();
        stopActivator();
        stopForeground(true);
    }

    private void stopActivator()
    {
        if (activator != null)
        {
            Log.d(TAG, "stopped: " + activator.getClass().getSimpleName());
            activator.stop();
            isStarted = false;
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}