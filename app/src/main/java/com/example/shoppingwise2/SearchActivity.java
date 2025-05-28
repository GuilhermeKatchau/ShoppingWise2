package com.example.shoppingwise2;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class SearchActivity extends AppCompatActivity{
    private EditText searchEditText;
    private TextView resultTextView;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_history) {
                startActivity(new Intent(SearchActivity.this, HistoryActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(SearchActivity.this, ProfileActivity.class));
                return true;
            } else if (itemId == R.id.nav_search) {
             return true;
            } else if (itemId == R.id.nav_scan) {
                startActivity(new Intent(SearchActivity.this, ScannerActivity.class));
                return true;
            }
            return false;
        });



        bottomNavigationView.setSelectedItemId(R.id.nav_search);


        searchEditText = findViewById(R.id.searchEditText);
        resultTextView = findViewById(R.id.resultTextView);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                resultTextView.setText("A procurar: " + query);
                Intent intent = new Intent(SearchActivity.this, ShowPriceActivity.class);
                intent.putExtra("produto", query);
                startActivity(intent);
            } else {
                resultTextView.setText("Por favor insira um termo de pesquisa.");
            }
        });
    }
}
