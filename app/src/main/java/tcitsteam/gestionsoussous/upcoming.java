package tcitsteam.gestionsoussous;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class upcoming extends AppCompatActivity implements View.OnClickListener {

    MaBaseSQLite maBase;
    ArrayList<Operation> cpt;
    MyAdapter mAdapter;
    RecyclerView mRecyclerView;
    TextView dateLabel;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateLabel = (TextView) findViewById(R.id.dateLabel);
        maBase = new MaBaseSQLite(this);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        Date d = new Date();
        dateLabel.setText(dateFormatter.format(d.getTime()));
        cpt = maBase.getAllValuesForDate(d);
        Log.d("values4date",cpt.toString());

        mRecyclerView = (RecyclerView) findViewById(R.id.upcoming_rv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(cpt);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent i = new Intent(upcoming.this, AddExpense.class);
                        i.putExtra("obj", cpt.get(position));
                        i.putExtra("key", position);
                        startActivityForResult(i, 1);
                    }
                })
        );

        Button b = (Button) findViewById(R.id.toggleCalendar);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                cpt = maBase.getAllValuesForDate(newDate.getTime());
                Log.d("DATA",cpt.toString());
                mAdapter = new MyAdapter(cpt);
                mRecyclerView.setAdapter(mAdapter);
                dateLabel.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.show();
    }

}
