package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static pt.ipg.rateit.DefinicoesApp.adicionar_filme;

public class AddFilme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filme);

        Toast.makeText(AddFilme.this,adicionar_filme,Toast.LENGTH_SHORT).show();
    }
}
