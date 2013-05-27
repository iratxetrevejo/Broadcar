/*********************************************************************
**																	**
** MODULES USED 													**
** 																	**
**********************************************************************/
package broad.car.broadcar.alerts;

/** @addtogroup Broadcar
*
* @{

* @file RoadState_Alerts
* @brief Clase objeto: Muestra los datos que podria tener una alerta 
* 		del estado de la carretera.
* @Extends: Extiende de alertas por lo que tendra todas sus funciones y caracteristicas.
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
public class RoadState_Alerts extends Alerts{
	/*********************************************************************
	** 																	**
	** LOCAL VARIABLES 													**
	** 																	**
	**********************************************************************/
	static private String incidencetype;
	static private int severity;
	static private final String ICE="Ice";
	static private final String OIL="Oil";
	static private final String MUD="Mud";
	static private final String FOG="Fog";
	static private final String WATER="Water";
	static private final String DETERIORATION="Deterioration";
	
	/*********************************************************************
	** 																	**
	** LOCAL FUNCTIONS 													**
	** 																	**
	**********************************************************************/
	
	/**********************************************************************
	 *  					 Setters 	
	**********************************************************************/
	

	/**********************************************************************
	 * @brief   Cambia el valor de la direccion
	 * @param   boolean direction- nuevo valor para la direcccion
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setDirection(boolean direction){
		this.direction=direction;
	}
	

	/**********************************************************************
	 * @brief   Cambia el valor de la severidad
	 * @param   int severity- severiad que se le va a asignar
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setSeverity(int severity){ //From 1 to 5
		this.severity=severity;
	}
	
	/**********************************************************************
	 * @brief   Cambia el tipo de accidente
	 * @param   int incidencetype - nuevo tipo de accidente
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setIncidencetype(int incidencetype){
		if(incidencetype==0){
			this.incidencetype=FOG;
		}else if(incidencetype==1){
			this.incidencetype=OIL;
		}else if(incidencetype==2){
			this.incidencetype=MUD;
		}else if(incidencetype==3){
			this.incidencetype=WATER;
		}else if(incidencetype==4){
			this.incidencetype=DETERIORATION;
		}		
	}
	/**********************************************************************
	 *  				 Getters
	**********************************************************************/
	
	/**********************************************************************
	 * @brief   Devuelve el tipo de accidente
	 * @param   
	 * @return this.incidencetype- tipo de incidente
	 * @TODO 
	**********************************************************************/	
	public String getIncidenceType(){
		return this.incidencetype;
	}
	
	/**********************************************************************
	 * @brief   Devuelve el valor de la severidad de una alerta
	 * @param   
	 * @return this.severity- la severidad
	 * @TODO 
	**********************************************************************/	
	public int getSeverity(){
		return this.severity;
	}	
	
	
	/**********************************************************************
	 * @brief   Devuelve el valor de la direccion
	 * @param   
	 * @return this.direction- la direccion
	 * @TODO 
	**********************************************************************/	
	boolean direction;
	
	public boolean getDirection(){
		return this.direction;
	}
	
}
