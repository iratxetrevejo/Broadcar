/*********************************************************************
**																	**
** MODULES USED 													**
** 																	**
**********************************************************************/
package broad.car.broadcar.alerts;

import android.content.SharedPreferences;


/** @addtogroup Broadcar
*
* @{

* @file AlertManager
* @brief Clase encargada de generar los arrays de las alertas
*
* @par VERSION HISTORY
* Version : v0.0
* Date : 30/01/2013
* Revised by : BroadCar team
* Description : Original version.
* @author  Iratxe Trevejo
* @author  Ibon Ortega
* @}
*/

public class AlertManager {
	/*********************************************************************
	** 																	**
	** IMPORTED CLASSES / Declarations  								**
	** 																	**
	**********************************************************************/
	public Crashes_Alerts crashes_Alerts[];
	public HeavyTraffic_Alerts heavytraffic_Alerts[];
	public LowVisibility_Alerts lowVisibility_Alerts[][];
	public Works_Alerts works_Alerts[];
	public NoVisibleVehicle_Alerts noVisibleVehicle_Alerts[];
	public RoadState_Alerts roadstate_Alerts[][];
	
	
	/*********************************************************************
	** 																	**
	** LOCAL VARIABLES 													**
	** 																	**
	**********************************************************************/
	private static final int MAX_ALERT=10;
	private static final int MAX_TYPEVisibility = 3;
	private static final int MAX_TYPERoadstate = 5;
	
	private int posCrashes=0;
	private int posHeavyTraffic=0;
	private int posLowVisibility[];
	private int posWorks=0;
	private int posNoVisibleVehicle=0;
	private int posRoadState[];
	

	/*********************************************************************
	** 																	**
	** GLOBAL FUNCTIONS 												**
	** 																	**
	**********************************************************************/
	
	/**********************************************************************
	 * @brief  Constructor de la clase AlertManager.java
	 * @par	   Logica 
	 * 		    -	Inializa las alertas del sistema 
	 * @param   
	 * @return
	 * @TODO 

	**********************************************************************/		

	public AlertManager(){
		
		heavytraffic_init();
		lowvisibility_init();
		crashes_init();
		novisiblevehicle_init();
		roadstate_init();
		works_init();	
	}
		
	
	
	/*********************************************************************
	** 																	**
	** LOCAL FUNCTIONS 													**
	** 																	**
	**********************************************************************/
		
	
	/**********************************************************************
	       Getters de los contadores de los arrays.	
	**********************************************************************/
	
	/**********************************************************************
	 * @brief  Devuelve la posicion en el array que se almacenan las alertas
	 * 		   de Crashes
	 * @param   
	 * @return posCrashes - posicion en el array
	 * @TODO 
	**********************************************************************/	
	public int getposCrashes(){
		return posCrashes;
	}
	/**********************************************************************
	 * @brief  Devuelve la posicion en el array que se almacenan las alertas
	 * 			de Heavy Traffic
	 * @param   
	 * @return posHeavyTraffic-posicion en el array
	 * @TODO 
	**********************************************************************/	
	public int getposHeavyTraffic(){
		return posHeavyTraffic;
	}
	/**********************************************************************
	 * @brief  Devuelve la posicion en el array que se almacenan las alertas 
	 * 			de low visibility
	 * @param   int type - tipo de visibilidad reducida
	 * @return 	posLowVisibility[type]-posicion en el array
	 * @TODO 
	**********************************************************************/	
	public int getposLowVisibility(int type){
		return posLowVisibility[type];
	}
	/**********************************************************************
	 * @brief  Devuelve la posicion en el array que se almacenan los las alertas
	 *   		de Works
	 * @param   
	 * @return posWorks- posicion en el array
	 * @TODO 
	**********************************************************************/	
	public int getposWorks(){
		return posWorks;
	}
	/**********************************************************************
	 * @brief  Devuelve la posicion en el array que se almacenan los las alertas
	 *   		de No Visible Vehicle
	 * @param   
	 * @return posNoVisibleVehicle- posicion en el array
	 * @TODO 
	**********************************************************************/	
	public int getposNoVisibleVehicle(){
		return posNoVisibleVehicle;
	}
	/**********************************************************************
	 * @brief  Devuelve la posicion en el array que se almacenan los las alertas
	 *   		de Road State
	 * @param   int type- Tipo de estado de carretera
	 * @return posRoadState[type]- posicion en el array
	 * @TODO 
	**********************************************************************/	
	public int getposRoadState(int type){
		return posRoadState[type];
	}
	/**********************************************************************
	   			Setters de los contadores de los arrays.
	**********************************************************************/
	/**********************************************************************
	 * @brief  Cambiar el valor del contador en el array de las alertas de 
	 * 			Crashes
	 * @param   
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setPosCrashes(int posCrashes){
		this.posCrashes=posCrashes;
	}
	
	
	/**********************************************************************
	 * @brief  Cambiar el valor del contador en el array de las alertas de 
	 * 			Heavy Traffic
	 * @param   
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setposHeavyTraffic(int posHeavyTraffic){
		this.posHeavyTraffic=posHeavyTraffic;
	}
	
	/**********************************************************************
	 * @brief  Cambiar el valor del contador en el array de las alertas de 
	 * 			Low visibility
	 * @param   
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setposLowVisibility(int posLowVisibility, int type){
		this.posLowVisibility[type]=posLowVisibility;
	}
	/**********************************************************************
	 * @brief  Cambiar el valor del contador en el array de las alertas de 
	 * 			works
	 * @param   
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setposWorks(int posWorks){
		this.posWorks=posWorks;
	}
	/**********************************************************************
	 * @brief  Cambiar el valor del contador en el array de las alertas de 
	 * 			no visible vehicle
	 * @param   
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setposNoVisibleVehicle(int posNoVisibleVehicle){
		this.posNoVisibleVehicle=posNoVisibleVehicle;
	}
	/**********************************************************************
	 * @brief  Cambiar el valor del contador en el array de las alertas de 
	 * 			road state
	 * @param   
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setposRoadState(int posRoadState, int type){
		this.posRoadState[type]=posRoadState;
	}
	
	
	/**********************************************************************
	 * 					  Add de las posiciones
	**********************************************************************/
	
	
	/**********************************************************************
	 * @brief  Añade uno mas al contador del array de las alertas de Crashes.
	 * 			Y lleva la cuenta de cual es el ultimo.
	 * @param   
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void addPosCrashes(){
		setPosCrashes(getposCrashes()+1);
		if(getposCrashes()==10){
			setPosCrashes(0);
		}
	}

	/**********************************************************************
	 * @brief  Añade uno mas al contador del array de las alertas de Heavy
	 * 			Traffic. Y lleva la cuenta de cual es el ultimo.
	 * @param   
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void addposHeavyTraffic(){
		setposHeavyTraffic(this.getposHeavyTraffic()+1);
		if(getposHeavyTraffic()==10){
			setposHeavyTraffic(0);
		}
	}
	/**********************************************************************
	 * @brief  Añade uno mas al contador del array de las alertas de Low
	 * 			visibility. Y lleva la cuenta de cual es el ultimo.
	 * @param   int type - tipo de visibilidad reducida
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void addposLowVisibility(int type){
		setposLowVisibility(getposLowVisibility(type)+1,type);
		if(getposLowVisibility(type)==10){
			setposLowVisibility(0,type);
		}
	}
	/**********************************************************************
	 * @brief  Añade uno mas al contador del array de las alertas de works
	 * 	        Y lleva la cuenta de cual es el ultimo.
	 * @param   
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void addposWorks(){
		setposWorks(getposWorks()+1);
		if(getposWorks()==10){
			setposWorks(0);
		}
	}
	
	/**********************************************************************
	 * @brief  Añade uno mas al contador del array de las alertas de no
	 * 	      visible vehicle. Y lleva la cuenta de cual es el ultimo.
	 * @param   
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void addposNoVisibleVehicle(){
		setposNoVisibleVehicle(this.getposNoVisibleVehicle()+1);
		if(getposNoVisibleVehicle()==10){
			setposNoVisibleVehicle(0);
		}
	}
	
	/**********************************************************************
	 * @brief  Añade uno mas al contador del array de las alertas de road
	 * 			state. Y lleva la cuenta de cual es el ultimo.
	 * @param   int type- tipo de road state
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void addposRoadState(int type){
		setposRoadState(getposRoadState(type)+1,type);
		if(getposRoadState(type)==10){
			setposRoadState(0,type);
		}
	}
	
	/**********************************************************************
	 * @brief  Getters de los arrays.
	**********************************************************************/
	/**********************************************************************
	 * @brief  Devuelve el array de Crashes completo
	 * @param   
	 * @return crashes_Alerts - array con las alertas de crashes
	 * @TODO 
	**********************************************************************/
	
	public Crashes_Alerts[] getCrashes_Alerts(){
		return crashes_Alerts;
	}
	
	/**********************************************************************
	 * @brief  Devuelve el array de heavy traffic completo
	 * @param   
	 * @return heavytraffic_Alerts - array con las alertas de heavy traffic
	 * @TODO 
	**********************************************************************/
	public HeavyTraffic_Alerts[] getHeavytraffic_alerts(){
		return heavytraffic_Alerts;
	}
	
	
	/**********************************************************************
	 * @brief  Devuelve el array de low visibility completo
	 * @param   
	 * @return lowVisibility_Alerts - array con las alertas de low visibility
	 * @TODO 
	**********************************************************************/
	public LowVisibility_Alerts[][] getLowVisibility_Alerts(){
		return lowVisibility_Alerts;
	}
	
	/**********************************************************************
	 * @brief  Devuelve el array de works completo
	 * @param   
	 * @return works_Alerts - array con las alertas de works
	 * @TODO 
	**********************************************************************/
	public Works_Alerts[] getWorks_Alerts(){
		return works_Alerts;
	}
	
	
	/**********************************************************************
	 * @brief  Devuelve el array de no visible vehicle completo
	 * @param   
	 * @return noVisibleVehicle_Alerts - array con las alertas de NoVisibleVehicl
	 * @TODO 
	**********************************************************************/
	public NoVisibleVehicle_Alerts[] getNoVisibleVehicle_Alerts(){
		return noVisibleVehicle_Alerts;
	}
	
	/**********************************************************************
	 * @brief  Devuelve el array de road state completo
	 * @param   
	 * @return roadstate_Alerts - array con las alertas de road state
	 * @TODO 
	**********************************************************************/
	public RoadState_Alerts[][] getRoadState_Alerts(){
		return roadstate_Alerts;
	}
	
	

	/**********************************************************************
	 * @brief  heavytraffic_init es la funcion que se ocupa de inicializar 
	 * las alertas correspondientes al trafico denso.
	 * @par   Logica:
	 * 			-Crea los arrays para las alertas y dentro de el crea las alertas en 
	 * 			 si 
	 * @return 
	 * @TODO 
	**********************************************************************/
	
	public void heavytraffic_init(){
		heavytraffic_Alerts=new HeavyTraffic_Alerts[MAX_ALERT];
		for(int i=0;i<heavytraffic_Alerts.length;i++){
			heavytraffic_Alerts[i]=new HeavyTraffic_Alerts();
			
		}
	}
	
	/**********************************************************************
	 * @brief  lowvisibility_init es la funcion que se ocupa de inicializar 
	 * las alertas correspondientes a la poca visibilidad
	 * @par   Logica:
	 * 			-Crea los arrays para las alertas y dentro de el crea las alertas en 
	 * 			si 
	 * @return 
	 * @TODO 
	**********************************************************************/

	public void lowvisibility_init(){
		lowVisibility_Alerts=new LowVisibility_Alerts[MAX_TYPEVisibility][MAX_ALERT]; //<--CAMBIAR PARA QUE SEA GENERICO EL 3
		posLowVisibility=new int[MAX_TYPEVisibility];
		for(int j=0;j<getLowVisibility_Alerts().length;j++){		
			posLowVisibility[j]=0;
			for(int i=0;i<lowVisibility_Alerts[j].length;i++){
				lowVisibility_Alerts[j][i]=new LowVisibility_Alerts();
				lowVisibility_Alerts[j][i].setIncidencetype(j);
				lowVisibility_Alerts[j][i].setId(i);			
			}
		}

	}
	
	/**********************************************************************
	 * @brief  works_init es la funcion que se ocupa de inicializar 
	 * las alertas correspondientes a las obras.
	 * @par   Logica:
	 * 			-Crea los arrays para las alertas y dentro de el crea las alertas en 
	 * 			si
	 * @return 
	 * @TODO 
	**********************************************************************/

	public void works_init(){
		works_Alerts=new Works_Alerts[MAX_ALERT];
		for(int i=0;i<works_Alerts.length;i++){
			works_Alerts[i]=new Works_Alerts();
		}
	}
	
	
	/**********************************************************************
	 * @brief  novisiblevehicle_init es la funcion que se ocupa de inicializar 
	 * las alertas correspondientes a las obras.
	 * @par   Logica:
	 * 			-Crea los arrays para las alertas y dentro de el crea las alertas en 
	 * 			si 
	 * @return 
	 * @TODO 
	**********************************************************************/

	public void novisiblevehicle_init(){
		noVisibleVehicle_Alerts=new NoVisibleVehicle_Alerts[MAX_ALERT];
		for(int i=0;i<noVisibleVehicle_Alerts.length;i++){
			noVisibleVehicle_Alerts[i]=new NoVisibleVehicle_Alerts();
		}
	}
	
	/**********************************************************************
	 * @brief  roadstate_init es la funcion que se ocupa de inicializar 
	 * las alertas correspondientes al estado de la carretera.
	 * @par   Logica:
	 * 			-Crea los arrays para las alertas y dentro de el crea las alertas en 
	 * 		si 
	 * @return 
	 * @TODO 
	**********************************************************************/

	public void roadstate_init(){
		roadstate_Alerts=new RoadState_Alerts[MAX_TYPERoadstate][MAX_ALERT];
		posRoadState=new int[MAX_TYPERoadstate];
		for(int j=0;j<getRoadState_Alerts().length;j++){
			posRoadState[j]=0;
			for(int i=0;i<roadstate_Alerts[j].length;i++){
				roadstate_Alerts[j][i]=new RoadState_Alerts();
				roadstate_Alerts[j][i].setIncidencetype(j);
				roadstate_Alerts[j][i].setId(i);			
			}
		}
	}

	
	/**********************************************************************
	 * @brief   crashes_init es la funcion que se ocupa de inicializar 
	 * las alertas correspondientes a los accidentes.
	 * @par   Logica:
	 * 			-Crea los arrays para las alertas y dentro de el crea las alertas en 
	 * 		si 
	 * @return 
	 * @TODO 
	**********************************************************************/
	public void crashes_init(){
		crashes_Alerts=new Crashes_Alerts[MAX_ALERT];
		for(int i=0;i<crashes_Alerts.length;i++){
			crashes_Alerts[i]=new Crashes_Alerts();

		}
	}
	
	/**********************************************************************
	 * @brief   Se encarga de activar el estado de las alertas del array correspondiente
	 * 			a heavy traffic. 
	 * @par   Logica:
	 * 			Comprueba si se ha pulsado activar o no, y de ser cierto utiliza la funcion
	 * 			setState para ponerla a true.
	 * 
	 * @param SharedPreferences preferencias- preferencias del menú
	 * @param boolean switchHeavy_traffic_pref - estado de la preferencia
	 * @param String keyPrefHeavyTraffic - string definido en string.xml con la key para la preferencia de heavy traffic
	 * @return 
	 * @TODO 
	**********************************************************************/
	public void change_HeavyTraffic_alertState(SharedPreferences preferencias, boolean switchHeavy_traffic_pref, String keyPrefHeavyTraffic){
		switchHeavy_traffic_pref = preferencias.getBoolean(keyPrefHeavyTraffic, true);
		if(switchHeavy_traffic_pref){
			for(int i=0;i<getHeavytraffic_alerts().length;i++){
				heavytraffic_Alerts[i].setState(true);
			}
        }else{
			for(int i=0;i<getHeavytraffic_alerts().length;i++){
				heavytraffic_Alerts[i].setState(false);
			}
        }  
	}
	
	/**********************************************************************
	 * @brief   Se encarga de activar el estado de las alertas del array correspondiente
	 * 			a crashes. 
	 * @par   Logica:
	 * 			Comprueba si se ha pulsado activar o no, y de ser cierto utiliza la funcion
	 * 			setState para ponerla a true.
	 * 
	 * @param SharedPreferences preferencias- preferencias del menú
	 * @param boolean switchCrashes_pref - estado de la preferencia
	 * @param String keyCrashes - string definido en string.xml con la key para la preferencia de crashes
	 * @return 
	 * @TODO 
	**********************************************************************/
	
	public void change_Crashes_alertState(SharedPreferences preferencias, boolean switchCrashes_pref, String keyCrashes){

	switchCrashes_pref = preferencias.getBoolean(keyCrashes, true);
	if(switchCrashes_pref){
		for(int i=0;i<getCrashes_Alerts().length;i++){
			crashes_Alerts[i].setState(true);
		}
    }else{
    	for(int i=0;i<getCrashes_Alerts().length;i++){
			crashes_Alerts[i].setState(false);
		}
    } 
	}


	/**********************************************************************
	 * @brief   Se encarga de activar el estado de las alertas del array correspondiente
	 * 			a road state. 
	 * @par   Logica:
	 * 			Comprueba si se ha pulsado activar o no, y de ser cierto utiliza la funcion
	 * 			setState para ponerla a true.
	 * 
	 * @param SharedPreferences preferencias- preferencias del menú
	 * @param boolean switchRoad_state_pref - estado de la preferencia
	 * @param String keyPrefRoadState - string definido en string.xml con la key para la preferencia de road state
	 * @return 
	 * @TODO 
	**********************************************************************/

	public void change_RoadState_alertState(SharedPreferences preferencias, boolean switchRoad_state_pref, String keyPrefRoadState) {
		switchRoad_state_pref = preferencias.getBoolean(keyPrefRoadState, true);
		if(switchRoad_state_pref){
			for(int j=0;j<getRoadState_Alerts().length;j++){
				for(int i=0;i<getRoadState_Alerts().length;i++){
					roadstate_Alerts[j][i].setState(true);
				}
			}

        }else{
        	for(int j=0;j<getRoadState_Alerts().length;j++){
	        	for(int i=0;i<getRoadState_Alerts().length;i++){
					roadstate_Alerts[j][i].setState(false);
				}
        	}  
        }		
	}


	/**********************************************************************
	 * @brief   Se encarga de activar el estado de las alertas del array correspondiente
	 * 			a low visibility. 
	 * @par   Logica:
	 * 			Comprueba si se ha pulsado activar o no, y de ser cierto utiliza la funcion
	 * 			setState para ponerla a true.
	 * 
	 * @param SharedPreferences preferencias- preferencias del menú
	 * @param boolean switchLow_visibility_pref - estado de la preferencia
	 * @param String keyPrefLowVisibility - string definido en string.xml con la key para la preferencia de low visibility
	 * @return 
	 * @TODO 
	**********************************************************************/

	public void change_LowVisibility_alertState(SharedPreferences preferencias, boolean switchLow_visibility_pref, String keyPrefLowVisibility) {
		switchLow_visibility_pref = preferencias.getBoolean(keyPrefLowVisibility, true);
		if(switchLow_visibility_pref){
			for(int j=0;j<getLowVisibility_Alerts().length;j++){
				for(int i=0;i<lowVisibility_Alerts[j].length;i++){
					lowVisibility_Alerts[j][i].setState(true);
				}	
			}
			
        }else{
        	for(int j=0;j<getLowVisibility_Alerts().length;j++){
	        	for(int i=0;i<lowVisibility_Alerts[j].length;i++){
					lowVisibility_Alerts[j][i].setState(false);
				}
        	}
        }  		
	}


	/**********************************************************************
	 * @brief   Se encarga de activar el estado de las alertas del array correspondiente
	 * 			a vehicle no visible 
	 * @par   Logica:
	 * 			Comprueba si se ha pulsado activar o no, y de ser cierto utiliza la funcion
	 * 			setState para ponerla a true.
	 * 
	 * @param SharedPreferences preferencias- preferencias del menú
	 * @param boolean switchVehicle_no_visible_pref - estado de la preferencia
	 * @param String keyPrefVehicleNoVisible - string definido en string.xml con la key para la preferencia de vehicle no visible
	 * @return 
	 * @TODO 
	**********************************************************************/


	public void change_VnoVisible_alertState(SharedPreferences preferencias, boolean switchVehicle_no_visible_pref, String keyPrefVehicleNoVisible) {
		switchVehicle_no_visible_pref = preferencias.getBoolean(keyPrefVehicleNoVisible, true);
		if(switchVehicle_no_visible_pref){
			for(int i=0;i<getNoVisibleVehicle_Alerts().length;i++){
				noVisibleVehicle_Alerts[i].setState(true);
			}
        }else{
        	for(int i=0;i<getNoVisibleVehicle_Alerts().length;i++){
				noVisibleVehicle_Alerts[i].setState(false);
			}
        }  		
	}


	/**********************************************************************
	 * @brief   Se encarga de activar el estado de las alertas del array correspondiente
	 * 			a works 
	 * @par   Logica:
	 * 			Comprueba si se ha pulsado activar o no, y de ser cierto utiliza la funcion
	 * 			setState para ponerla a true.
	 * 
	 * @param SharedPreferences preferencias- preferencias del menú
	 * @param boolean switchWorks_pref - estado de la preferencia
	 * @param String keyPrefWorks - string definido en string.xml con la key para la preferencia de works
	 * @return 
	 * @TODO 
	**********************************************************************/

	public void change_works_alertState(SharedPreferences preferencias, boolean switchWorks_pref, String keyPrefWorks) {
		switchWorks_pref = preferencias.getBoolean(keyPrefWorks, true);
		if(switchWorks_pref){
			for(int i=0;i<getWorks_Alerts().length;i++){
				works_Alerts[i].setState(true);
			}
        }else{
        	for(int i=0;i<getWorks_Alerts().length;i++){
				works_Alerts[i].setState(false);
			}
        }  		
	}
}
