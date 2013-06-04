package broad.car.broadcar.gps;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class Gps {
	

	/**********************************************************************
	 * @brief  Desactiva el gps
	 * @param   Context context- contexto de la aplicacion
	 * @return
	 * @TODO 

	 **********************************************************************/
	public static void turnGPSOff(Context context){
        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
 
        if(provider.contains("gps")){
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider"); 
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3")); 
            context.sendBroadcast(poke);
        }
    } 

	/**********************************************************************
	 * @brief  Activa el gps
	 * @param   Context context- contexto de la aplicacion
	 * @return
	 * @TODO 

	 **********************************************************************/
	
	public static void turnGPSOn(Context context){
	
	//activar el gps
    String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
    
    if(!provider.contains("gps"))
    { //if gps is disabled
        final Intent poke = new Intent();
        poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider"); 
        poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
        poke.setData(Uri.parse("3")); 
        context.sendBroadcast(poke);
    }
	}
}
