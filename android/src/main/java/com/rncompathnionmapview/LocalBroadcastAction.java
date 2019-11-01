package com.rncompathnionmapview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

public class LocalBroadcastAction {
    public static final String ACTION_REQUESTVIEW = "requestView";
    public static final String EXTRA_VIEWNAME = "viewName";
    public static final String EXTRA_INIT_PARAMS = "initialParameters";
    public static final String VIEW_MAP = "mapView";


    public static void requestView(Context context, String viewName) {
        Intent intent = new Intent(ACTION_REQUESTVIEW);
        intent.putExtra(EXTRA_VIEWNAME, viewName);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void requestView(Context context, String viewName, Bundle initialParameters) {
        Intent intent = new Intent(ACTION_REQUESTVIEW);
        intent.putExtra(EXTRA_VIEWNAME, viewName);
        intent.putExtra(EXTRA_INIT_PARAMS, initialParameters);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
