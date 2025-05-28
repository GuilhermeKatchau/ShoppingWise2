package com.example.shoppingwise2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHistorico;
    private HistoricoAdapter adapter;
    private SupabaseApi api;
    private TextView tituloHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_search);

        recyclerViewHistorico = findViewById(R.id.recyclerViewHistorico);
        tituloHistorico = findViewById(R.id.tituloHistorico);
        api = RetrofitClient.getInstance().create(SupabaseApi.class);

        recyclerViewHistorico.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoricoAdapter(new ArrayList<>(), this);
        recyclerViewHistorico.setAdapter(adapter);

        setupBottomNavigation();

        // Carregar histórico do usuário
        carregarHistorico();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_preco) {
                startActivity(new Intent(HistoryActivity.this, ShowPriceActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(HistoryActivity.this, ProfileActivity.class));
                return true;
            } else if (itemId == R.id.nav_search) {
                startActivity(new Intent(HistoryActivity.this, SearchActivity.class));
                return true;
            } else if (itemId == R.id.nav_history) {
                return true;
            } else if (itemId == R.id.nav_scan) {
                startActivity(new Intent(HistoryActivity.this, ScannerActivity.class));
                return true;
            }

            return false;
        });
            bottomNavigationView.setSelectedItemId(R.id.nav_history);
    }

    private void carregarHistorico() {
        // Obtem ID do utilizador das SharedPreferences
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int idUtilizador = preferences.getInt("id", -1);

        Log.d("HistoryActivity", "ID do utilizador: " + idUtilizador);

        if (idUtilizador == -1) {
            Toast.makeText(this, "Erro: Utilizador não identificado", Toast.LENGTH_LONG).show();
            return;
        }

        api.getHistoricoByUserId("eq." + idUtilizador, "data.desc").enqueue(new Callback<List<Historia>>() {
            @Override
            public void onResponse(Call<List<Historia>> call, Response<List<Historia>> response) {
                Log.d("HistoryActivity", "Response code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    List<Historia> historico = response.body();

                    if (historico.isEmpty()) {
                        Toast.makeText(HistoryActivity.this, "Nenhum histórico encontrado", Toast.LENGTH_SHORT).show();
                        tituloHistorico.setText("Histórico Vazio");
                    } else {
                        tituloHistorico.setText("Histórico de Pesquisas (" + historico.size() + ")");
                        adapter.atualizarLista(historico);
                    }
                } else {
                    Log.e("HistoryActivity", "Erro na resposta: " + response.code());
                    try {
                        if (response.errorBody() != null) {
                            String errorString = response.errorBody().string();
                            Log.e("HistoryActivity", "Error body: " + errorString);
                        }
                    } catch (Exception e) {
                        Log.e("HistoryActivity", "Erro ao ler error body: " + e.getMessage());
                    }
                    Toast.makeText(HistoryActivity.this, "Erro ao carregar histórico", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Historia>> call, Throwable t) {
                Log.e("HistoryActivity", "Falha na requisição: " + t.getMessage(), t);
                Toast.makeText(HistoryActivity.this, "Erro de conexão", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarHistorico();
    }
}