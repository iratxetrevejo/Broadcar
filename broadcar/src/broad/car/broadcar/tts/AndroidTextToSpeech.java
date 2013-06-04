package broad.car.broadcar.tts;


import java.io.IOException;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import broad.car.broadcar.map.*;
/**
* This class demonstrates checking for a TTS engine, and if one is
* available it will spit out some speak.
*/
public class AndroidTextToSpeech implements TextToSpeech.OnInitListener

{
	/*********************************************************************
	** 																	**
	** GLOBAL VARIABLES 												**
	** 																	**
	**********************************************************************/
   private TextToSpeech textToSpeech;
   
   LocationAddress alertAddr;
   
   /**********************************************************************
	 * @brief Constructor de la clase AndroidTextToSpeech
	**********************************************************************/		
   public AndroidTextToSpeech(LocationAddress alertAddress) {
	   alertAddr=alertAddress;
}
	
	/*********************************************************************
	** 																	**
	** LOCAL FUNCTIONS 													**
	** 																	**
	**********************************************************************/
		
	/**********************************************************************
	 * @brief  Desde el main se llama a esta clase que crea un Intent para 
	 * 		   poner en marcha la voz
	 * @param  Context mainContext- el contexto de la clase main
	 * @return 
	 * @TODO 
	**********************************************************************/	

   public void start(Context mainContext)
   {
       // Fire off an intent to check if a TTS engine is installed
       Intent checkIntent = new Intent();
  	   checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
  	   textToSpeech = new TextToSpeech(mainContext,this);
   }


	/**********************************************************************
	 * @brief Executed when a new TTS is instantiated. Some static text is spoken via TTS here.
	 * 		  Da la bienvenida a la aplicacion
	 * @param  i
	 * @return 
	 * @TODO 
	**********************************************************************/	
   public void onInit(int i)
   {
		textToSpeech.setLanguage(Locale.US);

		String myText1 = "Wellcome to the broadcar application. ";
		textToSpeech.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
	}
   
   /**********************************************************************
	 * @brief Getter para que devuelva el mTts que es el que realiza la voz
	 * @param  
	 * @return mTts- encargado de la voz
	 * @TODO 
	**********************************************************************/	
   public TextToSpeech getMtts(){
	   return textToSpeech;
   }
   
   /**********************************************************************
	 * @brief Getter para que devuelva TextToSpeech.QUEUE_FLUSH
	 * @param  
	 * @return TextToSpeech.QUEUE_FLUSH- integer necesario para la voz
	 * @TODO 
	**********************************************************************/	
   public int getQueue(){
	   return TextToSpeech.QUEUE_FLUSH;
   }
   
   /**********************************************************************
	 * @brief Se encarga de anunciar un nueva alerta del tipo heavy traffic
	 * 			y de mencionar la direccion en la que ha ocurrido
	 * @param  
	 * @return 
	 * @TODO 
	**********************************************************************/	
   
   public void HeavyTraffic(double dlat, double dlon) throws IOException, InterruptedException{
	  	Locale spanish = new Locale("es", "ES");
	  	textToSpeech.setLanguage(spanish);
		String myText1 = "Tráfico denso en";
		String addr = alertAddr.getAddress(dlat, dlon);
		String  texto= myText1.concat(addr);
		textToSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH, null);

   }
   /**********************************************************************
 	 * @brief Se encarga de anunciar un nueva alerta del tipo low visibility
 	 * 			y de mencionar la direccion en la que ha ocurrido
 	 * @param  
 	 * @return 
 	 * @TODO 
 	**********************************************************************/	
   public void LowVisibility(double dlat, double dlon) throws IOException{

 		String myText1 = "Poca visibilidad en ";
 		String addr = alertAddr.getAddress(dlat, dlon);
		String  texto= myText1.concat(addr);
		textToSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH, null);
    }
   /**********************************************************************
 	 * @brief Se encarga de anunciar un nueva alerta del tipo road state
 	 * 			y de mencionar la direccion en la que ha ocurrido
 	 * @param  
 	 * @return 
 	 * @TODO 
 	**********************************************************************/	
   public void Roadstate(double dlat, double dlon) throws IOException{

		String myText1 = "Mal estado de la carretera en ";
		String addr = alertAddr.getAddress(dlat, dlon);
		String  texto= myText1.concat(addr);
		textToSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH, null);
   }
   
   /**********************************************************************
 	 * @brief Se encarga de anunciar un nueva alerta del tipo crashes
 	 * 			y de mencionar la direccion en la que ha ocurrido
 	 * @param  
 	 * @return 
 	 * @TODO 
 	**********************************************************************/	
   public void Crashes(double dlat, double dlon) throws IOException{

		String myText1 = "Accidente en";
		String addr = alertAddr.getAddress(dlat, dlon);
		String  texto= myText1.concat(addr);
		textToSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH, null);
   }
   
   /**********************************************************************
 	 * @brief Se encarga de anunciar un nueva alerta del tipo works on road
 	 * 			y de mencionar la direccion en la que ha ocurrido
 	 * @param  
 	 * @return 
 	 * @TODO 
 	**********************************************************************/	
   public void WorksOnRoad(double dlat, double dlon) throws IOException{

		String myText1 = "Obras en la carretera en ";
		String addr = alertAddr.getAddress(dlat, dlon);
		String  texto= myText1.concat(addr);
		textToSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH, null);  
	}   
   /**********************************************************************
 	 * @brief Se encarga de anunciar un nueva alerta del tipo vehicle no visible
 	 * 			y de mencionar la direccion en la que ha ocurrido
 	 * @param  
 	 * @return 
 	 * @TODO 
 	**********************************************************************/	
   public void VehicleNoVisible(double dlat, double dlon) throws IOException{

		String myText1 = "Vehiculo no visible en";
		String addr = alertAddr.getAddress(dlat, dlon);
		String  texto= myText1.concat(addr);
		textToSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH, null);
  }
   
}