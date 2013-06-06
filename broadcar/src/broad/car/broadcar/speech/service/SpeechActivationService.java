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



import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
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

    
	/*********************************************************************
	 ** 																**
	 ** LOCAL FUNCTIONS 												**
	 ** 																**
	 **********************************************************************/
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        isStarted = false;
    }

    public static Intent makeStartServiceIntent(Context context,
            String activationType)
    {
        Intent i = new Intent(context, SpeechActivationService.class);
        i.putExtra(ACTIVATION_TYPE_INTENT_KEY, activationType);
        return i;
    }

    public static Intent makeServiceStopIntent(Context context)
    {
        Intent i = new Intent(context, SpeechActivationService.class);
        i.putExtra(ACTIVATION_STOP_INTENT_KEY, true);
        return i;
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
                 // activator not started, start it
                 
                 //Log.d(TAG, "extras: " + intent.getExtras().toString());
                 if (activator == null)
                 {
                     Log.d(TAG, "null activator");
                 }
                     
                 activator = SpeechActivatorFactory.createSpeechActivator(this, this, "hola");
                 
                 Log.d(TAG, "started: " + activator.getClass().getSimpleName());
                 isStarted = true;
                 activator.detectActivation();
       
         }
    	 //You can use START_STICKY to restart the Service if your Serice is killed
         // restart in case the Service gets canceled, use START_REDELIVER_INTENT
         return Service.START_STICKY;
    }

    private void startDetecting(Intent intent)
    {
        Log.d(TAG, "extras: " + intent.getExtras().toString());
        if (activator == null)
        {
            Log.d(TAG, "null activator");
        }
            
        activator = getRequestedActivator(intent);
        Log.d(TAG, "started: " + activator.getClass().getSimpleName());
        isStarted = true;
        activator.detectActivation();

    }

    private SpeechActivator getRequestedActivator(Intent intent)
    {
        String type = intent.getStringExtra(ACTIVATION_TYPE_INTENT_KEY);
        // create based on a type name
        SpeechActivator speechActivator = SpeechActivatorFactory.createSpeechActivator(this, this, type);
        return speechActivator;
    }

    /**
     * determine if the intent contains an activator type 
     * that is different than the currently running type
     */
    private boolean isDifferentType(Intent intent)
    {
        boolean different = false;
        if (activator == null)
        {
            return true;
        }
        else
        {
            SpeechActivator possibleOther = getRequestedActivator(intent);
            different = !(possibleOther.getClass().getName().
                    equals(activator.getClass().getName()));
        }
        return different;
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
    	Intent dialogIntent = new Intent(getBaseContext(), VoiceRecognition.class);
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