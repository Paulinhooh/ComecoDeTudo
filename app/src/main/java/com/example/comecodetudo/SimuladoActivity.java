package com.example.comecodetudo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import com.google.android.material.button.MaterialButton;
import java.util.HashMap;
import java.util.Map;

public class SimuladoActivity extends AppCompatActivity {

    private Spinner spinnerMarca, spinnerModelo;
    private TextView textoModelos, textoPrecoAno;
    private MaterialButton botaoProximoResumo;

    private Map<String, String[]> modelosPorMarca = new HashMap<>();
    private Map<String, String> precoAnoPorModelo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulado);

        spinnerMarca = findViewById(R.id.spinnerMarca);
        spinnerModelo = findViewById(R.id.spinnerModelo);
        textoModelos = findViewById(R.id.textoModelos);
        textoPrecoAno = findViewById(R.id.textoPrecoAno);
        botaoProximoResumo = findViewById(R.id.botaoProximoResumo);

        // === Dados ===
        modelosPorMarca.put("AUDI", new String[]{"Audi A3", "Audi A4"});
        modelosPorMarca.put("CHEVROLET", new String[]{"Celta", "Corsa", "Cruze", "Onix"});
        modelosPorMarca.put("CITROËN", new String[]{"C3", "C4 Cactus", "C4 Picasso"});
        modelosPorMarca.put("FIAT", new String[]{"Argo", "Mobi", "Toro", "Palio"});
        modelosPorMarca.put("FORD", new String[]{"Ka", "Fusion"});
        modelosPorMarca.put("HONDA", new String[]{"City", "Civic", "HR-V"});
        modelosPorMarca.put("HYUNDAI", new String[]{"HB20", "Creta", "Tucson"});
        modelosPorMarca.put("PEUGEOT", new String[]{"208"});
        modelosPorMarca.put("RENAULT", new String[]{"Duster", "Kwid", "Logan", "Sandero"});
        modelosPorMarca.put("TOYOTA", new String[]{"Corolla"});
        modelosPorMarca.put("VOLKSWAGEN", new String[]{"Gol", "Golf", "Jetta", "Polo", "T-Cross"});

        precoAnoPorModelo.put("Audi A3", "2018|124900.00");
        precoAnoPorModelo.put("Audi A4", "2017|172990.00");
        precoAnoPorModelo.put("Celta", "2012|16202.00");
        precoAnoPorModelo.put("Corsa", "2012|20500.00");
        precoAnoPorModelo.put("Cruze", "2017|89900.00");
        precoAnoPorModelo.put("Onix", "2018|67900.00");
        precoAnoPorModelo.put("C3", "2019|44000.00");
        precoAnoPorModelo.put("C4 Cactus", "2018|69000.00");
        precoAnoPorModelo.put("Argo", "2019|50090.00");
        precoAnoPorModelo.put("Mobi", "2019|48672.00");
        precoAnoPorModelo.put("Toro", "2017|83900.00");
        precoAnoPorModelo.put("Ka", "2019|53000.00");
        precoAnoPorModelo.put("Fusion", "2019|111000.00");
        precoAnoPorModelo.put("City", "2017|63400.00");
        precoAnoPorModelo.put("Civic", "2018|124900.00");
        precoAnoPorModelo.put("HR-V", "2016|91500.00");
        precoAnoPorModelo.put("HB20", "2018|37990.00");
        precoAnoPorModelo.put("Creta", "2019|77890.00");
        precoAnoPorModelo.put("Tucson", "2013|42800.00");
        precoAnoPorModelo.put("208", "2015|41000.00");
        precoAnoPorModelo.put("Duster", "2017|56900.00");
        precoAnoPorModelo.put("Kwid", "2018|29000.00");
        precoAnoPorModelo.put("Logan", "2018|44690.00");
        precoAnoPorModelo.put("Sandero", "2017|40380.00");
        precoAnoPorModelo.put("Corolla", "2020|130990.00");
        precoAnoPorModelo.put("Gol", "2016|45737.00");
        precoAnoPorModelo.put("Golf", "2019|80000.00");
        precoAnoPorModelo.put("Jetta", "2018|97900.00");
        precoAnoPorModelo.put("Polo", "2019|64636.00");
        precoAnoPorModelo.put("T-Cross", "2018|115000.00");

        // === Configura marca ===
        spinnerMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String marca = spinnerMarca.getSelectedItem().toString();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        SimuladoActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        modelosPorMarca.get(marca)
                );

                spinnerModelo.setAdapter(adapter);
                textoModelos.setVisibility(View.VISIBLE);
                spinnerModelo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // === Mostra ano e preço ===
        spinnerModelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String modelo = spinnerModelo.getSelectedItem().toString();
                String valor = precoAnoPorModelo.get(modelo);

                if (valor != null) {
                    String[] partes = valor.split("\\|");
                    String ano = partes[0];
                    String preco = partes[1];

                    textoPrecoAno.setText("Ano: " + ano + " | Preço: R$ " + preco);
                    textoPrecoAno.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // === BOTÃO PROXIMO ===
        botaoProximoResumo.setOnClickListener(v -> {
            String marca = spinnerMarca.getSelectedItem().toString();
            String modelo = spinnerModelo.getSelectedItem().toString();
            String precoAno = precoAnoPorModelo.get(modelo);

            String ano = precoAno.split("\\|")[0];
            String preco = precoAno.split("\\|")[1];

            Intent anterior = getIntent();
            Intent intent = new Intent(this, FinanciamentoActivity.class);

            intent.putExtras(anterior);
            intent.putExtra("marcaEscolhida", marca);
            intent.putExtra("modeloEscolhido", modelo);
            intent.putExtra("anoVeiculo", ano);
            intent.putExtra("precoVeiculo", preco);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
        });
    }
}
