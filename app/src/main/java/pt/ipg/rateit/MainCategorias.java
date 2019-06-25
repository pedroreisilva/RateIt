package pt.ipg.rateit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainCategorias extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ID_CURSO_LOADER_CATEGORIAS = 0;
    public static final String ID_CATEGORIA = "ID_CATEGORIA";

    private AdaptadorCategorias adaptadorCategorias;
    private RecyclerView recyclerViewCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_categorias);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewCategorias = (RecyclerView) findViewById(R.id.RecyclerViewCategorias);
        adaptadorCategorias = new AdaptadorCategorias(this);
        recyclerViewCategorias.setAdapter(adaptadorCategorias);
        recyclerViewCategorias.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_CATEGORIAS, null, this);

        super.onResume();
    }
    private Menu menu;

    public void atualizaOpcoesMenu(){
        Categorias categoria = adaptadorCategorias.getCategoriaSelecionado();

        boolean mostraAlterarEliminar = (categoria != null);

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

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_inserir) {
            Intent intent = new Intent(this, AddCategoria.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.action_alterar) {
            Intent intent = new Intent(this, EditCategoria.class);
            intent.putExtra(ID_CATEGORIA, adaptadorCategorias.getCategoriaSelecionado().getId());
            startActivity(intent);
            return true;
        } else if (id == R.id.action_eliminar) {
            Intent intent = new Intent(this, DelCategoria.class);
            intent.putExtra(ID_CATEGORIA, adaptadorCategorias.getCategoriaSelecionado().getId());

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {

        CursorLoader cursorLoader = new CursorLoader(this, RateItContentProvider.ENDERECO_CATEGORIAS, BdTableCategorias.TODAS_COLUNAS, null, null, BdTableCategorias.CAMPO_GENERO);


        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorCategorias.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorCategorias.setCursor(null);

    }
}
