package tcitsteam.gestionsoussous;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class Statistics extends AppCompatActivity {

    MaBaseSQLite maBase;
    ArrayList<Operation> cpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        BarChart chart = (BarChart) findViewById(R.id.chart);

        HashMap<Integer,Double> values = new HashMap<>();

        maBase = new MaBaseSQLite(this);
        cpt = maBase.getAllValues();
        for (Operation o : cpt) {
            if (!values.containsKey(o.getDate().getMonth()))
                if (o.getType())
                    values.put(o.getDate().getMonth(), o.getMontant());
                else
                    values.put(o.getDate().getMonth(), -o.getMontant());
            else
            if (o.getType())
                values.put(o.getDate().getMonth(), values.get(o.getDate().getMonth()) + o.getMontant());
            else
                values.put(o.getDate().getMonth(), values.get(o.getDate().getMonth()) - o.getMontant());
        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (values.containsKey(i))
                entries.add(new BarEntry(values.get(i).intValue(), i));
        }
        BarDataSet dataset = new BarDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");

        BarData data = new BarData(labels, dataset);
        chart.setDescription("Solde en Fonction du mois");
        chart.setData(data);
    }
}
