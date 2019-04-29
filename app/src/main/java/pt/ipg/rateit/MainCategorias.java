package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import static pt.ipg.rateit.DefinicoesApp.atividade_categorias;

public class MainCategorias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        Toast.makeText(MainCategorias.this,atividade_categorias,Toast.LENGTH_SHORT).show();

        Button buttonAddCategoria = findViewById(R.id.buttonAddCategoria);
        Button buttonEditCategoria = findViewById(R.id.buttonEditCategoria);
        Button buttonDelCategoria = findViewById(R.id.buttonDelCategoria);
        Button buttonListaCategorias = findViewById(R.id.buttonListaCategorias);

        buttonAddCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentaddcategoria = new Intent(v.getContext(), AddCategoria.class);
                startActivity(intentaddcategoria);
            }
        });

        buttonEditCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenteditcategoria = new Intent(v.getContext(), EditCategoria.class);
                startActivity(intenteditcategoria);
            }
        });

        buttonDelCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentdelcategoria = new Intent(v.getContext(), DelCategoria.class);
                startActivity(intentdelcategoria);
            }
        });

        buttonListaCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlistacategorias = new Intent(v.getContext(), ListaCategorias.class);
                startActivity(intentlistacategorias);
            }
        });
    }
}
