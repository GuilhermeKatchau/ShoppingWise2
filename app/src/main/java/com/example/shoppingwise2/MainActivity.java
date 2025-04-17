package com.example.shoppingwise2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
   // private Button scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*
      scanButton = findViewById(R.id.scan_button);

        // Abre diretamente a ScannerActivity ao clicar no botão
        scanButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
            startActivity(intent);
        });
      * */
    }
}
