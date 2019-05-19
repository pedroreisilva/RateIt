package pt.ipg.rateit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BdRateTest {

    @Before
    public void apagaBaseDados() {
        getAppContext().deleteDatabase(BdRateOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void criaBaseDados() {
        // Context of the app under test.
        Context appContext = getAppContext();

        BdRateOpenHelper openHelper = new BdRateOpenHelper(appContext);

        SQLiteDatabase db = openHelper.getReadableDatabase();

        assertTrue(db.isOpen());
    }

    private Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testCRUD() {
        BdRateOpenHelper openHelper = new BdRateOpenHelper(getAppContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        //TESTE FILMES

        BdTableFilmes tableFilmes = new BdTableFilmes(db);

        Cursor cursorFilmes = getFilmes(tableFilmes);
        assertEquals(0, cursorFilmes.getCount());

        //CREATE/READ FILMES

        String nome ="Interstellar";
        int nota = 1;
        int genero = 1;
        String data = "10/09/2018";
        long idITS = criaFilme(tableFilmes, nome, genero, nota, data);


        cursorFilmes = getFilmes(tableFilmes);
        assertEquals(1, cursorFilmes.getCount());

        Filmes filme = getFilmeComID(cursorFilmes, idITS);

        assertEquals(nome, filme.getNome());
        assertEquals(genero, filme.getCategory());
        assertEquals(nota, filme.getNota());
        assertEquals(data, filme.getData());

        nome ="John Wick 3";
        genero = 4;
        nota = 8;
        data = "17/05/2019";
        long idJW = criaFilme(tableFilmes, nome, genero, nota, data);

        cursorFilmes = getFilmes(tableFilmes);
        assertEquals(2, cursorFilmes.getCount());

        filme = getFilmeComID(cursorFilmes, idJW);

        assertEquals(nome, filme.getNome());
        assertEquals(genero, filme.getCategory());
        assertEquals(nota, filme.getNota());
        assertEquals(data, filme.getData());

        //UPDATE FILMES

        nome ="Avangers";
        genero = 1;
        nota = 1;
        data = "18/05/2019";
        filme.setNome(nome);
        filme.setNota(nota);
        filme.setCategory(genero);
        filme.setData(data);

        int registosAlteradosFilmes = tableFilmes.update(filme.getContentValues(), BdTableFilmes._ID + "=?", new String[]{String.valueOf(idITS)});

        assertEquals(1, registosAlteradosFilmes);

        cursorFilmes = getFilmes(tableFilmes);
        filme = getFilmeComID(cursorFilmes, idITS);

        assertEquals(nome, filme.getNome());
        assertEquals(nota, filme.getNota());
        assertEquals(genero, filme.getCategory());
        assertEquals(data, filme.getData());

        //CRUD FILMES

        long id = criaFilme(tableFilmes, "TESTE",1,5,"16/05/2017");
        cursorFilmes = getFilmes(tableFilmes);
        assertEquals(3, cursorFilmes.getCount());

        tableFilmes.delete(BdTableFilmes._ID + "=?", new String[]{String.valueOf(id)});
        cursorFilmes = getFilmes(tableFilmes);
        assertEquals(2, cursorFilmes.getCount());

        getFilmeComID(cursorFilmes, idITS);
        getFilmeComID(cursorFilmes, idJW);




        //TESTE SÉRIES

        BdTableSeries tableSeries = new BdTableSeries(db);

        Cursor cursorSeries = getSeries(tableSeries);
        assertEquals(0, cursorSeries.getCount());

        //CREATE/READ SÉRIES

        nome ="The 100";
        nota = 1;
        genero = 1;
        int temporada = 1;
        int episodio = 1;
        data = "10/09/2018";
        long idTC = criaSerie(tableSeries, nome, genero, nota, temporada, episodio, data);


        cursorSeries = getSeries(tableSeries);
        assertEquals(1, cursorSeries.getCount());

        Series serie = getSerieComID(cursorSeries, idTC);

        assertEquals(nome, filme.getNome());
        assertEquals(genero, filme.getCategory());
        assertEquals(nota, filme.getNota());
        assertEquals(data, filme.getData());

        nome ="Game Of Thrones";
        genero = 4;
        nota = 7;
        data = "17/05/2019";
        long idGOT = criaSerie(tableSeries, nome, genero, nota, temporada, episodio, data);

        cursorSeries = getSeries(tableSeries);
        assertEquals(2, cursorSeries.getCount());

        serie = getSerieComID(cursorSeries, idGOT);

        assertEquals(nome, serie.getNome());
        assertEquals(genero, serie.getCategory());
        assertEquals(nota, serie.getNota());
        assertEquals(temporada, serie.getTemporada());
        assertEquals(episodio, serie.getEpisodio());
        assertEquals(data, serie.getData());

        //UPDATE SÉRIES

        nome ="Avangers";
        genero = 1;
        nota = 1;
        data = "18/05/2019";
        serie.setNome(nome);
        serie.setNota(nota);
        serie.setTemporada(temporada);
        serie.setEpisodio(episodio);
        serie.setCategory(genero);
        serie.setData(data);

        int registosAlteradosSeries = tableSeries.update(serie.getContentValues(), BdTableSeries._ID + "=?", new String[]{String.valueOf(idITS)});

        assertEquals(1, registosAlteradosSeries);

        cursorFilmes = getFilmes(tableFilmes);
        filme = getFilmeComID(cursorFilmes, idITS);

        assertEquals(nome, filme.getNome());
        assertEquals(nota, filme.getNota());
        assertEquals(genero, filme.getCategory());
        assertEquals(data, filme.getData());

        //CRUD SÉRIES

        id = criaFilme(tableFilmes, "TESTE",1,7,"11/05/2019");
        cursorSeries = getSeries(tableSeries);
        assertEquals(3, cursorSeries.getCount());

        tableFilmes.delete(BdTableFilmes._ID + "=?", new String[]{String.valueOf(id)});
        cursorSeries = getSeries(tableSeries);
        assertEquals(2, cursorSeries.getCount());

        getSerieComID(cursorSeries, idTC);
        getSerieComID(cursorSeries, idGOT);




        //TESTE CATEGORIAS

        BdTableCategorias tableCategorias = new BdTableCategorias(db);
        // Teste read servicos (cRud)
        Cursor cursorCategorias = getCategoria(tableCategorias);
        assertEquals(0, cursorCategorias.getCount());

        // Teste create/read servicos (CRud)
        genero = 3;

        long idRomance = criaCategoria(tableCategorias, genero);

        cursorCategorias = getCategoria(tableCategorias);
        assertEquals(1, cursorCategorias.getCount());

        Categorias categorias = getCategoriaComID(cursorCategorias, idRomance);

        assertEquals(genero, categorias.getCategoria());

        genero = 4;

        long idComedia = criaCategoria(tableCategorias, genero);

        cursorCategorias = getCategoria(tableCategorias);
        assertEquals(2, cursorCategorias.getCount());

        categorias = getCategoriaComID(cursorCategorias, idComedia);

        assertEquals(genero, categorias.getCategoria());

        // Teste Update/Read servicos (cRUd)
        genero = 4;

        categorias.setCategoria(genero);


        int registosAlteradosCategorias = tableCategorias.update(categorias.getContentValues(), BdTableCategorias._ID + "=?", new String[]{String.valueOf(idRomance)});

        assertEquals(1, registosAlteradosCategorias);

        cursorCategorias = getCategoria(tableCategorias);
        categorias = getCategoriaComID(cursorCategorias, idRomance);

        assertEquals(genero, categorias.getCategoria());

        // Teste Create/Delete/Read servicos (CRuD)
        id = criaCategoria(tableCategorias, 1);
        cursorCategorias = getCategoria(tableCategorias);
        assertEquals(3, cursorCategorias.getCount());

        tableCategorias.delete(BdTableCategorias._ID + "=?", new String[]{String.valueOf(id)});
        cursorCategorias = getCategoria(tableCategorias);
        assertEquals(2, cursorCategorias.getCount());

        getCategoriaComID(cursorCategorias, idRomance);
        getCategoriaComID(cursorCategorias, idComedia);
    }



    //TABELA FILMES

    private long criaFilme(BdTableFilmes tableFilmes, String nome, int genero, int nota, String data) {

        Filmes filme = new Filmes();

        filme.setNome(nome);
        filme.setNota(nota);
        filme.setData(data);
        filme.setCategory(genero);

        long id = tableFilmes.insert(filme.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getFilmes(BdTableFilmes tableFilmes) {
        return tableFilmes.query(BdTableFilmes.TODAS_COLUNAS, null, null, null, null, null);
    }

    private Filmes getFilmeComID(Cursor cursor, long id) {
        Filmes filme = null;

        while (cursor.moveToNext()) {
            filme = Filmes.fromCursor(cursor);

            if (filme.getId() == id) {
                break;
            }
        }

        assertNotNull(filme);

        return filme;
    }

    //TABELA SÉRIES

    private long criaSerie(BdTableSeries tableSeries, String nome, int genero, int nota, int temporada, int episodio, String data) {

        Series serie = new Series();

        serie.setNome(nome);
        serie.setNota(nota);
        serie.setData(data);
        serie.setTemporada(temporada);
        serie.setEpisodio(episodio);
        serie.setCategory(genero);

        long id = tableSeries.insert(serie.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getSeries(BdTableSeries tableSeries) {
        return tableSeries.query(BdTableSeries.TODAS_COLUNAS, null, null, null, null, null);
    }

    private Series getSerieComID(Cursor cursor, long id) {
        Series serie = null;

        while (cursor.moveToNext()) {
            serie = Series.fromCursor(cursor);

            if (serie.getId() == id) {
                break;
            }
        }

        assertNotNull(serie);

        return serie;
    }

    //TABELA CATEGORIAS

    private long criaCategoria(BdTableCategorias tableCategorias, int genero) {

        Categorias category = new Categorias();

        category.setCategoria(genero);

        long id = tableCategorias.insert(category.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getCategoria(BdTableCategorias tableCategorias) {
        return tableCategorias.query(BdTableCategorias.TODAS_COLUNAS, null, null, null, null, null);
    }

    private Categorias getCategoriaComID(Cursor cursor, long id) {
        Categorias genero = null;

        while (cursor.moveToNext()) {
            genero = Categorias.fromCursor(cursor);

            if (genero.getId() == id) {
                break;
            }
        }

        assertNotNull(genero);

        return genero;
    }



}