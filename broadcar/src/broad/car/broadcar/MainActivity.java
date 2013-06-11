
package broad.car.broadcar;


/*********************************************************************
 **																	**
 ** MODULES USED 													**
 ** 																**
 *********************************************************************/

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import broad.car.broadcar.alerts.AlertManager;
import broad.car.broadcar.bluetooth.BluetoothChatService;
import broad.car.broadcar.bluetooth.Manage_BT_Comunication;
import broad.car.broadcar.gps.gps;
import broad.car.broadcar.map.LocationAddress;
import broad.car.broadcar.map.MapGoogle;
import broad.car.broadcar.map.markers;
import broad.car.broadcar.speech.service.SpeechActivationService;
import broad.car.broadcar.tts.AndroidTextToSpeech;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;


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
	 ** 																**
	 ** IMPORTED CLASSES / Declarations  								**
	 ** 																**
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
	MapGoogle mapa;
	//Clase encargada de la configuracion del gps	
	gps gps;
	//calcula la direccion en la que a ocurrido la alerta
	LocationAddress alertAddress;
	//la voz
	AndroidTextToSpeech ttSpeech;
	//Los marcadores
	markers marker;



	/*********************************************************************
	 ** 																**
	 ** GLOBAL VARIABLES 												**
	 ** 																**
	 **********************************************************************/
	//Indicador para determinar una conexión segura
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	
	//Variables encargadas de recoger el estado de las alertas (true/false)
	public boolean switchHeavy_traffic_pref;
	public boolean switchLow_visibility_pref;
	public boolean switchRoad_state_pref;
	public boolean switchCrashes_pref;
	public boolean switchWorks_pref;
	public boolean switchVehicle_no_visible_pref;
	public boolean switchSpeech_recog_pref;
	//string recogidos de R.strings con el codigo para cada alerta
	private String KEY_PREF_HEAVY_TRAFFIC;
	private String KEY_PREF_LOW_VISIBILITY;
	private String KEY_PREF_ROAD_STATE;
	private String KEY_PREF_CRASHES;
	private String KEY_PREF_WORKS;
	private String KEY_PREF_VEHICLE_NO_VISIBLE;
	private String KEY_PREF_SPEECH_RECOG;
	private String KEY_PREF_LIST_PREF;
	private String maplistpref;

	boolean activado;

	Intent recog;
	SpeechActivationService speechrecog;
	/*********************************************************************
	 ** 																**
	 ** LOCAL FUNCTIONS 												**
	 ** 																**
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
		speechrecog =new SpeechActivationService();
		recog= new Intent (MainActivity.this,speechrecog.getClass());
		
		//PREFERENCIAS
		KEY_PREF_LIST_PREF= getResources().getText(R.string.KEY_PREF_LIST_PREF).toString();

		//Obtiene el adaptador del bluetooth
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    mBluetoothAdapter.disable();
	    
		//Crea los objetos de las clases AlertManager y googleMap
		alertManager=new AlertManager();
		mapa=new MapGoogle();
		//start de getLocation class-Get the address of a lon-alt
		alertAddress=new LocationAddress(this.getApplicationContext());

		//START THE TTS
		ttSpeech= new AndroidTextToSpeech(alertAddress);
		ttSpeech.start(this.getApplicationContext());

		//related to the map
		// Getting reference to the SupportMapFragment of activity_main.xml		
		SupportMapFragment fragmentManager = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		// Getting LocationManager object from System Service LOCATION_SERVICE
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		marker=new markers(fragmentManager.getMap(),alertManager);

		//inicializa la localización del gps en el mapa
		mapa.init_location(fragmentManager,locationManager,alertManager,marker);

		//Se inicializan los estados de las alertas( default:true)
		initPreferences();	
		
		//Inicia el BT 
	    if (!mBluetoothAdapter.isEnabled()) { 
	    	Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); 
			startActivityForResult(enableBtIntent, 3);
	    }else{
	    	manage_BT = new Manage_BT_Comunication(this,mBluetoothAdapter,alertManager,ttSpeech,preferencias,marker);
			//se comprueba el estado del bluetooth (on/off)
			manage_BT.check_BluetoothStatus();	
			//pasa el mChatChatService a la case Manage_BT_Comunication
			manage_BT.setChatService(mChatService);
			//pide al Manage_BT_Comunication que cree el BluetoothChatService
			manage_BT.createBluetoothChatService(this);
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
	protected void onDestroy(){
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

	private void initPreferences() {
		preferencias= PreferenceManager.getDefaultSharedPreferences(this);
		preferencias.registerOnSharedPreferenceChangeListener(this);
    	
		preferencias = getSharedPreferences("prefereces", Activity.MODE_PRIVATE);
		preferencias.registerOnSharedPreferenceChangeListener(this);
		//get the state of the alerts
		switchHeavy_traffic_pref = preferencias.getBoolean(KEY_PREF_HEAVY_TRAFFIC, true);
		switchLow_visibility_pref = preferencias.getBoolean(KEY_PREF_LOW_VISIBILITY, true);
		switchRoad_state_pref = preferencias.getBoolean(KEY_PREF_ROAD_STATE, true);
		switchCrashes_pref = preferencias.getBoolean(KEY_PREF_CRASHES, true);
		switchWorks_pref = preferencias.getBoolean(KEY_PREF_WORKS, true);     
		switchSpeech_recog_pref = preferencias.getBoolean(KEY_PREF_SPEECH_RECOG, true);   
		//inicializa las variables con el string determinado en strings.xml
		KEY_PREF_HEAVY_TRAFFIC = getResources().getText(R.string.KEY_PREF_HEAVY_TRAFFIC).toString();
		KEY_PREF_LOW_VISIBILITY= getResources().getText(R.string.KEY_PREF_LOW_VISIBILITY).toString();
		KEY_PREF_ROAD_STATE= getResources().getText(R.string.KEY_PREF_ROAD_STATE).toString();
		KEY_PREF_CRASHES= getResources().getText(R.string.KEY_PREF_CRASHES).toString();
		KEY_PREF_WORKS= getResources().getText(R.string.KEY_PREF_WORKS).toString();
		KEY_PREF_VEHICLE_NO_VISIBLE= getResources().getText(R.string.KEY_PREF_VEHICLE_NO_VISIBLE).toString();
		KEY_PREF_SPEECH_RECOG=getResources().getText(R.string.KEY_PREF_SPEECH_RECOG).toString();
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
		case 3:
			if (resultCode == Activity.RESULT_OK){
				manage_BT = new Manage_BT_Comunication(this,mBluetoothAdapter,alertManager,ttSpeech,preferencias,marker);
				//se comprueba el estado del bluetooth (on/off)
				manage_BT.check_BluetoothStatus();	
				//pasa el mChatChatService a la case Manage_BT_Comunication
				manage_BT.setChatService(mChatService);
				//pide al Manage_BT_Comunication que cree el BluetoothChatService
				manage_BT.createBluetoothChatService(this);
				break;
			}else {
				finish();
			}			
		}   
	}


	/**********************************************************************
	 * @brief  Encargada de gestionar las alertas
	 * @par	   Logica 
	 * 	 	    - Comprueba los switch de las alertas y si se activa la recepcion de una 
	 * 			   alerta, el estado de la misma alerta pasa a estar activada.
	 * 			- Dibuja en el mapa las alertas activadas
	 * @param  SharedPreferences sharedPreferences
	 * 	@param String key - alerta a activar
	 * @return
	 **********************************************************************/		  

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(KEY_PREF_HEAVY_TRAFFIC)) {
			alertManager.change_HeavyTraffic_alertState(preferencias,switchHeavy_traffic_pref, KEY_PREF_HEAVY_TRAFFIC);
		}
		if (key.equals(KEY_PREF_CRASHES)) {
			alertManager.change_Crashes_alertState(preferencias,switchCrashes_pref,KEY_PREF_CRASHES); 
		}
		if (key.equals(KEY_PREF_ROAD_STATE)) {
			alertManager.change_RoadState_alertState(preferencias,switchRoad_state_pref,KEY_PREF_ROAD_STATE);
		}
		if (key.equals(KEY_PREF_LOW_VISIBILITY)) {
			alertManager.change_LowVisibility_alertState(preferencias,switchLow_visibility_pref,KEY_PREF_LOW_VISIBILITY);
		}
		if (key.equals(KEY_PREF_VEHICLE_NO_VISIBLE)) {
			alertManager.change_VnoVisible_alertState(preferencias,switchVehicle_no_visible_pref,KEY_PREF_VEHICLE_NO_VISIBLE);
		}
		if (key.equals(KEY_PREF_WORKS)) {
			alertManager.change_works_alertState(preferencias,switchWorks_pref,KEY_PREF_WORKS);
		}
		if (key.equals(KEY_PREF_LIST_PREF)){
			maplistpref = preferencias.getString(KEY_PREF_LIST_PREF,"NORMAL");
			mapa.changeMapView(maplistpref);
		}
		if (key.equals(KEY_PREF_SPEECH_RECOG)) {
			if(!activado){
				startService(recog);
				activado=true;
			}else{
				stopService(recog);
				activado=false;
			}
		}
		marker.addMarkersToMap(); // dibuja en el mapa las alertas activadas
	}
	
}
