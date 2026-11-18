package com.example.comecodetudo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.LinearLayout;
import com.google.android.material.button.MaterialButton;
import android.content.Intent;

public class FinalAprovadoActivity extends AppCompatActivity {

    private int etapa = 0;
    private TextView texto;
    private MaterialButton botao;
    private LinearLayout layout;

    private Intent dadosAnteriores; // <- vamos armazenar tudo aqui

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_aprovado);

        texto = findViewById(R.id.textoAprovado);
        botao = findViewById(R.id.botaoProximoAprovado);
        layout = findViewById(R.id.layoutAprovado);

        // Guarda TODOS os dados vindos das telas anteriores
        dadosAnteriores = getIntent();

        avancarEtapa();
        botao.setOnClickListener(v -> avancarEtapa());
    }

    private void avancarEtapa() {
        Handler handler = new Handler();

        switch (etapa) {
            case 0:
                texto.setText("🎉 PARABÉNS! SUA APROVAÇÃO FOI CONCLUÍDA COM SUCESSO! ⚡");
                etapa++;
                break;

            case 1:
                texto.append("\n\n🚗 Agora é só aguardar o nosso contato via WhatsApp ou ligação para finalizar sua compra!");
                etapa++;
                break;

            case 2:
                texto.append("\n\n💬 Gostaríamos de saber como foi seu atendimento. Avalie de 1 a 10:");
                mostrarAvaliacoes();
                etapa++;
                break;
        }
    }

    private void mostrarAvaliacoes() {

        layout.removeView(botao); // remove botão padrão

        for (int i = 1; i <= 10; i++) {

            MaterialButton btn = new MaterialButton(this);
            btn.setText(String.valueOf(i));
            btn.setBackgroundTintList(getColorStateList(R.color.colorPrimary));
            btn.setTextColor(getColor(R.color.white));

            int nota = i;

            btn.setOnClickListener(v -> {

                layout.removeAllViews();  // limpa botões

                if (nota <= 4) {
                    texto.append("\n\n😔 Lamentamos que não tenha ficado satisfeito. Vamos melhorar!");
                } else {
                    texto.append("\n\n💛 Obrigado pelo elogio! Você é parte da energia AUTORAIO ⚡");
                }

                // BOTÃO DE AVANÇAR PARA O RESUMO
                MaterialButton botaoResumo = new MaterialButton(this);
                botaoResumo.setText("AVANÇAR PARA RESUMO");
                botaoResumo.setBackgroundTintList(getColorStateList(R.color.colorPrimary));
                botaoResumo.setTextColor(getColor(R.color.white));

                botaoResumo.setOnClickListener(v2 -> {

                    Intent resumo = new Intent(this, ResumoActivity.class);

                    // leva tudo para o resumo
                    resumo.putExtras(dadosAnteriores);

                    // adiciona informações finais
                    resumo.putExtra("statusAprovacao", "APROVADO");
                    resumo.putExtra("notaAtendimento", nota);

                    startActivity(resumo);
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                });

                layout.addView(botaoResumo);
            });

            layout.addView(btn);
        }
    }

}
