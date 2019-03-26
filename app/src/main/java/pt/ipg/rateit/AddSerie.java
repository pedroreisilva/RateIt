package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSerie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_serie);

        Button buttonCancel = findViewById(R.id.buttonCancel);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button buttonInsSerie = findViewById(R.id.buttonInsSerie);

        buttonInsSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextSerieName = findViewById(R.id.editTextSerieName);
                String mensagem = editTextSerieName.getText().toString();

                if (mensagem.trim().length() == 0) {
                    editTextSerieName.setError(getString(R.string.nome_obrigatoria));
                }else if (!mensagem.matches("[a-zA-Z ]+")){
                    editTextSerieName.setError(getString(R.string.characters));
                }else{
                    finish();
                    Toast.makeText(AddSerie.this,getString(R.string.serie_adicionada),Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
