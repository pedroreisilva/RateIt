package pt.ipg.rateit;

import android.content.ContentValues;
import android.database.Cursor;

public class Categorias {
    private long id;
    private String categoria;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTableCategorias.CAMPO_CATEGORIA, categoria);

        return valores;
    }

    public static Categorias fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableCategorias._ID)
        );

        String categoria = cursor.getString(
                cursor.getColumnIndex(BdTableCategorias.CAMPO_CATEGORIA)
        );

        Categorias categorias = new Categorias();

        categorias.setId(id);
        categorias.setCategoria(categoria);

        return categorias;
    }
}
