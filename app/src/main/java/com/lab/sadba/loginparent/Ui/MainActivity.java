package com.lab.sadba.loginparent.Ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.sadba.loginparent.Common.Common;
import com.lab.sadba.loginparent.Model.PostUser;
import com.lab.sadba.loginparent.Model.User;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView txt_register, txt_verif;
    EditText edt_ien, edt_password;
    Button btn_login;
    SharedPreferences sp;
    SharedPreferences sp1;
    IMyAPI mService;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init Service
        mService = Common.getAPI();

        Realm.init(this);

        //Init View
        txt_verif = findViewById(R.id.txt_verif);
        edt_ien = findViewById(R.id.edt_ien);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);



        sp = getSharedPreferences("btn_login", MODE_PRIVATE);
        sp1 = getSharedPreferences("ien_parent", MODE_PRIVATE);

        if (sp.getBoolean("logged", false)){
            gotToHomeActivity();
        }

        //Event
        txt_verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VerifActivity.class));
            }
        });

       // public void connexion(View view)

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ien = edt_ien.getText().toString();
                String password = edt_password.getText().toString();
                if (TextUtils.isEmpty(password)){
                    edt_password.setError("Mot de passe ne pas etre vide");
                } else if (TextUtils.isEmpty(ien)){
                    edt_ien.setError("Ien ne doit pas etre vide");
                } else {
                    authenticateUser(ien, password);
                }

            }
        });
    }

    private void gotToHomeActivity() {
        Intent i = new Intent(this, EnfantActivity.class);
        startActivity(i);
    }



    private void authenticateUser(String ien, String password) {
        PostUser postUser = new PostUser();
        postUser.setPassword(password);
        postUser.setIen(ien);
        mService.loginUser(postUser)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        User result = response.body();
                        if (result.getCode().equals("1")) {
                            Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            //startActivity(new Intent(MainActivity.this, EnfantActivity.class));

                            gotToHomeActivity();
                            sp.edit().putBoolean("logged", true).apply();
                            //SharedPreferences.Editor editor = sp.edit();
                            //editor.remove("logged");
                           // editor.commit();
                            //finish();
                            //sp1.edit().putString("ien_parent", "").apply();
                            realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            realm.copyToRealm(response.body());
                            realm.commitTransaction();

                        }


                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
