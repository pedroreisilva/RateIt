package pt.ipg.rateit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AddCategoria extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private EditText editTextNomeCategoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categoria);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextNomeCategoria = (EditText) findViewById(R.id.editTextNomeCategoria);

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

    private void guardar(){

        String NomeCategoria = editTextNomeCategoria.getText().toString();

        if (NomeCategoria.trim().length() == 0) {
            editTextNomeCategoria.setError(getString(R.string.nome_obrigatoria));
            editTextNomeCategoria.requestFocus();
            return;
        }else if(NomeCategoria.trim().length() >= 25){
            editTextNomeCategoria.setError("Nome demasiado grande!");
            editTextNomeCategoria.requestFocus();
            return;
        }else {
            Toast.makeText(AddCategoria.this, getString(R.string.categoria_adicionado), Toast.LENGTH_SHORT).show();
            finish();
        }

        Categorias categoria = new Categorias();

        categoria.setGenero(NomeCategoria);

        try {
            getContentResolver().insert(RateItContentProvider.ENDERECO_CATEGORIAS, categoria.getContentValues());

            Toast.makeText(this, getString(R.string.categoria_adicionado), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeCategoria,
                    "Erro a guardar a categoria",
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