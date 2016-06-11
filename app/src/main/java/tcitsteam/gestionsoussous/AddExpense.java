package tcitsteam.gestionsoussous;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;


public class AddExpense extends AppCompatActivity {

    TextView editNom;
    TextView editSum;
    TextView editDetail;
    RadioButton outcome, income;
    RadioGroup radioGroup;
    Button saveButton;
    DatePicker mDatePicker;

    Operation ex;
    boolean type;
    boolean nouveau = true;
    int key;

    MaBaseSQLite maBase;

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

        mDatePicker = (DatePicker) findViewById(R.id.dpResult);

        Calendar c = Calendar.getInstance();

        Intent i = getIntent();
        if (i.getSerializableExtra("obj") != null) {
            nouveau = false;
            ex = (Operation) i.getSerializableExtra("obj");

            editNom.setText(ex.getNom());

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat();
            df.setDecimalFormatSymbols(symbols);
            df.setMaximumFractionDigits(2);

            editSum.setText(df.format(ex.getMontant()));
            editDetail.setText(ex.getDetail());
            if (ex.getType()) {
                income.setChecked(true);
            } else {
                outcome.setChecked(true);
            }

            c.setTimeInMillis(ex.getDate().getTime());

            key = i.getIntExtra("key",0);
        }

        mDatePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        maBase = new MaBaseSQLite(this);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, mDatePicker.getDayOfMonth());
                cal.set(Calendar.MONTH, mDatePicker.getMonth());
                cal.set(Calendar.YEAR, mDatePicker.getYear());
                if (nouveau) {
                    if ("".equals(editNom.getText().toString()) || "".equals(editSum.getText().toString())) {
                        Toast.makeText(AddExpense.this, R.string.fillAllFields, Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        ex = new Operation(editNom.getText().toString(), editDetail.getText().toString(), Double.parseDouble(editSum.getText().toString()), type, new Date(cal.getTimeInMillis()));
                    }
                } else {
                    ex.setNom(editNom.getText().toString());
                    ex.setMontant(Float.parseFloat(editSum.getText().toString()));
                    ex.setDetail(editDetail.getText().toString());
                    ex.setType(type);
                    ex.setDate(new Date(cal.getTimeInMillis()));
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

        if (id == R.id.delete_expense) {
            if (!nouveau) {
                new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.confirmDeleteTitle)
                    .setMessage(R.string.confirmDeleteText)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            intent.putExtra("delete", true);
                            intent.putExtra("key", key);
                            intent.putExtra("obj",ex);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();
            return true;
            }
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
