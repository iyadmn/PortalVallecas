package com.tehagotuweb.portalvallecas.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AboutDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_dialog);

        //Este es para la version code
        //int versionCode = BuildConfig.VERSION_CODE;

        //Este es para la version name
        String versionName = BuildConfig.VERSION_NAME;

        TextView versionText = (TextView) findViewById(R.id.version);
        versionText.setText("Version: " + versionName);
        Log.d("AboutDialogActivity", versionName);
    }
}
