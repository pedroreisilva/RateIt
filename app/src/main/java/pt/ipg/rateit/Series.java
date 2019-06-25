package pt.ipg.rateit;

import android.content.ContentValues;
import android.database.Cursor;

public class Series {

    private long id;
    private String nome;
    private int nota;
    private int temporada;
    private int episodio;
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

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public int getEpisodio() {
        return episodio;
    }

    public void setEpisodio(int episodio) {
        this.episodio = episodio;
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

        valores.put(BdTableSeries.CAMPO_NOME, nome);
        valores.put(BdTableSeries.CAMPO_CATEGORIA, categoria);
        valores.put(BdTableSeries.CAMPO_NOTA, nota);
        valores.put(BdTableSeries.CAMPO_TEMPORADA, temporada);
        valores.put(BdTableSeries.CAMPO_EPISODIO, episodio);
        valores.put(BdTableSeries.CAMPO_DATA_VISUALIZACAO, data);

        return valores;
    }

    public static Series fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableSeries._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTableSeries.CAMPO_NOME)
        );

        int nota = cursor.getInt(
                cursor.getColumnIndex(BdTableSeries.CAMPO_NOTA)
        );

        int temporada = cursor.getInt(
                cursor.getColumnIndex(BdTableSeries.CAMPO_TEMPORADA)
        );

        int episodio = cursor.getInt(
                cursor.getColumnIndex(BdTableSeries.CAMPO_EPISODIO)
        );

        long categoria = cursor.getLong(
                cursor.getColumnIndex(BdTableSeries.CAMPO_CATEGORIA)
        );

        String data = cursor.getString(
                cursor.getColumnIndex(BdTableSeries.CAMPO_DATA_VISUALIZACAO)
        );

        String nomeCategoria = cursor.getString(
                cursor.getColumnIndex(BdTableFilmes.ALIAS_NOME_CATEGORIA)
        );

        Series serie = new Series();

        serie.setId(id);
        serie.setNome(nome);
        serie.setNota(nota);
        serie.setTemporada(temporada);
        serie.setEpisodio(episodio);
        serie.setCategoria(categoria);
        serie.setData(data);
        serie.nomeCategoria = nomeCategoria;

        return serie;
    }

}
