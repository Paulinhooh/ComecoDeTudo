package com.example.comecodetudo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import android.content.Intent;

public class ResumoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo);

        TextView textoResumo = findViewById(R.id.textoResumo);
        MaterialButton botaoVoltarInicio = findViewById(R.id.botaoVoltarInicio);
        MaterialButton botaoFinalizar = findViewById(R.id.botaoFinalizar);

        Bundle dados = getIntent().getExtras();

        if (dados == null) {
            textoResumo.setText("Nenhuma informação recebida.");
            return;
        }

        // =========================
        //     DADOS PESSOAIS
        // =========================
        String nome = dados.getString("nome", "—");
        String dataNasc = dados.getString("data", "—");
        String rg = dados.getString("rg", "—");
        String cpf = dados.getString("cpf", "—");
        String genero = dados.getString("genero", "—");
        String estadoCivil = dados.getString("estadoCivil", "—");
        String mae = dados.getString("mae", "—");
        String pai = dados.getString("pai", "—");

        // =========================
        //  CONTATO / ENDEREÇO
        // =========================
        String telefone = dados.getString("telefone", "—");
        String email = dados.getString("email", "—");
        String endereco = dados.getString("endereco", "—");
        String numero = dados.getString("numero", "—");
        String bairro = dados.getString("bairro", "—");
        String cidade = dados.getString("cidade", "—");
        String estado = dados.getString("estado", "—");
        String cep = dados.getString("cep", "—");

        // =========================
        //  ANÁLISE DE PERFIL
        // =========================
        String profissao = dados.getString("profissao", "—");
        String veiculoPossui = dados.getString("veiculo", "—");
        String casaPossui = dados.getString("casa", "—");
        String banco = dados.getString("banco", "—");
        String restricao = dados.getString("restricao", "—");

        // =========================
        //  SIMULADO AUTORAIO
        // =========================
        String marca = dados.getString("marcaEscolhida", "—");
        String modelo = dados.getString("modeloEscolhido", "—");
        String preco = dados.getString("precoVeiculo", "—");
        String ano = dados.getString("anoVeiculo", "—");

        // =========================
        //  APROVAÇÃO
        // =========================
        String statusAprovacao = dados.getString("statusAprovacao", "—");
        double valorParcela = dados.getDouble("valorParcela", 0);
        int qtdParcelas = dados.getInt("parcelas", 0);
        int notaAtendimento = dados.getInt("notaAtendimento", -1);

        double valorFinalFinanciado = valorParcela * qtdParcelas;

        // =========================
        //   MONTANDO O RESUMO
        // =========================

        String resumoFinal =
                "👤 DADOS PESSOAIS\n" +
                        "Nome: " + nome + "\n" +
                        "Data de Nascimento: " + dataNasc + "\n" +
                        "RG: " + rg + "\n" +
                        "CPF: " + cpf + "\n" +
                        "Gênero: " + genero + "\n" +
                        "Estado Civil: " + estadoCivil + "\n" +
                        "Nome da Mãe: " + mae + "\n" +
                        "Nome do Pai: " + pai + "\n\n" +

                        "📞 CONTATO E ENDEREÇO\n" +
                        "Telefone: " + telefone + "\n" +
                        "E-mail: " + email + "\n" +
                        "Endereço: " + endereco + ", Nº " + numero + "\n" +
                        "Bairro: " + bairro + "\n" +
                        "Cidade: " + cidade + " / " + estado + "\n" +
                        "CEP: " + cep + "\n\n" +

                        "🧠 ANÁLISE DE PERFIL\n" +
                        "Profissão: " + profissao + "\n" +
                        "Possui veículo: " + veiculoPossui + "\n" +
                        "Possui casa: " + casaPossui + "\n" +
                        "Banco: " + banco + "\n" +
                        "Restrição: " + restricao + "\n\n" +

                        "🚗 SIMULADO DO CARRO\n" +
                        "Marca: " + marca + "\n" +
                        "Modelo: " + modelo + "\n" +
                        "Ano: " + ano + "\n" +
                        "Preço: " + preco + "\n\n" +

                        "📋 RESULTADO FINAL\n" +
                        "Status: " + statusAprovacao + "\n" +
                        "Parcelas: " + qtdParcelas + "\n" +
                        "Valor da parcela: R$ " + String.format("%.2f", valorParcela) + "\n" +
                        "💵 Valor final do veículo: R$ " + String.format("%.2f", valorFinalFinanciado) + "\n\n";

        if (notaAtendimento != -1)
            resumoFinal += "⭐ Nota do atendimento: " + notaAtendimento + "/10\n";

        textoResumo.setText(resumoFinal);

        botaoVoltarInicio.setOnClickListener(v -> {
            Intent voltar = new Intent(this, MainActivity.class);
            voltar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(voltar);
        });

        botaoFinalizar.setOnClickListener(v -> finishAffinity());
    }
}
