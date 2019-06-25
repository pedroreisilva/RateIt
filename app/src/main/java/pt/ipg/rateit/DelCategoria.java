package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DelCategoria extends AppCompatActivity {

    private Uri enderecoCategoriaApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_categoria);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewCategoria = (TextView) findViewById(R.id.textViewCategoria);

        Intent intent = getIntent();
        long idCategoria = intent.getLongExtra(MainCategorias.ID_CATEGORIA, -1);
        if (idCategoria == -1) {
            Toast.makeText(this, "Erro: não foi possível apagar a categoria", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoCategoriaApagar = Uri.withAppendedPath(RateItContentProvider.ENDERECO_CATEGORIAS, String.valueOf(idCategoria));

        Cursor cursor = getContentResolver().query(enderecoCategoriaApagar, BdTableCategorias.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível apagar a categoria", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Categorias categoria = Categorias.fromCursor(cursor);

        textViewCategoria.setText(categoria.getGenero());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eliminar, menu);
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
        } else if (id == R.id.action_eliminar) {
            eliminar();
            return true;
        } else if (id == R.id.action_cancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void eliminar() {
        // todo: perguntar ao utilizador se tem a certeza
        int categoriasApagados = getContentResolver().delete(enderecoCategoriaApagar, null, null);

        if (categoriasApagados == 1) {
            Toast.makeText(this, "Categoria eliminada com sucesso", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Erro: Não foi possível eliminar a categoria", Toast.LENGTH_LONG).show();
        }
    }

}
