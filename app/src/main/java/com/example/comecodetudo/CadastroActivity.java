package com.example.comecodetudo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;
import android.widget.ArrayAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        TextInputEditText campoNomeCompleto = findViewById(R.id.campoNomeCompleto);
        TextInputEditText campoDataNascimento = findViewById(R.id.campoDataNascimento);
        TextInputEditText campoRG = findViewById(R.id.campoRG);
        TextInputEditText campoCPF = findViewById(R.id.campoCPF);
        Spinner spinnerGenero = findViewById(R.id.spinnerGenero);
        Spinner spinnerEstadoCivil = findViewById(R.id.spinnerEstadoCivil);
        TextInputEditText campoMae = findViewById(R.id.campoMae);
        TextInputEditText campoPai = findViewById(R.id.campoPai);
        MaterialButton botaoProximo = findViewById(R.id.botaoProximo);

        // Adapters dos spinners
        ArrayAdapter<CharSequence> adapterGenero = ArrayAdapter.createFromResource(
                this, R.array.generos, android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterEstadoCivil = ArrayAdapter.createFromResource(
                this, R.array.estado_civil_opcoes, android.R.layout.simple_spinner_dropdown_item);

        spinnerGenero.setAdapter(adapterGenero);
        spinnerEstadoCivil.setAdapter(adapterEstadoCivil);

        // Máscaras
        campoDataNascimento.addTextChangedListener(new MaskTextWatcher(campoDataNascimento, "##/##/####"));
        campoRG.addTextChangedListener(new MaskTextWatcher(campoRG, "##.###.###-##"));
        campoCPF.addTextChangedListener(new MaskTextWatcher(campoCPF, "###.###.###-##"));

        botaoProximo.setOnClickListener(v -> {

            String nome = campoNomeCompleto.getText().toString().trim();
            String dataNasc = campoDataNascimento.getText().toString().trim();
            String rg = campoRG.getText().toString().trim();
            String cpf = campoCPF.getText().toString().trim();
            String genero = spinnerGenero.getSelectedItem().toString();
            String estadoCivil = spinnerEstadoCivil.getSelectedItem().toString();
            String mae = campoMae.getText().toString().trim();
            String pai = campoPai.getText().toString().trim();

            if (nome.isEmpty() || dataNasc.isEmpty() || cpf.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isMaiorDeIdade(dataNasc)) {
                Toast.makeText(this, "É necessário ser maior de 18 anos para prosseguir!", Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent(this, ContatoActivity.class);
            intent.putExtra("nome", nome);
            intent.putExtra("data", dataNasc);
            intent.putExtra("rg", rg);
            intent.putExtra("cpf", cpf);
            intent.putExtra("genero", genero);
            intent.putExtra("estadoCivil", estadoCivil);
            intent.putExtra("mae", mae);
            intent.putExtra("pai", pai);

            startActivity(intent);
        });
    }

    private boolean isMaiorDeIdade(String dataNascimento) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = sdf.parse(dataNascimento);

            Calendar nascimento = Calendar.getInstance();
            nascimento.setTime(data);

            Calendar hoje = Calendar.getInstance();

            int idade = hoje.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR);

            if (hoje.get(Calendar.DAY_OF_YEAR) < nascimento.get(Calendar.DAY_OF_YEAR)) {
                idade--;
            }

            return idade >= 18;
        } catch (ParseException e) {
            return false;
        }
    }
}
