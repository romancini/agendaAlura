package br.com.alura.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.adapter.AlunosAdapter;
import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Button novoAluno = findViewById(R.id.lista_novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFormulario = new Intent(
                        ListaAlunosActivity.this,
                        FormularioActivity.class);
                startActivity(intentFormulario);

            }
        });

        listaAlunos = findViewById(R.id.listview_alunos);
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                Intent intentFormulario = new Intent(
                        ListaAlunosActivity.this,
                        FormularioActivity.class);
                intentFormulario.putExtra("aluno", aluno);
                startActivity(intentFormulario);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    private void loadListaAluno() {
        AlunoDAO alunoDAO = new AlunoDAO(this);
        List<Aluno> alunos = alunoDAO.getAll();
        alunoDAO.close();

        AlunosAdapter adapter = new AlunosAdapter(this, alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListaAluno();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_enviar_notas:
                new EnviaAlunosTask(this).execute();
                break;

            case R.id.menu_baixar_provas:
                Intent intent = new Intent(this, ProvasActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        MenuItem callItem = menu.add("Ligar");
        callItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentCall = new Intent(Intent.ACTION_CALL);
                    intentCall.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentCall);
                }
                return false;
            }
        });

        MenuItem smsItem = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        smsItem.setIntent(intentSMS);

        MenuItem mapItem = menu.add("Enviar SMS");
        Intent intentMap = new Intent(Intent.ACTION_VIEW);
        intentMap.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        mapItem.setIntent(intentMap);

        MenuItem siteItem = menu.add("Visitar Site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = aluno.getSite();
        if (!site.startsWith("http://")){
            site = "http://" + site;
        }
        intentSite.setData(Uri.parse(site));
        siteItem.setIntent(intentSite);

        MenuItem deletarItem = menu.add("Deletar");
        deletarItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDAO alunoDAO = new AlunoDAO(ListaAlunosActivity.this);
                alunoDAO.delete(aluno);
                alunoDAO.close();
                loadListaAluno();
                Toast.makeText(ListaAlunosActivity.this, aluno.getNome() + " deletado", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
}
