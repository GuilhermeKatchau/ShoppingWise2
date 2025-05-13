package com.example.shoppingwise2;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Pesquisa extends AppCompatActivity{
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

        searchEditText = findViewById(R.id.searchEditText);
        resultTextView = findViewById(R.id.resultTextView);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                resultTextView.setText("A procurar: " + query);
                fetchProductInfo(query);
            } else {
                resultTextView.setText("Por favor insira um termo de pesquisa.");
            }
        });
    }

    private void fetchProductInfo(String query) {
        new Thread(() -> {
            try {
                String apiKey = "e58249472ff73d22735b840bfe1c1a2d7e94bec9dcfbda5baaa6bad4673eefd8";
                String url = "https://serpapi.com/search.json?q=" + query + "&engine=google_shopping&api_key=" + apiKey;

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();

                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    JSONObject json = new JSONObject(responseBody);
                    JSONArray products = json.optJSONArray("shopping_results");

                    if (products != null && products.length() > 0) {
                        JSONObject firstProduct = products.getJSONObject(0);
                        String title = firstProduct.optString("title", "Sem título");
                        String price = firstProduct.optString("price", "Preço desconhecido");

                        runOnUiThread(() -> resultTextView.setText("Produto: " + title + "\nPreço: " + price));
                    } else {
                        runOnUiThread(() -> resultTextView.setText("Produto não encontrado."));
                    }
                } else {
                    runOnUiThread(() -> resultTextView.setText("Erro na resposta da API."));
                }

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> resultTextView.setText("Erro ao chamar a API: " + e.getMessage()));
            }
        }).start();
    }
}
