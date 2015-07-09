package com.tehagotuweb.portalvallecas.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tehagotuweb.portalvallecas.app.Mail.GMailSender;

public class FormularioActivity extends AppCompatActivity {

    private EditText fromEmail = null;
    private EditText emailSubject = null;
    private EditText emailBody = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        // Aquí se le mete la toolbar, dado que hemos hecho el Theme sin ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Activamos la opción para que muestre la flecha de atras en la ActionBar para volver a la activity PARENT
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // En versiones inferiores a SDK 19 oculto el FrameLayaout que hemos usado para desplazar la toolbar
        if (Build.VERSION.SDK_INT < 19){
            FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
            ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.height = 0;
        }

        Log.d("FormularioActivity", "onCreate");

        fromEmail = (EditText) findViewById(R.id.fromEmail);
        fromEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailSubject = (EditText) findViewById(R.id.subject);
        emailSubject.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
        emailBody = (EditText) findViewById(R.id.emailBody);

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflar el recurso del menu para esta ActionBar
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Aquí debería hacer la opción de salir
        switch (item.getItemId()) {

            // Según la web de Google esto seria necesario ademas de definir en el Manifest el PARENT a las activitys de segundo nivel
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

                /* ANTES

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{from});
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                // need this to prompts email client only
                email.setType("message/rfc822");

                try {
                    startActivity(Intent.createChooser(email, "Seleccionar un cliente de correo"));
                }
                catch (Exception e){
                    Toast.makeText(FormularioActivity.this,"No dispone de ninguna cuenta de correo en su móvil.", Toast.LENGTH_SHORT).show();
                }

                */

                try {
                    GMailSender sender = new GMailSender("latabernadedominguez@gmail.com", "roberytino");
                    sender.sendMail(subject,message,from,to);
                    Toast.makeText(getApplicationContext(), "Mensaje enviado", Toast.LENGTH_LONG).show();
                    Log.d("FormularioActivity", "De: " + from);
                    Log.d("FormularioActivity", "Para: " + to);
                    Log.d("FormularioActivity", "Asunto: " + subject);
                    Log.d("FormularioActivity", "Mensaje: " + message);
                    fromEmail.setText("");
                    emailBody.setText("");
                    emailSubject.setText("");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
