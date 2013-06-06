
package broad.car.broadcar.speech;
/*********************************************************************
 **																	**
 ** MODULES USED 													**
 ** 																**
 **********************************************************************/
import java.util.ArrayList;
import java.util.Arrays;

/** @addtogroup Broadcar
*
* @{

* @file CommandDictionary
* @brief Clase que almacena el diccionario de palabras.
*
* @par VERSION HISTORY
* Version : v1.0
* Date : 05/006/2013
* Revised by : BroadCar team
* @author  Iratxe Trevejo
* @author  Ibon Ortega
* Description : Original version.
*
* @}
*/

public class CommandDictionary {
	/*********************************************************************
	 ** 																**
	 ** GLOBAL VARIABLES 												**
	 ** 																**
	 **********************************************************************/
	ArrayList<String> KEY_STRING_ACTIVATE;
	ArrayList<String> KEY_STRING_DESACTIVATE;
	ArrayList<String> KEY_STRING_ALERT;
	ArrayList<String> KEY_STRING_CRASHES;
	ArrayList<String> KEY_STRING_HEAVY_TRAFFIC;
	ArrayList<String> KEY_STRING_LOW_VISIBILITY;
	ArrayList<String> KEY_STRING_ROAD_STATE;
	ArrayList<String> KEY_STRING_WORKS;
	ArrayList<String> KEY_STRING_VEHICLE_NO_VISIBLE;
	
	/**********************************************************************
	 * @brief  CommandDictionary es el constructor de la clase que sirve 
	 * 		   para lanzar la funcion initializeDictionary().
	 * @par	   Logica 
	 * 		    -	Lanza initializeDictionary()
	 * @param   
	 * @return
	 * @TODO 
	 **********************************************************************/		
	public CommandDictionary(){
		initializeDictionary();
	}
	
	/*********************************************************************
	 ** 																**
	 ** LOCAL FUNCTIONS 												**
	 ** 																**
	 **********************************************************************/
	
	/**********************************************************************
	 * @brief  initializeDictionary sirve para inicializar los strings de los comandos.
	 * 			Cada comando tiene ciertas palabras dentro que la representan. 
	 * @par	   Logica 
	 * 		    -	crea los ArrayList de los comandos
	 * @param   
	 * @return
	 * @TODO 
	 **********************************************************************/	
	public void initializeDictionary(){
		KEY_STRING_ACTIVATE=new ArrayList<String>(Arrays.asList("Activar","activar","activate","Activate","Activa"
				,"activa","Actives","actives","muestra","Muestra","muestrame","Muestrame","Mostrar","mostrar"));
		KEY_STRING_DESACTIVATE=new ArrayList<String>(Arrays.asList("No","NO","no","Desactivar","desactivar","Desactiva"
				,"desactiva","Desactives","desactives","oculta","Oculta","Ocultame","ocultame","Ocultar","ocultar"));
		
		KEY_STRING_ALERT=new ArrayList<String>(Arrays.asList("Alerta","alerta","Alertas","alertas","Todas","todas"));
		
		KEY_STRING_CRASHES=new ArrayList<String>(Arrays.asList("Accidentes","accidentes","Choques","choques"));
		KEY_STRING_HEAVY_TRAFFIC=new ArrayList<String>(Arrays.asList("Tráfico","tráfico","caravana","Caravanas"));
		KEY_STRING_LOW_VISIBILITY=new ArrayList<String>(Arrays.asList("poca","Poca","Visibilidad","visibilidad","Niebla","niebla","lluvia","Lluvia","Nieve","nieve"));
		KEY_STRING_ROAD_STATE=new ArrayList<String>(Arrays.asList("Carretera","carretera","Camino","camino","estado","Estado"));
		KEY_STRING_WORKS=new ArrayList<String>(Arrays.asList("Obras","obras","Trabajos","trabajos","Trabajo","trabajo"));
		KEY_STRING_VEHICLE_NO_VISIBLE=new ArrayList<String>(Arrays.asList("vehiculo","visible","Vehiculo","Visible"));
	}
}
