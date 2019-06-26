package pt.ipg.rateit;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class BdTableFilmes implements BaseColumns {
    public static final String NOME_TABELA = "Filmes";

    public static final String ALIAS_NOME_CATEGORIA = "nome_categ";
    public static final String CAMPO_NOME = "Nome";
    public static final String CAMPO_CATEGORIA = "Categoria";
    public static final String CAMPO_NOTA = "Nota";
    public static final String CAMPO_DATA_VISUALIZACAO = "Data";
    public static final String CAMPO_NOME_CATEGORIA = BdTableCategorias.NOME_TABELA + "." + BdTableCategorias.CAMPO_GENERO + " AS " + ALIAS_NOME_CATEGORIA; // tabela de categorias (só de leitura)

    public static final String[] TODAS_COLUNAS = new String[] { NOME_TABELA + "." + _ID, CAMPO_NOME, CAMPO_NOTA, CAMPO_DATA_VISUALIZACAO, CAMPO_CATEGORIA, CAMPO_NOME_CATEGORIA };

    private final SQLiteDatabase db;

    public BdTableFilmes(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_NOME + " TEXT NOT NULL," +
                        CAMPO_CATEGORIA + " INTEGER NOT NULL," +
                        CAMPO_NOTA + " INTEGER NOT NULL," +
                        CAMPO_DATA_VISUALIZACAO + " TEXT NOT NULL," +
                        "FOREIGN KEY (" + CAMPO_CATEGORIA + ") REFERENCES " + BdTableCategorias.NOME_TABELA + "(" + BdTableCategorias._ID + ")" +
                        ")"
        );
    }

    //CRUD
    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        String colunasSelect = TextUtils.join(",", columns);

        String sql = "SELECT " + colunasSelect + " FROM " +
                NOME_TABELA + " INNER JOIN " + BdTableCategorias.NOME_TABELA + " WHERE " + NOME_TABELA + "." + CAMPO_CATEGORIA + "=" + BdTableCategorias.NOME_TABELA + "." + BdTableCategorias._ID
                ;

        if (selection != null) {
            sql += " AND " + selection;
        }

        Log.d("Tabela Filmes", "query: " + sql);

        return db.rawQuery(sql, selectionArgs);

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
