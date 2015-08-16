package me.denniss.hackcycle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ecn.ptam.PTAMActivity;
import com.google.android.gms.appdatasearch.GetRecentContextCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    Socket sock;

    LocationSensor locSensor;
    InertialSensor imuSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_main);

        Socket.inst = new Socket();

        locSensor = new LocationSensor(this);

        //imuSensor = new InertialSensor(this);


        //Intent in = new Intent(this, PTAMActivity.class);
        //startActivity(in);


        cameraView = (CameraBridgeViewBase) findViewById(R.id.camera_view);
        cameraView.setCvCameraViewListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }











    private CameraBridgeViewBase cameraView;



    private int screen_width;
    private int screen_height;


    private static final String TAG = "dragon-hack";

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    cameraView.enableView();

                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };


    public static Bitmap lastframe;


    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat frameBuffer = inputFrame.rgba();


        lastframe = Bitmap.createBitmap(frameBuffer.width(), frameBuffer.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(frameBuffer, lastframe);

        return frameBuffer;
    }




    @Override
    public void onPause()
    {
        DataLayerListenerService.setHandler(null);

        super.onPause();
        if (cameraView != null)
            cameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);

        DataLayerListenerService.setHandler(handler);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(cameraView != null)
            cameraView.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        //frameBuffer = new Mat(height, width, CvType.CV_8UC4);

        screen_width = width;
        screen_height = height;

        //detector = new ColorDetector();

    }

    @Override
    public void onCameraViewStopped() {
        //frameBuffer.release();
    }







    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // message from API client! message from wear! The contents is the heartbeat.

            Log.i("heart", Integer.toString(msg.what));
            //if(textView!=null)
            //    textView.setText(Integer.toString(msg.what));


            {
                JSONObject obj = new JSONObject();

                try {
                    obj.put("bpm", msg.what);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Socket.inst.socket.emit("beat", obj);
            }



           // if(msg.what > 100)
            {
                Log.i("heart", "send image");


                JSONObject obj = new JSONObject();

                try {
                    obj.put("lat", LocationSensor.lastlocation.getLatitude());
                    obj.put("lon", LocationSensor.lastlocation.getLongitude());

                    obj.put("image_data", encodeTobase64(lastframe));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JSONArray arr = new JSONArray();
                arr.put(obj);

                Socket.inst.socket.emit("data", arr);


            }


        }
    };







    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        //Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }


}
