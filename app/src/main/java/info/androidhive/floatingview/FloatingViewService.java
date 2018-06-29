package info.androidhive.floatingview;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class FloatingViewService extends Service {

    private WindowManager mWindowManager;
    private View mFloatingView,mFloatingView2;

    public FloatingViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle extras = intent.getExtras();

        if(extras == null) {
            Log.d("Service","null");
        } else {
            Log.d("Service","not null");
            String from = extras.get("STATE").toString();

            if(from.equalsIgnoreCase("1")){
                StartListenLocation(1);
            }else if(from.equalsIgnoreCase("2")) {
                StartListenLocation(2);

            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void StartListenLocation(int g) {

        if (g == 1) {

            //Inflate the floating view layout we created
            mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);
            //Add the view to the window.
            final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

            //Specify the view position
            params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
            params.x = 0;
            params.y = 100;

            //Add the view to the window
            mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            mWindowManager.addView(mFloatingView, params);
        }
        else if (g==2){

            //Inflate the floating view layout we created
            mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widgettt, null);
            //Add the view to the window.
            final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

            //Specify the view position
            params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
            params.x = 0;
            params.y = 100;

            //Add the view to the window
            mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            mWindowManager.addView(mFloatingView, params);

        }
    }
    @Override
    public void onCreate() {
        super.onCreate();





    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }
}
