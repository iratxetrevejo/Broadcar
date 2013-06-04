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

* @file Alerts
* @brief Clase objeto: Muestra los datos que podria tener una alerta 
* 		y sus funciones
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

public class Alerts {
	/*********************************************************************
	** 																	**
	** LOCAL VARIABLES 													**
	** 																	**
	**********************************************************************/
	private int id=0;
	private boolean state=false;
	private boolean show=false;
	private double lat;
	private double lon;
	private int icon=R.drawable.default_alert_icon;
	private String key="Alert";
	
	
	
	/**********************************************************************
  				Getters de los datos de las alertas.
	**********************************************************************/
	/**********************************************************************
	 * @brief  Devuelve el icono de una alerta
	 * @param   
	 * @return this.icon - Icono de una alerta
	 * @TODO 
	**********************************************************************/	
	public int getIcon(){
		return this.icon;
	}	
	
	/**********************************************************************
	 * @brief  Devuelve ID de una alerta
	 * @param   
	 * @return this.id - Id de una alerta
	 * @TODO 
	**********************************************************************/	
	public int getId(){
		return this.id;
	}
	
	/**********************************************************************
	 * @brief  Devuelve la key referente a una alerta
	 * @param   
	 * @return this.key - Key de una alerta
	 * @TODO 
	**********************************************************************/	
	public String getKey(){
		return this.key;
	}
	
	/**********************************************************************
	 * @brief  Devuelve el estado referente a una alerta
	 * @param   
	 * @return this.state - Estado de la alerta
	 * @TODO 
	**********************************************************************/	
	
	public boolean getState(){
		return this.state;
	}
	
	/**********************************************************************
	 * @brief  Devuelve la vista de una alerta
	 * @param   
	 * @return this.show - Vista de la alerta
	 * @TODO 
	**********************************************************************/	
	public boolean getShow(){
		return this.show;
	}
	
	/**********************************************************************
	 * @brief  Devuelve la latitud
	 * @param   
	 * @return this.lat - Latitud en la que se encuentra una alerta
	 * @TODO 
	**********************************************************************/
	public double getLat(){
		return this.lat;
	}
	
	/**********************************************************************
	 * @brief  Devuelve la longitud
	 * @param   
	 * @return this.lon - Longitud en la que se encuentra una alerta
	 * @TODO 
	**********************************************************************/
	public double getLon(){
		return this.lon;
	}
	
	/**********************************************************************
	   				Setters de los datos de las alertas.
	**********************************************************************/
	
	/**********************************************************************
	 * @brief  Cambia el icono de una alerta
	 * @param   int icon- nuevo icono
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setIcon(int icon){
		this.icon=icon;
	}
	
	/**********************************************************************
	 * @brief  Cambia el id de una alerta
	 * @param   int id- nuevo id
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setId(int id){
		this.id=id;
	}
	
	
	/**********************************************************************
	 * @brief  Cambia la key de una alerta
	 * @param   String key- nueva key para la alerta
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setKey(String key){
		this.key=key;
	}
	
	/**********************************************************************
	 * @brief  Cambia el estado de una alerta
	 * @param   boolean state - nuevo estado de la alerta
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setState(boolean state){
		this.state=state;
	}
	
	/**********************************************************************
	 * @brief  Cambia la visibilidad de la alerta
	 * @param   boolean show -
	 * @return
	 * @TODO 
	**********************************************************************/	

	public void setShow(boolean show){
		this.show=show;
	}
	
	/**********************************************************************
	 * @brief  Cambia la latitud de una alerta
	 * @param  double lat- la nueva latitud
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setLat(double lat){
		this.lat=lat;
	}
	
	
	/**********************************************************************
	 * @brief  Cambia la longitud de una alerta
	 * @param  double lon- la nueva longitud
	 * @return
	 * @TODO 
	**********************************************************************/	
	public void setLon(double lon){
		this.lon=lon;
	}
}
