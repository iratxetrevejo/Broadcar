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

* @file Crashes_Alerts
* @brief Clase objeto: Muestra los datos que podria tener una alerta 
* 		de accidente.
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
public class Crashes_Alerts extends Alerts{
	/*********************************************************************
	** 																	**
	** LOCAL VARIABLES 													**
	** 																	**
	**********************************************************************/
	private boolean direction;
	private boolean closedroad;
	
	/*********************************************************************
	** 																	**
	** GLOBAL FUNCTIONS 												**
	** 																	**
	**********************************************************************/
	

	/**********************************************************************
	 * @brief  Crashes_Alerts() es el constructor de la clase.
	 * @par  Logica
	 *   		- Inicializar el icono de la alerta.
	 * @return
	 * @TODO 
	**********************************************************************/	
	public Crashes_Alerts(){
		this.setIcon(R.drawable.crash_alert_icon);
	}
	
	
	/**********************************************************************
	 				Getters de los datos de las alertas.
	**********************************************************************/
	
	/**********************************************************************
	 * @brief  Se encarga de devolver el valor de la direccion.
	 * @return this.direction- la direccion en la que esta la alerta
	 * @TODO 
	**********************************************************************/	

	public boolean getDirection(){
		return this.direction;
	}
	
	/**********************************************************************
	 * @brief  Se encarga de devolver el valor de la carretera cortada.
	 * @return this.closedroad- Carretera cortada
	 * @TODO 
	**********************************************************************/	
	public boolean getClosedRoad(){
		return this.closedroad;
	}
	
	
	
	/**********************************************************************
	 * 				  Setters de los datos de las alertas.
	**********************************************************************/
	
	/**********************************************************************
	 * @brief  Se encarga de poner una nueva direccion
	 * @return boolean direction-nueva direccion
	 * @TODO 
	**********************************************************************/	
	public void setDirection(boolean direction){
		this.direction=direction;
	}
	
	/**********************************************************************
	 * @brief  Se encarga de poner una nueva carretera cortada
	 * @return boolean closedroad
	 * @TODO 
	**********************************************************************/	
	public void setClosedRoad(boolean closedroad){
		this.closedroad=closedroad;
	}
}
