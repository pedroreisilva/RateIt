package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import static pt.ipg.rateit.DefinicoesApp.atividade_filmes;

public class Filmes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);

        Toast.makeText(Filmes.this,atividade_filmes,Toast.LENGTH_SHORT).show();
    }
}
