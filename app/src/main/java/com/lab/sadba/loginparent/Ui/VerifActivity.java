package com.lab.sadba.loginparent.Ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lab.sadba.loginparent.Common.Common;
import com.lab.sadba.loginparent.Model.PostVerifUser;
import com.lab.sadba.loginparent.Model.VerifUser;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifActivity extends AppCompatActivity {

    EditText edt_ien, edt_cni;
    Button btn_verif;
    IMyAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif);

        //Init Service
        mService = Common.getAPI();

        edt_ien = findViewById(R.id.edt_ienEnfant);
        edt_cni = findViewById(R.id.edt_cni);
        btn_verif = findViewById(R.id.btn_verif);

        btn_verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ien = edt_ien.getText().toString();
                String cni = edt_cni.getText().toString();

                if (TextUtils.isEmpty(ien)){
                    edt_ien.setError("L'IEN ne doit pas etre vide");
                } else if (TextUtils.isEmpty(cni)){
                    edt_cni.setError("Le CNI ne doit pas etre vide");
                } else {
                    VerifUser(ien, cni);
                }
            }
        });

    }

    private void VerifUser(String ien, String cni) {
        PostVerifUser postVerifUser = new PostVerifUser();
        postVerifUser.setIen(ien);
        postVerifUser.setCni(cni);
        mService.verifUser(postVerifUser)
                .enqueue(new Callback<VerifUser>() {
                    @Override
                    public void onResponse(Call<VerifUser> call, Response<VerifUser> response) {
                        VerifUser result = response.body();
                        if (result.getCode().equals("1")){
                            Toast.makeText(VerifActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("ien_Parent", result.getIen_parent());
                            editor.apply();
                            //Toast.makeText(getApplicationContext(), result.getIen_parent(),Toast.LENGTH_LONG).show();
                            startActivity(new Intent(VerifActivity.this, RegisterActivity.class));
                        }

                    }

                    @Override
                    public void onFailure(Call<VerifUser> call, Throwable t) {
                        Toast.makeText(VerifActivity.this, "Veuillez vérifier votre connection", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
