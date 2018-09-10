package com.example.juan.reconvoz;

import android.content.Intent;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;

public class SegundoActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final int RECONOCEDOR_VOZ = 7;
    private TextView escuchando;
    private TextView respuesta;
    private ArrayList<Respuestas> respuest;
    private TextToSpeech leer;
    public ArrayList<Markov> algoritmo;


    public void Anterior(View view) {
        Intent anterior = new Intent(this, MainActivity.class);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        inicializar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RECONOCEDOR_VOZ) {
            ArrayList<String> reconocido = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String escuchado = reconocido.get(0);
            escuchando.setText(escuchado);
            prepararRespuesta(escuchado);
        }
    }

    private void prepararRespuesta(String escuchado) {
        String normalizar = Normalizer.normalize(escuchado, Normalizer.Form.NFD);
        String sintilde = normalizar.replaceAll("[^\\p{ASCII}]", "");

        int resultado;
        String respuesta = respuest.get(0).getRespuestas();
        for (int i = 0; i < respuest.size(); i++) {
            resultado = sintilde.toLowerCase().indexOf(respuest.get(i).getCuestion());
            if (resultado != -1) {
                respuesta = respuest.get(i).getRespuestas();
            }
        }
        responder(respuesta);
    }

    private void responder(String respuestita) {
        respuesta.setText(respuestita);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            leer.speak(respuestita, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            leer.speak(respuestita, TextToSpeech.QUEUE_FLUSH, null);
        }
    }


    public void inicializar() {
        escuchando = (TextView) findViewById(R.id.tvEscuchado);
        respuesta = (TextView) findViewById(R.id.tvRespuesta);
        respuest = proveerDatos();
        leer = new TextToSpeech(this, this);
    }

    public void hablar(View v) {
        Intent hablar = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        hablar.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
        startActivityForResult(hablar, RECONOCEDOR_VOZ);
    }

    public ArrayList<Markov> getAlgoritmo(Markov_Nodo correr) {
        return algoritmo;
    }



    public ArrayList<Respuestas> proveerDatos() {
        ArrayList<Respuestas> respuestas = new ArrayList<>();
        respuestas.add(new Respuestas("agua que no haz de beber", "dejala correr"));
        respuestas.add(new Respuestas("cria cuervos", "y te sacaran los ojos"));
        respuestas.add(new Respuestas("este grupo aprobara", "no lo creo"));
        respuestas.add(new Respuestas("integrantes del grupo", "cristhian, juan, roxana, vladimir"));
        respuestas.add(new Respuestas("mas vale prevenir", "que lamentar"));
        respuestas.add(new Respuestas("docente de la materia", "licenciada carmen rosa garcia perez"));

        return respuestas;
    }






    @Override
    public void onInit(int status) {

    }
}