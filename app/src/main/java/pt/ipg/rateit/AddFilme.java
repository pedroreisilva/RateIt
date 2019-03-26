package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFilme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filme);

        Button buttonCancel = findViewById(R.id.buttonCancel);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Button buttonInsFilme = findViewById(R.id.buttonInsFilme);

        buttonInsFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextFilmeName = findViewById(R.id.editTextFilmeName);
                String mensagem = editTextFilmeName.getText().toString();

                if (mensagem.trim().length() == 0) {
                    editTextFilmeName.setError(getString(R.string.nome_obrigatoria));
                }else{
                    finish();
                    Toast.makeText(AddFilme.this,getString(R.string.filme_adicionado),Toast.LENGTH_SHORT).show();
                    }
                }



        });
    }
}
