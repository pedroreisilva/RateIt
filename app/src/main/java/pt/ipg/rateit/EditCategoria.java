package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditCategoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categoria);

        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button buttonSaveFilme = findViewById(R.id.buttonSaveCategoria);
        buttonSaveFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextCategoriaName = findViewById(R.id.editTextNomeCategoria);
                String mensagem = editTextCategoriaName.getText().toString();

                if (mensagem.trim().length() == 0) {
                    editTextCategoriaName.setError(getString(R.string.nome_obrigatoria));
                }else {
                    finish();
                    Toast.makeText(EditCategoria.this, getString(R.string.categoria_atualizado), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
