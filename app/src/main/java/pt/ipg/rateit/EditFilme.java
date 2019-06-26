package pt.ipg.rateit;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditFilme extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int ID_CURSOR_LOADER_CARROS = 0;

    private Spinner spinnerCategoria;
    private EditText editTextNome;
    private EditText editTextNota;
    private EditText editTextData;

    private Filmes filme = null;

    private boolean categoriasCarregadas = false;
    private boolean categoriaAtualizada = false;

    private Uri enderecoFilmeEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_filme);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextData = (EditText) findViewById(R.id.editTextData);
        spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategorias);
        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextNota = (EditText) findViewById(R.id.editTextNota);

        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_CARROS, null, this);

        Intent intent = getIntent();

        long idFilme = intent.getLongExtra(MainFilmes.ID_FILME ,-1);

        if(idFilme == -1){
            Toast.makeText(this, "Erro: não foi possivel ler o filme!", Toast.LENGTH_LONG ).show();
            finish();
            return;
        }

        enderecoFilmeEditar = Uri.withAppendedPath(RateItContentProvider.ENDERECO_FILMES, String.valueOf(idFilme));

        Cursor cursor = getContentResolver().query(enderecoFilmeEditar, BdTableFilmes.TODAS_COLUNAS, null, null, null);

        if(!cursor.moveToNext()){
            Toast.makeText(this,"Erro não foi possivel ler o filme!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        filme = Filmes.fromCursor(cursor);

        editTextData.setText(filme.getData());
        editTextNome.setText(filme.getNome());
        editTextNota.setText(String.valueOf(filme.getNota()));

        actualizaCategoriaSelecionada();


        /*SpinnerTipoDespesas = findViewById(R.id.spinnerBrand);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.Despesas,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerTipoDespesas.setAdapter(adapter1);*/

    }

    private void actualizaCategoriaSelecionada() {
        if (!categoriasCarregadas) return;
        if (categoriaAtualizada) return;

        for (int i = 0; i < spinnerCategoria.getCount(); i++) {
            if (spinnerCategoria.getItemIdAtPosition(i) == filme.getCategoria()) {
                spinnerCategoria.setSelection(i);
                break;
            }
        }

        categoriaAtualizada = true;
    }

    public void validarCampos(){
        EditText mensagemData = (EditText) findViewById(R.id.editTextData);
        EditText mensagemNome = (EditText) findViewById(R.id.editTextNome);
        EditText mensagemNota = (EditText) findViewById(R.id.editTextNota);

        String Data = mensagemData.getText().toString();
        String Nome = mensagemNome.getText().toString();
        String Nota = mensagemNota.getText().toString();


        if(Data.length() != 10 || Data.charAt(2) != '/' || Data.charAt(5) != '/' ){
            mensagemData.setError("Introduza uma data válida ! (XX/XX/XXXX)");
            mensagemData.requestFocus();

            return;
        }


        if (Nome.trim().length()==0){
            mensagemNome.setError("Introduza um nome!");
            mensagemNome.requestFocus();

            return;

        }


        if (Nota.trim().length() == 0) {
            mensagemNota.setError("Introduza uma nota!");
            mensagemNota.requestFocus();

            return;
        }


        String data = editTextData.getText().toString();
        String nome = editTextNome.getText().toString();

        int nota;
        String strNota = editTextNota.getText().toString();
        nota = Integer.parseInt(strNota);



        long idCategoria = spinnerCategoria.getSelectedItemId();

        // save the data
        Filmes filme = new Filmes();


        filme.setCategoria(idCategoria);
        filme.setData(data);
        filme.setNome(nome);
        filme.setNota(nota);



        try {
            getContentResolver().update(enderecoFilmeEditar, filme.getContentValues(), null, null );

            Toast.makeText(this, (getString(R.string.filme_atualizado)), Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Snackbar.make(editTextData, ("Erro!"), Snackbar.LENGTH_LONG).show();
            e.printStackTrace();
        }



    }


    public void Guardar(View view) {

        validarCampos();

    }

    public void finish(View view) {

        finish();
        Toast.makeText(this, (getString(R.string.finish)), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_CARROS, null, this);

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
        spinnerCategoria.setAdapter(adaptadorCategorias);
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
        actualizaCategoriaSelecionada();

    }

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
