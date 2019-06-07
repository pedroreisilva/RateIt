package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddFilme extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSO_LOADER_CATEGORIAS = 0;

    private EditText editTextNomeFilme;
    private Spinner spinnerCategorias;
    private Spinner spinnerNota;
    private TextView textViewData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filme);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        spinnerCategorias = (Spinner) findViewById(R.id.spinnercat);
        spinnerNota = (Spinner) findViewById(R.id.spinnernotas);

        Spinner SpinnerCat = findViewById(R.id.spinnercat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCat.setAdapter(adapter);
        SpinnerCat.setOnItemSelectedListener(this);

        Spinner SpinnerNota = findViewById(R.id.spinnernotas);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this, R.array.notas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerNota.setAdapter(adapters);
        SpinnerNota.setOnItemSelectedListener(this);


        mTextView = findViewById(R.id.textViewData);
        mButton = findViewById(R.id.buttonData);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int ano = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(AddFilme.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mTextView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, dia, mes, ano);
                dpd.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);

        textViewData = (TextView) findViewById(R.id.textViewData);
        SimpleDateFormat formatadata= new SimpleDateFormat("dd-MM-yyyy");
        Date data =  new Date();
        String dataFormatada =  formatadata.format(data);

        textViewData.setText(dataFormatada);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    TextView mTextView;
    Button mButton;
    Calendar c;
    DatePickerDialog dpd;

    public void guardar(){

        String nome = editTextNomeFilme.getText().toString();

        if (nome.trim().isEmpty()) {
            editTextNomeFilme.setError(getString(R.string.preecha_nome));
            return;
        }

        SimpleDateFormat formatadata= new SimpleDateFormat("dd-MM-yyyy");
        Date data =  new Date();
        String dataFormatada =  formatadata.format(data);

        textViewData.setText(dataFormatada);

        long idNota = spinnerNota.getSelectedItemId();
        long idCategoria = spinnerCategorias.getSelectedItemId();

        // guardar os dados
        Filmes filme = new Filmes();

        filme.setNome(nome);
        filme.setNota((int) idNota);
        filme.setCategory(idCategoria);
        filme.setData(dataFormatada);


        try {
            getContentResolver().insert(RateItContentProvider.ENDERECO_FILMES, filme.getContentValues());

            Toast.makeText(this, getString(R.string.filme_adicionado), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeFilme,
                    getString(R.string.erro_guardar_filme),
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }


    public void finish(View view) {
        finish();
        Toast.makeText(this, (getString(R.string.finish)), Toast.LENGTH_SHORT).show();
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
        mostraCategoriasSpinner(null);
    }
}
