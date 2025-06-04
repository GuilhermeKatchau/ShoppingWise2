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

            if (validateInput(email, password)) {
                performLogin(email, password);
            }
        });
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            emailEditText.setError("Email é obrigatório");
            emailEditText.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password é obrigatória");
            passwordEditText.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Email inválido");
            emailEditText.requestFocus();
            return false;
        }

        return true;
    }

    private void performLogin(String email, String password) {
        Log.d("LoginActivity", "Tentando fazer login para: " + email);

        // Desabilita o botão para evitar múltiplos cliques
        loginButton.setEnabled(false);

        Call<List<Utilizador>> call = api.login("eq." + email, "eq." + password);

        call.enqueue(new Callback<List<Utilizador>>() {
            @Override
            public void onResponse(Call<List<Utilizador>> call, Response<List<Utilizador>> response) {

                loginButton.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    List<Utilizador> utilizadores = response.body();

                    if (!utilizadores.isEmpty()) {
                        Utilizador user = utilizadores.get(0);

                        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isLoggedIn", true);

                        // Guarda informações do utilizador para uso posterior
                        editor.putInt("id", user.getId());
                        editor.putString("nome", user.getNome());
                        editor.putString("email", user.getEmail());

                        editor.commit();

                        Log.d("LoginActivity", "ID salvo: " + user.getId());
                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();

                        Toast.makeText(LoginActivity.this, "Bem-vindo, " + user.getNome() + "!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.w("LoginActivity", "Nenhum utilizador encontrado com as credenciais fornecidas");
                    }
                } else {
                    Log.e("LoginActivity", "Resposta não bem-sucedida. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Utilizador>> call, Throwable t) {
                // Reabilita o botão
                loginButton.setEnabled(true);
                loginButton.setText("Entrar");

                Log.e("LoginActivity", "Erro ao conectar com a API", t);
            }
        });
    }

}
