package pt.ipg.rateit;

import android.content.Context;
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
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.Toast;

public class MainFilmes extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ID_FILME = "ID_FILME";

    private static final int ID_CURSO_LOADER_FILMES = 0;
    private RecyclerView recyclerViewFilmes;
    private AdaptadorFilmes adaptadorFilmes;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_filmes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_FILMES, null, this);
        recyclerViewFilmes = (RecyclerView) findViewById(R.id.RecyclerViewFilmes);
        adaptadorFilmes = new AdaptadorFilmes(this);
        recyclerViewFilmes.setAdapter(adaptadorFilmes);
        recyclerViewFilmes.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_FILMES, null, this);

        super.onResume();
    }

    public void atualizaOpcoesMenu() {
        Filmes filme = adaptadorFilmes.getFilmeSelecionado();

        boolean mostraAlterarEliminar = (filme != null);

        menu.findItem(R.id.action_alterar).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.action_eliminar).setVisible(mostraAlterarEliminar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        this.menu = menu;

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_inserir) {
            Intent intent = new Intent(this, AddFilme.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.action_alterar) {
            Intent intent = new Intent(this, EditFilme.class);
            intent.putExtra(ID_FILME, adaptadorFilmes.getFilmeSelecionado().getId());
            startActivity(intent);

            return true;
        } else if (id == R.id.action_eliminar) {
            Intent intent = new Intent(this, DelFilme.class);
            intent.putExtra(ID_FILME, adaptadorFilmes.getFilmeSelecionado().getId());

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
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

