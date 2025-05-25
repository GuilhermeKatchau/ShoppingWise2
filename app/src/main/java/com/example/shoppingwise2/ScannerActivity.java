package com.example.shoppingwise2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScannerActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;
    private PreviewView previewView;
    private BarcodeScanner barcodeScanner;
    private boolean alreadyScanned = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        previewView = findViewById(R.id.previewView);
        BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build();
        barcodeScanner = BarcodeScanning.getClient(options);


        // Verifica a permissão da câmara
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            startCamera();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (barcodeScanner != null) {
            barcodeScanner.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        alreadyScanned = false; // Permite escanear novamente
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a activity se a permissão for negada
        }
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private void startCamera() {
        ProcessCameraProvider cameraProvider;
        try {
            cameraProvider = ProcessCameraProvider.getInstance(this).get();

            Preview preview = new Preview.Builder().build();
            preview.setSurfaceProvider(previewView.getSurfaceProvider());

            ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().
                    setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build();

            AtomicBoolean isProcessing = new AtomicBoolean(false);
            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), image -> {
                if (image.getImage() != null && !alreadyScanned && isProcessing.compareAndSet(false, true)) {
                    InputImage inputImage = InputImage.fromMediaImage(
                            image.getImage(), image.getImageInfo().getRotationDegrees());

                    barcodeScanner.process(inputImage)
                            .addOnSuccessListener(barcodes -> {
                                for (Barcode barcode : barcodes) {
                                    String value = barcode.getDisplayValue();
                                    if (value != null && !alreadyScanned) {
                                        alreadyScanned = true;
                                        Log.d("ScannerActivity", "Código detectado: " + value);
                                        Toast.makeText(this, "Código: " + value, Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(ScannerActivity.this, ShowPrice.class);
                                        intent.putExtra("barcode", value);
                                        startActivity(intent);
                                        break;
                                    }
                                }
                            })
                            .addOnFailureListener(e ->{
                                Log.e("ScannerActivity", "Erro ao escanear", e);
                                isProcessing.set(false); // Liberta para novo processamento
                                Toast.makeText(this, "Erro na leitura", Toast.LENGTH_SHORT).show();
                            })
                            .addOnCompleteListener(task -> {
                                image.close();
                                isProcessing.set(false);
                            });
                } else {
                    image.close();
                }
            });

            CameraSelector cameraSelector = new CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build();

            cameraProvider.unbindAll(); // Limpa antes de associar
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);

        } catch (ExecutionException | InterruptedException e) {
            Log.e("ScannerActivity", "Erro ao iniciar a câmera: ", e);
        }
    }
}
