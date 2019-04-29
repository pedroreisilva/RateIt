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

public class EditFilme extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    TextView mTextView;
    Button mButton;
    Calendar c;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_filme);

        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button buttonSaveFilme = findViewById(R.id.buttonSaveFilme);
        buttonSaveFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextSerieName = findViewById(R.id.editTextNomeFilme);
                String mensagem = editTextSerieName.getText().toString();

                if (mensagem.trim().length() == 0) {
                    editTextSerieName.setError(getString(R.string.nome_obrigatoria));
                }else {
                    finish();
                    Toast.makeText(EditFilme.this, getString(R.string.filme_atualizado), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Spinner spinnercat = findViewById(R.id.spinnercat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercat.setAdapter(adapter);
        spinnercat.setOnItemSelectedListener(this);

        Spinner spinnernota = findViewById(R.id.spinnernotas);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this, R.array.notas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnernota.setAdapter(adapters);
        spinnernota.setOnItemSelectedListener(this);


        mTextView = (TextView) findViewById(R.id.textViewData);
        mButton = (Button) findViewById(R.id.buttonData);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int ano = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(EditFilme.this, new DatePickerDialog.OnDateSetListener() {
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
}
