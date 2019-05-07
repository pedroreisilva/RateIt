package pt.ipg.rateit;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableFilmes implements BaseColumns {
    public static final String NOME_TABELA = "Filmes";
    public static final String CAMPO_NOME = "Nome";
    public static final String CAMPO_CATEGORIA = "Categoria";
    public static final String CAMPO_NOTA = "Nota";
    public static final String CAMPO_DATA_VISUALIZACAO = "Data";
    private final SQLiteDatabase db;

    public BdTableFilmes(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_NOME + " TEXT NOT NULL," +
                        CAMPO_CATEGORIA + " TEXT NOT NULL," +
                        CAMPO_NOTA + " INTEGER NOT NULL," +
                        CAMPO_DATA_VISUALIZACAO + " DATE NOT NULL," +
                        "FOREIGN KEY (" + CAMPO_CATEGORIA + ") REFERENCES " + BdTableCategorias.NOME_TABELA + "(" + BdTableCategorias._ID + ")" +
                        ")"
        );
    }

    //CRUD
    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    public int update(ContentValues values, String whereClause, String [] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}