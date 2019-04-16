package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import static pt.ipg.rateit.DefinicoesApp.atividade_series;

public class MainSeries extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        Toast.makeText(MainSeries.this,atividade_series,Toast.LENGTH_SHORT).show();

        Button buttonAddSerie = findViewById(R.id.buttonAddSerie);
        Button buttonEditSerie = findViewById(R.id.buttonEditSerie);
        Button buttonDelSerie = findViewById(R.id.buttonDelSerie);
        Button buttonListaSeries = findViewById(R.id.buttonListaSeries);

                buttonAddSerie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentaddserie = new Intent(v.getContext(), AddSerie.class);
                        startActivity(intentaddserie);
                    }
                });

                buttonEditSerie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intenteditserie = new Intent(v.getContext(), EditSerie.class);
                        startActivity(intenteditserie);
                    }
                });

                buttonDelSerie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentdelserie = new Intent(v.getContext(), DelSerie.class);
                        startActivity(intentdelserie);
                    }
                });

                buttonListaSeries.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentlistaseries = new Intent(v.getContext(), ListaSeries.class);
                        startActivity(intentlistaseries);
                    }
                });

    }
}
