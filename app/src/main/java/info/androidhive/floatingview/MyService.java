package info.androidhive.floatingview;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MyService extends Service {

    final Handler handler = new Handler();

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
            initializeView(1);
            //Toast.makeText(MyService.this, "You are connected to Internet", Toast.LENGTH_SHORT).show();
        }else{

            initializeView(2);

           // Toast.makeText(MyService.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeView(int y) {
        if (y==1){
                startService(new Intent(getApplicationContext(), FloatingViewService.class).putExtra("STATE",y));
        }else if(y==2) {
            startService(new Intent(getApplicationContext(), FloatingViewService.class).putExtra("STATE",y));

        }
    }
}