package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddFilme extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_filme);


        Spinner SpinnerCat = findViewById(R.id.spinnercat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCat.setAdapter(adapter);
        SpinnerCat.setOnItemSelectedListener(this);

        Spinner SpinnerNota = findViewById(R.id.spinnernotas);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this, R.array.notas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerNota.setAdapter(adapters);
        SpinnerNota.setOnItemSelectedListener(this);


        mTextView = findViewById(R.id.textViewData);
        mButton = findViewById(R.id.buttonData);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int ano = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(AddFilme.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mTextView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, dia, mes, ano);
                dpd.show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    TextView mTextView;
    Button mButton;
    Calendar c;
    DatePickerDialog dpd;


    public void validarCampos(){
        EditText editTextFilmeName = findViewById(R.id.editTextNomeFilme);
        String mensagem = editTextFilmeName.getText().toString();

        if (mensagem.trim().length() == 0) {
            editTextFilmeName.setError(getString(R.string.nome_obrigatoria));
        }else{
            finish();
            Toast.makeText(AddFilme.this,getString(R.string.filme_adicionado),Toast.LENGTH_SHORT).show();
        }
    }


    public void guardar(View view) {

        validarCampos();

    }

    public void finish(View view) {

        finish();
        Toast.makeText(this, (getString(R.string.finish)), Toast.LENGTH_SHORT).show();
    }
}
