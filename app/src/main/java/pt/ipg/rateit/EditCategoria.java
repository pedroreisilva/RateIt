package pt.ipg.rateit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class EditCategoria extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{


    private EditText editTextNomeCategoria;

    private Categorias categoria = null;

    private Uri enderecoCategoriaEditar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categoria);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editTextNomeCategoria= (EditText) findViewById(R.id.editTextNomeCategoria);

        Intent intent = getIntent();

        long idListas = intent.getLongExtra(MainCategorias.ID_CATEGORIA, -1);

        if(idListas==-1){
            Toast.makeText(this, "Erro: não foi possível ler a lista", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        enderecoCategoriaEditar = Uri.withAppendedPath(RateItContentProvider.ENDERECO_CATEGORIAS, String.valueOf(idListas));

        Cursor cursor = getContentResolver().query(enderecoCategoriaEditar, BdTableCategorias.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler a categoria", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

         categoria = Categorias.fromCursor(cursor);

        editTextNomeCategoria.setText(categoria.getGenero());
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

        String NewName = editTextNomeCategoria.getText().toString();
        if (NewName.trim().length() == 0) {
            editTextNomeCategoria.setError(getString(R.string.nome_obrigatoria));
            editTextNomeCategoria.requestFocus();
        } else {
            finish();
            Toast.makeText(EditCategoria.this, getString(R.string.categoria_atualizado), Toast.LENGTH_SHORT).show();
        }

        // guardar os dados
        categoria.setGenero(NewName);

        try {
            getContentResolver().update(enderecoCategoriaEditar, categoria.getContentValues(), null, null);

            Toast.makeText(this, getString(R.string.categoria_atualizado), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeCategoria,
                    getString(R.string.erro_guardar_filme),
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}


