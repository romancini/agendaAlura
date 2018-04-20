package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.model.Aluno;

public class AlunoDAO extends SQLiteOpenHelper{
    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos ( " +
                "id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT NOT NULL, " +
                "telefone TEXT NOT NULL, " +
                "site TEXT NOT NULL, " +
                "nota REAL, " +
                "caminhoFoto TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql;
        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql);
        }
    }

    @NonNull
    private ContentValues getContentValuesAluno(Aluno aluno) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", aluno.getNome());
        contentValues.put("endereco", aluno.getEndereco());
        contentValues.put("telefone", aluno.getTelefone());
        contentValues.put("site", aluno.getSite());
        contentValues.put("nota", aluno.getNota());
        contentValues.put("caminhoFoto", aluno.getCaminhoFoto());
        return contentValues;
    }

    public void insertAluno(Aluno aluno){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = getContentValuesAluno(aluno);
        sqLiteDatabase.insert("Alunos", null, contentValues);
    }

    public List<Aluno> getAll() {
        String sql = "SELECT * FROM Alunos";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(sql, null);
        List<Aluno> alunos = new ArrayList<>();
        while (c.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            alunos.add(aluno);
        }
        c.close();

        return alunos;
    }

    public void delete(Aluno aluno) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String[] params = {aluno.getId().toString()};
        sqLiteDatabase.delete("Alunos", "id = ?", params);
    }

    public void updateAluno(Aluno aluno) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = getContentValuesAluno(aluno);
        String[] params = {aluno.getId().toString()};
        sqLiteDatabase.update("Alunos", contentValues, "id = ?", params);
    }

    public boolean isAluno(String telefone){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM Alunos WHERE telefone = ?", new String[]{telefone});
        int result = c.getColumnCount();
        c.close();

        return result > 0;
    }
}
