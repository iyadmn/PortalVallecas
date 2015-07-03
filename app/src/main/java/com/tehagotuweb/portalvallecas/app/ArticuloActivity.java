package com.tehagotuweb.portalvallecas.app;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;


// Recordar que hay que declarar siempre en el AndroidManifest todas las Activitys
public class ArticuloActivity extends Activity {

    /* Etiqueta de depuración */
    private static final String TAG = ArticuloActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Ocultar la barra del titulo */
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_articulo);

        // En versiones inferiores a SDK 19 oculto el FrameLayout que hemos usado para desplazar la toolbar
        if (Build.VERSION.SDK_INT < 19){
            // Hago que el FrameLayout tenga 0 de alto
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.height = 0;

            //NOTA - AQUI HABRIA QUE CONSEGUIR QUE EL WEBVIEW PIERDA EL MARGIN DE 25
        }

        // Recuperar url
        String urlExtra = getIntent().getStringExtra("url-extra");

        // Obtener WebView
        WebView webview = (WebView)findViewById(R.id.articulo_Webview);

        // Habilitar Javascript en el renderizado
        webview.getSettings().setJavaScriptEnabled(true);

        // Transmitir localmente
        webview.setWebViewClient(new WebViewClient());

        // Cargar el contenido de la url
        webview.loadUrl(urlExtra);


    }

}
