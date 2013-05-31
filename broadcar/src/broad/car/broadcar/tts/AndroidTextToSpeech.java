package broad.car.broadcar.tts;


import java.util.Locale;

import broad.car.broadcar.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

/**
* This class demonstrates checking for a TTS engine, and if one is
* available it will spit out some speak.
*/
//public class AndroidTextToSpeechActivity extends Activity implements TextToSpeech.OnInitListener
public class AndroidTextToSpeech implements TextToSpeech.OnInitListener

{
   private TextToSpeech mTts;
   // This code can be any value you want, its just a checksum.
   private static final int MY_DATA_CHECK_CODE = 0;

   public void start(Context mainContext)
   {
       // Fire off an intent to check if a TTS engine is installed
       Intent checkIntent = new Intent();
  	   checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
  	   mTts = new TextToSpeech(mainContext,this);
   }


   /**
    * Executed when a new TTS is instantiated. Some static text is spoken via TTS here.
    * @param i
    */
   public void onInit(int i)
   {
		mTts.setLanguage(Locale.US);

		String myText1 = "Wellcome to the broadcar application. ";
		mTts.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
	}

   public TextToSpeech getMtts(){
	   return mTts;
   }
   
   
   public int getQueue(){
	   return TextToSpeech.QUEUE_FLUSH;
   }
   
   
   public void HeavyTraffic(){

		String myText1 = "Heavy Traffic";
		mTts.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
   }
   
   public void LowVisibility(){

 		String myText1 = "Low visibility";
 		mTts.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
    }
   public void Roadstate(){

		String myText1 = "road state";
		mTts.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
   }
   public void Crashes(){

		String myText1 = "Crashes";
		mTts.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
   }
   public void WorksOnRoad(){

		String myText1 = "Works on road";
		mTts.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
   }   
   public void VehicleNoVisible(){

		String myText1 = "Vehicle no visible";
		mTts.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
  }
   
}