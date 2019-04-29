package pt.ipg.rateit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSeries = findViewById(R.id.buttonSeries);
        Button buttonFilmes = findViewById(R.id.buttonFilmes);
        Button buttonCategorias = findViewById(R.id.buttonCategorias);

                buttonFilmes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentfilmes = new Intent(MainActivity.this, MainFilmes.class);
                            startActivity(intentfilmes);

                        }
                    }
                    );

                buttonSeries.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentseries = new Intent(MainActivity.this, MainSeries.class);
                            startActivity(intentseries);
                        }
                    }
                    );

                buttonCategorias.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentcategorias = new Intent(MainActivity.this, MainCategorias.class);
                            startActivity(intentcategorias);
                                            }
                                        }
        );

    }
}
