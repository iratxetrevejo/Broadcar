
/*********************************************************************
**																	**
** MODULES USED 													**
** 																	**
**********************************************************************/
package broad.car.broadcar.bluetooth;

import java.io.IOException;
import broad.car.broadcar.MainActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;
import broad.car.broadcar.alerts.AlertManager;
import broad.car.broadcar.bluetooth.DeviceListActivity;
import broad.car.broadcar.tts.AndroidTextToSpeech;


/** @addtogroup Broadcar
*
* @{

* @file Manage_BT_Comunication
* @brief Esta clase se encarga de manejar el estado del bluetooth y 
* de crear la conexión
*
* @par VERSION HISTORY
* Version : v0.0
* Date : 30/01/2013
* Revised by : BroadCar team
* Description : Original version.
*
* @}
*/
public class Manage_BT_Comunication {
	/*********************************************************************
	** 																	**
	** IMPORTED CLASSES / Declarations  								**
	** 																	**
	**********************************************************************/
	//Main Activity class
	MainActivity main; 
	//Adaptador para el manejo del bluetood
	BluetoothAdapter BluetoothAdapter; //
	// Member object for the chat services;hilo para la comunicacion buetooth
	BluetoothChatService ChatService;
	// Gestor de las alertas
	AlertManager alertManager;
	AndroidTextToSpeech ttspeech;
	//String que almacena el mensaje recibido
	String BT_in_message="";
	
	SharedPreferences preferencesButtons;
	/*********************************************************************
	** 																	**
	** GLOBAL VARIABLES 												**
	** 																	**
	**********************************************************************/
	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	
	String[] lista;
	String[] lat;
	String[] lon;
	TextToSpeech speech;
	int queue;
	
	public static final String KEY_PREF_HEAVY_TRAFFIC="heavy_traffic_pref";
	public static final String KEY_PREF_LOW_VISIBILITY="low_visibility_pref";
	public static final String KEY_PREF_ROAD_STATE="road_state_pref";
	public static final String KEY_PREF_CRASHES="crashes_pref";
	public static final String KEY_PREF_WORKS="work_pref";	
	public static final String KEY_PREF_VEHICLE_NO_VISIBLE="vehicle_no_visible_pref";
	
	/*********************************************************************
	** 																	**
	** CONSTRUCTOR      												**
	** 																	*
	 * @param mTts 
	 * @param queueFlush 
	 * @param tts 
	 * @param preferencias 
	 * @param  
	 * @param alertManager *
	**********************************************************************/
	//public Manage_BT_Comunication(MainActivity mainActivity, BluetoothAdapter mBluetoothAdapter,AlertManager alertManager1) {

		public Manage_BT_Comunication(MainActivity mainActivity, BluetoothAdapter mBluetoothAdapter,AlertManager alertManager1, TextToSpeech mTts, int queueFlush, AndroidTextToSpeech tts, SharedPreferences preferencias) {
		//inicializar 
		main=mainActivity;
		BluetoothAdapter=mBluetoothAdapter;
		alertManager=alertManager1;
		speech=mTts;
		queue=queueFlush;
		ttspeech=tts;
		preferencesButtons=preferencias;
	}


   /***********************************************************************
   * @param mainActivity 
   * @brief  Comprueba el estado del bluetooth <conectado o desconectado>
   *  y si esta desactivado, muestra un mensaje
   ************************************************************************/

		public void check_BluetoothStatus() {
			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();    
		    if (!mBluetoothAdapter.isEnabled()) {
	         	//mensaje emergente
		    	Toast.makeText( main, "The Bluetooth is not activated", Toast.LENGTH_SHORT).show();
		    }		
		}
		
		
	/****************************************************************
	* @return 
	 * @brief Pone el dispositivo del usuario visible para el resto
	************************************************************/	
	public Intent set_bt_discoverable() {
			/* Pone visible el dispositivo visible a otros 
			 sin ningun limite de tiempo*/
	        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
	        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);//el valor  significa que siempre este activado, sin un tiempo determinado
	        return discoverableIntent;	
	        
	
	}
	
	
	public void turnOffBT(android.bluetooth.BluetoothAdapter mBluetoothAdapter){
  	    mBluetoothAdapter.disable();

	}
	
	
	
   /********************************************************
   * @return serverIntent
   * @brief Encargada de mostrar los dispositivos
   * disponibles para la conexion bluetooth
   ********************************************************/	
		
	public Intent show_Discoverable_devices() {
		//Obtiene una lista con los dispositibos disponibles a conectar
		Intent serverIntent = null;
	    serverIntent = new Intent(main, DeviceListActivity.class);		
	    return serverIntent;
	}
		
	/*****************************************************************************
	* @brief  The Handler that gets information back from the BluetoothChatService
	******************************************************************************/	

    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                    //setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                    //mConversationArrayAdapter.clear();
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                   // setStatus(R.string.title_connecting);
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                  //  setStatus(R.string.title_not_connected);
                    break;
                }
                break;
      
            case MESSAGE_READ://leer el mensaje recibido bia bluetooth
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                //FUNCION PARA CAMBIAR LAS CARACTERISTICAS DE LAS ALERTAS.
                try {
					btListenerAlertChange(readMessage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                break;
            }
        }
 
    };
		

  /**
   * @name	btListenerAlertChange
   * @brief	Funcion que almacena en un buffer toda la informacion que llega del Bluetooth.
   * @par	updateMap 
   *	 		- Recoje lo recibido por bluetooth y lo almacena en un array.
   * @author Iratxe Trevejo
   * @author Ibon Ortega
 * @throws IOException 
 * @throws InterruptedException 
   * @date 	2013-02-26 
   */
	  public void btListenerAlertChange(String readMessage) throws IOException, InterruptedException {
		 
		 String temp="";
		 String mensajeLimpio = "";//sin espacios
		 char caracter;
		 int cont_spaces=0;
		 
		 for (int i=0;i<readMessage.length();i++){
				caracter = readMessage.charAt(i);
				if (caracter!=' '){	
					mensajeLimpio=mensajeLimpio.concat(Character.toString(caracter));				
				}
				if (caracter==' '){	//para comprobar la cantidad de espacios. Puede que un mensaje solo sean espacios
					cont_spaces++;				
				}
		 }
		 if(cont_spaces==readMessage.length()){//si el mensaje son todo espacios no se sige, muestra un dialog
		        Toast.makeText(main.getApplicationContext(), "TODO ESPACIOS", Toast.LENGTH_SHORT).show();
		 }else{//el mensaje contiene algo mas aparte de espacios
		 char finalChar=mensajeLimpio.charAt(mensajeLimpio.length()-1);

		  if(finalChar!='$'){
			  BT_in_message=mensajeLimpio.concat(mensajeLimpio);
			  //concat Alerta
		  }else{
			  //concat Alerta
			  BT_in_message=BT_in_message.concat(mensajeLimpio);
			  temp=BT_in_message.substring(0, BT_in_message.length()-1);	
              Toast.makeText(main.getApplicationContext(), readMessage, Toast.LENGTH_SHORT).show();
			  alert_Info_Update(temp,readMessage);
			  BT_in_message="";

		  }
		  }
	  }
	  /**
	   * @name	alert_bufferReader
	   * @brief	Funcion actualiza la informacion que se recibe en el buffer de entrada BT.
	   * @par	updateMap 
	   *	 		- Recoje el mensaje del buffer.
	   *			- Divide sus componentes. 
	   *			- Calcula la lat y lon.
	   *			- Actualiza los datos.
	   * @author Iratxe Trevejo
	   * @author Ibon Ortega
	 * @throws IOException 
	 * @throws InterruptedException 
	   * @date 	2013-02-26 
	   */
	public void alert_Info_Update(String readMessage,String readMessageCompleto) throws IOException, InterruptedException{
		//NORTE/SUR =0/1 (mirar +/-) 
		//este/oeste =2/3 (mirar +/-) 
		//los grados igual + minutos/60 + segundos/3600
	  // Creamos unos string donde almacenaremos los trozos separados.
		
		init_arrays();		
		Double dlat=0.0,dlon=0.0;
		int cont_barras;
		
		String cadenaEntrante = readMessage;		
	
		cont_barras=check_tamTrama(cadenaEntrante);//comprobar si hay 4 o 5 barras, si las hay esta bien, sino el mensaje es erroneo
		
		if (cont_barras!=4 && cont_barras!=5){
			Toast.makeText(main.getApplicationContext(), "Mensaje erroneo", Toast.LENGTH_SHORT).show();
		}else{						
			lista = cadenaEntrante.split("/");//Separamos el string grande que se recibe.
			
			int contTamLat=0;			
			contTamLat = check_latLong(lista);// comprobar si en la latitud hay cuatro caracteres, si no esta mal mensaje erroneo
						
			if (contTamLat!=3){
		        Toast.makeText(main.getApplicationContext(), "Mensaje erroneo-latitud incorrecta", Toast.LENGTH_SHORT).show();
			}else{				
				lat = lista[1].split("\\.");
				
				int contTamLon=0;//comprobar si la longitud tiene el tamaño correcto		
				contTamLon = check_lonLong(lista);						
				if (contTamLon!=3){
			        Toast.makeText(main.getApplicationContext(), "Mensaje erroneo-longitud incorrecta", Toast.LENGTH_SHORT).show();
				}else{
					lon = lista[2].split("\\.");//Separamos los string mas pequeños (Lat y Long)
					//convertimos los grados, minutos y segundos a grados para que google maps los interprete
					dlat=Double.parseDouble(lat[1])+(Double.parseDouble(lat[2])/60)+(Double.parseDouble(lat[3])/3600);
					dlon=Double.parseDouble(lon[1])+(Double.parseDouble(lon[2])/60)+(Double.parseDouble(lon[3])/3600);
					//Si el primer numero es 1 o 3 la lat y lon seran negativas.
					if(lat[0].equals("1")){
						dlat=0-dlat;
					}
					if(lon[0].equals("3")){
						dlon=0-dlon;
					}
					all_Alerts_Update(lista,dlat,dlon);	//Actualizamos el la informacion de los arrays.
				}
			}			
		}		
	}
	/**********************************************************************
	 * @brief  Funcion que se encarga de inicializar los array que guardarán el mensaje
	 * 			entrante , la longitud y la latitud 
	 * @param 
	 * @return
	 * @TODO 
	**********************************************************************/
	public void init_arrays(){
		
		lista= new String[6];
		for(int i=0;i<lista.length;i++){
			lista[i]=new String();
		}
		
		lat=new String[4];
		for(int i=0;i<lat.length;i++){
			lat[i]=new String();
		}
		lon=new String[4];
		for(int i=0;i<lon.length;i++){
			lon[i]=new String();
		}
	}
	/**********************************************************************
	 * @brief  Esta funcion se encarga de comprobar que la longitud que se
	 * 			ha recibido para la alerta tiene el tamaño adecuado.
	 * 			La longitud seran cuatro numeros divididos por 3 puntos. Ejem: 5.36.5.4
	 * 			Si no tiene tres puntos sera un longitud incorrecta 
	 * @par	   Logica 
	 * 		    - recorre el dato de la longitud y calcula el numero de puntos que tiene.	 
	 * @param   String[] lista- contiene el mensaje recibido, y en la posicion 2 concretamente
	 * 			el dato para la longitud
	 * @return cont_lista2- el numero de puntos que tiene el dato de la longitud
	 * @TODO 
	**********************************************************************/
	public int  check_lonLong(String[] lista){
		int cont_lista2=0;
		String listaLon = lista[2];
		char caracterLon;

		for (int i=0;i<listaLon.length();i++){
			caracterLon=listaLon.charAt(i);
			if (caracterLon=='.'){
				cont_lista2++;
			}
		}
		
		return cont_lista2;
	}
	
	
	/**********************************************************************
	 * @brief  Esta funcion se encarga de comprobar que la latitud que se
	 * 			ha recibido para la alerta tiene el tamaño adecuado.
	 * 			La latitud seran cuatro numeros divididos por 3 puntos. Ejem: 5.36.5.4
	 * 			Si no tiene tres puntos sera una latitud incorrecta 
	 * @par	   Logica 
	 * 		    - recorre el dato de la latitud y calcula el numero de puntos que tiene.	 
	 * @param   String[] lista- contiene el mensaje recibido, y en la posicion 1 concretamente
	 * 			el dato para la latitud
	 * @return cont_lista1- el numero de puntos que tiene el dato de la latitud
	 * @TODO 
	**********************************************************************/
	public int check_latLong(String[] lista){
		
		int cont_lista1=0;
		String listaLat = lista[1];
		char caracter;

		for (int i=0;i<listaLat.length();i++){
			caracter=listaLat.charAt(i);
			if (caracter=='.'){
				cont_lista1++;
			}
		}
		return cont_lista1;
	}
	
	
	/**********************************************************************
	 * @brief  Esta funcion se encarga de calcular la cantidad de barras que tiene el
	 * 			mensaje que recibe.Para que el mensaje sea correcto tiene que tener 4 o 5 barras.
	 * @par	   Logica 
	 * 		    - recorre el mensaje entrante caracter a caracter	 
	 * @param   String cadenaEntrante- el mensaje entrante por el bluetooth
	 * @return contador_barras-el numero de barras que tiene el mensaje
	 * @TODO 
	**********************************************************************/
	
	
	public int check_tamTrama(String cadenaEntrante){
		char finalChar;
		int contador_barras=0;

		for (int i=0;i<(cadenaEntrante.length());i++){
			finalChar=cadenaEntrante.charAt(i);
			if (finalChar=='/'){
				contador_barras++;
			}			
		}
	return contador_barras;
	}
	
	
	  /**
	   * @name	noVisibleVehicle_Alerts
	   * @brief	Funcion para dibujar los marcadores en el mapa.
	   * @par	updateMap 
	   *	 		- Actualiza todos los arrays noVisibleVehicle_Alerts.
	   * @author Iratxe Trevejo
	   * @author Ibon Ortega
	 * @throws IOException 
	 * @throws InterruptedException 
	   * @date 	2013-02-26 
	   */
	public void all_Alerts_Update(String[] lista,Double dlat, Double dlon) throws IOException, InterruptedException
	{
		//Cambiamos la ultima alerta de trafico denso
		heavytraffic_Alerts_markUpdate(lista,dlat,dlon);		
		//Cambiamos la ultima alerta de Vehiculo no visible
		noVisibleVehicle_Alerts_Update(lista,dlat,dlon);
		//Cambiamos la ultima alerta de obras
		works_Alerts_Update(lista,dlat,dlon);
		//Cambiamos la ultima alerta de accidente en carretera
		crashes_Alerts_Update(lista,dlat,dlon);
		//Cambiamos la ultima alerta de poca visibilidad	
		lowVisibility_Alerts_Update(lista,dlat,dlon);
		//Cambiamos la ultima alerta del estado de la carretera
		roadstate_Alerts_Update(lista,dlat,dlon);
	}
	  /**
	   * @name	noVisibleVehicle_Alerts
	   * @brief	Funcion para actualizar la informacion del array.
	   * @par	updateMap 
	   *	 		- Actualiza los marcadores del array de heavytraffic_Alerts.
	   * @author Iratxe Trevejo
	   * @author Ibon Ortega
	 * @throws IOException 
	 * @throws InterruptedException 
	   * @date 	2013-02-26 
	   */			
	public void heavytraffic_Alerts_markUpdate(String[] lista,Double dlat, Double dlon) throws IOException, InterruptedException {
		
		//Cambiamos la ultima alerta de trafico denso
		if(lista[0].equals("0")){
			boolean state = preferencesButtons.getBoolean(KEY_PREF_HEAVY_TRAFFIC, true);
			if (state==true){
				ttspeech.HeavyTraffic(dlat, dlon);			
			}

			if(lista[3].equals("1")){
				alertManager.heavytraffic_Alerts[alertManager.getposHeavyTraffic()].setDirection(true);
			}else{
				alertManager.heavytraffic_Alerts[alertManager.getposHeavyTraffic()].setDirection(false);
			}
			alertManager.heavytraffic_Alerts[alertManager.getposHeavyTraffic()].setSpeed(Integer.parseInt(lista[4]));
			alertManager.heavytraffic_Alerts[alertManager.getposHeavyTraffic()].setLat(dlat);
			alertManager.heavytraffic_Alerts[alertManager.getposHeavyTraffic()].setLon(dlon);
			alertManager.heavytraffic_Alerts[alertManager.getposHeavyTraffic()].setShow(true);
			alertManager.addposHeavyTraffic();	
		}	
	}
	  /**
	   * @name	noVisibleVehicle_Alerts
	   * @brief	Funcion para actualizar la informacion del array.
	   * @par	updateMap 
	   *	 		- Actualiza los marcadores del array de noVisibleVehicle_Alerts.
	   * @author Iratxe Trevejo
	   * @author Ibon Ortega
	 * @throws IOException 
	   * @date 	2013-02-26 
	   */			
	public void noVisibleVehicle_Alerts_Update(String[] lista,Double dlat, Double dlon) throws IOException {
		if(lista[0].equals("2")){
			boolean state = preferencesButtons.getBoolean(KEY_PREF_VEHICLE_NO_VISIBLE, true);
			if (state==true){
				ttspeech.VehicleNoVisible(dlat, dlon);
			}
			if(lista[3].equals("1")){
				alertManager.noVisibleVehicle_Alerts[alertManager.getposNoVisibleVehicle()].setDirection(true);
			}else{
				alertManager.noVisibleVehicle_Alerts[alertManager.getposNoVisibleVehicle()].setDirection(false);
			}
			alertManager.noVisibleVehicle_Alerts[alertManager.getposNoVisibleVehicle()].setSpeed(Integer.parseInt(lista[4]));
			alertManager.noVisibleVehicle_Alerts[alertManager.getposNoVisibleVehicle()].setLat(dlat);
			alertManager.noVisibleVehicle_Alerts[alertManager.getposNoVisibleVehicle()].setLon(dlon);
			alertManager.noVisibleVehicle_Alerts[alertManager.getposNoVisibleVehicle()].setShow(true);
			alertManager.addposNoVisibleVehicle();
		}
	}
	  /**
	   * @name	noVisibleVehicle_Alerts
	   * @brief	Funcion para actualizar la informacion del array.
	   * @par	updateMap 
	   *	 		- Actualiza los marcadores del array de works_Alerts.
	   * @author Iratxe Trevejo
	   * @author Ibon Ortega
	 * @throws IOException 
	   * @date 	2013-02-26 
	   */
	public void works_Alerts_Update(String[] lista,Double dlat, Double dlon) throws IOException {
		if(lista[0].equals("1")){ 
			boolean state = preferencesButtons.getBoolean(KEY_PREF_WORKS, true);
			if (state==true){
				ttspeech.WorksOnRoad(dlat, dlon);
			}
			if(lista[3].equals("1")){
				alertManager.works_Alerts[alertManager.getposWorks()].setDirection(true);
			}else{
				alertManager.works_Alerts[alertManager.getposWorks()].setDirection(false);
			}
			if(lista[4].equals("1")){
				alertManager.works_Alerts[alertManager.getposWorks()].setClosedRoad(true);
			}else{
				alertManager.works_Alerts[alertManager.getposWorks()].setClosedRoad(false);
			}
			alertManager.works_Alerts[alertManager.getposWorks()].setLat(dlat);
			alertManager.works_Alerts[alertManager.getposWorks()].setLon(dlon);
			alertManager.works_Alerts[alertManager.getposWorks()].setShow(true);
			alertManager.addposWorks();
		}
	
	}
	  /**
	   * @name	noVisibleVehicle_Alerts
	   * @brief	Funcion para actualizar la informacion del array.
	   * @par	updateMap 
	   *	 		- Actualiza los marcadores del array de crashes_Alerts.
	   * @author Iratxe Trevejo
	   * @author Ibon Ortega
	 * @throws IOException 
	   * @date 	2013-02-26 
	   */	
	public void crashes_Alerts_Update(String[] lista,Double dlat, Double dlon) throws IOException {
		if(lista[0].equals("5")){ 
			boolean state = preferencesButtons.getBoolean(KEY_PREF_CRASHES, true);
			if (state==true){
				ttspeech.Crashes(dlat, dlon);
			}
			if(lista[3].equals("1")){
				alertManager.crashes_Alerts[alertManager.getposCrashes()].setDirection(true);
			}else{
				alertManager.crashes_Alerts[alertManager.getposCrashes()].setDirection(false);
			}
			if(lista[4].equals("1")){
				alertManager.crashes_Alerts[alertManager.getposCrashes()].setClosedRoad(true);
			}else{
				alertManager.crashes_Alerts[alertManager.getposCrashes()].setClosedRoad(false);
			}
			alertManager.crashes_Alerts[alertManager.getposCrashes()].setLat(dlat);
			alertManager.crashes_Alerts[alertManager.getposCrashes()].setLon(dlon);
			alertManager.crashes_Alerts[alertManager.getposCrashes()].setShow(true);
			alertManager.addPosCrashes();
		}   
	
	}
	  /**
	   * @name	noVisibleVehicle_Alerts
	   * @brief	Funcion para actualizar la informacion del array.
	   * @par	updateMap 
	   *	 		- Actualiza los marcadores del array de lowVisibility_Alerts.
	   * @author Iratxe Trevejo
	   * @author Ibon Ortega
	   * @throws IOException 
	   * @date 	2013-02-26 
	   */
	public void lowVisibility_Alerts_Update(String[] lista,Double dlat, Double dlon) throws IOException {
		if(lista[0].equals("3")){  
			boolean state = preferencesButtons.getBoolean(KEY_PREF_LOW_VISIBILITY, true);
			if (state==true){
				ttspeech.LowVisibility(dlat, dlon);
			}
			alertManager.lowVisibility_Alerts[Integer.parseInt(lista[3])][alertManager.getposLowVisibility(Integer.parseInt(lista[3]))].setSeverity(Integer.parseInt(lista[4]));
			alertManager.lowVisibility_Alerts[Integer.parseInt(lista[3])][alertManager.getposLowVisibility(Integer.parseInt(lista[3]))].setLat(dlat);
			alertManager.lowVisibility_Alerts[Integer.parseInt(lista[3])][alertManager.getposLowVisibility(Integer.parseInt(lista[3]))].setLon(dlon);
			alertManager.lowVisibility_Alerts[Integer.parseInt(lista[3])][alertManager.getposLowVisibility(Integer.parseInt(lista[3]))].setShow(true);
			alertManager.addposLowVisibility(Integer.parseInt(lista[3]));
		}
	
	}
	  /**
	   * @name	noVisibleVehicle_Alerts
	   * @brief	Funcion para actualizar la informacion del array.
	   * @par	updateMap 
	   *	 		- Actualiza los marcadores del array de roadstate_Alerts.
	   * @author Iratxe Trevejo
	   * @author Ibon Ortega
	 * @throws IOException 
	   * @date 	2013-02-26 
	   */	
	public void roadstate_Alerts_Update(String[] lista,Double dlat, Double dlon) throws IOException {
		if(lista[0].equals("4")){
			boolean state = preferencesButtons.getBoolean(KEY_PREF_ROAD_STATE, true);
			if (state==true){
				ttspeech.Roadstate(dlat, dlon);
			}
			if(lista[4].equals("1")){
				alertManager.roadstate_Alerts[Integer.parseInt(lista[3])][alertManager.getposRoadState(Integer.parseInt(lista[3]))].setDirection(true);
			}else{
				alertManager.roadstate_Alerts[Integer.parseInt(lista[3])][alertManager.getposRoadState(Integer.parseInt(lista[3]))].setDirection(false);
			}
			alertManager.roadstate_Alerts[Integer.parseInt(lista[3])][alertManager.getposRoadState(Integer.parseInt(lista[3]))].setSeverity(Integer.parseInt(lista[5]));
			alertManager.roadstate_Alerts[Integer.parseInt(lista[3])][alertManager.getposRoadState(Integer.parseInt(lista[3]))].setLat(dlat);
			alertManager.roadstate_Alerts[Integer.parseInt(lista[3])][alertManager.getposRoadState(Integer.parseInt(lista[3]))].setLon(dlon);
			alertManager.roadstate_Alerts[Integer.parseInt(lista[3])][alertManager.getposRoadState(Integer.parseInt(lista[3]))].setShow(true);
			alertManager.addposRoadState(Integer.parseInt(lista[3]));
		}	
	
	}
		
/*********************************************************************
* @return mHandler
* @brief  FUunción que devuelve el mHander
**********************************************************************/	    
    public Handler getHandler(){
    	return mHandler;
    }
   
	  
/*************************************************************
* @param mBluetoothAdapter 
* @brief  Conecta dispositivos
**************************************************************/	
    public void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = BluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        ChatService.connect(device, secure);
    }

    
    
/*************************************************************
* @param mChatService
* @brief  Conecta dispositivos
**************************************************************/	
	public void setChatService(BluetoothChatService mChatService) {
		ChatService=mChatService;			
	}

/*************************************************************
* @param mainActivity
* @brief  Crea el objeto BluetoothChatService
**************************************************************/	
	
   public void createBluetoothChatService(MainActivity mainActivity) {
		if (BluetoothAdapter.isEnabled() && ChatService == null) {
        	// Initialize the BluetoothChatService to perform bluetooth connections
			ChatService = new BluetoothChatService(mainActivity, mHandler);
        }			
	}
	    
}
