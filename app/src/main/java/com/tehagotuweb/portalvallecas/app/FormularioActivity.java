package com.tehagotuweb.portalvallecas.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tehagotuweb.portalvallecas.app.Mail.GMailSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // Modo Developer - Una manera fácil de evitar la excepción es insertar el siguiente código
        // La manera correcta es hacer una nueva hebra (thread) o hacer un metodo AsyncTask
        // StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        // StrictMode.setThreadPolicy(policy);

        fromEmail = (EditText) findViewById(R.id.fromEmail);
        // Al poner esto no coge correctamente el inputType asignado a este EditText del layout del formulario
        //fromEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailSubject = (EditText) findViewById(R.id.subject);
        // Al poner esto no coge correctamente el inputType asignado a este EditText del layout del formulario
        //emailSubject.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
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
                // Comprobación de que es un correo
                if (!isValidEmail(from)) {
                    fromEmail.setError("Correo incorrecto");
                }
                String to = "escribenos@portalvallecas.com";
                String subject = emailSubject.getText().toString();
                String message = emailBody.getText().toString();

                if (isValidEmail(from)) {
                    try {
                        GMailSender sender = new GMailSender("portalvallecas@gmail.com", "saraiyadapp");
                        sender.sendMail(from,subject,message,from,to);
                        // Escondo el Teclado
                        hideSoftKeyboard();
                        // Muestro el Toast de mensaje enviado
                        Toast.makeText(getApplicationContext(), "Mensaje enviado", Toast.LENGTH_LONG).show();
                        // Log de los datos recogidos
                        Log.d("FormularioActivity", "De: " + from);
                        Log.d("FormularioActivity", "Para: " + to);
                        Log.d("FormularioActivity", "Asunto: " + subject);
                        Log.d("FormularioActivity", "Mensaje: " + message);
                        // Vacio las casillas
                        fromEmail.setText("");
                        emailBody.setText("");
                        emailSubject.setText("");
                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Revisa la direcci\u00f3n de correo", Toast.LENGTH_LONG).show();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
