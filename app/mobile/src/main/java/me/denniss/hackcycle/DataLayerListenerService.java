package me.denniss.hackcycle;

/*
*   Original code at https://github.com/upost/MyHeartbeat/blob/master/wear/src/main/java/de/ludetis/android/myheartbeat/WearActivity.java
* */


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

public class DataLayerListenerService extends WearableListenerService {

    private static final String LOG_TAG = "WearableListener";
    public static final String HEARTBEAT = "HEARTBEAT";

    private static Handler handler;
    private static int currentValue=0;

    public static Handler getHandler() {
        return handler;
    }

    public static void setHandler(Handler handler) {
        DataLayerListenerService.handler = handler;
        // send current value as initial value.
        if(handler!=null)
            handler.sendEmptyMessage(currentValue);
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);

        String id = peer.getId();
        String name = peer.getDisplayName();

        Log.d(LOG_TAG, "Connected peer name & ID: " + name + "|" + id);
    }
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.d(LOG_TAG, "received a message from wear: " + messageEvent.getPath());
        // save the new heartbeat value
        currentValue = Integer.parseInt(messageEvent.getPath());
        if(handler!=null) {
            // if a handler is registered, send the value as new message
            handler.sendEmptyMessage(currentValue);
        }
    }


}
