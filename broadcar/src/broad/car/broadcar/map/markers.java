package broad.car.broadcar.map;

import broad.car.broadcar.alerts.AlertManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class markers {
	GoogleMap googleMap;
	 AlertManager alertManager;
	 
	public markers(GoogleMap googleMapa, AlertManager alert_Manager) {
		googleMap=googleMapa;
		alertManager=alert_Manager;
	}

	

	/**********************************************************************
	 * @brief Añade todas las alertas al mapa
	 * @par Logica:
	 * 		-recorre todos los arrays de cada uno de las alertas
	 * 		y si existe una alerta de ese tipo se printea en el mapa	
	 * @param 
	 * @return
	 * @TODO 
	**********************************************************************/	
	
	public  void addMarkersToMap(){

		//Limpia el mapa
		googleMap.clear();
		//HAY QUE CAMBIAR LAS ETIQUETAS DE LOS MARCADORES POR LOS @STRING/
		
		addLowVisibilitycMarkers();
	
		addCrashesMarkers();	
		
		addHeavyTrafficMarkers();
		
		addNoVisibleMarkers();
	
		addRoadStateMarkers();
		
		addWorksMarkers();
	}


	/**********************************************************************
	 * @brief Añade todas (si existen ) markers para las alertas de Heavy traffic
	 * @param 
	 * @return
	 * @TODO 
	**********************************************************************/	
	private void addHeavyTrafficMarkers() {
		for(int i=0;i<alertManager.getHeavytraffic_alerts().length;i++){
			if((alertManager.heavytraffic_Alerts[i].getState())&&(alertManager.heavytraffic_Alerts[i].getShow())){
				googleMap.addMarker(new MarkerOptions()
				.position(new LatLng(alertManager.heavytraffic_Alerts[i].getLat(), alertManager.heavytraffic_Alerts[i].getLon()))
				.snippet("Heavy traffic")
				.icon(BitmapDescriptorFactory.fromResource(alertManager.heavytraffic_Alerts[i].getIcon()))
				.title(alertManager.heavytraffic_Alerts[i].getKey()));
			}
			
		}			
	}



	/**********************************************************************
	 * @brief Añade todas (si existen ) markers para las alertas de No visible vehicle
	 * @param 
	 * @return
	 * @TODO 
	**********************************************************************/	
	private void addNoVisibleMarkers() {
		for(int i=0;i<alertManager.getNoVisibleVehicle_Alerts().length;i++){
			if((alertManager.noVisibleVehicle_Alerts[i].getState())&&(alertManager.noVisibleVehicle_Alerts[i].getShow())){
				googleMap.addMarker(new MarkerOptions()
				.position(new LatLng(alertManager.noVisibleVehicle_Alerts[i].getLat(), alertManager.noVisibleVehicle_Alerts[i].getLon()))
				.snippet("Vehicle not visible")
				.icon(BitmapDescriptorFactory.fromResource(alertManager.noVisibleVehicle_Alerts[i].getIcon()))
				.title(alertManager.noVisibleVehicle_Alerts[i].getKey()));
			}
		}			
	}



	/**********************************************************************
	 * @brief Añade todas (si existen ) markers para las alertas de road state
	 * @param 
	 * @return
	 * @TODO 
	**********************************************************************/	
	private void addRoadStateMarkers() {
		for(int j=0;j<alertManager.getRoadState_Alerts().length;j++){
			for(int i=0;i<alertManager.roadstate_Alerts[j].length;i++){
				if((alertManager.roadstate_Alerts[j][i].getState())&&(alertManager.roadstate_Alerts[j][i].getShow())){
					googleMap.addMarker(new MarkerOptions()
					.position(new LatLng(alertManager.roadstate_Alerts[j][i].getLat(), alertManager.roadstate_Alerts[j][i].getLon()))
					.snippet(alertManager.roadstate_Alerts[j][i].getIncidenceType())
					.icon(BitmapDescriptorFactory.fromResource(alertManager.roadstate_Alerts[j][i].getIcon()))
					.title(alertManager.roadstate_Alerts[j][i].getKey()));
				}
			}	
		}		
	}


	/**********************************************************************
	 * @brief Añade todas (si existen ) markers para las alertas de Works
	 * @param 
	 * @return
	 * @TODO 
	**********************************************************************/	

	private void addWorksMarkers() {
		for(int i=0;i<alertManager.getWorks_Alerts().length;i++){
			if((alertManager.works_Alerts[i].getState())&&(alertManager.works_Alerts[i].getShow())){
				googleMap.addMarker(new MarkerOptions()
				.position(new LatLng(alertManager.works_Alerts[i].getLat(), alertManager.works_Alerts[i].getLon()))
				.snippet("Works")
				.icon(BitmapDescriptorFactory.fromResource(alertManager.works_Alerts[i].getIcon()))
				.title(alertManager.works_Alerts[i].getKey()));
			}
		}			
	}



	/**********************************************************************
	 * @brief Añade todas (si existen ) markers para las alertas de crashes
	 * @param 
	 * @return
	 * @TODO 
	**********************************************************************/	
	private void addCrashesMarkers() {
		for(int i=0;i<alertManager.getCrashes_Alerts().length;i++){
			if((alertManager.crashes_Alerts[i].getState())&&(alertManager.crashes_Alerts[i].getShow())){
				googleMap.addMarker(new MarkerOptions()
				.position(new LatLng(alertManager.crashes_Alerts[i].getLat(), alertManager.crashes_Alerts[i].getLon()))
				.snippet("Crashes")
				.icon(BitmapDescriptorFactory.fromResource(alertManager.crashes_Alerts[i].getIcon()))
				.title(alertManager.crashes_Alerts[i].getKey()));
			}
		}		
	}



	/**********************************************************************
	 * @brief Añade todas (si existen ) markers para las alertas de low visibility
	 * @param 
	 * @return
	 * @TODO 
	**********************************************************************/	
	private void addLowVisibilitycMarkers() {
		for(int j=0;j<alertManager.getLowVisibility_Alerts().length;j++){
			for(int i=0;i<alertManager.lowVisibility_Alerts[j].length;i++){
				if((alertManager.lowVisibility_Alerts[j][i].getState())&&(alertManager.lowVisibility_Alerts[j][i].getShow())){
					googleMap.addMarker(new MarkerOptions()
					.position(new LatLng(alertManager.lowVisibility_Alerts[j][i].getLat(), alertManager.lowVisibility_Alerts[j][i].getLon()))
					.snippet(alertManager.lowVisibility_Alerts[j][i].getIncidenceType())
					.icon(BitmapDescriptorFactory.fromResource(alertManager.lowVisibility_Alerts[j][i].getIcon()))
					.title(alertManager.lowVisibility_Alerts[j][i].getKey()));
				}
			}	
		}			
	}
	
}
