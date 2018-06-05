package com.lab.sadba.loginparent.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.sadba.loginparent.Model.Temps;
import com.lab.sadba.loginparent.R;

import java.util.List;

public class TempsAdapter extends RecyclerView.Adapter<TempsAdapter.TempsViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Temps> temps;

    public TempsAdapter(Context context, List<Temps> temps) {
        this.context = context;
        this.temps = temps;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public TempsAdapter.TempsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.emploi_layout, parent, false);
        return new TempsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TempsAdapter.TempsViewHolder holder, int position) {
        Temps temp = temps.get(position);
        holder.setItemContent(temp);
    }

    @Override
    public int getItemCount() {
        return temps.size();
    }

    class TempsViewHolder extends RecyclerView.ViewHolder{

        TextView tvCours,tvHeureFin, tvHeureDebut, tvSale;

        public TempsViewHolder(View itemView){
            super(itemView);
            tvCours = itemView.findViewById(R.id.libelle_discipline_id);
            tvHeureDebut = itemView.findViewById(R.id.heure_debut_id);
            tvHeureFin = itemView.findViewById(R.id.heure_fin_id);
            tvSale = itemView.findViewById(R.id.salle_id);
        }

        void setItemContent(Temps temp){
            tvCours.setText(temp.getLibelle_discipline());
            tvHeureDebut.setText(temp.getHeure_debut());
            tvHeureFin.setText(temp.getHeure_fin());
            tvSale.setText(temp.getLibelle_classe_physique());

        }
    }
}
