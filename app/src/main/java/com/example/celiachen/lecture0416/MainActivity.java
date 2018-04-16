package com.example.celiachen.lecture0416;

import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        TextToSpeech.OnInitListener{


    private Button btnSpeak;
    private EditText txtText;
    // texttospeech object
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, this);

        btnSpeak = findViewById(R.id.btnSpeak);
        txtText = findViewById(R.id.txtEdit);

        // create a typeface for the custom font
        // assign it to my button and my edit text
        Typeface font = Typeface.createFromAsset(getAssets(),
                "fonts/Let's Bake Muffins - TTF.ttf");
        txtText.setTypeface(font);
        btnSpeak.setTypeface(font);

        // create a onclick for my button
        btnSpeak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // speak
                speak();
            }
        });
    }
    // onDestroy, make sure you shutdown any thing you are using
    @Override
    public void onDestroy(){

        // if the object is not null
        // call shutdown
        if (tts != null){
            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();

    }

    // since we are implementing the listener from TextToSpeech,
    // we need to override the onInit method

    @Override
    public void onInit(int status){

        // initialize the texttospeech object
        // set language
        // set speech speed, volume, etc.

        if (status == TextToSpeech.SUCCESS){
            // set the language
            int result = tts.setLanguage(Locale.US);

            // if the result is not legit
            // provide log/error message, the language isn't supported
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TextToSpeech", "language not supported.");
            }
            else{
                btnSpeak.setEnabled(true);
                speak();
            }
        }
        else{
            Log.e("TextToSpeech", "Cannot initialize.");
        }

    }

    // write a method that uses the methods in TextToSpeech Class to read the message
    private void speak(){

        // get the message from the edit text
        CharSequence text = txtText.getText();
        // call the speak method from TextToSpeech class
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "id1");

        // TextToSpeech.Queue_flush
        // all the entries in the playback queue are dopped and replaed by the new entry
        // QUEUE_ADD -> the new entry is added at the end of the playback queue.


        // set speech speed, pitch

    }
}
