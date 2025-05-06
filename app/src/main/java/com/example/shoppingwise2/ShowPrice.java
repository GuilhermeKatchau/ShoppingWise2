package com.example.shoppingwise2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        // Referência ao TextView que mostra o resultado
        priceTextView = findViewById(R.id.priceTextView);

        // Obtém o código de barras passado pela ScannerActivity
        String barcode = getIntent().getStringExtra("barcode");

        if (barcode != null && !barcode.isEmpty()) {
            // Mostra o código escaneado (podes mudar para mostrar o nome/preço do produto mais tarde)
            priceTextView.setText("Código escaneado: " + barcode);

            // Futuro: chamar API aqui com o código
            // fetchProductInfo(barcode);
        } else {
            priceTextView.setText("Erro: código de barras não recebido.");
        }
        //Cenas da API
    }
}
