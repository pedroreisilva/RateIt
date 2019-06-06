package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import static pt.ipg.rateit.DefinicoesApp.atividade_filmes;

public class MainFilmes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_filmes);

        Toast.makeText(MainFilmes.this,atividade_filmes,Toast.LENGTH_SHORT).show();
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

}
