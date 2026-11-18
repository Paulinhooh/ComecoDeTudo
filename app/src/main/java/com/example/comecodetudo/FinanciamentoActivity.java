package com.example.comecodetudo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;
import com.google.android.material.button.MaterialButton;

public class FinanciamentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financiamento);

        EditText campoValorVeiculo = findViewById(R.id.campoValorVeiculo);
        EditText campoQuantidade = findViewById(R.id.campoQuantidade);
        EditText campoEntrada = findViewById(R.id.campoEntrada);
        EditText campoParcelas = findViewById(R.id.campoParcelas);
        MaterialButton botaoCalcular = findViewById(R.id.botaoCalcular);
        TextView textoResultado = findViewById(R.id.textoResultado);
        MaterialButton botaoFinalizar = findViewById(R.id.botaoFinalizar);

        // ================================================
        //   🔹 Recupera o valor do veículo da tela anterior
        // ================================================
        Intent anterior = getIntent();
        String precoStr = anterior.getStringExtra("precoVeiculo");

        if (precoStr != null) {
            try {
                double valor = Double.parseDouble(precoStr);
                campoValorVeiculo.setText(String.format("%.2f", valor));
            } catch (Exception e) {
                campoValorVeiculo.setText("");
            }
        }

        // ========================
        //     BOTÃO CALCULAR
        // ========================
        botaoCalcular.setOnClickListener(v -> {
            try {
                // Converte valor do veículo
                String valorTexto = campoValorVeiculo.getText().toString()
                        .replace("R$", "")
                        .replaceAll("[^0-9,\\.]", "")
                        .replace(",", ".")
                        .trim();

                if (valorTexto.isEmpty()) {
                    Toast.makeText(this, "Informe o valor do veículo!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double valorVeiculo = Double.parseDouble(valorTexto);
                int quantidade = Integer.parseInt(campoQuantidade.getText().toString());
                double entrada = Double.parseDouble(campoEntrada.getText().toString());
                int parcelas = Integer.parseInt(campoParcelas.getText().toString());

                if (parcelas < 2 || parcelas > 96) {
                    Toast.makeText(this, "Informe de 2 a 96 parcelas!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Regra dos juros
                double juros;
                if (parcelas <= 32) juros = 0.10;
                else if (parcelas <= 65) juros = 0.15;
                else juros = 0.35;

                // Cálculos
                double preco = valorVeiculo * quantidade;
                double totalComJuros = preco + (preco * juros);
                double desconto = totalComJuros - entrada;
                double valorParcela = desconto / parcelas;

                // Exibe o resultado
                String resultado =
                        "💰 ESTAMOS FAZENDO O CÁLCULO... ⚙️\n\n" +
                                "Preço total (sem juros): R$ " + String.format("%.2f", preco) + "\n" +
                                "Preço com juros (" + (juros * 100) + "%): R$ " + String.format("%.2f", totalComJuros) + "\n" +
                                "Entrada: R$ " + String.format("%.2f", entrada) + "\n" +
                                "A financiar: R$ " + String.format("%.2f", desconto) + "\n" +
                                "Parcelas: " + parcelas + "\n" +
                                "💳 Valor da parcela: R$ " + String.format("%.2f", valorParcela);

                textoResultado.setText(resultado);
                botaoFinalizar.setVisibility(View.VISIBLE);

                // ========================================
                //     ENVIA para APROVAÇÃO
                // ========================================
                botaoFinalizar.setOnClickListener(b -> {
                    Intent aprovacao = new Intent(this, AprovacaoActivity.class);
                    aprovacao.putExtras(anterior); // mantém tudo anterior
                    aprovacao.putExtra("valorVeiculo", preco);
                    aprovacao.putExtra("juros", juros * 100);
                    aprovacao.putExtra("entrada", entrada);
                    aprovacao.putExtra("totalComJuros", totalComJuros);
                    aprovacao.putExtra("parcelas", parcelas);
                    aprovacao.putExtra("valorParcela", valorParcela);

                    startActivity(aprovacao);
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                });

            } catch (Exception e) {
                Toast.makeText(this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
