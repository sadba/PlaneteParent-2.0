package com.lab.sadba.loginparent.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.sadba.loginparent.Model.Evaluation;
import com.lab.sadba.loginparent.R;

import java.util.List;

public class EvalAdapter extends RecyclerView.Adapter<EvalAdapter.EvalViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<Evaluation> eval;

    public EvalAdapter(Context context, List<Evaluation> eval){
        this.context = context;
        this.eval = eval;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public EvalAdapter.EvalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.eval_layout, parent, false);
        return new EvalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EvalAdapter.EvalViewHolder holder, int position) {
        Evaluation eva = eval.get(position);
        holder.setItemContent(eva);
    }

    @Override
    public int getItemCount(){
        return eval.size();
    }

    class EvalViewHolder extends RecyclerView.ViewHolder {
        TextView discipline, periode, type_evaluation, duree_eval;

        public EvalViewHolder(View itemView){
            super(itemView);
            discipline = itemView.findViewById(R.id.discipline);
            periode = itemView.findViewById(R.id.periode);
            type_evaluation = itemView.findViewById(R.id.type_eval);
            duree_eval = itemView.findViewById(R.id.duree_eval);
        }

        void setItemContent(Evaluation eva){
            discipline.setText(eva.getLibelle_discipline());
            periode.setText(eva.getLibelle_periode_eval());
            type_evaluation.setText(eva.getLibelle_type_evaluation());
            duree_eval.setText(eva.getDate_eval());
        }
    }
}
