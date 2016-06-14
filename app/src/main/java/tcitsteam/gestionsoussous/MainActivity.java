package tcitsteam.gestionsoussous;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Operation> cpt;
    MaBaseSQLite maBase;

    TextView solde, soldeIncome, soldeOutcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view2);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        maBase = new MaBaseSQLite(this);

        cpt = maBase.getAllValues();
        Collections.sort(cpt, new Comparator<Operation>() {
            public int compare(Operation a, Operation b) {
                return b.getId() - a.getId();
            }
        });

        this.updateSoldes();

        mAdapter = new MyAdapter(cpt);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent i = new Intent(MainActivity.this, AddExpense.class);
                        i.putExtra("obj", cpt.get(position));
                        i.putExtra("key", position);
                        startActivityForResult(i, 1);
                    }
                })
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddExpense.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cpt", cpt);
                i.putExtras(bundle);
                startActivityForResult(i,1);
            }
        });

    }

    private Double getIncomeSum(ArrayList<Operation> op) {
        double sum = 0;
        for (Operation o:op)
            if (o.getType())
                sum+=o.getMontant();
        return sum;
    }

    private Double getExpensesSum(ArrayList<Operation> op) {
        double sum = 0;
        for (Operation o:op)
            if (!o.getType())
                sum+=o.getMontant();
        return sum;
    }

    private Double getOperationsSum(ArrayList<Operation> op) {
        return this.getIncomeSum(op) - this.getExpensesSum(op);
    }

    private void updateSoldes() {
        solde = (TextView) findViewById(R.id.soldeGlobal);
        int n = 0;
        if (this.getOperationsSum(cpt) > 200) {
            n = 0;
        } else if (this.getOperationsSum(cpt) < -200){
            n = 100;
        } else {
            n = 100-(this.getOperationsSum(cpt).intValue() + 200) / 4;
        }

        solde.setBackgroundColor(Color.rgb((255 * n) / 100, (255 * (100 - n)) / 100, 0));
        solde.setText(getString(R.string.balance) + " : " + this.getOperationsSum(cpt));

        soldeIncome = (TextView) findViewById(R.id.soldepos);
        soldeIncome.setText(getString(R.string.incomes) + ": " + this.getIncomeSum(cpt));

        soldeOutcome = (TextView) findViewById(R.id.soldeNeg);
        soldeOutcome.setText(getString(R.string.expenses) + " : " + this.getExpensesSum(cpt));
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if(resultCode == RESULT_OK){

                Operation ex = (Operation) data.getExtras().getSerializable("obj");

                if (data.getExtras().getBoolean("delete")) {
                    Log.d("Action","Deleting operation : " + ex);
                    maBase.deleteExpense(ex);
                    cpt.remove(data.getIntExtra("key", 0));


                } else {

                    if (data.getExtras().getBoolean("new")) {
                        Log.d("expense", ex.toString());
                        ex.setId(((int) maBase.insertExpense(ex)));
                        cpt.add(0, ex);
                    } else {
                        int pos = data.getIntExtra("key", 0);
                        cpt.set(pos, ex);
                        maBase.editExpense(ex);
                    }
                    for (int i = 0; i < cpt.size(); i++)
                        Log.d("TAG", cpt.get(i).toString());
                }
                this.updateSoldes();
                mAdapter.notifyDataSetChanged();
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.operations) {

        } else if (id == R.id.upcoming) {
            startActivity(new Intent(MainActivity.this, upcoming.class));
        } else if (id == R.id.statistics) {
            startActivity(new Intent(MainActivity.this, Statistics.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
