package pt.ipg.rateit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import static pt.ipg.rateit.DefinicoesApp.atividade_filmes;

public class MainFilmes extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ID_CURSO_LOADER_FILMES = 0;

    private RecyclerView recyclerViewFilmes;
    private AdaptadorFilmes adaptadorFilmes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_filmes);

        recyclerViewFilmes = (RecyclerView) findViewById(R.id.recyclerViewFilmes);
        adaptadorFilmes = new AdaptadorFilmes();
        recyclerViewFilmes.setAdapter(adaptadorFilmes);
        recyclerViewFilmes.setLayoutManager(new LinearLayoutManager(this));
    }

    public void AddFilmeActivity(View view) {
        Intent intent = new Intent(this, AddFilme.class);

        startActivity(intent);
    }

    public void EditFilmeActivity(View view) {
        Intent intent = new Intent(this, EditFilme.class);

        startActivity(intent);
    }

    public void DelFilmeActivity(View view) {
        Intent intent = new Intent(this, DelFilme.class);

        startActivity(intent);
    }

    public void ListaFilmeActivity(View view) {
        Intent intent = new Intent(this, ListaFilmes.class);

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

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorFilmes.setCursor(null);
    }
}
