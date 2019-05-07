package pt.ipg.rateit;

import android.content.Context;
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

        Filmes filmes = new Filmes();
        filmes.setNome("Interstellar");
        filmes.setCategory(2);
        filmes.setNota(9);
        filmes.setData("07/05/2019");
        long idFilme = tableFilmes.insert(filmes.getContentValues());
        assertNotEquals(-1, idFilme);

        //TESTE SÉRIES

        BdTableFilmes tableSeries = new BdTableFilmes(db);

        Series series = new Series();
        series.setNome("The 100");
        series.setCategory(1);
        series.setTemporada(5);
        series.setEpisodio(8);
        series.setNota(7);
        series.setData("07/05/2019");
        long idSerie = tableSeries.insert(series.getContentValues());
        assertNotEquals(-1, idSerie);

        //TESTE CATEGORIAS

        BdTableCategorias tabelaCategorias = new BdTableCategorias(db);

        Categorias categorias = new Categorias();
        categorias.setCategoria("Comédia");
        long idRomance = tabelaCategorias.insert(categorias.getContentValues());
        assertNotEquals(-1, idRomance);

    }
}
