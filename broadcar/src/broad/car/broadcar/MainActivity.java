
package broad.car.broadcar;


/*********************************************************************
**																	**
** MODULES USED 													**
** 																	**
**********************************************************************/

import java.util.Locale;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import broad.car.broadcar.alerts.AlertManager;
import broad.car.broadcar.map.googleMap;
import broad.car.broadcar.tts.*;
import broad.car.broadcar.bluetooth.*;
import broad.car.broadcar.gps.*;


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


public class MainActivity extends android.support.v4.app.FragmentActivity implements OnSharedPreferenceChangeListener{

	/*********************************************************************
	** 																	**
	** IMPORTED CLASSES / Declarations  								**
	** 																	**
	**********************************************************************/
	//Mapa de google
	GoogleMap googleMap = null;
	//Manager que gestiona el gps
	LocationManager locationManager;
	//Clase encargada de gestionar las alertas
	AlertManager alertManager;
	//Clase encargada de trabajar con el bluetooth del movil
	BluetoothAdapter mBluetoothAdapter ;
	//Preferencias del menú
	SharedPreferences preferencias;
	// Member object for the chat services;hilo para la comunicacion buetooth
    BluetoothChatService mChatService = null;
    //Clase encarga de gestionar el bluetooth y la conexión
    Manage_BT_Comunication manage_BT;
	//mapa de google
	googleMap mapa;
	//Clase encargada de la configuracion del gps	
	gps gps;
	AndroidTextToSpeech tts;
	/*********************************************************************
	** 																	**
	** GLOBAL VARIABLES 												**
	** 																	**
	**********************************************************************/
	//Indicador para determinar una conexión segura
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	// Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	TextToSpeech mTts;
    // Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	public static final String KEY_PREF_HEAVY_TRAFFIC="heavy_traffic_pref";
	public static final String KEY_PREF_LOW_VISIBILITY="low_visibility_pref";
	public static final String KEY_PREF_ROAD_STATE="road_state_pref";
	public static final String KEY_PREF_CRASHES="crashes_pref";
	public static final String KEY_PREF_WORKS="work_pref";	
	public static final String KEY_PREF_VEHICLE_NO_VISIBLE="vehicle_no_visible_pref";
	
	public static final String KEY_PREF_MAPS_NORMAL="NORMAL";
	public static final String KEY_PREF_MAPS_HYBRID="HYBRID";
	public static final String KEY_PREF_MAPS_SATELLITE="SATELLITE";
	public static final String KEY_PREF_MAPS_TERRAIN="TERRAIN";
	public static final String KEY_PREF_LIST_PREF="listPref";
	private static final int MY_DATA_CHECK_CODE = 0;
	public static String maplistpref;

	//Variables encargadas de recoger el estado de las alertas (true/false)
	boolean switchHeavy_traffic_pref;
	boolean switchLow_visibility_pref;
	boolean switchRoad_state_pref;
	boolean switchCrashes_pref;
	boolean switchWorks_pref;
	boolean switchVehicle_no_visible_pref;
	
	/*********************************************************************
	** 																	**
	** LOCAL FUNCTIONS 													**
	** 																	**
	**********************************************************************/
		
	/**********************************************************************
	 * @brief  onCreate es la primera función que se ejecuta al inicializar 
	 * 		   el programa Broadcar
	 * @par	   Logica 
	 * 		    -	Inializa la posicion del gps en el mapa
	 * 			-   Inicializa las alertas
	 * 			-   Comprueba el estado del bluetooth	 
	 * @param   Bundle savedeIntanceState
	 * @return
	 * @TODO 
	**********************************************************************/		
	
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);	
		//Crea los objetos de las clases AlertManager y googleMap
		alertManager=new AlertManager();
		mapa=new googleMap(this);


	 //START THE TTS
		tts= new AndroidTextToSpeech();
		tts.start(this.getApplicationContext());
		
		
		//related to the map
		// Getting reference to the SupportMapFragment of activity_main.xml		
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		// Getting LocationManager object from System Service LOCATION_SERVICE
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		//inicializa la localización del gps en el mapa
        mapa.init_location(fm,locationManager,alertManager);
        
		//Se inicializan los estados de las alertas( default:true)
		preferences_init();	
		//mTts=tts.getMtts();
		//Obtiene el adaptador del bluetooth
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mBluetoothAdapter.enable();
		
		manage_BT = new Manage_BT_Comunication(this,mBluetoothAdapter,alertManager,tts.getMtts(),tts.getQueue(),tts,preferencias);//////////////////////////////////////

		//manage_BT = new Manage_BT_Comunication(this,mBluetoothAdapter,alertManager,mTts,TextToSpeech.QUEUE_FLUSH);
		//manage_BT = new Manage_BT_Comunication(this,mBluetoothAdapter,alertManager);
        //se comprueba el estado del bluetooth (on/off)
        manage_BT.check_BluetoothStatus();	
        //pasa el mChatChatService a la case Manage_BT_Comunication
        manage_BT.setChatService(mChatService);
        //pide al Manage_BT_Comunication que cree el BluetoothChatService
        manage_BT.createBluetoothChatService(this);
       
        if(!mBluetoothAdapter.isEnabled()){
        	//pone el dispositivo visible para el resto
      		Intent intent_discoverable = manage_BT.set_bt_discoverable();
      	    startActivity(intent_discoverable); 
        }
        
        //habilita el gps desde el inicio
        gps.turnGPSOn(this);
                 
	}

	
	/**********************************************************************
	 * @brief 	Finaliza el Bluetooth y el GPS cuando se cierra la aplicacion
	 * @par	   Logica 
	 * 		    -	Desactiva el Bluetooth
	 * 			-  	Desactiva el GPS
	 * @param   
	 * @return
	 * @TODO 

	**********************************************************************/	
	 protected void onDestroy()
	    {
	        // TODO Auto-generated method stub
	       super.onDestroy();	      
	       manage_BT.turnOffBT(mBluetoothAdapter);
      	   gps.turnGPSOff(this);
      	 
	   }
	
	/**********************************************************************
	 * @brief  Inicializa las preferencias de las alertas
	 * @par	   Logica 
	 * 	 	    - Inicializa el listener para detectar cambios en los estados 
	 * 			  de las alertas
	 * 		    - Pone todas las alertas por defecto a true	 
	 * @param   
	 * @return
	 * @TODO 

	**********************************************************************/
	
	private void preferences_init() {
		preferencias= PreferenceManager.getDefaultSharedPreferences(this);
        preferencias.registerOnSharedPreferenceChangeListener(this);
    
        switchHeavy_traffic_pref = preferencias.getBoolean(KEY_PREF_HEAVY_TRAFFIC, true);
        switchLow_visibility_pref = preferencias.getBoolean(KEY_PREF_LOW_VISIBILITY, true);
        switchRoad_state_pref = preferencias.getBoolean(KEY_PREF_ROAD_STATE, true);
        switchCrashes_pref = preferencias.getBoolean(KEY_PREF_CRASHES, true);
        switchWorks_pref = preferencias.getBoolean(KEY_PREF_WORKS, true);
        
	}

		
	
	/**********************************************************************
	 * @brief  Inflate the menu; this adds items to the action bar if it is present.
	 * @param  Menu menu- el menu para las opciones de configuracion
	 * @return
	 * @TODO 

	**********************************************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	

	/**********************************************************************
	 * @brief  Gestiona los botones del menú.
	 * @par	   Logica 
	 * 	 	    - Comprueba que boton se a seleccionado en el menu y
	 * 			  actúa segun la opción (bluetooth, gps o settings)
	 * @param   MenuItem item- selección del menú
	 * @return
	 * @TODO 

	**********************************************************************/
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch (item.getItemId()){
		case R.id.menu_settings:// El usuario selecciona la opcion de Configuración de alertas del menú
			startActivity(new Intent(this, QuickPrefsActivity.class));
			return true;
		case R.id.menugps:// El usuario selecciona la opcion de GPS Settings del menú
			startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
			return true;
		case R.id.menubt: // El usuario selecciona la opcion de BluetoothSettings del menú
			
			//muestra los dispositivos disponibles
			Intent intent = manage_BT.show_Discoverable_devices();	
	        startActivityForResult(intent, REQUEST_CONNECT_DEVICE_SECURE);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	

	/**********************************************************************
	 * @brief  Gestiona que el dispositivo se conecte de forma segura mediante
	 * 		   el bluetooth
	 * @par	   Logica 
	 * 	 	    - Si el requisito que recibe es para conectarse de forma segura
	 * 			  procede a realizar la conexion.
	 * @param   int requestCode  -peticion que recibe
	 * @param   int resultCode   -el codigo resultante 
	 * @param   Intent data   
	 * @return
	 * @TODO 

	**********************************************************************/
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE_SECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                manage_BT.connectDevice(data, true);
            }
            break;
        }
    /* if (requestCode == MY_DATA_CHECK_CODE) {
	        if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
	            // success, create the TTS instance
	            mTts = new TextToSpeech(this, this);
	        } else {
	            // missing data, install it
	            Intent installIntent = new Intent();
	            installIntent.setAction(
	                TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
	            startActivity(installIntent);
	        }
	    }*/
      }
		  

  /**********************************************************************
	 * @brief  Encargada de gestionar las alertas
	 * @par	   Logica 
	 * 	 	    - Dependiendo de la key o peticion que reciba activa las
	 * 			  recepcion de la alerta seleccionada
	 * 			- Dibuja en el mapa las alertas activadas
	 * @param  SharedPreferences sharedPreferences
	 * 	@param String key - alerta a activar
	 * @return
	 * @TODO Esta funcion es demasiado larga. Aumenta la complejidad por
	 * 		 lo que habría que refactorizarla.

	**********************************************************************/		  
		  
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(KEY_PREF_HEAVY_TRAFFIC)) {
			switchHeavy_traffic_pref = preferencias.getBoolean(KEY_PREF_HEAVY_TRAFFIC, true);
			if(switchHeavy_traffic_pref){
				for(int i=0;i<alertManager.getHeavytraffic_alerts().length;i++){
					alertManager.heavytraffic_Alerts[i].setState(true);
				}
	        }else{
				for(int i=0;i<alertManager.getHeavytraffic_alerts().length;i++){
					alertManager.heavytraffic_Alerts[i].setState(false);
				}
	        }  
        }
		
		if (key.equals(KEY_PREF_CRASHES)) {
			switchCrashes_pref = preferencias.getBoolean(KEY_PREF_CRASHES, true);
			if(switchCrashes_pref){
				for(int i=0;i<alertManager.getCrashes_Alerts().length;i++){
					alertManager.crashes_Alerts[i].setState(true);
				}
	        }else{
	        	for(int i=0;i<alertManager.getCrashes_Alerts().length;i++){
					alertManager.crashes_Alerts[i].setState(false);
				}
	        }  
        }
		
		if (key.equals(KEY_PREF_ROAD_STATE)) {
			switchRoad_state_pref = preferencias.getBoolean(KEY_PREF_ROAD_STATE, true);
			if(switchRoad_state_pref){
				for(int j=0;j<alertManager.getRoadState_Alerts().length;j++){
					for(int i=0;i<alertManager.getRoadState_Alerts().length;i++){
						alertManager.roadstate_Alerts[j][i].setState(true);
					}
				}

	        }else{
	        	for(int j=0;j<alertManager.getRoadState_Alerts().length;j++){
		        	for(int i=0;i<alertManager.getRoadState_Alerts().length;i++){
						alertManager.roadstate_Alerts[j][i].setState(false);
					}
	        	}  
	        }
        }
		if (key.equals(KEY_PREF_LOW_VISIBILITY)) {
			switchLow_visibility_pref = preferencias.getBoolean(KEY_PREF_LOW_VISIBILITY, true);
			if(switchLow_visibility_pref){
				for(int j=0;j<alertManager.getLowVisibility_Alerts().length;j++){
					for(int i=0;i<alertManager.lowVisibility_Alerts[j].length;i++){
						alertManager.lowVisibility_Alerts[j][i].setState(true);
					}	
				}
				
	        }else{
	        	for(int j=0;j<alertManager.getLowVisibility_Alerts().length;j++){
		        	for(int i=0;i<alertManager.lowVisibility_Alerts[j].length;i++){
						alertManager.lowVisibility_Alerts[j][i].setState(false);
					}
	        	}
	        }  
        }
		if (key.equals(KEY_PREF_VEHICLE_NO_VISIBLE)) {
			switchVehicle_no_visible_pref = preferencias.getBoolean(KEY_PREF_VEHICLE_NO_VISIBLE, true);
			if(switchVehicle_no_visible_pref){
				for(int i=0;i<alertManager.getNoVisibleVehicle_Alerts().length;i++){
					alertManager.noVisibleVehicle_Alerts[i].setState(true);
				}
	        }else{
	        	for(int i=0;i<alertManager.getNoVisibleVehicle_Alerts().length;i++){
					alertManager.noVisibleVehicle_Alerts[i].setState(false);
				}
	        }  
        }
		if (key.equals(KEY_PREF_WORKS)) {
			switchWorks_pref = preferencias.getBoolean(KEY_PREF_WORKS, true);
			if(switchWorks_pref){
				for(int i=0;i<alertManager.getWorks_Alerts().length;i++){
					alertManager.works_Alerts[i].setState(true);
				}
	        }else{
	        	for(int i=0;i<alertManager.getWorks_Alerts().length;i++){
					alertManager.works_Alerts[i].setState(false);
				}
	        }  
        }

		if (key.equals(KEY_PREF_LIST_PREF)){
			maplistpref = preferencias.getString(KEY_PREF_LIST_PREF,"NORMAL");
			mapa.changeMapView(maplistpref);
		}
		mapa.addMarkersToMap(); // dibuja en el mapa las alertas activadas
	}


//@Override
/*public void onInit(int status) {

	// TODO Auto-generated method stub
	mTts.setLanguage(Locale.US);

	String myText1 = "Wellcome to the broadcar application";
	mTts.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
}
*/

public void HeavyTraffic() {


	String myText1 = "Heavy traffic";
	mTts.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
}


}
