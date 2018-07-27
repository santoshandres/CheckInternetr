package info.androidhive.floatingview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
                if (isFirstStart) {
                    final Intent i = new Intent(MainActivity.this, IntroActivity.class);
                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            startActivity(i);
                        }
                    });
                    SharedPreferences.Editor e = getPrefs.edit();
                    e.putBoolean("firstStart", false);
                    e.apply();
                }
            }
        });
        t.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode!=RESULT_OK){
            Toast.makeText(this,
                    "Draw over other app permission not available. Closing the application",
                    Toast.LENGTH_SHORT).show();

            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mn,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String s=item.getTitle().toString();
        if (s.equals("AboutUs")){
            Uri uri=Uri.parse("http://www.androidmanifester.com/");
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
        if (s.equals("ContactUs")){
            AlertDialog.Builder alrt=new AlertDialog.Builder(MainActivity.this);
            alrt.setTitle("Contact Details");
            alrt.setMessage("ranjitheinstin@gmail.com"+"/n"+"santoshandres26@gmail.com");
            alrt.setIcon(android.R.drawable.ic_menu_share);
            alrt.show();
        }
        if (s.equals("Contribution")){
            Uri uri=Uri.parse("https://www.instagram.com/isantoshandres/?hl=en");
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
        if (s.equals("Credit")){
            Uri uri=Uri.parse("https://www.instagram.com/ranjithstar256/?hl=en");
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void strt(View view) {
        Intent i=new Intent(MainActivity.this,MyService.class);
        startService(i);
    }

    public void stp(View view) {
        Intent i=new Intent(MainActivity.this,MyService.class);
        stopService(i);
    }
}
