
/*********************************************************************
**																	**
** MODULES USED 													**
** 																	**
**********************************************************************/
package broad.car.broadcar.map;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import broad.car.broadcar.alerts.AlertManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
/** @addtogroup Broadcar
*
* @{

* @file googleMap
* @brief Esta clase se encarga del mapa
* de hacer la localizaci�n y modificar la vista del mapa
* @par VERSION HISTORY
* Version : v0.0
* Date : 30/01/2013
* Revised by : BroadCar team
* Description : Original version.
* @author  Iratxe Trevejo
* @author  Ibon Ortega
* @}
*/

public class MapGoogle extends Activity implements LocationListener{

	/*********************************************************************
	** 																	**
	** IMPORTED CLASSES / Declarations  								**
	** 																	**
	**********************************************************************/
	GoogleMap googleMap;
	LocationManager locationManager;
	Location location; //localizaci�n
	LocationListener locListener;//listener para el cambio de posici�n
	CameraUpdate camUpd; // para refrescar el mapa
	Criteria criteria;
	AlertManager alertManager;
	markers marker;

/**********************************************************************
 * @param marcador 
 * @brief   Situa el mapa en torno a la localizaci�n en la posici�n
 * en la que esta situada el dispositivo
 * @param  SupportMapFragment fm
 * @param  LocationManager locationManager
 * @param  AlertManager alertManager2 - controlador de alertas
 * @return
 * @TODO 
**********************************************************************/	


	public void init_location(SupportMapFragment fragment, LocationManager locationManager, AlertManager alert_manager, markers marcador)
    {
		marker=marcador;
		alertManager = alert_manager;
		// Getting GoogleMap object from the fragment
        googleMap = fragment.getMap();
        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);
        // Creating a criteria object to retrieve provider
        criteria = new Criteria();
         // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
         // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider); 
        if(location!=null){
           onLocationChanged(location);
        }        
       //Mostramos la �ltima posici�n conocida
       viewLocation(location);
       //Nos registramos para recibir actualizaciones de la posici�n
       register_forUpdates();  
       locationManager.requestLocationUpdates(
    			LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }
     
	/**********************************************************************
	 * @brief  Maneja los cambios en el mapa, si detecta cambio de posici�n
	 * lo actualiza a la nueva
	 * @return
	 * @TODO 
	**********************************************************************/		
	
    private void register_forUpdates() {
    	locListener = new LocationListener() {
	    	//si cambia de posicion
    		public void onLocationChanged(Location location) {
    			//elimina todos los marcadores
    			//googleMap.clear();
    			//pinta el mapa en la nueva localizaci�n
    			viewLocation(location);
    			
	   	}
	    public void onProviderDisabled(String provider){
	   	}
	  	public void onProviderEnabled(String provider){
	   	}
	    	public void onStatusChanged(String provider, int status, Bundle extras){
	    	}
    	};		
	}
	/**********************************************************************
	 * @brief  Dibuja el mapa en la nueva localizaci�n
	 * @param Location loc
	 * @return
	 * @TODO 
	**********************************************************************/		

	private void viewLocation(Location loc) {
    	if(loc != null)
    	{	    			             
    		//obtenemos la posicion
    	    camUpd = CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(),loc.getLongitude()));
    	    //movemos el mapa a la posicion
    	    googleMap.moveCamera(camUpd);    
    	   // Zoom in the Google Map
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
			//activamos el compas 
			googleMap.getUiSettings().setCompassEnabled(true);
			
			marker.addMarkersToMap();//a�ade las alertas
    	}
    }

	@Override
	public void onLocationChanged(Location location) {
	}
	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	

	
	/**********************************************************************
	 * @brief Da la opci�n de modificar la vista del mapa
	 *- satellite
	 *- normal
	 *- terrain 
	 *- hybrid
	 * @param String vista - nueva vista que se desea tener
	 * @return
	 * @TODO 
	**********************************************************************/		
	public void changeMapView(String vista)
	{		
    	if(vista.equals("NORMAL")){
    		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    	}
    	else if(vista.equals("SATELLITE")){
    		googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    	}
    	else if(vista.equals("TERRAIN")){
    		googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    	}
    	else if(vista.equals("HYBRID")){
    		googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    	}
       		   
	}
	
	
}
