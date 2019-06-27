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

public class MainSeries extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSO_LOADER_SERIES = 0;
    public static final String ID_SERIE = "ID_SERIE";

    private RecyclerView recyclerViewSeries;
    private AdaptadorSeries adaptadorSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_series);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_SERIES, null, this);

        recyclerViewSeries = (RecyclerView) findViewById(R.id.RecyclerViewSeries);
        adaptadorSeries = new AdaptadorSeries(this);
        recyclerViewSeries.setAdapter(adaptadorSeries);
        recyclerViewSeries.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_SERIES, null, this);

        super.onResume();
    }

    private Menu menu;

    public void atualizaOpcoesMenu() {
        Series serie = adaptadorSeries.getSerieSelecionada();

        boolean mostraAlterarEliminar = (serie != null);

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
            Intent intent = new Intent(this, AddSerie.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.action_alterar) {
            Intent intent = new Intent(this, EditSerie.class);
            intent.putExtra(ID_SERIE, adaptadorSeries.getSerieSelecionada().getId());
            startActivity(intent);

            return true;
        } else if (id == R.id.action_eliminar) {
            Intent intent = new Intent(this, DelSerie.class);
            intent.putExtra(ID_SERIE, adaptadorSeries.getSerieSelecionada().getId());
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(this, RateItContentProvider.ENDERECO_SERIES, BdTableSeries.TODAS_COLUNAS, null, null, BdTableSeries.CAMPO_NOME
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorSeries.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorSeries.setCursor(null);
    }

}

