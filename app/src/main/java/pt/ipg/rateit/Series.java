package pt.ipg.rateit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import static pt.ipg.rateit.DefinicoesApp.atividade_series;

public class Series extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        Toast.makeText(Series.this,atividade_series,Toast.LENGTH_SHORT).show();
    }
}
