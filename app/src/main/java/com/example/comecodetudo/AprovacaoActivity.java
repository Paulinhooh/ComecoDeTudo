package com.example.comecodetudo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import com.google.android.material.button.MaterialButton;
import android.widget.LinearLayout;

public class AprovacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprovacao);

        EditText campoValorDesconto = findViewById(R.id.campoValorDesconto);
        EditText campoSalario = findViewById(R.id.campoSalario);
        EditText campoParcelas = findViewById(R.id.campoParcelas);
        TextView textoResultado = findViewById(R.id.textoResultadoAprovacao);
        MaterialButton botaoCalcular = findViewById(R.id.botaoCalcularAprovacao);
        LinearLayout layoutPrincipal = findViewById(R.id.layoutPrincipal);

        Intent anterior = getIntent();
        double totalComJuros = anterior.getDoubleExtra("totalComJuros", 0);
        double entrada = anterior.getDoubleExtra("entrada", 0);

        double valorComDesconto = totalComJuros - entrada;
        campoValorDesconto.setText(String.format("R$ %.2f", valorComDesconto));

        botaoCalcular.setOnClickListener(v -> {
            try {

                String salarioTexto = campoSalario.getText().toString()
                        .replace("R$", "")
                        .replaceAll("[^0-9,\\.]", "")
                        .replace(",", ".")
                        .trim();

                String parcelasTexto = campoParcelas.getText().toString().trim();

                if (salarioTexto.isEmpty() || parcelasTexto.isEmpty()) {
                    Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double salario = Double.parseDouble(salarioTexto);
                int qtdParcelas = Integer.parseInt(parcelasTexto);
                double valorParcela = valorComDesconto / qtdParcelas;
                double minimoPermitido = salario * 0.30;

                boolean aprovado = valorParcela <= minimoPermitido;

                // Texto principal
                StringBuilder resultado = new StringBuilder();
                resultado.append("💰 PARA PAGAR UM CARRO DE R$ ")
                        .append(String.format("%.2f", valorComDesconto))
                        .append(" EM ")
                        .append(qtdParcelas)
                        .append("x, A PARCELA SERÁ DE R$ ")
                        .append(String.format("%.2f", valorParcela))
                        .append("\n\n");

                resultado.append("💼 O SR(A) GANHA: R$ ")
                        .append(String.format("%.2f", salario))
                        .append("\n");

                if (aprovado) {
                    resultado.append("🟢 Desta forma, NÃO COMPROMETE os 30% do seu salário.\n\n");
                    resultado.append("🎉 RESULTADO: APROVADO!\n");
                } else {
                    resultado.append("🔴 Desta forma, COMPROMETE mais de 30% do seu salário.\n\n");
                    resultado.append("❌ RESULTADO: REPROVADO.\n");
                }

                textoResultado.setText(resultado.toString());

                // Remove botões antigos antes de adicionar
                layoutPrincipal.removeAllViews();
                layoutPrincipal.addView(botaoCalcular);
                layoutPrincipal.addView(textoResultado);

                // Agora adiciona o botão para continuar
                MaterialButton botaoContinuar = new MaterialButton(this);
                botaoContinuar.setText("CONTINUAR");
                botaoContinuar.setBackgroundTintList(getColorStateList(R.color.colorPrimary));
                botaoContinuar.setTextColor(getColor(R.color.white));

                botaoContinuar.setOnClickListener(btn -> {
                    Intent destino;

                    if (aprovado) {
                        destino = new Intent(this, FinalAprovadoActivity.class);
                    } else {
                        destino = new Intent(this, FinalReprovadoActivity.class);
                    }

                    destino.putExtras(anterior);
                    destino.putExtra("salario", salario);
                    destino.putExtra("valorParcela", valorParcela);
                    destino.putExtra("parcelas", qtdParcelas);

                    startActivity(destino);
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                });

                layoutPrincipal.addView(botaoContinuar);

            } catch (Exception e) {
                Toast.makeText(this, "Erro ao calcular. Verifique os campos!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
