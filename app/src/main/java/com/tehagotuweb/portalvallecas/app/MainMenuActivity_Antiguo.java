package com.tehagotuweb.portalvallecas.app;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

// Al hacer que la Segunda activity extienda de la clase Activity se autoimportar la clase Activity
// Recordar que hay que declarar siempre en el AndroidManifest todas las Activitys
// Esta clase tiene herencia multiple de la clase View.OnClickListener
public class MainMenuActivity_Antiguo extends AppCompatActivity implements View.OnClickListener {

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

        // Mostramos en el log que hemos alcanzado el onCreate de esta activity
        Log.d("MainMenuActivity", "onCreate");

        // Aqu� se le mete la toolbar, dado que hemos hecho el Theme sin ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Ver icono de la app en la ActionBar
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        // En versiones inferiores a SDK 19 oculto el FrameLayout que hemos usado para desplazar la toolbar
        if (Build.VERSION.SDK_INT < 19){
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            statusBar.setVisibility(View.GONE);
            /*ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.height = 0;*/
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.action_settings:
                //openSettings();
                Dialog dialog = new Dialog(MainMenuActivity_Antiguo.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.about_dialog);
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

                //La forma tipica es hacer un Switch case pero es m�s facil y permite m�s opciones una cascada de if�s
                /*
                    int id = item.getItemId();

                    if(id == R.id.action_settings){
                        //openSettings();
                        Dialog dialog = new Dialog(MainMenuActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.about_dialog);
                        dialog.show();
                        return true;
                    }
                    return super.onOptionsItemSelected(item);
                */
    }

    // Implementamos un m�todo con el cual tener un Listerner del evento onClick de cada bot�n
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

    // Implementamos un m�todo con el cual se lanza el Toast con el mensaje al pulsar el boton de Volver de esta activity
    public void onClick(View v) {
        if (v.getId()== R.id.BtnNoticias) {

            Log.d("MainMenuActivity", "Boton de noticias pulsado");

            // Creamos un intent, un objeto de la clase Intent. El intent tiene una activity origen (this) y una activity destino (NoticiasActivity.class)
            Intent b = new Intent(MainMenuActivity_Antiguo.this, NoticiasActivity.class);

            // Llamamos al m�todo startActivity pas�ndole como par�metro el intent
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
            Intent c = new Intent(MainMenuActivity_Antiguo.this, FormularioActivity.class);

            // Llamamos al m�todo startActivity pas?ndole como par?metro el intent
            startActivity(c);

        }
        if (v.getId()== R.id.BtnSalir) {

            Log.d("MainMenuActivity", "Boton de salir pulsado");
            // Creo un mensaje para mostrarlo en un toast
            Toast toastsalir = Toast.makeText(getBaseContext(), "Saliendo de la app", Toast.LENGTH_SHORT);
            // Segun he leido se debe hacer esto para que el toast se vea, si pones el rat�n encima de getBaseContext() te sale en la ayuda que necesita un show
            toastsalir.show();

            // Con esto al pulsar lo que hace es salir del activity secundario y llegar al escritorio de Android
            finish();

            /*// Creamos un intent para volver a la actividad anterior
            Intent b = new Intent(MainMenuActivity.this, SplashActivity.class);
            b.putExtra("EXIT", true);
            //Llamamos al m�todo startActivity pas�ndole como par�metro el intent
            startActivity(b);*/

        }// Fin del caso de que el bot�n pulsado sea (xml)BtnSalir == (java)botonsalir
    }

}
