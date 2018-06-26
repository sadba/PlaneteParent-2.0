package com.lab.sadba.loginparent.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.sadba.loginparent.Model.Abscence;
import com.lab.sadba.loginparent.R;

import java.util.List;

public class AbscenceAdapter extends RecyclerView.Adapter<AbscenceAdapter.AbscenceViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Abscence> abscences;

    public AbscenceAdapter(Context context, List<Abscence> abscences){
        this.context = context;
        this.abscences = abscences;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public AbscenceAdapter.AbscenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.abscence_layout,parent,false);
        return new AbscenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AbscenceAdapter.AbscenceViewHolder holder, int position) {
        Abscence abscence = abscences.get(position);
        holder.setItemContent(abscence);
    }

    @Override
    public int getItemCount() {
        return abscences.size();
    }

    class AbscenceViewHolder extends RecyclerView.ViewHolder{

        TextView abs_cours,abs_date, abs_heuref, abs_heured, abs_motif;

        public AbscenceViewHolder(View itemView){
            super(itemView);
            abs_cours = itemView.findViewById(R.id.discipline_abscence);
            abs_date = itemView.findViewById(R.id.date_abscence);
            abs_heured = itemView.findViewById(R.id.heure_d_abscence);
            abs_heuref = itemView.findViewById(R.id.heure_f_abscence);
            abs_motif = itemView.findViewById(R.id.motif_abscence);
        }

        void setItemContent(Abscence abscence){
            abs_cours.setText(abscence.getDiscipline());
            abs_date.setText(abscence.getDate_absence());
            abs_heured.setText(abscence.getHeure_d());
            abs_heuref.setText(abscence.getHeure_f());
            abs_motif.setText(abscence.getMotif());

        }
    }
}
