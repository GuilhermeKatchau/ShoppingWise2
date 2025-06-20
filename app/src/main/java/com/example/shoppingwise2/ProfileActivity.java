package com.example.shoppingwise2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView userNameHeader, userEmailHeader, userName, userEmail, userMobile;

    private SupabaseApi api;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        api = RetrofitClient.getInstance().create(SupabaseApi.class);

        userNameHeader = findViewById(R.id.user_name);
        userEmailHeader = findViewById(R.id.user_email);
        userName = findViewById(R.id.user_name_value);
        userEmail = findViewById(R.id.user_email_value);
        userMobile = findViewById(R.id.user_mobile_value);

        setupBottomNavigation();

        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> logoutUser());

        Log.d("ProfileActivity", "ID lido: " + userId);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = prefs.getInt("id", -1);

        Log.d("ProfileActivity", "User ID obtido: " + userId);

        if (userId != -1) {
            loadUserData();
        } else {
            Toast.makeText(this, "Erro: Utilizador não identificado", Toast.LENGTH_SHORT).show();
            Log.e("ProfileActivity", "User ID não encontrado nas SharedPreferences");
        }
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_history) {
                startActivity(new Intent(ProfileActivity.this, HistoryActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                return true;
            } else if (itemId == R.id.nav_search) {
                startActivity(new Intent(ProfileActivity.this, SearchActivity.class));
                return true;
            } else if (itemId == R.id.nav_scan) {
                startActivity(new Intent(ProfileActivity.this, ScannerActivity.class));
                return true;
            } else if (itemId == R.id.nav_preco) {
                startActivity(new Intent(ProfileActivity.this, ShowPriceActivity.class));
                return true;
                }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
    }

    private void loadUserData() {
        Log.d("ProfileActivity", "Iniciando carregamento de dados do utilizador ID: " + userId);


        // Fazer chamada à API - Supabase usa eq.ID para filtrar por ID
        Call<List<Utilizador>> call = api.getUserById("eq." + userId);

        call.enqueue(new Callback<List<Utilizador>>() {
            @Override
            public void onResponse(Call<List<Utilizador>> call, Response<List<Utilizador>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<Utilizador> utilizadores = response.body();

                    if (!utilizadores.isEmpty()) {
                        Utilizador user = utilizadores.get(0);
                        updateUI(user);
                    } else {
                        Toast.makeText(ProfileActivity.this, "Utilizador não encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Erro ao carregar dados: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Utilizador>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUI(Utilizador user) {
        try {

            if (userNameHeader != null) {
                userNameHeader.setText(user.getNome() != null ? user.getNome() : "Nome não disponível");
            }
            if (userEmailHeader != null) {
                userEmailHeader.setText(user.getEmail() != null ? user.getEmail() : "Email não disponível");
            }


            if (userName != null) {
                userName.setText(user.getNome() != null ? user.getNome() : "Nome não disponível");
            }
            if (userEmail != null) {
                userEmail.setText(user.getEmail() != null ? user.getEmail() : "Email não disponível");
            }
            if (userMobile != null) {
                userMobile.setText(user.getPnum() != 0 ? String.valueOf(user.getPnum()) : "Número não disponível");
            }

            Log.d("ProfileActivity", "UI atualizada com sucesso");

        } catch (Exception e) {
            Log.e("ProfileActivity", "Erro ao atualizar UI: " + e.getMessage(), e);
            Toast.makeText(this, "Erro ao exibir dados do utilizador", Toast.LENGTH_SHORT).show();
        }
    }

    private void logoutUser() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        prefs.edit().clear().apply();

        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

        Toast.makeText(ProfileActivity.this, "Sessão terminada com sucesso", Toast.LENGTH_SHORT).show();
    }

}