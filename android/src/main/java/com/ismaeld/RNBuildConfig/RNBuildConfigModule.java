package com.ismaeld.RNBuildConfig;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class RNBuildConfigModule extends ReactContextBaseJavaModule {
    private Class buildConfigClass;
    private String NAME = "RNBuildConfig";
    private final Map<String, Object> constants = new HashMap<>();

    public RNBuildConfigModule(ReactApplicationContext reactContext, Class buildConfigClass) {
        super(reactContext);

        this.buildConfigClass = buildConfigClass;

        if(constants.isEmpty()) {
            getConstants();
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void getFlavor(Promise p) {

        String value = "";

        try {

            value = this.constants.get("FLAVOR").toString();

        } catch (Exception e) {

            Log.d(NAME, e.getMessage());

        }

        p.resolve(value);

    }

    @ReactMethod
    public void getVersionName(Promise p) {

        String value = "";

        try {

            value = this.constants.get("VERSION_NAME").toString();

        } catch (Exception e) {

            Log.d(NAME, e.getMessage());

        }

        p.resolve(value);

    }

    @ReactMethod
    public void getVersionCode(Promise p) {

        String value = "";

        try {

            value = this.constants.get("VERSION_CODE").toString();

        } catch (Exception e) {

            Log.d(NAME, e.getMessage());

        }

        p.resolve(value);

    }

    @ReactMethod
    public void getConfig(Promise p) {
        p.resolve(this.constants);
    }

    @Override
    public Map<String, Object> getConstants() {

        if(this.constants.isEmpty()) {

            Field[] fields = buildConfigClass.getDeclaredFields();
            for (Field f : fields) {
                try {
                    this.constants.put(f.getName(), f.get(null));
                } catch (IllegalAccessException e) {
                    Log.d(NAME, "Could not access BuildConfig field " + f.getName());
                }
            }

        }

        return this.constants;

    }
}
