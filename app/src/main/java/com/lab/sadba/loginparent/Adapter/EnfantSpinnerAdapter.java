package com.lab.sadba.loginparent.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.R;

import java.util.List;

public class EnfantSpinnerAdapter extends ArrayAdapter<String> {

    private final List<Enfant> enfants;
    private final Context context;
    private LayoutInflater inflater;
    private int mResource;

    public EnfantSpinnerAdapter(Context context, int mResource, List enfants) {
        super(context, mResource, 0, enfants);
        this.enfants = enfants;
        this.context = context;
        this.mResource = mResource;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }




    @SuppressLint("SetTextI18n")
    private View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.enfants_item, parent, false);
        Enfant enfant = enfants.get(position);

        TextView label = row.findViewById(R.id.enf);


        label.setText(enfant.getPrenom_eleve() + " " + enfant.getNom_eleve() + "  ");


        return row;
    }
}
