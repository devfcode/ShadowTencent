package com.maniu.shadowtencent;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.maniu.shadowtencent.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "plugin_test";

    // Used to load the 'shadowtencent' library on application startup.
    static {
        System.loadLibrary("shadowtencent");
    }

    private ActivityMainBinding binding;

    public static final int PERMISSION_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        System.out.println(stringFromJNI());

        checkPermission();

        PluginManagerImpl.getInstance().setContext(this);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_STORAGE);
        }
    }

    public void load(View view) {
        String filesDir = this.getCacheDir().getAbsolutePath();
        String apkPath = filesDir + File.separator + "plugina.apk";
//        String apkPath = filesDir + File.separator + "checkenv.apk";
        Log.i(TAG, apkPath + "\tfileexist:" + new File(apkPath).exists());

        PluginManagerImpl.getInstance().loadPath(apkPath);
//        File file = new File(Environment.getExternalStorageDirectory(),"plugina.apk");
//        Log.e(TAG,file.getAbsolutePath());
//        if(file.exists()) {
//            PluginManagerImpl.getInstance().loadPath(file.getAbsolutePath());
//        }else {
//            Log.e(TAG,file.getAbsolutePath() + "\t not exist");
//        }
    }

    public void click(View view) {
        Intent intent = new Intent(this, PluginContainerActivity.class);
        startActivity(intent);
    }

    /**
     * A native method that is implemented by the 'shadowtencent' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
}