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
        setContentView(R.layout.content_main_categorias);

        Toast.makeText(MainCategorias.this,atividade_categorias,Toast.LENGTH_SHORT).show();
    }

    public void AddCategoriaActivity(View view) {
        Intent intent = new Intent(this, AddCategoria.class);

        startActivity(intent);
    }
    public void EditCategoriaActivity(View view) {
        Intent intent = new Intent(this, EditCategoria.class);

        startActivity(intent);
    }
    public void DelCategoriaActivity(View view) {
        Intent intent = new Intent(this, DelCategoria.class);

        startActivity(intent);
    }

}
