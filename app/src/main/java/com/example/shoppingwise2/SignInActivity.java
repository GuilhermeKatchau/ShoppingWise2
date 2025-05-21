package com.example.shoppingwise2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private EditText editNome, editPasswd, editEmail, editPnum;
    private Button btnSignIn;
    private TextView btnLogin;
    private SupabaseApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editNome = findViewById(R.id.username);
        editPasswd = findViewById(R.id.password);
        editEmail = findViewById(R.id.email);
        editPnum = findViewById(R.id.phoneNumber);
        btnSignIn = findViewById(R.id.SignInButton);
        btnLogin = findViewById(R.id.notNewButton);

        api = RetrofitClient.getInstance().create(SupabaseApi.class);

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        btnSignIn.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();
            String password = editPasswd.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            int pnum = Integer.parseInt(editPnum.getText().toString().trim());

            Utilizador utilizador = new Utilizador(nome, password, email, pnum);

            Call<Void> call = api.createUser(utilizador);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(SignInActivity.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SignInActivity.this, "Erro: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(SignInActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
