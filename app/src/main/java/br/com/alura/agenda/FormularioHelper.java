package br.com.alura.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.alura.agenda.model.Aluno;

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;

    private Aluno aluno;

    public FormularioHelper(FormularioActivity formularioActivity){
        campoNome = formularioActivity.findViewById(R.id.formulario_nome);
        campoEndereco = formularioActivity.findViewById(R.id.formulario_endereco);
        campoTelefone = formularioActivity.findViewById(R.id.formulario_telefone);
        campoSite = formularioActivity.findViewById(R.id.formulario_site);
        campoNota = formularioActivity.findViewById(R.id.formulario_nota);
        campoFoto = formularioActivity.findViewById(R.id.formulario_foto);

        this.aluno = new Aluno();
    }

    public Aluno getAluno(){

        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String)campoFoto.getTag());

        return aluno;
    }

    public void setAluno(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        loadImage(aluno.getCaminhoFoto());

        this.aluno = aluno;
    }

    public void loadImage(String path) {
        if (path != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Bitmap bitmapReducted = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmapReducted);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(path);
        }
    }
}
