/*********************************************************************
**																	**
** MODULES USED 													**
** 																	**
**********************************************************************/
package broad.car.broadcar.alerts;
import broad.car.broadcar.R;
/** @addtogroup Broadcar
*
* @{

* @file LowVisibility_Alerts
* @brief Clase objeto: Muestra los datos que podria tener una alerta 
* 		de poca visibilidad.
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
public class LowVisibility_Alerts extends Alerts{
	/*********************************************************************
	** 																	**
	** LOCAL VARIABLES 													**
	** 																	**
	**********************************************************************/
	private String FOG="Fog";
	private String SNOW="Snow";
	private String HEAVYRAIN="Heavy rain";
	private String incidencetype;
	private int severity;
	
	/*********************************************************************
	** 																	**
	** GLOBAL FUNCTIONS 												**
	** 																	**
	**********************************************************************/
	
	/*********************************************************************
	** 																	**
	** LOCAL FUNCTIONS 													**
	** 																	**
	**********************************************************************/
	
	/**********************************************************************
	 * 		 			Setters 
	**********************************************************************/
	/**********************************************************************
	 * @brief  Cambia el valor del tipo de accidente
	 * @param   int j-
	 * @return 
	 * @TODO cambiar el nombre a la variable de entrada
	**********************************************************************/	
	public void setIncidencetype(int j){
		if(j==2){
			this.incidencetype=FOG;
			this.setIcon(R.drawable.fog_alert_icon);
		}else if(j==0){
			this.incidencetype=SNOW;
			this.setIcon(R.drawable.snow_alert_icon);
		}else if(j==1){
			this.incidencetype=HEAVYRAIN;
			this.setIcon(R.drawable.heavyrain_alert_icon);
		}
	}
	
	/**********************************************************************
	 * @brief  Cambia el valor del tipo importancia de la alerta
	 * @param   int severity- valor nuevo para la severidad
	 * @return 
	 * @TODO
	**********************************************************************/	
	public void setSeverity(int severity){ //From 1 to 5
		this.severity=severity;
	}
	
	
	/**********************************************************************
	 *   					Getters
	**********************************************************************/
	
	/**********************************************************************
	 * @brief  Devuelve el valor del tipo de accidente
	 * @param  
	 * @return this.incidencetype - el valor que devuelve para el tipo de accidente
	 * @TODO
	**********************************************************************/	
	public String getIncidenceType(){
		return this.incidencetype;
	}
	
	
	/**********************************************************************
	 * @brief  Devuelve el valor de importancia de una alerta-Severity
	 * @param  
	 * @return this.severity 
	  * @TODO
	**********************************************************************/	
	public int getSeverity(){
		return this.severity;
	}
	
}
