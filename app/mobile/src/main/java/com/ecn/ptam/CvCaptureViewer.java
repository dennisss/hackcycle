package com.ecn.ptam;

import android.content.Context;
import android.util.Log;
import android.view.View;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

/**
 * Created by dennis on 8/15/15.
 */
public class CvCaptureViewer extends JavaCameraView implements CameraBridgeViewBase.CvCameraViewListener2, View.OnClickListener{



    private BaseLoaderCallback  mLoaderCallback;


    public CvCaptureViewer(Context ctx){
        super(ctx, CAMERA_ID_FRONT);

        setCvCameraViewListener(this);

        mLoaderCallback = new BaseLoaderCallback(ctx) {
            @Override
            public void onManagerConnected(int status) {
                switch (status) {
                    case LoaderCallbackInterface.SUCCESS:
                    {
                        Log.i("CVCAPTURE", "OpenCV loaded successfully");
                        enableView();
                        //setOnTouchListener(ColorBlobDetectionActivity.this);
                    } break;
                    default:
                    {
                        super.onManagerConnected(status);
                    } break;
                }
            }
        };

    }

    public void onPause(){
        this.disableView();
    }

    public void onResume(Context ctx)
    {
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, ctx, mLoaderCallback);
    }



    @Override
    public void onCameraViewStarted(int i, int i1) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CvCameraViewFrame cvCameraViewFrame) {
        return cvCameraViewFrame.rgba();
    }

    @Override
    public void onClick(View v) {

    }
}
