package com.lab.sadba.loginparent.Ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.sadba.loginparent.Common.Common;
import com.lab.sadba.loginparent.Model.PostRegisterUser;
import com.lab.sadba.loginparent.Model.RegisterUser;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextView txt_sign_in;
    EditText edt_ien, edt_password,edt_password_confirm, edt_tel;
    Button btn_register;
    ProgressDialog progressDoalog;

    IMyAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Init Service
        mService = Common.getAPI();

        //Init View
        txt_sign_in = findViewById(R.id.txt_login);
        edt_ien = findViewById(R.id.edt_ien);
        edt_password = findViewById(R.id.edt_password);
        edt_tel = findViewById(R.id.edt_tel);
        edt_password_confirm = findViewById(R.id.edt_confirm_password);
        btn_register = findViewById(R.id.btn_register);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String value = sharedPreferences.getString("ien_Parent", "");
        //Toast.makeText(getApplicationContext(), value,Toast.LENGTH_LONG).show();
        edt_ien.setText(value);


        //Event
        txt_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ien = edt_ien.getText().toString();
                String code_verif = edt_tel.getText().toString();
                String password = edt_password.getText().toString();
                String confirm_password = edt_password_confirm.getText().toString();
                if (TextUtils.isEmpty(ien)){
                    edt_ien.setError("L'IEN ne doit pas etre vide");
                } else if (TextUtils.isEmpty(code_verif)){
                    edt_tel.setError("Le CNI ne doit pas etre vide");
                } else if (TextUtils.isEmpty(password) && !isPasswordValid(password)){
                    edt_password.setError("Mot de passe invalide");
                } else if (!password.equals(confirm_password)) {
                    edt_password_confirm.setError("Les mots de passe ne sont pas identiques");
                } else {
                    createNewUser(ien,password,code_verif);
                }

            }


        });
    }

    private void createNewUser(String ien, String password, String tel) {
        PostRegisterUser postRegisterUser = new PostRegisterUser();
        postRegisterUser.setIen(ien);
        postRegisterUser.setCode_verif(tel);
        postRegisterUser.setPassword(password);
        progressDoalog = new ProgressDialog(RegisterActivity.this);
        progressDoalog.setMessage("Verification des donnees...");
        progressDoalog.show();
        mService.registerUser(postRegisterUser)
                .enqueue(new Callback<RegisterUser>() {
                    @Override
                    public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {
                        RegisterUser result = response.body();
                        if (result.getCode().equals("1")) {
                            progressDoalog.dismiss();
                            Toast.makeText(RegisterActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            progressDoalog.dismiss();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                            /*gotToHomeActivity();
                            sp.edit().putBoolean("logged", true).apply();*/
                        }

                    }

                    @Override
                    public void onFailure(Call<RegisterUser> call, Throwable t) {
                        progressDoalog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Veuillez vérifier votre connection", Toast.LENGTH_LONG).show();

                    }
                });

    }


    private boolean isPasswordValid(String password) {

        return password.length() >= 6;
    }
}