package com.nandy.vkchanllenge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nandy.vkchanllenge.ui.PostFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PostFragment.newInstance(this), PostFragment.class.getSimpleName())
                .commit();
    }


    @Override
    public void onBackPressed() {

        PostFragment postFragment = (PostFragment)
                getSupportFragmentManager().findFragmentByTag(PostFragment.class.getSimpleName());

        if (postFragment != null && postFragment.onBackPressed()) {
            return;
        }

        super.onBackPressed();
    }
}
