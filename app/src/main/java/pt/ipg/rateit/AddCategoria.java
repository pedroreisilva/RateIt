package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCategoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categoria);

        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button buttonInsCategoria = findViewById(R.id.buttonInsCategoria);
        buttonInsCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextCategoriaName = findViewById(R.id.editTextNomeCategoria);
                String mensagem = editTextCategoriaName.getText().toString();

                if (mensagem.trim().length() == 0) {
                    editTextCategoriaName.setError(getString(R.string.nome_obrigatoria));
                }else{
                    finish();
                    Toast.makeText(AddCategoria.this,getString(R.string.categoria_adicionado),Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
