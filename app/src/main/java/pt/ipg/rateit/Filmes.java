package pt.ipg.rateit;

import android.content.ContentValues;
import android.database.Cursor;

public class Filmes {

    private long id;
    private String nome;
    private int nota;
    private String data;
    private long category;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(long Category) {
        this.category = Category;
    }

    public ContentValues getContentValues() {

        ContentValues valores = new ContentValues();

        valores.put(BdTableFilmes.CAMPO_NOME, nome);
        valores.put(BdTableFilmes.CAMPO_CATEGORIA, category);
        valores.put(BdTableFilmes.CAMPO_NOTA, nota);
        valores.put(BdTableFilmes.CAMPO_DATA_VISUALIZACAO, data);

        return valores;
    }

    public static Filmes fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableFilmes._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_NOME)
        );

        int nota = cursor.getInt(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_NOTA)
        );

        long categoria = cursor.getLong(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_CATEGORIA)
        );

        String data = cursor.getString(
                cursor.getColumnIndex(BdTableFilmes.CAMPO_DATA_VISUALIZACAO)
        );

        Filmes filme = new Filmes();

        filme.setId(id);
        filme.setNome(nome);
        filme.setNota(nota);
        filme.setCategory(categoria);
        filme.setData(data);

        return filme;
    }

}
