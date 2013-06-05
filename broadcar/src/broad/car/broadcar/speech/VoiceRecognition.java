package broad.car.broadcar.speech;
 
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import broad.car.broadcar.R;

 
/**
 * A very simple application to handle Voice Recognition intents
 * and display the results
 */
public class VoiceRecognition extends Activity
{
 
    private static final int REQUEST_CODE = 1234;
    
    CommandMatcher commandMatcher;
    /**
     * Called with the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_recog);
        commandMatcher = new CommandMatcher();
        startVoiceRecognitionActivity();
        
    } 
    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
        startActivityForResult(intent, REQUEST_CODE);
    }
 
    /**
     * Handle the results from the voice recognition activity.
     */
   
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
     
        	commandMatcher.setMatchesList(matches);
        	commandMatcher.searchForSpeechCommand();
        	
        }
        super.onActivityResult(requestCode, resultCode, data);
      this.finish();
    }
}