package pt.ipg.rateit;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DelFilme extends AppCompatActivity {

    private Uri enderecoFilmeApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_filme);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewNome = (TextView) findViewById(R.id.textViewNome);
        TextView textViewCategoria = (TextView) findViewById(R.id.textViewCategoria);
        TextView textViewNota = (TextView) findViewById(R.id.textViewNota);
        TextView textViewData = (TextView) findViewById(R.id.textViewData);

        Intent intent = getIntent();

        long idFilme = intent.getLongExtra(MainFilmes.ID_FILME, -1);

        if (idFilme == -1) {
            Toast.makeText(this, ("Erro a eliminar o filme!"), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoFilmeApagar = Uri.withAppendedPath(RateItContentProvider.ENDERECO_FILMES, String.valueOf(idFilme));

        Cursor cursor = getContentResolver().query(enderecoFilmeApagar, BdTableFilmes.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, ("Erro a eliminar o filme!"), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Filmes filme = Filmes.fromCursor(cursor);

        textViewNome.setText(filme.getNome());
        textViewCategoria.setText(filme.getNomeCategoria());
        textViewNota.setText(String.valueOf(filme.getNota()));
        textViewData.setText(filme.getData());
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
        int filmesApagados = getContentResolver().delete(enderecoFilmeApagar, null, null);

        if (filmesApagados == 1) {
            Toast.makeText(this, "Filme eliminado com sucesso", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Erro: Não foi possível eliminar o filme", Toast.LENGTH_LONG).show();
        }
    }

}
