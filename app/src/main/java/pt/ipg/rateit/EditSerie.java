package pt.ipg.rateit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditSerie extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSO_LOADER_CATEGORIAS = 0;

    private EditText editTextNome;
    private Spinner spinnerCategorias;
    private EditText editTextNota;
    private EditText editTextTemporada;
    private EditText editTextEpisodio;
    private EditText editTextData;

    private Series serie = null;

    private boolean categoriasCarregadas = false;
    private boolean categoriaAtualizada = false;

    private Uri enderecoSerieEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_serie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        spinnerCategorias = (Spinner) findViewById(R.id.spinnerCategorias);
        editTextNota = (EditText) findViewById(R.id.editTextNota);
        editTextTemporada = (EditText) findViewById(R.id.editTextTemporada);
        editTextEpisodio = (EditText) findViewById(R.id.editTextEpisodio);
        editTextData = (EditText) findViewById(R.id.editTextData);

        SimpleDateFormat formatoData= new SimpleDateFormat("dd/MM/yyyy");
        Date data =  new Date();
        String dataFormatada =  formatoData.format(data);
        editTextData.setText(dataFormatada);


        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);

        Intent intent = getIntent();

        long idSerie = intent.getLongExtra(MainSeries.ID_SERIE, -1);

        if (idSerie == -1) {
            Toast.makeText(this, "Erro: não foi possível ler a série", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoSerieEditar = Uri.withAppendedPath(RateItContentProvider.ENDERECO_SERIES, String.valueOf(idSerie));

        Cursor cursor = getContentResolver().query(enderecoSerieEditar, BdTableSeries.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler a série", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        serie = Series.fromCursor(cursor);

        editTextNome.setText(serie.getNome());
        editTextData.setText(serie.getData());
        editTextTemporada.setText(String.valueOf(serie.getTemporada()));
        editTextEpisodio.setText(String.valueOf(serie.getEpisodio()));

        actualizaCategoriaSelecionada();

    }

    private void actualizaCategoriaSelecionada() {
        if (!categoriasCarregadas) return;
        if (categoriaAtualizada) return;

        for (int i = 0; i < spinnerCategorias.getCount(); i++) {
            if (spinnerCategorias.getItemIdAtPosition(i) == serie.getCategoria()) {
                spinnerCategorias.setSelection(i);
                break;
            }
        }

        categoriaAtualizada = true;
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);

        super.onResume();
    }

    private void mostraCategoriasSpinner(Cursor cursorCategorias) {
        SimpleCursorAdapter adaptadorCategorias = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorCategorias,
                new String[]{BdTableCategorias.CAMPO_GENERO},
                new int[]{android.R.id.text1}
        );
        spinnerCategorias.setAdapter(adaptadorCategorias);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guardar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_guardar) {
            guardar();
            return true;
        } else if (id == R.id.action_cancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void guardar() {


        SimpleDateFormat formatoData= new SimpleDateFormat("dd/MM/yyyy");
        Date data =  new Date();
        String dataFormatada =  formatoData.format(data);
        editTextData.setText(dataFormatada);

        String nome = editTextNome.getText().toString();
        String strTemporada = editTextNome.getText().toString();
        String strEpisodio = editTextNome.getText().toString();
        String strNota = editTextNota.getText().toString();
        int nota;
        int temporada;
        int episodio;


        if (nome.trim().length() == 0){
            editTextNome.setError(getString(R.string.nome_obrigatoria));
            editTextNome.requestFocus();
        }else if(nome.trim().length() >= 25) {
            editTextNome.setError("Nome demasiado grande!");
            editTextNome.requestFocus();
        }else if (strNota.trim().isEmpty()) {
            editTextNota.setError("Introduza uma nota!");
            editTextNota.requestFocus();
        }else {
            Toast.makeText(EditSerie.this, getString(R.string.serie_adicionada), Toast.LENGTH_SHORT).show();
            finish();
        }


        if (strNota.trim().isEmpty()) {
            editTextNota.setError("Introduza uma nota!");
            return;
        }

        try {
            nota = Integer.parseInt(strNota);
        } catch (NumberFormatException e) {
            editTextNota.setError("Introduza apenas números!");
            return;
        }


        if (strTemporada.trim().isEmpty()) {
            editTextTemporada.setError("Introduza uma temporada!");
            return;
        }

        try {
            temporada = Integer.parseInt(strNota);
        } catch (NumberFormatException e) {
            editTextTemporada.setError("Introduza apenas números!");
            return;
        }

        if (strEpisodio.trim().isEmpty()) {
            editTextEpisodio.setError("Introduza um episódio!");
            return;
        }

        try {
            episodio = Integer.parseInt(strNota);
        } catch (NumberFormatException e) {
            editTextEpisodio.setError("Introduza apenas números!");
            return;
        }


        long idCategoria = spinnerCategorias.getSelectedItemId();

        // guardar os dados

        Series serie = new Series();

        serie.setNome(nome);
        serie.setCategoria(idCategoria);
        serie.setNota(nota);
        serie.setTemporada(temporada);
        serie.setEpisodio(episodio);
        serie.setData(dataFormatada);


        try {
            getContentResolver().update(enderecoSerieEditar, serie.getContentValues(), null, null);

            Toast.makeText(this, getString(R.string.serie_atualizada), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNome,
                    getString(R.string.erro_guardar_serie),
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, RateItContentProvider.ENDERECO_CATEGORIAS, BdTableCategorias.TODAS_COLUNAS, null, null, BdTableCategorias.CAMPO_GENERO
        );

        return cursorLoader;
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     *
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context,
     * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraCategoriasSpinner(data);
        categoriasCarregadas = true;
        actualizaCategoriaSelecionada();    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        categoriasCarregadas = false;
        categoriaAtualizada = false;
        mostraCategoriasSpinner(null);
    }
}
