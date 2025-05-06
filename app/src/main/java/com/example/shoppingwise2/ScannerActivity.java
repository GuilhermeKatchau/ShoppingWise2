package com.example.shoppingwise2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.OptIn;
import androidx.camera.core.ExperimentalGetImage;

import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.camera.view.PreviewView;

import java.util.concurrent.ExecutionException;

public class ScannerActivity extends AppCompatActivity {
    private boolean alreadyScanned = false;

    private PreviewView previewView;  // Alterado de SurfaceView para PreviewView
    private BarcodeScanner barcodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        previewView = findViewById(R.id.previewView);  // Alterado de SurfaceView para PreviewView

        barcodeScanner = BarcodeScanning.getClient();

        // Inicia a câmera
        startCamera();
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private void startCamera() {
        ProcessCameraProvider cameraProvider;
        try {
            // Obtém o ProcessCameraProvider
            cameraProvider = ProcessCameraProvider.getInstance(this).get();

            // Configuração do Preview para exibir a câmera na PreviewView
            Preview preview = new Preview.Builder().build();

            // Use a PreviewView com o SurfaceProvider
            preview.setSurfaceProvider(previewView.getSurfaceProvider());  // Método correto

            // Cria um analisador de imagens (ImageAnalysis) para escanear os códigos de barras
            ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                    .build();
            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), image -> {
                InputImage inputImage;
                try {
                    // Cria a InputImage a partir da ImageProxy corretamente
                    inputImage = InputImage.fromMediaImage(image.getImage(), image.getImageInfo().getRotationDegrees());
                    processImage(inputImage);
                } finally {
                    image.close();
                }
            });

            // Configura o CameraSelector para usar a câmera traseira
            CameraSelector cameraSelector = new CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build();

            // Bind camera com Preview e ImageAnalysis
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);

        } catch (ExecutionException | InterruptedException e) {
            Log.e("ScannerActivity", "Erro ao iniciar a câmera: ", e);
        }
    }

    private void processImage(InputImage image) {
        barcodeScanner.process(image)
                .addOnSuccessListener(barcodes -> {
                    // Para cada código de barras detectado, exibe ou faz o que for necessário
                    for (Barcode barcode : barcodes) {
                        String value = barcode.getDisplayValue();
                        Log.d("ScannerActivity", "Código detectado: " + value);
                        // Exibe o valor do código de barras
                        Toast.makeText(this, "Código: " + value, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ScannerActivity.this, ShowPrice.class);
                        intent.putExtra("barcode", value);
                        startActivity(intent);
                        finish(); // encerra o ScannerActivity
                        break;
                    }
                })
                .addOnFailureListener(e -> Log.e("ScannerActivity", "Erro ao escanear", e));
    }
}
