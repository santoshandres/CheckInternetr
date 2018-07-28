package info.androidhive.floatingview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance("Start Service Button", "It starts the floating widget and you can move it.."
                ,R.drawable.startservicee, ContextCompat.getColor(getApplicationContext(),R.color.slide1)));

        addSlide(AppIntroFragment.newInstance("Close Button", "It just unvisible the floating widget not the service...So to close properly press the service button"
                ,R.drawable.close, ContextCompat.getColor(getApplicationContext(),R.color.slide2)));


        addSlide(AppIntroFragment.newInstance("Stop Service Button", "It closes all the background service and stop the process."
                ,R.drawable.stopservicee, ContextCompat.getColor(getApplicationContext(),R.color.slide3)));

        setFadeAnimation();
    }
    @Override
    public void onDonePressed(android.support.v4.app.Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }
}

