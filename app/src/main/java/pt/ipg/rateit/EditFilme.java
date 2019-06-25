package pt.ipg.rateit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditFilme extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int ID_CURSO_LOADER_CATEGORIAS = 0;

    private EditText editTextNome;
    private Spinner spinnerCategorias;
    private EditText editTextNota;
    private EditText editTextData;

    private  Filmes filme = null;

    private boolean filmesCarregados = false;
    private boolean filmeAtualizado = false;

    private Uri enderecoFilmeEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_filme);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editTextNome = (EditText) findViewById(R.id.editTextNome);
        spinnerCategorias = (Spinner) findViewById(R.id.spinnerCategorias);
        editTextNota = (EditText) findViewById(R.id.editTextNota);
        editTextData = (EditText) findViewById(R.id.editTextData);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);

        Intent intent = getIntent();

        long idFilme = intent.getLongExtra(MainFilmes.ID_FILME, -1);

        if (idFilme == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o filme", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoFilmeEditar = Uri.withAppendedPath(RateItContentProvider.ENDERECO_FILMES, String.valueOf(idFilme));

        Cursor cursor = getContentResolver().query(enderecoFilmeEditar, BdTableFilmes.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler o film", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        filme = Filmes.fromCursor(cursor);

        editTextNome.setText(filme.getNome());
        editTextNota.setText(String.valueOf(filme.getNota()));
        editTextData.setText(filme.getData());

        actualizaFilmeSelecionado();


    }

    private void actualizaFilmeSelecionado() {
        if (!filmesCarregados) return;
        if (filmeAtualizado) return;

        for (int i = 0; i < spinnerCategorias.getCount(); i++) {
            if (spinnerCategorias.getItemIdAtPosition(i) == filme.getCategoria()) {
                spinnerCategorias.setSelection(i);
                break;
            }
        }

        filmeAtualizado = true;
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
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_guardar) {
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
        int nota;
        String strNota = editTextNota.getText().toString();


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
            Toast.makeText(EditFilme.this, getString(R.string.filme_adicionado), Toast.LENGTH_SHORT).show();
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


        long idCategoria = spinnerCategorias.getSelectedItemId();

        // guardar os dados

        Filmes filme = new Filmes();

        filme.setNome(nome);
        filme.setCategoria(idCategoria);
        filme.setNota(nota);
        filme.setData(dataFormatada);


        try {
            getContentResolver().insert(RateItContentProvider.ENDERECO_FILMES, filme.getContentValues());

            Toast.makeText(this, getString(R.string.filme_adicionado), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNome,
                    getString(R.string.erro_guardar_filme),
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }

    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, RateItContentProvider.ENDERECO_CATEGORIAS, BdTableCategorias.TODAS_COLUNAS, null, null, BdTableCategorias.CAMPO_GENERO
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraCategoriasSpinner(data);
        filmesCarregados = true;
        actualizaFilmeSelecionado();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        filmesCarregados = false;
        filmeAtualizado = false;
        mostraCategoriasSpinner(null);
    }
}
