package com.example.shoppingwise2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private Button beginButton;
    boolean isAuthenticated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beginButton = findViewById(R.id.btn_comecar);

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

        beginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

    }

   public boolean userJaRegistrado() {
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return preferences.getBoolean("SignedIn", false);
    }
}
