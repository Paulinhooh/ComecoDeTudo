package com.example.comecodetudo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ContatoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        // Referências dos campos
        TextInputEditText telefone = findViewById(R.id.campoTelefone);
        TextInputEditText email = findViewById(R.id.campoEmail);
        TextInputEditText endereco = findViewById(R.id.campoEndereco);
        TextInputEditText numero = findViewById(R.id.campoNumero);
        TextInputEditText bairro = findViewById(R.id.campoBairro);
        TextInputEditText cidade = findViewById(R.id.campoCidade);
        TextInputEditText cep = findViewById(R.id.campoCEP);
        Spinner estado = findViewById(R.id.spinnerEstado);
        MaterialButton botaoFinalizar = findViewById(R.id.botaoFinalizar);

        // Máscaras automáticas
        telefone.addTextChangedListener(new MaskTextWatcher(telefone, "(##) #####-####"));
        cep.addTextChangedListener(new MaskTextWatcher(cep, "#####-###"));

        botaoFinalizar.setOnClickListener(v -> {

            // Campos preenchidos
            String txtTelefone = telefone.getText().toString().trim();
            String txtEmail = email.getText().toString().trim();
            String txtEndereco = endereco.getText().toString().trim();
            String txtNumero = numero.getText().toString().trim();
            String txtBairro = bairro.getText().toString().trim();
            String txtCidade = cidade.getText().toString().trim();
            String txtEstado = estado.getSelectedItem().toString();
            String txtCep = cep.getText().toString().trim();

            // Verifica obrigatórios
            if (txtEmail.isEmpty() || txtCidade.isEmpty() || txtEndereco.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Recupera dados da tela anterior (CadastroActivity)
            Intent intentAnterior = getIntent();

            String nomeCompleto = intentAnterior.getStringExtra("nome");
            String data = intentAnterior.getStringExtra("data");
            String rg = intentAnterior.getStringExtra("rg");
            String cpf = intentAnterior.getStringExtra("cpf");
            String genero = intentAnterior.getStringExtra("genero");
            String mae = intentAnterior.getStringExtra("mae");
            String pai = intentAnterior.getStringExtra("pai");

            // Envia para AnaliseActivity
            Intent analise = new Intent(this, AnaliseActivity.class);

            // Dados pessoais
            analise.putExtra("nome", nomeCompleto);
            analise.putExtra("data", data);
            analise.putExtra("rg", rg);
            analise.putExtra("cpf", cpf);
            analise.putExtra("genero", genero);
            analise.putExtra("mae", mae);
            analise.putExtra("pai", pai);

            // Dados de contato
            analise.putExtra("telefone", txtTelefone);
            analise.putExtra("email", txtEmail);
            analise.putExtra("endereco", txtEndereco);
            analise.putExtra("numero", txtNumero);
            analise.putExtra("bairro", txtBairro);
            analise.putExtra("cidade", txtCidade);
            analise.putExtra("estado", txtEstado);
            analise.putExtra("cep", txtCep);

            startActivity(analise);
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
        });
    }
}
