package team1.fpoly.duan_n1_17303;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingScreen extends AppCompatActivity {
    int Check = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Check = 1;
                Intent intent = new Intent(LoadingScreen.this, LoginActivity.class);
                startActivity(intent);
            }
        },5000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Check==1) finish();
    }
}