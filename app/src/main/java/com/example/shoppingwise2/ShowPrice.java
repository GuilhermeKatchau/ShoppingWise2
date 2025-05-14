package com.example.shoppingwise2;

import android.os.Bundle;
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

public class ShowPrice extends AppCompatActivity {

    private TextView priceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_price);

        // Lida com insets (barras do sistema)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        priceTextView = findViewById(R.id.priceTextView);

        // Recebe o código de barras da atividade anterior
        String barcode = getIntent().getStringExtra("barcode");

        if (barcode != null && !barcode.isEmpty()) {
            priceTextView.setText("A procurar: " + barcode);
            fetchProductInfo(barcode);
        } else {
            priceTextView.setText("Erro: código de barras não recebido.");
        }
    }



    private void fetchProductInfo(String barcode) {
        new Thread(() -> {
            try {
                String apiKey = "e58249472ff73d22735b840bfe1c1a2d7e94bec9dcfbda5baaa6bad4673eefd8"; // <- SUBSTITUI AQUI
                String url = "https://serpapi.com/search.json?q=" + barcode + "&engine=google_shopping&api_key=" + apiKey;

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();

                    JSONObject json = new JSONObject(responseBody);
                    JSONArray products = json.optJSONArray("shopping_results");

                    if (products != null && products.length() > 0) {
                        JSONObject firstProduct = products.getJSONObject(0);
                        String title = firstProduct.optString("title", "Sem título");
                        String preco = firstProduct.optString("price", "Preço desconhecido");

                        runOnUiThread(() -> priceTextView.setText("Produto: " + title + "\nPreço: " + preco));
                    } else {
                        runOnUiThread(() -> priceTextView.setText("Produto não encontrado."));
                    }
                } else {
                    runOnUiThread(() -> priceTextView.setText("Erro na resposta da API."));
                }

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> priceTextView.setText("Erro ao chamar a API: " + e.getMessage()));
            }
        }).start();
    }
}
