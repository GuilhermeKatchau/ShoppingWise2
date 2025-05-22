package com.example.shoppingwise2;

import android.annotation.SuppressLint;
import android.os.Bundle;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingwise2.DatabaseHelper;
import com.example.shoppingwise2.Preco;
import com.example.shoppingwise2.PriceAdapter;
import com.example.shoppingwise2.Produto;
import com.example.shoppingwise2.R;
import com.example.shoppingwise2.SupabaseApi;

import java.util.ArrayList;
        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

        public class ShowPrice extends AppCompatActivity {

            private RecyclerView recyclerView;
            private PriceAdapter adapter;
            private List<Produto> productList = new ArrayList<>();
            private DatabaseHelper databaseHelper;

            @SuppressLint("MissingInflatedId")
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_show_price);

                recyclerView = findViewById(R.id.recyclerViewPrices);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter = new PriceAdapter(productList);
                recyclerView.setAdapter(adapter);

                databaseHelper = new DatabaseHelper(this);

                String barcode = getIntent().getStringExtra("barcode");
                if (barcode != null && !barcode.isEmpty()) {
                    fetchPricesFromApi(barcode);
                }
            }

            private void fetchPricesFromApi(String barcode) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("postgresql://postgres:WiseShopping1234;@db.fjsjbxmilqxnyvnwzfbv.supabase.co:5432/postgres")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                SupabaseApi api = retrofit.create(SupabaseApi.class);
                Call<List<Preco>> call = api.getPrecos(barcode);

                call.enqueue(new Callback<List<Preco>>() {
                    @Override
                    public void onResponse(Call<List<Preco>> call, Response<List<Preco>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            for (Preco preco : response.body()) {
                                Produto produto = new Produto(
                                        0, // ID fictício
                                        null, // Nome do produto (não fornecido pela API)
                                        barcode,
                                        preco.getURL_Produto(),
                                        null, // Lista de preços
                                        null, // Lista de avaliações
                                        preco.getLoja(),
                                        String.valueOf(preco.getPreco())
                                );
                                productList.add(produto);

                                databaseHelper.insertProduct(produto);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Preco>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }