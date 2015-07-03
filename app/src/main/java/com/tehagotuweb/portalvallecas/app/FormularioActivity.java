package com.tehagotuweb.portalvallecas.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

public class FormularioActivity extends AppCompatActivity {

    private EditText fromEmail = null;
    private EditText emailSubject = null;
    private EditText emailBody = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        // Meterle la toolbar dado que hemos hecho el Theme sin ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        switch (item.getItemId()) {
            case R.id.menu_clear:
                fromEmail.setText("");
                emailBody.setText("");
                emailSubject.setText("");
                break;
            case R.id.menu_send:
                String from = fromEmail.getText().toString();
                String to = "escribenos@portalvallecas.com";
                String subject = emailSubject.getText().toString();
                String message = emailBody.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[] { from });
                email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                // need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Seleccionar un cliente de correo"));

                break;
        }
        return true;
    }
}
