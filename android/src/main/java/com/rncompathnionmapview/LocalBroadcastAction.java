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
    public static final String ACTION_BACKPRESS_TRIGGERED = "backpressTriggered";
    public static final String ACTION_REQUEST_BACKPRESS = "requestBackpress";

    public static final String ACTION_REQUESTVIEW = "requestView";
    public static final String EXTRA_VIEWNAME = "viewName";
    public static final String EXTRA_INIT_PARAMS = "initialParameters";
    public static final String VIEW_ACTIVATION = "activationView";
    public static final String VIEW_HOWTOUSE = "howToUseView";
    public static final String VIEW_HOME = "homeView";
    public static final String VIEW_MAP = "mapView";
    public static final String VIEW_SAVE_LOCATION = "saveLocationView";
    public static final String VIEW_SETTING = "settingView";
    public static final String VIEW_FEEDBACK = "feedbackView";
    public static final String VIEW_SERVICE_DIRECTORY = "serviceDirectoryView";

    public static final String ACTION_REQUESTCLOSEVIEW = "requestCloseview";

    public static final String JSEVENT_DATA_INIT_DONE = "dataInitializationFinished";
    public static final String JSEVENT_VIEW_CHANGE = "viewChanged";
    public static final String JSEVENT_LANGUAGE_CHANGE = "appLanguageChanged";

    private static final Object synobjBackpressReceivers = new Object();
    private static int cntBackpressReceivers = 0;
    private static List<BroadcastReceiver> backpressReceivers = new ArrayList<>();

    public static void requestBackpress(Context context) {
        LocalBroadcastManager
                .getInstance(context)
                .sendBroadcast(new Intent(ACTION_REQUEST_BACKPRESS));
    }

    public static void registerBackpressListener(Context context, BroadcastReceiver receiver) {
        synchronized (synobjBackpressReceivers) {
            if (backpressReceivers.contains(receiver)) {
                return;
            }

            ++cntBackpressReceivers;
            backpressReceivers.add(receiver);

            LocalBroadcastManager
                    .getInstance(context.getApplicationContext())
                    .registerReceiver(receiver, new IntentFilter(ACTION_BACKPRESS_TRIGGERED));
        }
    }

    public static void unregisterBackpressListener(Context context, BroadcastReceiver receiver) {
        synchronized (synobjBackpressReceivers) {
            if (!backpressReceivers.contains(receiver)) {
                return;
            }

            --cntBackpressReceivers;
            backpressReceivers.remove(receiver);

            LocalBroadcastManager
                    .getInstance(context.getApplicationContext())
                    .unregisterReceiver(receiver);
        }
    }

    public static int getBackpressReceiverCount() {
        synchronized (synobjBackpressReceivers) {
            return cntBackpressReceivers;
        }
    }

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

    public static void requestCloseView(Context context) {
        Intent intent = new Intent(ACTION_REQUESTCLOSEVIEW);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
