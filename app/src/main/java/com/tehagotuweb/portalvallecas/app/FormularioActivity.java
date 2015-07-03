package com.tehagotuweb.portalvallecas.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    private EditText fromEmail = null;
    private EditText emailSubject = null;
    private EditText emailBody = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        // Aqu� se le mete la toolbar, dado que hemos hecho el Theme sin ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Activamos la opci�n para que muestre la flecha de atras en la ActionBar para volver a la activity PARENT
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // En versiones inferiores a SDK 19 oculto el FrameLayaout que hemos usado para desplazar la toolbar
        if (Build.VERSION.SDK_INT < 19){
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.height = 0;
        }

        Log.d("FormularioActivity", "onCreate");

        fromEmail = (EditText) findViewById(R.id.fromEmail);
        emailSubject = (EditText) findViewById(R.id.subject);
        emailBody = (EditText) findViewById(R.id.emailBody);

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflar el recurso del menu para esta ActionBar
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Aqu� deber�a hacer la opci�n de salir
        switch (item.getItemId()) {

            // Seg�n la web de Google esto seria necesario ademas de definir en el Manifest el PARENT a las activitys de segundo nivel
            // http://developer.android.com/intl/es/training/implementing-navigation/ancestral.html#NavigateUp
            // Segun la web de desarrollador.android no es necesario:
            // http://desarrollador-android.com/desarrollo/formacion/empezar-formacion/anadir-la-action-bar/anadir-botones-de-accion/

            /*case android.R.id.home:
                NavUtils.navigateUpFromSameTask(FormularioActivity.this);
                return true;*/

            case R.id.menu_clear:
                fromEmail.setText("");
                emailBody.setText("");
                emailSubject.setText("");
                return true;

            case R.id.menu_send:
                String from = fromEmail.getText().toString();
                String to = "escribenos@portalvallecas.com";
                String subject = emailSubject.getText().toString();
                String message = emailBody.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{from});
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                // need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Seleccionar un cliente de correo"));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
