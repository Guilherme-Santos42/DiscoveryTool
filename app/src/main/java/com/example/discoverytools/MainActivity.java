package com.example.discoverytools;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.discoverytools.NetworkDiscovery;  // Certifique-se de que este caminho está correto

import androidx.appcompat.app.AppCompatActivity;

import com.example.discoverytools.R;

public class MainActivity extends AppCompatActivity {

    private EditText subnetEditText;
    private TextView resultTextView;
    private Button startPingSweepButton;
    private Button startPortScanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subnetEditText = findViewById(R.id.subnetEditText);
        resultTextView = findViewById(R.id.resultTextView);
        startPingSweepButton = findViewById(R.id.startPingSweepButton);
        startPortScanButton = findViewById(R.id.startPortScanButton);

        // Iniciar Ping Sweep
        startPingSweepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subnet = subnetEditText.getText().toString();
                if (!subnet.isEmpty()) {
                    resultTextView.setText("Iniciando Ping Sweep...");
                    // Executa o ping sweep em um thread separado
                    new Thread(() -> {
                        NetworkDiscovery.pingSweep(subnet);
                        runOnUiThread(() -> resultTextView.setText("Ping Sweep concluído."));
                    }).start();
                }
            }
        });

        // Iniciar Scanner de Portas
        startPortScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subnet = subnetEditText.getText().toString();
                if (!subnet.isEmpty()) {
                    resultTextView.setText("Iniciando Scanner de Portas...");
                    // Executa o scanner de portas para o primeiro IP ativo da sub-rede
                    new Thread(() -> {
                        String ip = subnet + "1";  // Verifica o primeiro IP na sub-rede
                        NetworkDiscovery.scanPorts(ip);
                        runOnUiThread(() -> resultTextView.setText("Scanner de Portas concluído."));
                    }).start();
                }
            }
        });
    }
}
