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

public class AddFilme extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
                }else if (!mensagem.matches("[a-zA-Z ]+")){
                    editTextFilmeName.setError(getString(R.string.characters));
                }else{
                    finish();
                    Toast.makeText(AddFilme.this,getString(R.string.filme_adicionado),Toast.LENGTH_SHORT).show();
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
