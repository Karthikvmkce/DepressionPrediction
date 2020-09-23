package net.simplifiedcoding.speechtotext;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> happy = new ArrayList<String>();
    ArrayList<String> sad = new ArrayList<String>();

    String speech_words = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        happy.add("happy");
        happy.add("smile");
        happy.add("congrats");
        happy.add("god bless");
        happy.add("very happy");
        happy.add("success");
        happy.add("wow");
        happy.add("superb");
        happy.add("fantastic");
        happy.add("love");
        happy.add("joyful");
        happy.add("enthusiastic");
        happy.add("wonderful");
        happy.add("happiness");
        happy.add("fabulous");
        happy.add("fantastic");
        happy.add("prosperity");
        happy.add("excellent");
        happy.add("peaceful");
        happy.add("fond");
        happy.add("awesome");
        happy.add("marvellous");
        happy.add("very very happy");

        sad.add("lost");
        sad.add("sad");
        sad.add("really sad");
        sad.add("feeling depressed");
        sad.add("depressed");
        sad.add("feeling low");
        sad.add("lose");
        sad.add("unfortunate");

        sad.add("struggling");
        sad.add("need help");
        sad.add("in trouble");
        sad.add("sorrow");
        sad.add("helpless");
        sad.add("feeling worst");
        sad.add("missing");
        sad.add("disappoinment");
        sad.add("ill");
        sad.add("sick");
        sad.add("hell");
        sad.add("death");
        sad.add("feeling bad");
        sad.add("worry");
        sad.add("having fear");
        sad.add("fearful");
        sad.add("anxiety");
        sad.add("anger");
        sad.add("highly depressed");
        sad.add("worst life");
        sad.add("troublesome");
        sad.add("hate");
        sad.add("avoid");
        sad.add("feeling worse");
        sad.add("very tired");
        sad.add("worried lot");
        sad.add("heart broken");
        sad.add("mournful");
        sad.add("sorrowful");
        sad.add("low spirited");




        checkPermission();

        final EditText editText = findViewById(R.id.editText);

        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);


        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());


        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null) {
                    editText.setText(matches.get(0));
                    speech_words = matches.get(0);

                    int happy_count = 0;
                    int sad_count = 0;
                    for(int i=0;i<happy.size();i++) {
                        if (speech_words.contains(happy.get(i))) {
                            Log.d(speech_words+" contains "+happy.get(i)+" Success","HERE");
                            happy_count++;
                        } else
                        {
                            Log.d(speech_words+" contains "+happy.get(i)+" Fail","HERE");
                        }
                    }

                    for(int i=0;i<sad.size();i++)
                    {
                        if(speech_words.contains(sad.get(i)))
                        {
                            Log.d(speech_words+" contains "+sad.get(i)+" Success","HERE");
                            sad_count++;
                        }
                        else
                        {
                            Log.d(speech_words+" contains "+sad.get(i)+" Fail","HERE");
                        }

                    }



                        if(sad_count >= 2 && sad_count <= 5)
                            Toast.makeText(MainActivity.this,"The user is in extreme depression",Toast.LENGTH_LONG).show();
                        else if(sad_count>0 && sad_count <3)
                            Toast.makeText(MainActivity.this,"The user is in sad mood",Toast.LENGTH_LONG).show();
                        else if(happy_count > 0 && happy_count <3)
                            Toast.makeText(MainActivity.this,"The user is Happy",Toast.LENGTH_LONG).show();
                        else if(happy_count >= 3)
                            Toast.makeText(MainActivity.this,"The user is very happy",Toast.LENGTH_LONG).show();

                        //Toast.makeText(MainActivity.this,"The user is "+happy_percentage+" % Happy and "+sad_percentage+" % Depressed.",Toast.LENGTH_LONG).show();



                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        findViewById(R.id.button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        editText.setHint("You will see input here");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        editText.setText("");
                        editText.setHint("Listening...");
                        break;
                }
                return false;
            }
        });
    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }
}
