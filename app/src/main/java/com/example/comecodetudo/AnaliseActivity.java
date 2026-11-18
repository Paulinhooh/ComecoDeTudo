package com.example.comecodetudo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;
import com.google.android.material.button.MaterialButton;

public class AnaliseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analise);

        Spinner spinnerProfissao = findViewById(R.id.spinnerProfissao);
        Spinner spinnerVeiculo = findViewById(R.id.spinnerVeiculo);
        Spinner spinnerCasa = findViewById(R.id.spinnerCasa);
        Spinner spinnerBanco = findViewById(R.id.spinnerBanco);
        Spinner spinnerRestricao = findViewById(R.id.spinnerRestricao);
        MaterialButton botaoProximoResumo = findViewById(R.id.botaoProximoResumo);

        botaoProximoResumo.setOnClickListener(v -> {

            // Dados da análise
            String profissao = spinnerProfissao.getSelectedItem().toString();
            String veiculo = spinnerVeiculo.getSelectedItem().toString();
            String casa = spinnerCasa.getSelectedItem().toString();
            String banco = spinnerBanco.getSelectedItem().toString();
            String restricao = spinnerRestricao.getSelectedItem().toString();

            // Recupera todos os dados anteriores
            Intent anterior = getIntent();
            Bundle allData = anterior.getExtras();

            // Nova tela
            Intent simulado = new Intent(this, SimuladoActivity.class);

            // Mantém absolutamente TUDO que veio antes
            if (allData != null) {
                simulado.putExtras(allData);
            }

            // Adiciona os dados desta tela
            simulado.putExtra("profissao", profissao);
            simulado.putExtra("veiculo", veiculo);
            simulado.putExtra("casa", casa);
            simulado.putExtra("banco", banco);
            simulado.putExtra("restricao", restricao);

            startActivity(simulado);
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
        });
    }
}
