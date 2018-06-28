package com.lab.sadba.loginparent.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.sadba.loginparent.Model.Retard;
import com.lab.sadba.loginparent.R;

import java.util.List;

public class RetardAdapter extends RecyclerView.Adapter<RetardAdapter.RetardViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Retard> retards;

    public RetardAdapter(Context context, List<Retard> retards){
        this.context = context;
        this.retards = retards;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public RetardAdapter.RetardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.retard_layout,parent,false);
        return new RetardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RetardAdapter.RetardViewHolder holder, int position) {
        Retard retard = retards.get(position);
        holder.setItemContent(retard);
    }

    @Override
    public int getItemCount() {
        return retards.size();
    }

    class RetardViewHolder extends RecyclerView.ViewHolder{

        TextView retard_cours,retard_date, retard_heure_debut, retard_duree, retard_motif;

        public RetardViewHolder(View itemView){
            super(itemView);
            retard_cours = itemView.findViewById(R.id.discipline_retard);
            retard_date = itemView.findViewById(R.id.date_retard);
            retard_heure_debut = itemView.findViewById(R.id.heure_d_retard);
            retard_duree = itemView.findViewById(R.id.duree_retard);
            retard_motif = itemView.findViewById(R.id.motif_retard);
        }

        void setItemContent(Retard retard){
            retard_cours.setText(retard.getDiscipline());
            retard_date.setText(retard.getDate_absence());
            retard_heure_debut.setText(retard.getHeure_debut_cours());
            retard_duree.setText(retard.getDuree());
            retard_motif.setText(retard.getMotif());

        }
    }
}
