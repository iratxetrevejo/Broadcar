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

* @file Works_Alerts
* @brief Clase objeto: Muestra los datos que podria tener una alerta 
* 		de las obras.
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
public class Works_Alerts extends Alerts{
	/*********************************************************************
	** 																	**
	** LOCAL VARIABLES 													**
	** 																	**
	**********************************************************************/
	private boolean direction;
	private boolean closedroad;
	
	
	/*********************************************************************
	** 																	**
	** LOCAL FUNCTIONS 													**
	** 																	**
	**********************************************************************/
	
	/**********************************************************************
	 * @brief  NoVisibleVehicle_Alerts() es el constructor de la clase.
	 * @par Inicializar el icono de la alerta.
	 * @return
	 * @TODO 
	**********************************************************************/	

	public Works_Alerts(){
		this.setIcon(R.drawable.works_alert_icon);
	}
	
	

	/**********************************************************************
	 *   					Getters 	
	**********************************************************************/
	
	/**********************************************************************
	 * @brief  Devuelve la direccion donde esta la alerta
	 * @par 
	 * @return this.direction - direccion que devuelve
	 * @TODO 
	**********************************************************************/	
	public boolean getDirection(){
		return this.direction;
	}
	
	/**********************************************************************
	 * @brief  Devuelve ClosedRoad
	 * @par 
	 * @return this.closedroad
	 * @TODO 
	**********************************************************************/	
	public boolean getClosedRoad(){
		return this.closedroad;
	}
	
	
	/**********************************************************************
	 *  					 Setters 	
	**********************************************************************/
	
	/**********************************************************************
	 * @brief  Cambia el valor de la direccion
	 * @param	boolean direction- nueva direccion 
	 * @return 
	 * @TODO 
	**********************************************************************/
	public void setDirection(boolean direction){
		this.direction=direction;
	}
	/**********************************************************************
	 * @brief  Cambia el valor de Closed Road
	 * @param boolean closedroad 
	 * @return
	 * @TODO 
	**********************************************************************/
	public void setClosedRoad(boolean closedroad){
		this.closedroad=closedroad;
	}
}
