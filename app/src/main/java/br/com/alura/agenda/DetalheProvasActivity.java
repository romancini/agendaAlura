package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.alura.agenda.model.Prova;

public class DetalheProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_provas);

        Intent intent = getIntent();
        Prova prova = (Prova)intent.getSerializableExtra("prova");

        TextView materia = findViewById(R.id.detalhe_provas_materia);
        materia.setText(prova.getMateria());
        TextView data = findViewById(R.id.detalhe_provas_data);
        data.setText(prova.getData());
        ListView topicos = findViewById(R.id.detalhe_prova_topicos);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, prova.getTopicos());
        topicos.setAdapter(adapter);
    }
}
