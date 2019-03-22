package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static pt.ipg.rateit.DefinicoesApp.adicionar_serie;

public class AddSerie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_serie);

        Toast.makeText(AddSerie.this,adicionar_serie,Toast.LENGTH_SHORT).show();
    }
}
