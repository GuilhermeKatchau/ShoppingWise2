package com.example.shoppingwise2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private Button scanButton;
    boolean isAuthenticated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MAIN", "MainActivity iniciou");
        setContentView(R.layout.activity_main);

        scanButton = findViewById(R.id.btn_comecar);

        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        isAuthenticated = preferences.getBoolean("isLoggedIn", false);


        if (!isAuthenticated) {
            if (userJaRegistrado()) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, SignInActivity.class));
                finish();
            }
        }

        scanButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
            startActivity(intent);
        });

    }

   public boolean userJaRegistrado() {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return preferences.getBoolean("ja_registrado", false);
    }
}
