package info.androidhive.floatingview;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MyService extends Service {

    final Handler handler = new Handler();
    private WindowManager mWindowManager;
    private View mFloatingView;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
     }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkConnection();
                handler.postDelayed(this, 1000);
            }
        },1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        //if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
        //if (mFloatingView1 != null) mWindowManager.removeView(mFloatingView1);
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    public void checkConnection(){

        if(isOnline()){
            // startService(new Intent(vv, FloatingViewService.class));
           // initializeView(1);
            // Toast.makeText(MyService.this, "You are connected to Internet", Toast.LENGTH_SHORT).show();
            //Inflate the floating view layout we created
            //stopSelf();
            //mWindowManager.removeView(mFloatingView);
            if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
            mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);

            //Add the view to the window.
        }else{
            //stopSelf();
            //mWindowManager.removeView(mFloatingView);
            if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
            mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widgettt, null);
            //initializeView(2);
           // Toast.makeText(MyService.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;
        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 50;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);
    }

//    private void initializeView(int y) {
//        if (y==1){
//                startService(new Intent(getApplicationContext(), FloatingViewService.class).putExtra("STATE",y));
//        }else if(y==2) {
//            startService(new Intent(getApplicationContext(), FloatingViewService.class).putExtra("STATE",y));
//
//        }
//    }
}