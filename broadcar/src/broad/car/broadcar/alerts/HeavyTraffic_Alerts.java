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

* @file HeavyTraffic_Alerts
* @brief Clase objeto: Muestra los datos que podria tener una alerta 
* 		de trafico denso.
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
public class HeavyTraffic_Alerts extends Alerts{
	/*********************************************************************
	** 																	**
	** LOCAL VARIABLES 													**
	** 																	**
	**********************************************************************/
	private boolean direction;
	private int speed;
	
	/*********************************************************************
	** 																	**
	** GLOBAL FUNCTIONS 												**
	** 																	**
	**********************************************************************/
	
	/**********************************************************************
	 * @brief  HeavyTraffic_Alerts() es el constructor de la clase.
	 * @param    Inicializar el icono de la alerta.
	 * @return 
	 * @TODO 
	**********************************************************************/	
	public HeavyTraffic_Alerts(){
		this.setIcon(R.drawable.heavytraffic_alert_icon);
	}
	
	/**********************************************************************
	 * 				  Getters de los datos de las alertas.
	**********************************************************************/
	/**********************************************************************
	 * @brief  Devuelve el valor de la direccion
	 * @param    this.direction-direccion de la alerta a devolver
	 * @return 
	 * @TODO 
	**********************************************************************/		
	
	public boolean getDirection(){
		return this.direction;
	}
	
	/**********************************************************************
	 * @brief  Devuelve el valor de la velocidad
	 * @param    this.speed
	 * @return 
	 * @TODO 
	**********************************************************************/	
	public int getSpeed(){
		return this.speed;
	}
	
	
	/**********************************************************************
	 *   			Setters de los datos de las alertas.	
	**********************************************************************/
	/**********************************************************************
	 * @brief  Cambia el valor de la direccion
	 * @param    boolean direction- nueva direccion
	 * @return 
	 * @TODO 
	**********************************************************************/	
	public void setDirection(boolean direction){
		this.direction=direction;
	}
	/**********************************************************************
	 * @brief  Cambia el valor de la velocidad
	 * @param    int speed- nueva velocidad
	 * @return 
	 * @TODO 
	**********************************************************************/	
	public void setSpeed(int speed){
		this.speed=speed;
	}
}
