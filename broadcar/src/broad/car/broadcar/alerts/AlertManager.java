/*********************************************************************
**																	**
** MODULES USED 													**
** 																	**
**********************************************************************/
package broad.car.broadcar.alerts;


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
	
	private static int posCrashes=0;
	private static int posHeavyTraffic=0;
	private static int posLowVisibility[];
	private static int posWorks=0;
	private static int posNoVisibleVehicle=0;
	private static int posRoadState[];
	

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
		crashes_inti();
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
	 * @brief   crashes_inti es la funcion que se ocupa de inicializar 
	 * las alertas correspondientes a los accidentes.
	 * @par   Logica:
	 * 			-Crea los arrays para las alertas y dentro de el crea las alertas en 
	 * 		si 
	 * @return 
	 * @TODO 
	**********************************************************************/
	public void crashes_inti(){
		crashes_Alerts=new Crashes_Alerts[MAX_ALERT];
		for(int i=0;i<crashes_Alerts.length;i++){
			crashes_Alerts[i]=new Crashes_Alerts();

		}
	}
}
