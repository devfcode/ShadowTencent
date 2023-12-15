package com.maniu.shadowtencent;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.File;

public class PluginContainerActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        XmlResourceParser xmlResourceParser = getResources().getLayout(R.layout.activity_main);
//        final Resources res = this.getResources();
        setContentView(R.layout.activity_main);

//        try {
//            Class clazz = getClassLoader().loadClass("com.maniu.plugina.ShadowActivity");
//            Object o = clazz.newInstance();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManagerImpl.getInstance().getDexClassLoader();
    }

    // getResources() 方法 可以做到将 资源 进行重定向
    @Override
    public Resources getResources() {
        return PluginManagerImpl.getInstance().getResources();
    }
}
