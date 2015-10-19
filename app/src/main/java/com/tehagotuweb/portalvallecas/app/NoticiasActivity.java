package com.tehagotuweb.portalvallecas.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SimpleCursorAdapter;
//import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;
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

    /* Etiqueta de depuraci�n */
    private static final String TAG = NoticiasActivity.class.getSimpleName();

    /* URL del feed */
    public static String URL_FEED = "http://www.portalvallecas.es/linea-temporal/feed/";

    /* Lista */
    private ListView listView;

    /* Adaptador */
    private FeedAdapter adapter;

    // Añadidos
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Si se quiere con tabs cambiar esta l�nea por la siguiente:
        // setContentView(R.layout.activity_noticias_contabs);
        // Y desactivar lo que hace la carga de noticias
        setContentView(R.layout.activity_noticias);

        // Necesario para saber sobre que contexto trabajamos para tareas posteriores
        final Context context = getApplicationContext();

        // Mostramos en el log que hemos alcanzado el onCreate de esta activity
        Log.d("NoticiasActivity", "onCreate");

        //**************************************************************************
        // Aqu� se le mete la toolbar, dado que hemos hecho el Theme sin ActionBar *
        //**************************************************************************

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // Le oculto el t�tulo para poder meter los filters
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            // Esta correccion se hacia antes, ahora con la nueva forma de gestionar la toolbar se hace por medio del fichero attrs.xml en sus diferentes versiones
            // En versiones inferiores a SDK 19 oculto el FrameLayout que hemos usado para desplazar la toolbar
            /*if (Build.VERSION.SDK_INT < 19){
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setVisibility(View.GONE);
            }*/

        //**************************************************
        //*                   Fin de toolbar               *
        //**************************************************

        //***********************************************
        //*               Inicio de ActionBar           *
        //***********************************************

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //********************************************
        //*               Fin de ActionBar           *
        //********************************************

        //*******************************************************
        //*               Inicio del NavigationDrawer           *
        //*******************************************************

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);

        //****************************************************
        //*               Fin del NavigationDrawer           *
        //****************************************************

        //**************************************************
        // Spinner con filtros de las distintas categor�as *
        //**************************************************

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
                    //... Acciones al seleccionar una opci�n de la lista
                    Log.i("Spinner", "Seleccionada la opci�n " + i);
                    if (i==0){
                        // No hacer nada es la misma opci�n
                        // URL_FEED = "http://www.portalvallecas.es/linea-temporal/feed/";
                        // Log.i("Url", URL_FEED);
                    }
                    if (i==1){
                        // URL_FEED = "http://www.portalvallecas.es/categoria/el-barrio/feed/";
                        // Log.i("Url", URL_FEED);
                    }
                    if (i==2) {

                    }
                    if (i==3){

                    }
                    if (i==4){

                    }
                    if (i==5){

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //... Acciones al no existir ning�n elemento seleccionado
                }
            });

        //*************************************************
        //*                   Fin del Spinner             *
        //*************************************************

        //*************************************************
        //*                 Tabs + ViewPager              *
        //*************************************************

            //Establecer el PageAdapter del componente ViewPager
            /*ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setAdapter(new FragmentAdapter(
                    getSupportFragmentManager()));

            TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            tabLayout.setupWithViewPager(viewPager);*/

        //*************************************************
        //*         Fin de los Tabs + ViewPager           *
        //*************************************************


        // Continuaci�n con el List view de las noticias

        // Obtener la lista
        listView = (ListView)findViewById(R.id.noticias_listView);

        Toast toastcargado = Toast.makeText(context, "Cargando las noticias...", Toast.LENGTH_LONG);
        // Seg�n he le�do se debe hacer esto para que el toast se vea, si pones el rat�n encima de getBaseContext() te sale en la ayuda que necesita un show
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
            Log.i(TAG, "La conexi�n a internet no est� disponible");
            adapter= new FeedAdapter(
                    this,
                    FeedDatabase.getInstance(this).obtenerEntradas(),
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            listView.setAdapter(adapter);

            Toast toastnointernet = Toast.makeText(context, "No hay conexi\u00f3n a Internet", Toast.LENGTH_SHORT);
            // Segun he leido se debe hacer esto para que el toast se vea, si pones el rat�n encima de getBaseContext() te sale en la ayuda que necesita un show
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

                // Nuevo intent expl�cito
                Intent i = new Intent(NoticiasActivity.this, ArticuloActivity.class);

                // Setear url
                i.putExtra("url-extra", url);

                // Iniciar actividad
                startActivity(i);
            }
        });
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
            // Si se pulsa sobre el botón de Home (Navigation Drawer tres líneas)
            // Si pones "case android.R.id.home:" si funciona pero si pones "case R.id.home:" NO FUNCIONA
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            // Si se pulsa sobre el botón de Settings (tres puntos)
            case R.id.action_settings:
                //openSettings();
                Dialog dialog = new Dialog(NoticiasActivity.this);
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

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //textView = (TextView) findViewById(R.id.textView);
                        Uri uri = Uri.parse("");
                        Intent gotoweb = new Intent();

                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_listado_noticias:
                                menuItem.setChecked(true);
                                //textView.setText(menuItem.getTitle());
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_facebook:
                                menuItem.setChecked(true);
                                Toast.makeText(NoticiasActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                uri = Uri.parse("https://www.facebook.com/PortalVallecas");
                                gotoweb = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(gotoweb);
                                return true;
                            case R.id.item_navigation_drawer_twitter:
                                menuItem.setChecked(true);
                                Toast.makeText(NoticiasActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                uri = Uri.parse("https://twitter.com/PortalVallecas");
                                gotoweb = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(gotoweb);
                                return true;
                            case R.id.item_navigation_drawer_googleplus:
                                menuItem.setChecked(true);
                                Toast.makeText(NoticiasActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                uri = Uri.parse("https://plus.google.com/+PortalvallecasEs");
                                gotoweb = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(gotoweb);
                                return true;
                            case R.id.item_navigation_drawer_ajustes:
                                menuItem.setChecked(true);
                                //textView.setText(menuItem.getTitle());
                                Toast.makeText(NoticiasActivity.this, "Abriendo " + menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                //Comento esto pero debería llevar a una nueva activity de las Settings
                                //Intent intent = new Intent(NoticiasActivity.this, SettingsActivity.class);
                                //startActivity(intent);
                                return true;
                            case R.id.item_navigation_drawer_correo:
                                menuItem.setChecked(true);
                                Toast.makeText(NoticiasActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent = new Intent(NoticiasActivity.this, FormularioActivity.class);
                                startActivity(intent);
                                return true;
                        }
                        return true;
                    }
                });
    }
}


