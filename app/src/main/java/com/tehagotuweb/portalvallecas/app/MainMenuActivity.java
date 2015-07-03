package com.tehagotuweb.portalvallecas.app;

import android.app.Activity;    // Autoimportada al hacer extends (herencia) de la clase activity
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;       // Clase que se añaden al copiar el método onCreate de la SplashActivity
import android.support.v7.app.AppCompatActivity;
import android.util.Log;        // Clase que se añaden al copiar el método onCreate de la SplashActivity
import android.view.MenuInflater;
import android.widget.Button;   // Clase que añade al usar los botones
import android.view.View;       // Clase que se añade al hacer las vistas de mensajes
import android.widget.Toast;    // Clase para que se puedan usar los Toast
import android.support.v7.app.ActionBarActivity;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.support.v7.widget.Toolbar;

// Al hacer que la Segunda activity extienda de la clase Activity se autoimportar la clase Activity
// Recordar que hay que declarar siempre en el AndroidManifest todas las Activitys
// Esta clase tiene herencia multiple de la clase View.OnClickListener
public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    // Creamos las variables necesarias
    Button botonnoticias;
    Button botonfacebook;
    Button botontwitter;
    Button botongoogleplus;
    Button botoncontactar;
    Button botonsalir;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Log.d("MainMenuActivity", "onCreate");

        // Meterle la toolbar dado que hemos hecho el Theme sin ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // En versiones inferiores a SDK 19 oculto el FrameLayout que hemos usado para desplazar la toolbar
        if (Build.VERSION.SDK_INT < 19){
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.height = 0;
        }

        /*//Capturamos en una variable de tipo String el String que pasamos desde el Intent de una activity a otra
        String varActivity = getIntent().getStringExtra("valorTest");
        // Lo mostramos en el log
        Log.d("MainMenuActivity", varActivity);*/

        //Inicializamos
        inicializar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflar el recurso del menu para esta ActionBar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Implementamos un método con el cual tener un Listerner del evento onClick de cada botón
    private void inicializar(){

        botonnoticias=(Button)findViewById(R.id.BtnNoticias);
        botonnoticias.setOnClickListener(this);

        botonfacebook=(Button)findViewById(R.id.BtnFacebook);
        botonfacebook.setOnClickListener(this);

        botontwitter=(Button)findViewById(R.id.BtnTwitter);
        botontwitter.setOnClickListener(this);

        botongoogleplus=(Button)findViewById(R.id.BtnGooglePlus);
        botongoogleplus.setOnClickListener(this);

        botoncontactar=(Button)findViewById(R.id.BtnContactar);
        botoncontactar.setOnClickListener(this);

        botonsalir=(Button)findViewById(R.id.BtnSalir);
        botonsalir.setOnClickListener(this);

    }

    // Implementamos un método con el cual se lanza el Toast con el mensaje al pulsar el boton de Volver de esta activity
    public void onClick(View v) {
        if (v.getId()== R.id.BtnNoticias) {

            Log.d("MainMenuActivity", "Boton de noticias pulsado");

            // Creamos un intent, un objeto de la clase Intent. El intent tiene una activity origen (this) y una activity destino (NoticiasActivity.class)
            Intent b = new Intent(MainMenuActivity.this, NoticiasActivity.class);

            // Le meto dos flags al intent
            // Llamamos al método startActivity pasándole como parámetro el intent
            b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(b);

        }
        if (v.getId()== R.id.BtnFacebook){
            Uri uri = Uri.parse("https://www.facebook.com/PortalVallecas");
            Intent gotoweb = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(gotoweb);

        }
        if (v.getId()== R.id.BtnTwitter){
            Uri uri = Uri.parse("https://twitter.com/PortalVallecas");
            Intent gotoweb = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(gotoweb);
        }
        if (v.getId()== R.id.BtnGooglePlus){
            Uri uri = Uri.parse("https://plus.google.com/+PortalvallecasEs");
            Intent gotoweb = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(gotoweb);
        }
        if (v.getId()== R.id.BtnContactar) {

            Log.d("MainMenuActivity", "Boton de contactar pulsado");

            // Creamos un intent, un objeto de la clase Intent. El intent tiene una activity origen (this) y una activity destino (NoticiasActivity.class)
            Intent c = new Intent(MainMenuActivity.this, FormularioActivity.class);

            // Le meto dos flags al intent
            // Llamamos al método startActivity pas?ndole como par?metro el intent
            c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(c);

        }
        if (v.getId()== R.id.BtnSalir) {

            Log.d("MainMenuActivity", "Boton de salir pulsado");
            // Creo un mensaje para mostrarlo en un toast
            Toast toastsalir = Toast.makeText(getBaseContext(), "Saliendo de la app", Toast.LENGTH_SHORT);
            // Segun he leido se debe hacer esto para que el toast se vea, si pones el ratón encima de getBaseContext() te sale en la ayuda que necesita un show
            toastsalir.show();

            // Con esto al pulsar lo que hace es salir del activity secundario y llegar al escritorio de Android
            finish();

            /*// Creamos un intent para volver a la actividad anterior
            Intent b = new Intent(MainMenuActivity.this, SplashActivity.class);
            b.putExtra("EXIT", true);
            //Llamamos al método startActivity pasándole como parámetro el intent
            startActivity(b);*/

        }// Fin del caso de que el botón pulsado sea (xml)BtnSalir == (java)botonsalir
    }

}
