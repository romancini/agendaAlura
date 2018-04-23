package br.com.alura.agenda;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.alura.agenda.model.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_principal, new ListaProvasFragment());
        if (isLandscape()){
            fragmentTransaction.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }

        fragmentTransaction.commit();
    }

    private boolean isLandscape() {
        return getResources().getBoolean(R.bool.landMode);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (!isLandscape()) {
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

            DetalhesProvaFragment detalhesProvaFragment = new DetalhesProvaFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("prova", prova);
            detalhesProvaFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.frame_principal, detalhesProvaFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            DetalhesProvaFragment detalhesFragment = (DetalhesProvaFragment) supportFragmentManager.findFragmentById(R.id.frame_secundario);
            detalhesFragment.populaCamposProva(prova);

        }
    }
}
