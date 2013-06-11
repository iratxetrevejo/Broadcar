package broad.car.broadcar.speech;
/*********************************************************************
 **																	**
 ** MODULES USED 													**
 ** 																**
 *********************************************************************/
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import broad.car.broadcar.R;
import broad.car.broadcar.speech.service.SpeechActivationService;
/** @addtogroup Broadcar
*
* @{

* @file VoiceRecognition
* @brief Clase del reconocedor de voz.
*
* @par VERSION HISTORY
* Version : v0.0
* Date : 30/01/2013
* Revised by : BroadCar team
* @author  Iratxe Trevejo
* @author  Ibon Ortega
* Description : Clase que lanza un reconocedor de voz y ejecuta el 
* 				reconocedor de comandos.
*
* @}
*/

public class VoiceRecognition extends Activity
{
	/*********************************************************************
	 ** 																**
	 ** IMPORTED CLASSES / Declarations  								**
	 ** 																**
	 *********************************************************************/
	CommandMatcher commandMatcher;
	Context ctx;
	SharedPreferences prefs;
	
	/*********************************************************************
	 ** 																**
	 ** GLOBAL VARIABLES 												**
	 ** 																**
	 *********************************************************************/
    private static final int REQUEST_CODE = 1234;
	
    private String KEY_PREF_HEAVY_TRAFFIC;
   	private String KEY_PREF_LOW_VISIBILITY;
   	private String KEY_PREF_ROAD_STATE;
   	private String KEY_PREF_CRASHES;
   	private String KEY_PREF_WORKS;
   	private String KEY_PREF_VEHICLE_NO_VISIBLE;
   	boolean active;
	/*********************************************************************
	 ** 																**
	 ** LOCAL FUNCTIONS 												**
	 ** 																**
	 *********************************************************************/
	/**********************************************************************
	 * @brief  onCreate es una funcion que se ejecuta al iniciar la actividad.
	 * @par	   Logica 
	 * 		    -	Lanza el layout
	 * 			-	Crea el reconocedor de comandos
	 * 			-	Inicia la actividad de reconocimiento de voz.
	 * @param   Bundle savedInstanceState
	 * @return	
	 * @TODO 
	 **********************************************************************/
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_recog);
    	ctx = getApplicationContext(); 
    	prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        commandMatcher = new CommandMatcher(prefs);
        active = true;
    	KEY_PREF_HEAVY_TRAFFIC = getResources().getText(R.string.KEY_PREF_HEAVY_TRAFFIC).toString();
       	KEY_PREF_LOW_VISIBILITY= getResources().getText(R.string.KEY_PREF_LOW_VISIBILITY).toString();
       	KEY_PREF_ROAD_STATE= getResources().getText(R.string.KEY_PREF_ROAD_STATE).toString();
       	KEY_PREF_CRASHES= getResources().getText(R.string.KEY_PREF_CRASHES).toString();
       	KEY_PREF_WORKS= getResources().getText(R.string.KEY_PREF_WORKS).toString();
       	KEY_PREF_VEHICLE_NO_VISIBLE=getResources().getText(R.string.KEY_PREF_VEHICLE_NO_VISIBLE).toString();
       	set_KEY_Resources();
        startVoiceRecognitionActivity();
    } 
	/**********************************************************************
	 * @brief  set_KEY_Resources lanza los set para los recursos de los settings.
	 * @par	   Logica 
	 * 		    -	Lanza cada uno de los set.
	 * @param   
	 * @return	
	 * @TODO 
	 **********************************************************************/
    public void set_KEY_Resources(){
    	commandMatcher.set_KEY_PREF_HEAVY_TRAFFIC_Resource(this.KEY_PREF_HEAVY_TRAFFIC);
    	commandMatcher.set_KEY_PREF_LOW_VISIBILITY_Resource(this.KEY_PREF_LOW_VISIBILITY);
    	commandMatcher.set_KEY_PREF_ROAD_STATE_Resource(this.KEY_PREF_ROAD_STATE);
    	commandMatcher.set_KEY_PREF_CRASHES_Resource(this.KEY_PREF_CRASHES);
    	commandMatcher.set_KEY_PREF_WORKS_Resource(this.KEY_PREF_WORKS);
    	commandMatcher.set_KEY_PREF_VEHICLE_NO_VISIBLE_Resource(this.KEY_PREF_VEHICLE_NO_VISIBLE);
    	
    }
	/**********************************************************************
	 * @brief  startVoiceRecognitionActivity lanza el reconocimiento de voz.
	 * @par	   Logica 
	 * 		    -	crea el intent de reconocimiento de voz.
	 * 			-	guarda el lenguaje para lanzar la actividad
	 * 			-	lanza el intent con un request_code
	 * @param   
	 * @return	
	 * @TODO 
	 **********************************************************************/
    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
        startActivityForResult(intent, REQUEST_CODE);
    }
 
	/**********************************************************************
	 * @brief  onActivityResult es la funcion que salta tras ejecutar el 
	 * 			reconocimiento de voz.
	 * @par	   Logica 
	 * 		    -	Comprueba el resultado del reconocedor.
	 * 			-	Crea un arrayList recogiendo los datos del reconocedor.
	 * 			-	Le pasa los datos a reconocimiento de comandos.
	 * 			-	Busca los comandos y ejecuta las acciones.
	 * @param   int requestCode, int resultCode, Intent data
	 * @return	
	 * @TODO 
	 **********************************************************************/
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
     
        	commandMatcher.setMatchesList(matches);
        	commandMatcher.searchForAllCommands();
        	
        }
        super.onActivityResult(requestCode, resultCode, data);
      this.finish();
    }
    
    @Override
    public void onStart() {
       super.onStart();
       active = true;
    } 

    @Override
    public void onStop() {
       super.onStop();
       active = false;
    }
    public boolean getActive(){
    	return this.active;
    }
}