package com.example.shoppingwise2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowPrice extends AppCompatActivity {

    private RecyclerView produtosRecyclerView;
    private ComparisonAdapter adapter;
    private SupabaseApi api;
    

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
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_history) {
                startActivity(new Intent(ShowPrice.this, HistoricoSearch.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(ShowPrice.this, ProfileActivity.class));
                return true;
            } else if (itemId == R.id.nav_search) {
                startActivity(new Intent(ShowPrice.this, Pesquisa.class));
                return true;
            } else if (itemId == R.id.nav_scan) {
                startActivity(new Intent(ShowPrice.this, ScannerActivity.class));
                return true;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.nav_preco);

        produtosRecyclerView = findViewById(R.id.priceTextView);
        produtosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ComparisonAdapter(new ArrayList<>(), this);
        produtosRecyclerView.setAdapter(adapter);
        api = RetrofitClient.getInstance().create(SupabaseApi.class);

        // Recebe o código de barras da atividade anterior
        String barcode = getIntent().getStringExtra("barcode");
        String produtoPesquisado = getIntent().getStringExtra("produto");

        if (barcode != null && !barcode.isEmpty()) {
            fetchProductInfo(barcode);
        } else if (produtoPesquisado != null && !produtoPesquisado.isEmpty()) {
            fetchProductInfo(produtoPesquisado);
        } else {
            Toast.makeText(this, "Nenhum código de barras ou produto recebido", Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        }
    }
    private void fetchProductInfo(String query) {
        ExecutorService executor = Executors.newSingleThreadExecutor(); // Usa thread pool
        executor.execute(() -> {
            try {
                String apiKey = "e58249472ff73d22735b840bfe1c1a2d7e94bec9dcfbda5baaa6bad4673eefd8"; // <- SUBSTITUI AQUI
                String url = "https://serpapi.com/search.json?q=" + query + "&engine=google_shopping&api_key=" + apiKey;

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                okhttp3.Response response = client.newCall(request).execute();

                if (!response.isSuccessful() || response.body() == null) {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Erro na API", Toast.LENGTH_LONG).show();
                        finish();
                    });
                    return;
                }

                String responseBody = response.body().string();
                JSONObject json = new JSONObject(responseBody);
                JSONArray products = json.optJSONArray("shopping_results");

                runOnUiThread(() -> {
                    List<Produto> novosProdutos = processarProdutos(products, query);
                    adapter.atualizarLista(novosProdutos);
                });

            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private List<Produto> processarProdutos(JSONArray products, String query) {
        List<Produto> resultado = new ArrayList<>();

        if (products == null || products.length() == 0) {
            return resultado;
        }

        try {
            JSONObject primeiroItem = products.getJSONObject(0);
            Produto produto = new Produto(
                    primeiroItem.optString("title", "Produto desconhecido"),
                    query,
                    primeiroItem.optString("thumbnail", "")
            );

            // Processa todos os itens para preços por loja
            for (int i = 0; i < products.length(); i++) {
                try {
                    JSONObject item = products.getJSONObject(i);
                    produto.addPrecoLoja(
                            item.optString("source", "Loja desconhecida"),
                            item.optString("price", "Preço indisponível"),
                            item.optString("thumbnail", "")
                    );
                } catch (JSONException e) {
                    Log.e("API", "Erro ao processar loja " + i, e);
                    // Continua para a próxima loja
                }
            }

            resultado.add(produto);

            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                for (Produto produtoAtual : resultado) {

                    // Envia o Produto via Retrofit de forma assíncrona
                    api.createProduto(produtoAtual).enqueue(new Callback<List<Produto>>() {
                        @Override
                        public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                            // LOG DETALHADO PARA DEBUG
                            Log.d("Supabase", "=== RESPONSE DEBUG ===");
                            Log.d("Supabase", "Response code: " + response.code());
                            Log.d("Supabase", "Response message: " + response.message());
                            Log.d("Supabase", "Is successful: " + response.isSuccessful());
                            Log.d("Supabase", "Response headers: " + response.headers().toString());

                            // Tentar ler o corpo raw da resposta
                            try {
                                if (response.raw().body() != null) {
                                    // CUIDADO: isto só funciona uma vez, depois o stream fecha
                                    okhttp3.ResponseBody rawBody = response.raw().peekBody(Long.MAX_VALUE);
                                    String rawString = rawBody.string();
                                    Log.d("Supabase", "Raw response body: " + rawString);
                                }
                            } catch (Exception e) {
                                Log.e("Supabase", "Erro ao ler raw body: " + e.getMessage());
                            }

                            if (response.isSuccessful()) {
                                Log.d("Supabase", "Response body is null: " + (response.body() == null));

                                if (response.body() != null) {
                                    Produto produtoInserido = response.body().get(0);
                                    Log.d("Supabase", "Produto deserializado: " + produtoInserido.getNome());
                                    Log.d("Supabase", "ID do produto: " + produtoInserido.getId_produto());

                                    if (produtoInserido.getId_produto() != 0) {
                                        int idProduto = produtoInserido.getId_produto();
                                        Log.d("Supabase", "Produto inserido com ID: " + idProduto);

                                        // Agora envia todos os PrecoLoja com o ID recebido
                                        for (PrecoLoja precoLoja : produtoAtual.getPrecosLojas()) {
                                            precoLoja.setId_produto(idProduto);

                                            api.createPrecoLoja(precoLoja).enqueue(new Callback<PrecoLoja>() {
                                                @Override
                                                public void onResponse(Call<PrecoLoja> precoCall, Response<PrecoLoja> precoResponse) {
                                                    if (precoResponse.isSuccessful()) {
                                                        Log.d("Supabase", "Preço/Loja inserido com sucesso.");
                                                    } else {
                                                        Log.e("Supabase", "Falha ao inserir preço: " + precoResponse.code());
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<PrecoLoja> precoCall, Throwable t) {
                                                    Log.e("Supabase", "Falha ao enviar preço: " + t.getMessage(), t);
                                                }
                                            });
                                        }

                                        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                        int idUtilizador = preferences.getInt("id", -1);
                                        Log.d("Historia", "ID do utilizador recuperado: " + idUtilizador);

                                        if (idUtilizador != -1) {
                                            Historia novaHistoria = new Historia(
                                                    idUtilizador,
                                                    idProduto,
                                                    produtoInserido.getNome(),
                                                    produtoInserido.getUrl_imagem()
                                            );

                                            api.createHistorico(novaHistoria).enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    if (response.isSuccessful()) {
                                                        Log.d("Supabase", "Histórico inserido com sucesso!");
                                                    } else {
                                                        Log.e("Supabase", "Erro ao inserir no histórico: " + response.code());
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {
                                                    Log.e("Supabase", "Falha ao enviar histórico: " + t.getMessage(), t);
                                                }
                                            });
                                        } else {
                                            Log.e("Supabase", "ID do utilizador não encontrado.");
                                        }

                                    } else {
                                        Log.e("Supabase", "ID do produto é null!");
                                    }
                                } else {
                                    Log.e("Supabase", "Resposta bem-sucedida, mas corpo vazio");

                                    // Tentar ler o error body também
                                    try {
                                        if (response.errorBody() != null) {
                                            String errorString = response.errorBody().string();
                                            Log.e("Supabase", "Error body: " + errorString);
                                        }
                                    } catch (Exception e) {
                                        Log.e("Supabase", "Erro ao ler error body: " + e.getMessage());
                                    }
                                }
                            } else {
                                Log.e("Supabase", "Response não bem-sucedida: " + response.code());
                                try {
                                    if (response.errorBody() != null) {
                                        String errorString = response.errorBody().string();
                                        Log.e("Supabase", "Error body: " + errorString);
                                    }
                                } catch (Exception e) {
                                    Log.e("Supabase", "Erro ao ler error body: " + e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Produto>> call, Throwable t) {
                            Log.e("Supabase", "Falha ao criar produto: " + t.getMessage(), t);
                        }
                    });
                }
            });

        } catch (JSONException e) {
            Log.e("API", "Erro crítico ao processar produtos", e);
        }

        return resultado;
    }
}
