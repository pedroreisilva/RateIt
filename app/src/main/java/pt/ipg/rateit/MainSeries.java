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
        setContentView(R.layout.content_series);

        Toast.makeText(MainSeries.this,atividade_series,Toast.LENGTH_SHORT).show();
    }

    public void AddSerieActivity(View view) {
        Intent intent = new Intent(this, AddSerie.class);

        startActivity(intent);
    }

    public void EditSerieActivity(View view) {
        Intent intent = new Intent(this, EditSerie.class);

        startActivity(intent);
    }

    public void DelSerieActivity(View view) {
        Intent intent = new Intent(this, DelSerie.class);

        startActivity(intent);
    }


}
