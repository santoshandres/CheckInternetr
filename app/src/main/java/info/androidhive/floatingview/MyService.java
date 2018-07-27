package info.androidhive.floatingview;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyService extends Service {

    private boolean isdataavailable() {
        MyMusicc m = new MyMusicc();
        m.execute();
        return prefsq.getBoolean("ck", false);
    }

    public class MyMusicc extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            String  s1="nul";
            MyApp myApp = new MyApp();
                       Log.d("sdfgh","dgdgdd");

            try {
                String reslt = myApp.getdetils("http://echo.jsontest.com/key/value/one/two");
                editor.putBoolean("ck",true).commit();
                Log.d("qaxcv",reslt);
            }catch (NullPointerException e){
                editor.putBoolean("ck",false).commit();
            }

            return s1;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

    public void checkConnection() {
        if (mFloatingView != null) {
            if (isdataavailable()) {
                imageView.setImageResource(R.drawable.green);
            } else {
                imageView.setImageResource(R.drawable.red);
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
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
        }, 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
        super.onDestroy();
    }

    private boolean isViewCollapsed() {
        return mFloatingView == null || mFloatingView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        prefsq = getSharedPreferences("san",MODE_PRIVATE);
        editor=prefsq.edit();

        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);
        imageView = (ImageView) mFloatingView.findViewById(R.id.collapsed_iv);
        imageView.setAlpha(90);
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;
        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);
        final View collapsedView = mFloatingView.findViewById(R.id.collapse_view);

        ImageView closeButton = (ImageView) mFloatingView.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //collapsedView.setVisibility(View.VISIBLE);
                //// if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
                //stopSelf();
            }
        });

        //Drag and move floating view using user's touch action.
        mFloatingView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);


                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (Xdiff < 10 && Ydiff < 10) {
                            if (isViewCollapsed()) {
                                //When user clicks on the image view of the collapsed layout,
                                //visibility of the collapsed layout will be changed to "View.GONE"
                                //and expanded view will become visible.
                                collapsedView.setVisibility(View.GONE);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);


                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });
    }

    final Handler handler = new Handler();
    private WindowManager mWindowManager;
    private View mFloatingView;
    WindowManager.LayoutParams params;
    ImageView imageView;
    SharedPreferences prefsq;
    SharedPreferences.Editor editor;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}