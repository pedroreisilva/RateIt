package pt.ipg.rateit;

import android.content.ContentValues;
import android.database.Cursor;

public class Filmes {

    private long id;
    private String nomef;
    private int nota;
    private String data;
    private long categoria;
    private String nomeCategoria; // Campo "externo"

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nomef;
    }

    public void setNome(String nomef) {
        this.nomef = nomef;
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

    public long getCategoria() {
        return categoria;
    }

    public void setCategoria(long Category) {
        this.categoria = Category;
    }

    public ContentValues getContentValues() {

        ContentValues valores = new ContentValues();

        valores.put(BdTableFilmes.CAMPO_NOME, nomef);
        valores.put(BdTableFilmes.CAMPO_CATEGORIA, categoria);
        valores.put(BdTableFilmes.CAMPO_NOTA, nota);
        valores.put(BdTableFilmes.CAMPO_DATA_VISUALIZACAO, data);

        return valores;
    }

    public static Filmes fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableFilmes._ID)
        );

        String nomef = cursor.getString(
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

        String nomeCategoria = cursor.getString(
                cursor.getColumnIndex(BdTableFilmes.ALIAS_NOME_CATEGORIA)
        );


        Filmes filme = new Filmes();

        filme.setId(id);
        filme.setNome(nomef);
        filme.setNota(nota);
        filme.setCategoria(categoria);
        filme.setData(data);
        filme.nomeCategoria = nomeCategoria;


        return filme;
    }

}
