package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import static pt.ipg.rateit.DefinicoesApp.atividade_filmes;

public class Filmes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);

        Toast.makeText(Filmes.this,atividade_filmes,Toast.LENGTH_SHORT).show();

        Button buttonAddFilme = findViewById(R.id.buttonAddFilme);
        Button buttonEditFilme = findViewById(R.id.buttonEditFilme);
        Button buttonDelFilme = findViewById(R.id.buttonDelFilme);
        Button buttonListaFilmes = findViewById(R.id.buttonListaFilmes);

                buttonAddFilme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentaddfilme = new Intent(v.getContext(), AddFilme.class);
                        startActivity(intentaddfilme);
                    }
                });

                buttonEditFilme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intenteditfilme = new Intent(v.getContext(), EditFilme.class);
                        startActivity(intenteditfilme);
                    }
                });

                buttonDelFilme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentdelfilme = new Intent(v.getContext(), DelFilme.class);
                        startActivity(intentdelfilme);
                    }
                });

                buttonListaFilmes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentlistafilmes = new Intent(v.getContext(), ListaFilmes.class);
                        startActivity(intentlistafilmes);
                    }
                });
    }
}
