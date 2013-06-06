package broad.car.broadcar.speech;
/*********************************************************************
 **																	**
 ** MODULES USED 													**
 ** 																**
 *********************************************************************/
import java.util.ArrayList;

import broad.car.broadcar.R;
import broad.car.broadcar.R.id;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/** @addtogroup Broadcar
*
* @{

* @file MainActivity
* @brief Clase principal del programa
*
* @par VERSION HISTORY
* Version : v0.0
* Date : 30/01/2013
* Revised by : BroadCar team
* @author  Iratxe Trevejo
* @author  Ibon Ortega
* Description : Original version.
*
* @}
*/

public class CommandMatcher {
	/*********************************************************************
	 ** 																**
	 ** IMPORTED CLASSES / Declarations  								**
	 ** 																**
	 *********************************************************************/
	ArrayList<String> matches=null;
	CommandDictionary dictionary;
	SharedPreferences prefs;
	SharedPreferences.Editor editor;
	/*********************************************************************
	 ** 																**
	 ** GLOBAL VARIABLES 												**
	 ** 																**
	 *********************************************************************/
	boolean detectado;
	boolean KEY_BOOL_ACTIVATE=false;
	boolean KEY_BOOL_DESACTIVATE=false;
	boolean KEY_BOOL_ALERT=false;
	boolean KEY_BOOL_CRASHES=false;
	boolean KEY_BOOL_HEAVY_TRAFFIC=false;
	boolean KEY_BOOL_LOW_VISIBILITY=false;
	boolean KEY_BOOL_ROAD_STATE=false;
	boolean KEY_BOOL_VEHICLE_NO_VISIBLE=false;
	boolean KEY_BOOL_WORKS=false;
	
	private String KEY_PREF_HEAVY_TRAFFIC;
	private String KEY_PREF_LOW_VISIBILITY;
	private String KEY_PREF_ROAD_STATE;
	private String KEY_PREF_CRASHES;
	private String KEY_PREF_WORKS;
	private String KEY_PREF_VEHICLE_NO_VISIBLE;
		

		
	/*********************************************************************
	 ** 																**
	 ** LOCAL FUNCTIONS 												**
	 ** 																*
	 * @param prefs *
	 *********************************************************************/
	
	public CommandMatcher(SharedPreferences prefs){
		setMatchesList(matches);
		dictionary=new CommandDictionary();    	
		this.prefs=prefs;
	}
	/**********************************************************************
	 * @brief  getMatchesList es la funcion que sirve para acceder al matches
	 * @par	   Logica 
	 * 		    -	Devuelve el valor de matches 
	 * @param   
	 * @return	this.matches
	 * @TODO 
	 **********************************************************************/
    public ArrayList<String> getMatchesList(){
    	return this.matches;
    }
	/**********************************************************************
	 * @brief  setMatchesList es la funcion que sirve para cambiar el valor de 
	 * 			matches
	 * @par	   Logica 
	 * 		    -	cambia el valor de matches 
	 * @param	ArrayList<String> matches
	 * @return	
	 * @TODO 
	 **********************************************************************/
    public void  setMatchesList(ArrayList<String> matches){
    	this.matches=matches;
    }
	/**********************************************************************
	 * @brief  searchForAllCommands Funcion que busca si las palabras recogidas con
	 * 			el speech se encuentran en nuestro diccionario.
	 * @par	   Logica 
	 * 		    -	Llama a cada una de las funciones que reconocen comandos. 
	 * 			-	Ejecuta las acciones tras conocer los comandos. 
	 * @param	
	 * @return	
	 * @TODO 	-	Poner todos los comandos a false otra vez ¿?
	 **********************************************************************/
    public void searchForAllCommands(){
    	if(matches!=null){
    		detectado=true;
    		searchForActivateCommand();
    		searchForDesactivateCommand();
    		searchForAlertCommand();
    		searchForCrashesCommand();
    		searchForHeavyTrafficCommand();
    		searchForLowVisibilityCommand();
    		searchForRoadStateCommand();
    		searchForVehicleNoVisibleCommand();
    		searchForWorksCommand();
    	}	
    	commandExecutor();
    }
	/**********************************************************************
	 * @brief  searchForActivateCommand Funcion que busca si las palabras 
	 * 			relacionada con el comando Activar se encuentran entre las 
	 * 			escuchadas. 
	 * @par	   Logica 
	 * 		    -	recorre las palabras escuchadas
	 * 			-	separa las palabras si son una frase
	 * 			-	Compara esas palabras con las palabras del comando 
	 * @param	
	 * @return	
	 * @TODO 	
	 **********************************************************************/
    private void searchForActivateCommand(){
    	//KEY_BOOL_ACTIVATE
		for(int i=0;i<getMatchesList().size();i++){
			String[] splitted = getMatchesList().get(i).split(" ");
			for(int j=0;j<splitted.length;j++){
				for(int k=0;k<dictionary.KEY_STRING_ACTIVATE.size();k++){		
    				if(dictionary.KEY_STRING_ACTIVATE.get(k).equals(splitted[j])){
            			KEY_BOOL_ACTIVATE=true;	
            			break;
    				}	
    			}
    		}break;
    	}
    }
	/**********************************************************************
	 * @brief  searchForDesactivateCommand Funcion que busca si las palabras 
	 * 			relacionada con el comando Desactivar se encuentran entre las 
	 * 			escuchadas. 
	 * @par	   Logica 
	 * 		    -	recorre las palabras escuchadas
	 * 			-	separa las palabras si son una frase
	 * 			-	Compara esas palabras con las palabras del comando 
	 * @param	
	 * @return	
	 * @TODO 	
	 **********************************************************************/
    private void searchForDesactivateCommand(){
    	//KEY_BOOL_DESACTIVATE
		for(int i=0;i<getMatchesList().size();i++){
			String[] splitted = getMatchesList().get(i).split(" ");
			for(int j=0;j<splitted.length;j++){
				for(int k=0;k<dictionary.KEY_STRING_DESACTIVATE.size();k++){		
    				if(dictionary.KEY_STRING_DESACTIVATE.get(k).equals(splitted[j])){
            			KEY_BOOL_DESACTIVATE=true;	
            			break;
    				}	
    			}
    		}break;
    	}
    }
	/**********************************************************************
	 * @brief  searchForAlertCommand Funcion que busca si las palabras 
	 * 			relacionada con el comando Alertas se encuentran entre las 
	 * 			escuchadas. 
	 * @par	   Logica 
	 * 		    -	recorre las palabras escuchadas
	 * 			-	separa las palabras si son una frase
	 * 			-	Compara esas palabras con las palabras del comando 
	 * @param	
	 * @return	
	 * @TODO 	
	 **********************************************************************/
    private void searchForAlertCommand(){
    	//KEY_BOOL_ALERT
		for(int i=0;i<getMatchesList().size();i++){
			String[] splitted = getMatchesList().get(i).split(" ");
			for(int j=0;j<splitted.length;j++){
				for(int k=0;k<dictionary.KEY_STRING_ALERT.size();k++){		
    				if(dictionary.KEY_STRING_ALERT.get(k).equals(splitted[j])){
            			KEY_BOOL_ALERT=true;	
            			break;
    				}	
    			}
    		}break;
    	}
    }
	/**********************************************************************
	 * @brief  searchForCrashesCommand Funcion que busca si las palabras 
	 * 			relacionada con el comando Accidentes se encuentran entre las 
	 * 			escuchadas. 
	 * @par	   Logica 
	 * 		    -	recorre las palabras escuchadas
	 * 			-	separa las palabras si son una frase
	 * 			-	Compara esas palabras con las palabras del comando 
	 * @param	
	 * @return	
	 * @TODO 	
	 **********************************************************************/
    private void searchForCrashesCommand(){
    	//KEY_BOOL_CRASHES
		for(int i=0;i<getMatchesList().size();i++){
			String[] splitted = getMatchesList().get(i).split(" ");
			for(int j=0;j<splitted.length;j++){
				for(int k=0;k<dictionary.KEY_STRING_CRASHES.size();k++){		
    				if(dictionary.KEY_STRING_CRASHES.get(k).equals(splitted[j])){
            			KEY_BOOL_CRASHES=true;	
            			break;
    				}	
    			}
    		}break;
    	}
    }
	/**********************************************************************
	 * @brief  searchForHeavyTrafficCommand Funcion que busca si las palabras 
	 * 			relacionada con el comando Trafico se encuentran entre las 
	 * 			escuchadas. 
	 * @par	   Logica 
	 * 		    -	recorre las palabras escuchadas
	 * 			-	separa las palabras si son una frase
	 * 			-	Compara esas palabras con las palabras del comando 
	 * @param	
	 * @return	
	 * @TODO 	
	 **********************************************************************/
    private void searchForHeavyTrafficCommand(){
    	//KEY_BOOL_HEAVY_TRAFFIC
		for(int i=0;i<getMatchesList().size();i++){
			String[] splitted = getMatchesList().get(i).split(" ");
			for(int j=0;j<splitted.length;j++){
				for(int k=0;k<dictionary.KEY_STRING_HEAVY_TRAFFIC.size();k++){		
    				if(dictionary.KEY_STRING_HEAVY_TRAFFIC.get(k).equals(splitted[j])){
            			KEY_BOOL_HEAVY_TRAFFIC=true;	
            			break;
    				}	
    			}
    		}break;
    	}
    }
	/**********************************************************************
	 * @brief  searchForLowVisibilityCommand Funcion que busca si las palabras 
	 * 			relacionada con el comando Poca Visibilidad se encuentran entre 
	 * 			las escuchadas. 
	 * @par	   Logica 
	 * 		    -	recorre las palabras escuchadas
	 * 			-	separa las palabras si son una frase
	 * 			-	Compara esas palabras con las palabras del comando 
	 * @param	
	 * @return	
	 * @TODO 	
	 **********************************************************************/
    private void searchForLowVisibilityCommand(){
    	//KEY_BOOL_LOW_VISIBILITY
		for(int i=0;i<getMatchesList().size();i++){
			String[] splitted = getMatchesList().get(i).split(" ");
			for(int j=0;j<splitted.length;j++){
				for(int k=0;k<dictionary.KEY_STRING_LOW_VISIBILITY.size();k++){		
    				if(dictionary.KEY_STRING_LOW_VISIBILITY.get(k).equals(splitted[j])){
            			KEY_BOOL_LOW_VISIBILITY=true;	
            			break;
    				}	
    			}
    		}break;
    	}
    }
	/**********************************************************************
	 * @brief  searchForRoadStateCommand Funcion que busca si las palabras 
	 * 			relacionada con el comando Estado de la Carretera se 
	 * 			encuentran entre las escuchadas. 
	 * @par	   Logica 
	 * 		    -	recorre las palabras escuchadas
	 * 			-	separa las palabras si son una frase
	 * 			-	Compara esas palabras con las palabras del comando 
	 * @param	
	 * @return	
	 * @TODO 	
	 **********************************************************************/
    private void searchForRoadStateCommand(){
    	//KEY_BOOL_LOW_VISIBILITY
		for(int i=0;i<getMatchesList().size();i++){
			String[] splitted = getMatchesList().get(i).split(" ");
			for(int j=0;j<splitted.length;j++){
				for(int k=0;k<dictionary.KEY_STRING_ROAD_STATE.size();k++){		
    				if(dictionary.KEY_STRING_ROAD_STATE.get(k).equals(splitted[j])){
    					KEY_BOOL_ROAD_STATE=true;	
            			break;
    				}	
    			}
    		}break;
    	}
    }
	/**********************************************************************
	 * @brief  searchForVehicleNoVisibleCommand Funcion que busca si las palabras 
	 * 			relacionada con el comando Vehiculo no visible se encuentran 
	 * 			entre las escuchadas. 
	 * @par	   Logica 
	 * 		    -	recorre las palabras escuchadas
	 * 			-	separa las palabras si son una frase
	 * 			-	Compara esas palabras con las palabras del comando 
	 * @param	
	 * @return	
	 * @TODO 	
	 **********************************************************************/
    private void searchForVehicleNoVisibleCommand(){
    	//KEY_BOOL_VEHICLE_NO_VISIBLE
		for(int i=0;i<getMatchesList().size();i++){
			String[] splitted = getMatchesList().get(i).split(" ");
			for(int j=0;j<splitted.length;j++){
				for(int k=0;k<dictionary.KEY_STRING_VEHICLE_NO_VISIBLE.size();k++){		
    				if(dictionary.KEY_STRING_VEHICLE_NO_VISIBLE.get(k).equals(splitted[j])){
    					KEY_BOOL_VEHICLE_NO_VISIBLE=true;	
            			break;
    				}	
    			}
    		}break;
    	}
    }
	/**********************************************************************
	 * @brief  searchForWorksCommand Funcion que busca si las palabras 
	 * 			relacionada con el comando Obras se encuentran 
	 * 			entre las escuchadas. 
	 * @par	   Logica 
	 * 		    -	recorre las palabras escuchadas
	 * 			-	separa las palabras si son una frase
	 * 			-	Compara esas palabras con las palabras del comando 
	 * @param	
	 * @return	
	 * @TODO 	
	 **********************************************************************/
    private void searchForWorksCommand(){
    	//KEY_BOOL_WORKS
		for(int i=0;i<getMatchesList().size();i++){
			String[] splitted = getMatchesList().get(i).split(" ");
			for(int j=0;j<splitted.length;j++){
				for(int k=0;k<dictionary.KEY_STRING_WORKS.size();k++){		
    				if(dictionary.KEY_STRING_WORKS.get(k).equals(splitted[j])){
            			KEY_BOOL_WORKS=true;	
            			break;
    				}	
    			}
    		}break;
    	}
    }
	/**********************************************************************
	 * @brief  commandExecutor Funcion que busca el estado de las variables 
	 * 			de los comandos y ejecuta la tarea propuesta.
	 * @par	   Logica 
	 * 		    -	comprueba los estados
	 * 			-	Actua en consecuencia
	 * @param	
	 * @return	
	 * @TODO 	
	 **********************************************************************/
    private void commandExecutor(){
    	if(KEY_BOOL_ACTIVATE){
    		if(KEY_BOOL_ALERT){
    			if(!KEY_BOOL_CRASHES && !KEY_BOOL_HEAVY_TRAFFIC && !KEY_BOOL_LOW_VISIBILITY && 
    					!KEY_BOOL_ROAD_STATE && !KEY_BOOL_VEHICLE_NO_VISIBLE && !KEY_BOOL_WORKS){
    				//ACTIVAR TODAS LAS ALERTAS
    				updateAllPrefs(true);
    			}
    		}else{
    			//Activar las alertas si se ha dicho una concreta.
    			changeSinglePref(true);
			}		
    	}else if(KEY_BOOL_DESACTIVATE){
    		if(KEY_BOOL_ALERT){
    			if(!KEY_BOOL_CRASHES && !KEY_BOOL_HEAVY_TRAFFIC && !KEY_BOOL_LOW_VISIBILITY && 
    					!KEY_BOOL_ROAD_STATE && !KEY_BOOL_VEHICLE_NO_VISIBLE && !KEY_BOOL_WORKS){
    				//DESACTIVAR TODAS LAS ALERTAS
    				updateAllPrefs(false);;
    			}
    		}else{
    			//Desactivar las alertas si se ha dicho una concreta.
    			changeSinglePref(false);
			}	
    	}
    }
	/**********************************************************************
	 * @brief  	updateAllPrefs Cambia el estado de todos los booleanos de 
	 * 			Preferences
	 * @param   boolean states
	 * @par		Logica:
	 * 				-	Cambia todos los estados de la preferencia del que 
	 * 					hayan sido detectados
	 * @return
	 * @TODO 
	**********************************************************************/	
    private void updateAllPrefs(boolean states){
		prefs.edit().putBoolean( KEY_PREF_VEHICLE_NO_VISIBLE, states ).commit();
		prefs.edit().putBoolean( KEY_PREF_HEAVY_TRAFFIC, states ).commit();
		prefs.edit().putBoolean( KEY_PREF_LOW_VISIBILITY, states ).commit();
		prefs.edit().putBoolean( KEY_PREF_ROAD_STATE, states ).commit();
		prefs.edit().putBoolean( KEY_PREF_CRASHES, states ).commit();
		prefs.edit().putBoolean( KEY_PREF_WORKS, states ).commit();
    }
	/**********************************************************************
	 * @brief  changeSinglePref Cambia el estado de los booleanos de Preferences
	 * @param   boolean states
	 * @par		Logica:
	 * 				-	Comprubea si se ha activado los flag.
	 * 				-	Cambia el estado de la preferencia del que haya sido detectado
	 * @return
	 * @TODO 
	**********************************************************************/	
    private void changeSinglePref(boolean states){
		/*Activar Alertas de accidentes*/
		if(KEY_BOOL_CRASHES){prefs.edit().putBoolean( KEY_PREF_CRASHES, states ).commit();}
		/*Activar Alertas de trafico*/
		if(KEY_BOOL_HEAVY_TRAFFIC){prefs.edit().putBoolean( KEY_PREF_HEAVY_TRAFFIC, states ).commit();}
		/*Activar Alertas de poca visibilidad*/
		if(KEY_BOOL_LOW_VISIBILITY){prefs.edit().putBoolean( KEY_PREF_LOW_VISIBILITY, states ).commit();}
		/*Activar Alertas de estado carretera*/
		if(KEY_BOOL_ROAD_STATE){prefs.edit().putBoolean( KEY_PREF_ROAD_STATE, states ).commit();}
		/*Activar Alertas de vehiculo no visible*/
		if(KEY_BOOL_VEHICLE_NO_VISIBLE){prefs.edit().putBoolean( KEY_PREF_VEHICLE_NO_VISIBLE, states ).commit();}
		/*Activar Alertas de obras*/
		if(KEY_BOOL_WORKS){prefs.edit().putBoolean( KEY_PREF_WORKS, states ).commit();}
    }
    
	/**********************************************************************
	 * @brief  Inicializa el string que proviene de {@link VoiceRecognition}
	 * @param   String KEY_PREF_HEAVY_TRAFFIC
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void set_KEY_PREF_HEAVY_TRAFFIC_Resource(String KEY_PREF_HEAVY_TRAFFIC) {
		this.KEY_PREF_HEAVY_TRAFFIC=KEY_PREF_HEAVY_TRAFFIC;
	}
	/**********************************************************************
	 * @brief  	Inicializa el string que proviene de {@link VoiceRecognition}
	 * @param  	String KEY_PREF_LOW_VISIBILITY
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void set_KEY_PREF_LOW_VISIBILITY_Resource(String KEY_PREF_LOW_VISIBILITY) {
		this.KEY_PREF_LOW_VISIBILITY=KEY_PREF_LOW_VISIBILITY;		
	}
	/**********************************************************************
	 * @brief  	Inicializa el string que proviene de {@link VoiceRecognition}
	 * @param  	String KEY_PREF_VEHICLE_NO_VISIBLE
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void set_KEY_PREF_VEHICLE_NO_VISIBLE_Resource(String KEY_PREF_VEHICLE_NO_VISIBLE) {
		this.KEY_PREF_VEHICLE_NO_VISIBLE=KEY_PREF_VEHICLE_NO_VISIBLE;		
	}
	/**********************************************************************
	 * @brief  	Inicializa el string que proviene de {@link VoiceRecognition}
	 * @param  	String KEY_PREF_ROAD_STATE
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void set_KEY_PREF_ROAD_STATE_Resource(String KEY_PREF_ROAD_STATE) {
		this.KEY_PREF_ROAD_STATE=KEY_PREF_ROAD_STATE;		
	}
	/**********************************************************************
	 * @brief  	Inicializa el string que proviene de {@link VoiceRecognition}
	 * @param  	String KEY_PREF_CRASHES
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void set_KEY_PREF_CRASHES_Resource(String KEY_PREF_CRASHES) {
		this.KEY_PREF_CRASHES=KEY_PREF_CRASHES;		
	}
	/**********************************************************************
	 * @brief  	Inicializa el string que proviene de {@link VoiceRecognition}
	 * @param  	String KEY_PREF_WORKS
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void set_KEY_PREF_WORKS_Resource(String KEY_PREF_WORKS) {
		this.KEY_PREF_WORKS=KEY_PREF_WORKS;
	}    
}
