package com.tehagotuweb.portalvallecas.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
        //String versionName = BuildConfig.VERSION_NAME;

        Context context = getApplicationContext(); // or activity.getApplicationContext()
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        String versionName = "not available"; // initialize String

        try {
            versionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView versionText = (TextView) findViewById(R.id.numVersion);

        versionText.setText(versionName);
        Log.d("AboutDialogActivity", versionName);

    }
}
