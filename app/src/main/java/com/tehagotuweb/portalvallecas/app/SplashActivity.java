package com.tehagotuweb.portalvallecas.app;

import android.app.Activity;
import android.content.Intent;  //Autoa?ade la clase Intent al usarla en el onCreate
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Mensaje que se mostrar? en el Log del Debug
        Log.d("SplashActivity", "onCreate");

        // Este método meterá una latencia en el splash para mantenerlo en pantalla y lanzar? el intent a la segunda actividad
        meterLatencia();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Sobreescribimos los m?todos del flujo de una app y metemos un log que nos mostrar? en el debug en que paso del proceso estamos en cada fase del flujo

    @Override
    protected void onRestart() {
        super.onRestart();
        // Mensaje que se mostrar? en el Log del Debug
        Log.d("SplashActivity","onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Mensaje que se mostrar? en el Log del Debug
        Log.d("SplashActivity", "onPause");
        // Cierro el activity del Splash principal
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Mensaje que se mostrar? en el Log del Debug
        Log.d("SplashActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Mensaje que se mostrar? en el Log del Debug
        Log.d("SplashActivity", "onDestroy");
    }

    private void meterLatencia()
    {
        Thread timer = new Thread(){
            public void run(){
                // Como la ejecucion de un activity no contempla el metodo sleep, es decir retrasar un proceso de renderizado,
                // debemos implementarlo por medio de un try catch
                try
                {
                    //Con esto congelamos el splash dos segundos en pantalla
                    sleep(2000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {

                    // Creamos un intent, un objeto de la clase Intent. El intent tiene una activity origen (this) y una activity destino (MainMenuActivity.class)
                    Intent a = new Intent(SplashActivity.this, MainMenuActivity.class);

                    // Le meto dos flags al intent
                    // Llamamos al método startActivity pasándole como parámetro el intent
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(a);

                    // Pasamos un parametro de una activity a otra por medio del intent creado
                    a.putExtra("valorTest", "true");

                }

            }
        }; //Cerramos la hebra de programaci?n

        //Lanzamos el hebra
        timer.start();
    }
}
