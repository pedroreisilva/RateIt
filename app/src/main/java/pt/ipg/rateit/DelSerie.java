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

public class DelSerie extends AppCompatActivity {

    private Uri enderecoSerieApagar;

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
        TextView textViewTemporada = (TextView) findViewById(R.id.textViewDelTemporada);
        TextView textViewEpisodio = (TextView) findViewById(R.id.textViewDelEpisodio);
        TextView textViewData = (TextView) findViewById(R.id.textViewData);

        Intent intent = getIntent();
        long idSerie = intent.getLongExtra(MainSeries.ID_SERIE, -1);
        if (idSerie == -1) {
            Toast.makeText(this, "Erro: não foi possível apagar a série", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoSerieApagar = Uri.withAppendedPath(RateItContentProvider.ENDERECO_SERIES, String.valueOf(idSerie));

        Cursor cursor = getContentResolver().query(enderecoSerieApagar, BdTableSeries.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível apagar a série", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Series serie = Series.fromCursor(cursor);

        textViewNome.setText(serie.getNome());
        textViewCategoria.setText(serie.getNomeCategoria());
        textViewNota.setText(String.valueOf(serie.getNota()));
        textViewTemporada.setText(String.valueOf(serie.getTemporada()));
        textViewEpisodio.setText(String.valueOf(serie.getEpisodio()));
        textViewData.setText(String.valueOf(serie.getData()));
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
        int seriesApagados = getContentResolver().delete(enderecoSerieApagar, null, null);

        if (seriesApagados == 1) {
            Toast.makeText(this, "Série eliminado com sucesso", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Erro: Não foi possível eliminar a série", Toast.LENGTH_LONG).show();
        }
    }

}
