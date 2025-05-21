package com.example.shoppingwise2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private SupabaseApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailEditText = findViewById(R.id.loginEmail);
        passwordEditText = findViewById(R.id.loginPasswd);
        loginButton = findViewById(R.id.loginButton);

        api = RetrofitClient.getInstance().create(SupabaseApi.class);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            api.login("eq." + email,"eq." + password).enqueue(new Callback<List<Utilizador>>() {
                @Override
                public void onResponse(Call<List<Utilizador>> call, Response<List<Utilizador>> response) {
                    if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {

                        // Faz login automaticamente a partir de informação armazenada no telemovel
                        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Utilizador>> call, Throwable t) {
                    Log.e("LoginActivity", "Erro ao conectar", t);
                    Toast.makeText(LoginActivity.this, "Erro de rede", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
