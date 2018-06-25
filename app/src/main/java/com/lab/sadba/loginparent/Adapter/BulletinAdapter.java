package com.lab.sadba.loginparent.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lab.sadba.loginparent.Model.Bulletin;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Webview;

import java.util.List;

public class BulletinAdapter extends RecyclerView.Adapter<BulletinAdapter.BulletinViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<Bulletin> bulletins;

    public BulletinAdapter(Context context, List<Bulletin>bulletins){
        this.context = context;
        this.bulletins = bulletins;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public BulletinAdapter.BulletinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bulletin_layout,parent,false);
        return new BulletinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BulletinAdapter.BulletinViewHolder holder, int position){
        Bulletin bulletin = bulletins.get(position);
        holder.setItemContent(bulletin);

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Webview.class);
                String id_semestre = bulletin.getId_semestre();
                intent.putExtra("id_semestre", id_semestre);
                context.startActivity(intent);
            }
        });



    }

    private void openPdf() {

    }

    @Override
    public int getItemCount() {
        return bulletins.size();
    }


    class BulletinViewHolder extends RecyclerView.ViewHolder{

        TextView txt_bulletin;
        RecyclerView bulletin_recycler;
        RelativeLayout parent_layout;

        public BulletinViewHolder(View itemView){
            super(itemView);
            txt_bulletin = itemView.findViewById(R.id.txt_bulletin);
            bulletin_recycler = itemView.findViewById(R.id.recycler_bulletin);
            parent_layout = itemView.findViewById(R.id.bulletin_parent);
        }

        void setItemContent(Bulletin bulletin){
            txt_bulletin.setText(bulletin.getLibelle_semestre());
        }

    }


}
