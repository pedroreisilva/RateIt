package pt.ipg.rateit;

import android.content.Intent;
import android.content.Context;
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
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ID_CURSO_LOADER_FILMES = 0;

    private RecyclerView recyclerViewFilmes;
    private AdaptadorFilmes adaptadorFilmes;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_FILMES, null, this);

        recyclerViewFilmes = (RecyclerView) findViewById(R.id.recyclerViewFilmes);
        adaptadorFilmes = new AdaptadorFilmes(this);
        recyclerViewFilmes.setAdapter(adaptadorFilmes);
        recyclerViewFilmes.setLayoutManager(new LinearLayoutManager(this));

    }

    public void FilmesActivity(View view) {

        Intent intent = new Intent(this, MainFilmes.class);

        startActivity(intent);
    }
    public void SeriesActivity(View view) {
        Intent intent = new Intent(this, MainSeries.class);

        startActivity(intent);
    }
    public void CategoriasActivity(View view) {
        Intent intent = new Intent(this, MainCategorias.class);

        startActivity(intent);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(this, RateItContentProvider.ENDERECO_FILMES, BdTableFilmes.TODAS_COLUNAS, null, null, BdTableFilmes.CAMPO_NOME
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorFilmes.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorFilmes.setCursor(null);
    }
}

