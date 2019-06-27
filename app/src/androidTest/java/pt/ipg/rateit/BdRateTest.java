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


        //TESTE CATEGORIAS

        BdTableCategorias tableCategorias = new BdTableCategorias(db);
        // Teste read servicos (cRud)
        Cursor cursorCategorias = getCategoria(tableCategorias);
        assertEquals(0, cursorCategorias.getCount());

        // Teste create/read servicos (CRud)
        String nome = "Ficção";

        long idFiccao = criaCategoria(tableCategorias, nome);

        cursorCategorias = getCategoria(tableCategorias);
        assertEquals(1, cursorCategorias.getCount());

        Categorias categoria = getCategoriaComID(cursorCategorias, idFiccao);

        assertEquals(nome, categoria.getGenero());

        nome = "Comedia";

        long idComedia = criaCategoria(tableCategorias, nome);

        cursorCategorias = getCategoria(tableCategorias);
        assertEquals(2, cursorCategorias.getCount());

        categoria = getCategoriaComID(cursorCategorias, idComedia);

        assertEquals(nome, categoria.getGenero());

        // Teste Update/Read servicos (cRUd)
        nome = "Drama / Acao";

        categoria.setGenero(nome);


        int registosAlteradosCategorias = tableCategorias.update(categoria.getContentValues(), BdTableCategorias._ID + "=?", new String[]{String.valueOf(idFiccao)});

        assertEquals(1, registosAlteradosCategorias);

        cursorCategorias = getCategoria(tableCategorias);
        categoria = getCategoriaComID(cursorCategorias, idFiccao);

        assertEquals(nome, categoria.getGenero());

        // Teste Create/Delete/Read servicos (CRuD)

        long id = criaCategoria(tableCategorias, "TESTE");
        cursorCategorias = getCategoria(tableCategorias);
        assertEquals(3, cursorCategorias.getCount());

        tableCategorias.delete(BdTableCategorias._ID + "=?", new String[]{String.valueOf(id)});
        cursorCategorias = getCategoria(tableCategorias);
        assertEquals(2, cursorCategorias.getCount());

        getCategoriaComID(cursorCategorias, idFiccao);
        getCategoriaComID(cursorCategorias, idComedia);




        BdTableFilmes tableFilmes = new BdTableFilmes(db);

        // Teste read livros (cRud)
        Cursor cursorFilmes = getFilmes(tableFilmes);
        assertEquals(0, cursorFilmes.getCount());

        // Teste create/read categorias (CRud)
        String nomef = "InterStellar";
        int nota = 9;
        String data = "16/05/2018";

        id = criaFilme(tableFilmes, nomef, idFiccao, nota, data);
        cursorFilmes = getFilmes(tableFilmes);
        assertEquals(1, cursorFilmes.getCount());

        Filmes filme = getFilmeComID(cursorFilmes, id);
        assertEquals(nomef, filme.getNome());
        assertEquals(nota, filme.getNota());
        assertEquals(data, filme.getData());
        assertEquals(idFiccao, filme.getCategoria());

        nomef = "Grow Up";
        nota = 10;
        data = "02/06/2019";

        id = criaFilme(tableFilmes, nomef, idComedia, nota, data);
        cursorFilmes = getFilmes(tableFilmes);
        assertEquals(2, cursorFilmes.getCount());

        filme = getFilmeComID(cursorFilmes, id);
        assertEquals(nomef, filme.getNome());
        assertEquals(nota, filme.getNota());
        assertEquals(data, filme.getData());
        assertEquals(idComedia, filme.getCategoria());

        id = criaFilme(tableFilmes, "Teste", idComedia, 10, "16/04/2019");
        cursorFilmes = getFilmes(tableFilmes);
        assertEquals(3, cursorFilmes.getCount());

        // Teste read/update livros (cRUd)
        filme = getFilmeComID(cursorFilmes, id);

        nomef = "Inception";
        nota = 9;
        data = "01/05/2019";

        filme.setNome(nomef);
        filme.setNota(nota);
        filme.setData(data);
        filme.setCategoria(idFiccao);

        tableFilmes.update(filme.getContentValues(), BdTableFilmes._ID + "=?", new String[]{String.valueOf(id)});

        cursorFilmes = getFilmes(tableFilmes);

        filme = getFilmeComID(cursorFilmes, id);
        assertEquals(nomef, filme.getNome());
        assertEquals(nota, filme.getNota());
        assertEquals(data, filme.getData());
        assertEquals(idFiccao, filme.getCategoria());

        // Teste read/delete livros (cRuD)
        tableFilmes.delete(BdTableFilmes._ID + "=?", new String[]{String.valueOf(id)});
        cursorFilmes = getFilmes(tableFilmes);
        assertEquals(2, cursorFilmes.getCount());






        //TESTE SÉRIES

        BdTableSeries tableSeries = new BdTableSeries(db);

        // Teste read livros (cRud)
        Cursor cursorSeries = getSeries(tableSeries);
        assertEquals(0, cursorSeries.getCount());

        // Teste create/read categorias (CRud)
        nomef = "Game Of Thrones";
        int temporada = 5;
        int episodio = 7;
        nota = 9;
        data = "16/05/2018";

        id = criaSerie(tableSeries, nomef, idFiccao, nota, temporada, episodio, data);
        cursorSeries = getSeries(tableSeries);
        assertEquals(1, cursorSeries.getCount());

        Series serie = getSerieComID(cursorSeries, id);
        assertEquals(nomef, serie.getNome());
        assertEquals(nota, serie.getNota());
        assertEquals(data, serie.getData());
        assertEquals(temporada, serie.getTemporada());
        assertEquals(episodio, serie.getEpisodio());
        assertEquals(idFiccao, serie.getCategoria());

        nomef = "Chernobyl";
        nota = 10;
        temporada = 1;
        episodio = 5;
        data = "27/04/2019";

        id = criaSerie(tableSeries, nomef, idFiccao, nota, temporada, episodio, data);
        cursorSeries = getSeries(tableSeries);
        assertEquals(2, cursorSeries.getCount());

        serie = getSerieComID(cursorSeries, id);
        assertEquals(nomef, serie.getNome());
        assertEquals(nota, serie.getNota());
        assertEquals(data, serie.getData());
        assertEquals(temporada, serie.getTemporada());
        assertEquals(episodio, serie.getEpisodio());
        assertEquals(idFiccao, serie.getCategoria());

        id = criaSerie(tableSeries, "Teste", idComedia, 10, 3, 12, "15/02/2019");
        cursorSeries = getSeries(tableSeries);
        assertEquals(3, cursorSeries.getCount());

        // Teste read/update series (cRUd)
        serie = getSerieComID(cursorSeries, id);

        nomef = "The 100";
        nota = 7 ;
        temporada = 6;
        episodio = 7;
        data = "01/05/2019";

        serie.setNome(nomef);
        serie.setNota(nota);
        serie.setTemporada(temporada);
        serie.setEpisodio(episodio);
        serie.setData(data);
        serie.setCategoria(idFiccao);

        tableSeries.update(serie.getContentValues(), BdTableSeries._ID + "=?", new String[]{String.valueOf(id)});

        cursorSeries = getSeries(tableSeries);

        serie = getSerieComID(cursorSeries, id);
        assertEquals(nomef, serie.getNome());
        assertEquals(nota, serie.getNota());
        assertEquals(data, serie.getData());
        assertEquals(temporada, serie.getTemporada());
        assertEquals(episodio, serie.getEpisodio());
        assertEquals(idFiccao, serie.getCategoria());

        // Teste read/delete livros (cRuD)
        tableSeries.delete(BdTableSeries._ID + "=?", new String[]{String.valueOf(id)});
        cursorSeries = getSeries(tableSeries);
        assertEquals(2, cursorSeries.getCount());


    }


    private long criaCategoria(BdTableCategorias tableCategorias, String nome) {

        Categorias category = new Categorias();

        category.setGenero(nome);

        long id = tableCategorias.insert(category.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getCategoria(BdTableCategorias tableCategorias) {
        return tableCategorias.query(BdTableCategorias.TODAS_COLUNAS, null, null, null, null, null);
    }

    private Categorias getCategoriaComID(Cursor cursor, long id) {
        Categorias categoria = null;

        while (cursor.moveToNext()) {
            categoria = Categorias.fromCursor(cursor);

            if (categoria.getId() == id) {
                break;
            }
        }

        assertNotNull(categoria);

        return categoria;
    }



    private long criaFilme(BdTableFilmes tableFilmes, String nome, long categoria, int nota, String data) {

        Filmes filme = new Filmes();

        filme.setNome(nome);
        filme.setNota(nota);
        filme.setData(data);
        filme.setCategoria(categoria);

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



    private long criaSerie(BdTableSeries tableSeries, String nome, long categoria, int nota, int temporada, int episodio, String data) {

        Series serie = new Series();

        serie.setNome(nome);
        serie.setNota(nota);
        serie.setData(data);
        serie.setTemporada(temporada);
        serie.setEpisodio(episodio);
        serie.setCategoria(categoria);

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


}