package tcitsteam.gestionsoussous;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class AddExpense extends AppCompatActivity {

    TextView editNom;
    TextView editSum;
    TextView editDetail;
    RadioButton outcome, income;
    RadioGroup radioGroup;
    Button saveButton;
    Expense ex;
    boolean type;
    boolean nouveau = true;
    int key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        editNom = (TextView) findViewById(R.id.editNom);
        editSum = (TextView) findViewById(R.id.editSum);
        editDetail = (TextView) findViewById(R.id.editDetail);
        saveButton = (Button) findViewById(R.id.saveExpense);
        outcome = (RadioButton) findViewById(R.id.outcome);
        income = (RadioButton) findViewById(R.id.income);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                type = checkedId == R.id.income;
            }
        });

        Intent i = getIntent();
        if (i.getSerializableExtra("obj") != null) {
            nouveau = false;
            ex = (Expense) i.getSerializableExtra("obj");

            editNom.setText(ex.getNom());
            editSum.setText(String.valueOf(ex.getMontant()));
            editDetail.setText(ex.getDetail());
            if (ex.getType()) {
                income.setChecked(true);
            } else {
                outcome.setChecked(true);
            }

            key = i.getIntExtra("key",0);
        }


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (nouveau) {
                    ex = new Expense(editNom.getText().toString(), editDetail.getText().toString(), Double.parseDouble(editSum.getText().toString()),type);
                } else {
                    ex.setNom(editNom.getText().toString());
                    ex.setMontant(Float.parseFloat(editSum.getText().toString()));
                    ex.setDetail(editDetail.getText().toString());
                    ex.setType(type);
                    intent.putExtra("key", key);
                }
                Log.d("inside", ex.toString());

                intent.putExtra("new", nouveau);
                intent.putExtra("obj",ex);
                setResult(RESULT_OK, intent);
                finish();
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
