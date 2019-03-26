package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddSerie extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

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


        Spinner spinnercat = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercat.setAdapter(adapter);
        spinnercat.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
