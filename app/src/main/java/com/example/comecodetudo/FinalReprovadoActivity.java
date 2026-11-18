package com.example.comecodetudo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.LinearLayout;
import com.google.android.material.button.MaterialButton;
import android.content.Intent;

public class FinalReprovadoActivity extends AppCompatActivity {

    private int etapa = 0;
    private TextView texto;
    private LinearLayout layout;

    // Guardar todos os dados anteriores
    private Intent dadosAnteriores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_reprovado);

        texto = findViewById(R.id.textoReprovado);
        layout = findViewById(R.id.layoutReprovado);

        // RECEBE TODOS os dados enviados até aqui
        dadosAnteriores = getIntent();

        avancarEtapa();
    }

    private void avancarEtapa() {
        Handler handler = new Handler();

        switch (etapa) {
            case 0:
                texto.setText("😔 SENTIMOS MUITO! SEU PEDIDO NÃO FOI APROVADO.");
                etapa++;
                handler.postDelayed(this::avancarEtapa, 2000);
                break;

            case 1:
                texto.append("\n\n📋 O motivo é que a parcela ultrapassa 30% do valor do seu salário.");
                etapa++;
                handler.postDelayed(this::avancarEtapa, 2000);
                break;

            case 2:
                texto.append("\n\n❓Gostaria de tentar outro veículo?");
                mostrarOpcoes();
                etapa++;
                break;
        }
    }

    private void mostrarOpcoes() {

        layout.removeAllViews(); // remove botões antigos

        MaterialButton btnSim = new MaterialButton(this);
        MaterialButton btnNao = new MaterialButton(this);

        btnSim.setText("🔁 Tentar outro veículo");
        btnNao.setText("📋 Ver resumo");

        btnSim.setBackgroundTintList(getColorStateList(R.color.colorPrimary));
        btnNao.setBackgroundTintList(getColorStateList(R.color.colorPrimary));
        btnSim.setTextColor(getColor(R.color.white));
        btnNao.setTextColor(getColor(R.color.white));

        layout.addView(btnSim);
        layout.addView(btnNao);

        // REFAZER SIMULADO
        btnSim.setOnClickListener(v -> {
            Intent simulado = new Intent(this, SimuladoActivity.class);

            // envia todos os dados
            simulado.putExtras(dadosAnteriores);

            startActivity(simulado);
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
        });

        // IR PARA RESUMO
        btnNao.setOnClickListener(v -> {
            Intent resumo = new Intent(this, ResumoActivity.class);

            resumo.putExtras(dadosAnteriores); // envia tudo
            resumo.putExtra("statusAprovacao", "REPROVADO");
            resumo.putExtra("notaAtendimento", -1); // cliente não avaliou

            startActivity(resumo);
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
        });
    }
}
