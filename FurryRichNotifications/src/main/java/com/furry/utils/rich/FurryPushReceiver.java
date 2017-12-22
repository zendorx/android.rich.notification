package com.furry.utils.rich;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import org.json.JSONObject;

public class FurryPushReceiver extends WakefulBroadcastReceiver {

    private static String TAG = "GOW.BroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive");
        ComponentName comp = new ComponentName(context.getPackageName(), FurryPushBuilderService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
        this.abortBroadcast();
    }
}
