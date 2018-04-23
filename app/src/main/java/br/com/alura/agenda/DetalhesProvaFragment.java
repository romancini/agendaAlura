package br.com.alura.agenda;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.alura.agenda.model.Prova;

public class DetalhesProvaFragment extends Fragment {

    private TextView campoMateria;
    private TextView campoData;
    private ListView topicos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);

        campoMateria = view.findViewById(R.id.detalhe_provas_materia);
        campoData = view.findViewById(R.id.detalhe_provas_data);
        topicos = view.findViewById(R.id.detalhe_prova_topicos);

        Bundle bundle = getArguments();
        if (bundle != null){
            Prova prova = (Prova) bundle.getSerializable("prova");
            populaCamposProva(prova);
        }

        return view;
    }

    public void populaCamposProva(Prova prova) {
        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, prova.getTopicos());
        topicos.setAdapter(adapter);
    }

}
