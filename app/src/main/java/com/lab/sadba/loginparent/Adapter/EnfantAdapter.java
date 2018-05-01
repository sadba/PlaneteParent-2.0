package com.lab.sadba.loginparent.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lab.sadba.loginparent.EnfantActivity;
import com.lab.sadba.loginparent.HomeActivity;
import com.lab.sadba.loginparent.MenuActivity;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.R;

import java.util.List;

public class EnfantAdapter extends RecyclerView.Adapter<EnfantAdapter.EnfantViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Enfant>enfants;

    public  EnfantAdapter(Context context , List<Enfant>enfants){
        this.context = context;
        this.enfants = enfants;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public EnfantAdapter.EnfantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.enfant_item,parent,false);
        return new EnfantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EnfantAdapter.EnfantViewHolder holder, int position) {
        Enfant enfant = enfants.get(position);
        holder.setItemContent(enfant);



        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //context.startActivity(new Intent(EnfantActivity.this, MenuActivity.class));
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return enfants.size();
    }

    class EnfantViewHolder extends RecyclerView.ViewHolder{

        TextView txt_prenom,txt_nom, txt_classe, txt_etab;
        RelativeLayout parent_layout;

        public EnfantViewHolder(View itemView) {
            super(itemView);
            txt_prenom = (TextView) itemView.findViewById(R.id.txt_prenom);
            txt_nom = (TextView) itemView.findViewById(R.id.txt_nom);
            txt_classe = (TextView) itemView.findViewById(R.id.txt_classe);
            txt_etab = (TextView) itemView.findViewById(R.id.txt_etab);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }

        void setItemContent(Enfant enfant){

            txt_prenom.setText(enfant.getPrenom_eleve());
            txt_nom.setText(enfant.getNom_eleve());
            txt_classe.setText(enfant.getLibelle_niveau());
            txt_etab.setText(enfant.getLibelle_etablissement());
        }
    }


}
