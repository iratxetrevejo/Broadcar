package broad.car.broadcar.speech;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandDictionary {
	ArrayList<String> KEY_STRING_ACTIVATE;
	ArrayList<String> KEY_STRING_DESACTIVATE;
	ArrayList<String> KEY_STRING_ALERT;
	ArrayList<String> KEY_STRING_CRASHES;
	ArrayList<String> KEY_STRING_HEAVY_TRAFFIC;
	ArrayList<String> KEY_STRING_LOW_VISIBILITY;
	ArrayList<String> KEY_STRING_ROAD_STATE;
	ArrayList<String> KEY_STRING_WORKS;
	ArrayList<String> KEY_STRING_VEHICLE_NO_VISIBLE;
	
	public CommandDictionary(){
		initializeDictionary();
	}
	public void initializeDictionary(){
		KEY_STRING_ACTIVATE=new ArrayList<String>(Arrays.asList("Activar","activar","activate","Activate","Activa"
				,"activa","Actives","actives","muestra","Muestra","muestrame","Muestrame","Mostrar","mostrar"));
		KEY_STRING_DESACTIVATE=new ArrayList<String>(Arrays.asList("No","NO","no","Desactivar","desactivar","Desactiva"
				,"desactiva","Desactives","desactives","oculta","Oculta","Ocultame","ocultame","Ocultar","ocultar"));
		
		KEY_STRING_ALERT=new ArrayList<String>(Arrays.asList("Alerta","alerta","Alertas","alertas","Todas","todas"));
		
		KEY_STRING_CRASHES=new ArrayList<String>(Arrays.asList("Accidentes","accidentes","Choques","choques"));
		KEY_STRING_HEAVY_TRAFFIC=new ArrayList<String>(Arrays.asList("Trafico","trafico","caravana","Caravanas"));
		KEY_STRING_LOW_VISIBILITY=new ArrayList<String>(Arrays.asList("poca","Poca","Visibilidad","visibilidad","Niebla","niebla","lluvia","Lluvia","Nieve","nieve"));
		KEY_STRING_ROAD_STATE=new ArrayList<String>(Arrays.asList("Carretera","carretera","Camino","camino","estado","Estado"));
		KEY_STRING_WORKS=new ArrayList<String>(Arrays.asList("Obras","obras","Trabajos","trabajos","Trabajo","trabajo"));
		KEY_STRING_VEHICLE_NO_VISIBLE=new ArrayList<String>(Arrays.asList("vehiculo","visible","Vehiculo","Visible"));
	}
}
