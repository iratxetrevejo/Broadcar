package broad.car.broadcar.map;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

public class LocationAddress {
	/*********************************************************************
	** 																	**
	** IMPORTED CLASSES / Declarations  								**
	** 																	**
	**********************************************************************/
	Context mainContext;
	
	/*********************************************************************
	** 																	**
	** GLOBAL VARIABLES 												**
	** 																	**
	**********************************************************************/
	
	public String addr;
	
	
	/**********************************************************************
	 * @brief Constructor de la clase getLocation
	**********************************************************************/		
	
	public LocationAddress(Context applicationContext) {
		mainContext=applicationContext;
	}
	
	/*********************************************************************
	** 																	**
	** LOCAL FUNCTIONS 													**
	** 																	**
	**********************************************************************/
		
	/**********************************************************************
	 * @brief  Dada una longitud y una latitud saca la dirección 
	 * @param  double dlat- latitud en la que ha ocurrido la alerta
	 * @param  double dlon- longitud en la que ha ocurrido la alerta
	 * @return String addr - la direccion de dicha longitud y latitud
	 * @TODO 
	**********************************************************************/	

	public String getAddress(double dlat, double dlon) throws IOException{
		Geocoder gc = new Geocoder(mainContext.getApplicationContext());
		List<Address> addresses = gc.getFromLocation(dlat, dlon, 1);
		if (addresses.size() > 0) {
		Address address = addresses.get(0);
		addr = address.getAddressLine(0);//consigue la direccion en la que ocurrido la alerta
		Toast.makeText(mainContext.getApplicationContext(),addr, Toast.LENGTH_LONG).show();
		}
		return addr;
	}
}
