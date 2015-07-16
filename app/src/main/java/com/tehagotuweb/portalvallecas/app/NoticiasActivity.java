package com.tehagotuweb.portalvallecas.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
//import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tehagotuweb.portalvallecas.app.Modelo.FeedDatabase;
import com.tehagotuweb.portalvallecas.app.Modelo.ScriptDatabase;
import com.tehagotuweb.portalvallecas.app.R;
import com.tehagotuweb.portalvallecas.app.RssParse.Rss;
import com.tehagotuweb.portalvallecas.app.UI.FeedAdapter;
import com.tehagotuweb.portalvallecas.app.Web.VolleySingleton;
import com.tehagotuweb.portalvallecas.app.Web.XmlRequest;

// Recordar que hay que declarar siempre en el AndroidManifest todas las Activitys
public class NoticiasActivity extends AppCompatActivity {

    /* Etiqueta de depuración */
    private static final String TAG = NoticiasActivity.class.getSimpleName();

    /* URL del feed */
    public static final String URL_FEED = "http://www.portalvallecas.es/linea-temporal/feed/";

    /* Lista */
    private ListView listView;

    /* Adaptador */
    private FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        // Mostramos en el log que hemos alcanzado el onCreate de esta activity
        Log.d("NoticiasActivity", "onCreate");

        // Aquí se le mete la toolbar, dado que hemos hecho el Theme sin ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Le oculto el titulo para poder meter los tabs
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Necesario para saber sobre que contexto trabajamos
        final Context context = getApplicationContext();

        // En versiones inferiores a SDK 19 oculto el FrameLayout que hemos usado para desplazar la toolbar
        if (Build.VERSION.SDK_INT < 19){
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.height = 0;
        }

        //*************************************************
        //Spinner con filtros de las distintas categorías *
        //*************************************************

            Spinner cmbToolbar = (Spinner) findViewById(R.id.CmbToolbar);

            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                    getSupportActionBar().getThemedContext(),
                    R.layout.appbar_filter_title,
                    new String[]{"\u00daltimas noticias",
                                 "El Barrio",
                                 "Ocio, Arte y Cultura",
                                 "Deporte y Salud",
                                 "Comercios",
                                 "Informaci\u00f3n y Web"});

            adapter2.setDropDownViewResource(R.layout.appbar_filter_list);

            cmbToolbar.setAdapter(adapter2);

            cmbToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //... Acciones al seleccionar una opción de la lista
                    Log.i("Spinner", "Seleccionada la opción " + i);
                    if (i==0){
                        //No hacer nada es la misma opción
                    }
                    if (i==1){

                    }
                    if (i==2){


                    if (i==3){

                    }
                    if (i==4){

                    }
                    if (i==5){

                    }                   }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //... Acciones al no existir ningún elemento seleccionado
                }
            });

        //*************************************************
        //*                   Fin del Spinner             *
        //*************************************************


        // Continuación con el List view de las noticias

        // Obtener la lista
        listView = (ListView)findViewById(R.id.noticias_listView);

        Toast toastcargado = Toast.makeText(context, "Cargando las noticias...", Toast.LENGTH_LONG);
        // Según he leído se debe hacer esto para que el toast se vea, si pones el ratón encima de getBaseContext() te sale en la ayuda que necesita un show
        toastcargado.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toastcargado.show();

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            VolleySingleton.getInstance(this).addToRequestQueue(
                    new XmlRequest<>(
                            URL_FEED,
                            Rss.class,
                            null,
                            new Response.Listener<Rss>() {
                                @Override
                                public void onResponse(Rss response) {

                                    // Caching
                                    FeedDatabase.getInstance(NoticiasActivity.this).
                                            sincronizarEntradas(response.getChannel().getItems());
                                    // Carga inicial de datos...
                                    new LoadData().execute();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d(TAG, "Error Volley: " + error.getMessage());
                                }
                            }
                    )
            );
        } else {
            Log.i(TAG, "La conexión a internet no está disponible");
            adapter= new FeedAdapter(
                    this,
                    FeedDatabase.getInstance(this).obtenerEntradas(),
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            listView.setAdapter(adapter);

            Toast toastnointernet = Toast.makeText(context, "No hay conexi\u00f3n a Internet", Toast.LENGTH_SHORT);
            // Segun he leido se debe hacer esto para que el toast se vea, si pones el ratón encima de getBaseContext() te sale en la ayuda que necesita un show
            toastnointernet.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toastnointernet.show();
        }


        // Regisgrar escucha de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) adapter.getItem(position);

                // Obtene url de la entrada seleccionada
                String url = c.getString(c.getColumnIndex(ScriptDatabase.ColumnEntradas.URL));

                // Nuevo intent explícito
                Intent i = new Intent(NoticiasActivity.this, ArticuloActivity.class);

                // Setear url
                i.putExtra("url-extra", url);

                // Iniciar actividad
                startActivity(i);
            }
        });
    }

    public class LoadData extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            // Carga inicial de registros
            return FeedDatabase.getInstance(NoticiasActivity.this).obtenerEntradas();

        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            // Crear el adaptador
            adapter = new FeedAdapter(
                    NoticiasActivity.this,
                    cursor,
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

            // Relacionar la lista con el adaptador
            listView.setAdapter(adapter);
        }
    }

}
