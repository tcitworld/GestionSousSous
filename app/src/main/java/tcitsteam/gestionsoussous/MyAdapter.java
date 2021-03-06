package tcitsteam.gestionsoussous;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tcit on 22/05/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Operation> cpt;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView nom;
        protected TextView detail;
        protected TextView montant;
        public ViewHolder(View v) {
            super(v);
            nom =  (TextView) v.findViewById(R.id.nom);
            detail = (TextView)  v.findViewById(R.id.detail);
            montant = (TextView)  v.findViewById(R.id.montant);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Operation> cpt) {
        this.cpt = cpt;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nom.setText(cpt.get(position).getNom());

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        holder.detail.setText(cpt.get(position).getDetail());
        holder.montant.setText(df.format(cpt.get(position).getMontant()) + "€");
        if (cpt.get(position).getType()) {
            holder.montant.setTextColor(Color.GREEN);
        } else {
            holder.montant.setTextColor(Color.RED);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cpt.size();
    }
}