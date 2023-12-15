package com.maniu.shadowtencent;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManagerImpl {

    // 单例
    private static final PluginManagerImpl ourInstance = new PluginManagerImpl();
    public static PluginManagerImpl getInstance() {
        return ourInstance;
    }

    // 插件的资源
    private Resources resources;

    // 插件的 class
    private DexClassLoader dexClassLoader;
    // 上下文
    private Context context;

    public void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

    public Resources getResources() {
        return resources;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    // 插件的路径传递进来    解析插件
    public void loadPath(String path) {
        // dexClassLoader 赋值
        File dexOutFile = context.getDir("dex", Context.MODE_PRIVATE);

//        ClassLoader baseParent = ClassLoader.getSystemClassLoader().getParent();
//        dexClassLoader = new DexClassLoader(path,
//                dexOutFile.getAbsolutePath(),
//                null,
//                baseParent);
        dexClassLoader = new DexClassLoader(path,
                dexOutFile.getAbsolutePath(),
                null,
                context.getClassLoader());

        // resource ---> apk
        try {
//            AssetManager assetManager = new AssetManager(); // 被系统隐藏
            AssetManager assetManager = AssetManager.class.newInstance();// AssetManager 被系统隐藏只能通过反射获取
            // 关联到插件的 asset 资源
//            assetManager.addAssetPath(path); // 被系统隐藏
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, path);

            resources = new Resources(assetManager,
                    context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
