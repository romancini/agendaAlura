package br.com.alura.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

import static android.app.ProgressDialog.*;

public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = show(context, "Aguarde", "Enviando alunos...", true, true);
    }

    @Override
    protected String doInBackground(Void[] objects) {
        AlunoDAO alunoDAO = new AlunoDAO(context);
        List<Aluno> alunos = alunoDAO.getAll();
        alunoDAO.close();

        AlunoConverter alunoConverter = new AlunoConverter();
        String json = alunoConverter.convertToJson(alunos);

        WebClient webClient = new WebClient();
        String resposta = webClient.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_SHORT).show();
    }
}
