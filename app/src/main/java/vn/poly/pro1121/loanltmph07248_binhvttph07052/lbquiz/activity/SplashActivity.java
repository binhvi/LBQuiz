package vn.poly.pro1121.loanltmph07248_binhvttph07052.lbquiz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Start home activity
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        //close splash activity
        finish();
    }
}
