package pt.ipg.rateit;

import android.content.ContentValues;
import android.database.Cursor;

public class Categorias {
    private long id;
    private String genero;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoria() {
        return genero;
    }

    public void setCategoria(String genero) {
        this.genero = genero;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTableCategorias.CAMPO_CATEGORIA, genero);

        return valores;
    }

    public static Categorias fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableCategorias._ID)
        );

        String genero = cursor.getString(
                cursor.getColumnIndex(BdTableCategorias.CAMPO_CATEGORIA)
        );

        Categorias categorias = new Categorias();

        categorias.setId(id);
        categorias.setCategoria(genero);

        return categorias;
    }
}
