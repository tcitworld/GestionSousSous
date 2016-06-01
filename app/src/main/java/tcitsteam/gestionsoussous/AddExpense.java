package tcitsteam.gestionsoussous;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class AddExpense extends AppCompatActivity {

    TextView editNom;
    TextView editSum;
    TextView editDetail;
    Button saveButton;
    Expense ex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        editNom = (TextView) findViewById(R.id.editNom);
        editSum = (TextView) findViewById(R.id.editSum);
        editDetail = (TextView) findViewById(R.id.editDetail);
        saveButton = (Button) findViewById(R.id.saveExpense);

        Intent i = getIntent();
        if (i.getSerializableExtra("obj") != null) {
            ex = (Expense) i.getSerializableExtra("obj");

            editNom.setText(ex.getNom());
            editSum.setText(String.valueOf(ex.getMontant()));
            editDetail.setText(ex.getDetail());
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_expense, menu);
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
            startActivity(new Intent(getBaseContext(),SettingsActivity.class));
            return true;
        }

        if (id == android.R.id.home) {
            // app icon in action bar clicked; go home
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
