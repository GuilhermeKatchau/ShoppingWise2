package com.example.shoppingwise2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoricoSearch extends AppCompatActivity {

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

        // Carregar histórico do usuário
        carregarHistorico();
    }

    private void carregarHistorico() {
        // Obter ID do usuário das SharedPreferences
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int idUtilizador = preferences.getInt("id", -1);

        Log.d("HistoricoSearch", "ID do utilizador: " + idUtilizador);

        if (idUtilizador == -1) {
            Toast.makeText(this, "Erro: Usuário não identificado", Toast.LENGTH_LONG).show();
            return;
        }

        api.getHistoricoByUserId(idUtilizador, "data.desc").enqueue(new Callback<List<Historia>>() {
            @Override
            public void onResponse(Call<List<Historia>> call, Response<List<Historia>> response) {
                Log.d("HistoricoSearch", "Response code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    List<Historia> historico = response.body();
                    Log.d("HistoricoSearch", "Histórico carregado: " + historico.size() + " itens");

                    if (historico.isEmpty()) {
                        Toast.makeText(HistoricoSearch.this, "Nenhum histórico encontrado", Toast.LENGTH_SHORT).show();
                        tituloHistorico.setText("Histórico Vazio");
                    } else {
                        tituloHistorico.setText("Histórico de Pesquisas (" + historico.size() + ")");
                        adapter.atualizarLista(historico);
                    }
                } else {
                    Log.e("HistoricoSearch", "Erro na resposta: " + response.code());
                    try {
                        if (response.errorBody() != null) {
                            String errorString = response.errorBody().string();
                            Log.e("HistoricoSearch", "Error body: " + errorString);
                        }
                    } catch (Exception e) {
                        Log.e("HistoricoSearch", "Erro ao ler error body: " + e.getMessage());
                    }
                    Toast.makeText(HistoricoSearch.this, "Erro ao carregar histórico", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Historia>> call, Throwable t) {
                Log.e("HistoricoSearch", "Falha na requisição: " + t.getMessage(), t);
                Toast.makeText(HistoricoSearch.this, "Erro de conexão", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarHistorico();
    }
}