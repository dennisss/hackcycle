package me.denniss.hackcycle;

import java.net.MalformedURLException;

import org.json.*;

import android.location.Location;
import android.util.Log;
import io.socket.*;

public class Socket {

    public static Socket inst;


	public SocketIO socket;

	private static String TAG = "hackcycle";
	
	public Socket(){
		try {
			socket = new SocketIO("http://cycle.denniss.me:8080/"); //"http://192.168.16.29:3000/");

			socket.connect(new IOCallback() {
				@Override
				public void onMessage(JSONObject json, IOAcknowledge ack) {
					try {
						Log.i(TAG, "Server said:" + json.toString(2));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onMessage(String data, IOAcknowledge ack) {
					System.out.println("Server said: " + data);
				}

				@Override
				public void onError(SocketIOException socketIOException) {
					Log.i(TAG, "an Error occured");
					socketIOException.printStackTrace();
				}

				@Override
				public void onDisconnect() {
					Log.i(TAG, "Connection terminated.");
				}

				@Override
				public void onConnect() {
					Log.i(TAG, "Connection established");
				}

				@Override
				public void on(String event, IOAcknowledge ack, Object... args) {
					System.out.println("Server triggered event '" + event + "'");
				}
			});

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	public void send_data(Location loc){

		JSONObject obj = new JSONObject();

		try {
			obj.put("lat", loc.getLatitude());
			obj.put("lon", loc.getLongitude());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONArray arr = new JSONArray();
		arr.put(obj);

		socket.emit("data", arr);

		return;

	}

/*
	public void send(float angle, float throttle, float dx, float dy){
		
		Log.i(TAG, "Send");
		
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("orient", angle);
			obj.put("throt", throttle);
			obj.put("dx", dx);
			obj.put("dy", dy);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Emits an event to the server.
		socket.emit("update", obj);
		
	}
*/
	
}
