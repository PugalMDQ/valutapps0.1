package com.vibro.care;

import android.app.Application;
import android.content.Intent;

import com.onesignal.OneSignal;
import com.vibro.care.Activity.JobDetailsActivity;
import com.vibro.care.Config.Config;

import org.json.JSONException;
import org.json.JSONObject;

public class App extends Application {

    private static final String ONESIGNAL_APP_ID = "48ef6dcb-e4b7-4ed4-b093-6c3849e0e80e";
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        OneSignal.setNotificationOpenedHandler(
                result -> {
                    JSONObject data = result.getNotification().getAdditionalData();

                    if (data != null) {
                        try {
                            String id = data.getString("id");

                            Intent intent = new Intent(mInstance, JobDetailsActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.putExtra(Config.id, id);
                            startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }
}