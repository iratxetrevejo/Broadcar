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

* @file NoVisibleVehicle_Alerts
* @brief Clase objeto: Muestra los datos que podria tener una alerta 
* 		de vehiculos no visibles.
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
public class NoVisibleVehicle_Alerts extends Alerts{
	
	/*********************************************************************
	** 																	**
	** LOCAL VARIABLES 													**
	** 																	**
	**********************************************************************/
	
	private boolean direction;
	private int speed;
	
	
	
	/*********************************************************************
	** 																	**
	** LOCAL FUNCTIONS 													**
	** 																	**
	**********************************************************************/	
	/**********************************************************************
	 * @brief   NoVisibleVehicle_Alerts() es el constructor de la clase.
	 * @param   
	 * @return 
	 * @TODO 
	**********************************************************************/	
	public NoVisibleVehicle_Alerts(){
		this.setIcon(R.drawable.novisible_alert_icon);
	}
	
	
	/**********************************************************************
	 *   						Getters 
	**********************************************************************/
	/**********************************************************************
	 * @brief   Devuelve el valor de la direccion
	 * @param   
	 * @return this.direction- la direccion
	 * @TODO 
	**********************************************************************/	
	public boolean getDirection(){
		return this.direction;
	}
	
	/**********************************************************************
	 * @brief   Devuelve el valor de la velocidad
	 * @param   
	 * @return this.speed- la velocidad
	 * @TODO 
	**********************************************************************/	
	public int getSpeed(){
		return this.speed;
	}
	
	
	/**********************************************************************
	 *   					Setters
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
	 * @brief   Cambia el valor de la velocidad
	 * @param   boolean speed- nuevo valor para la velocidad
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setSpeed(int speed){
		this.speed=speed;
	}
}
